package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Controlador para la ventana principal del perfil "Oficial".
 * Gestiona la lógica del dashboard, la carga de datos en memoria y la navegación
 * hacia los diferentes módulos de la aplicación.
 */
public class ControladorOficial {

    private final VentanaOficial vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Empleado empleado;
    
    // Listas en memoria para evitar llamadas constantes a BD
    private ArrayList<Cita> todasCitas;
    private ArrayList<Cita> citasFiltradas;
    private ArrayList<Cliente> todosClientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Taller> todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Traje> listaTrajes;

    /**
     * Constructor del controlador. Inicializa la interfaz, vincula los eventos de los menús
     * y carga la información inicial en el dashboard.
     * 
     * @param vista    Ventana principal del oficial.
     * @param acceso   Objeto de acceso a la base de datos (DAO).
     * @param c        Conexión activa a la base de datos.
     * @param empleado Objeto del empleado que ha iniciado sesión.
     */
    public ControladorOficial(VentanaOficial vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        // Mostrar usuario en la interfaz
        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")");

        // Cargar datos iniciales y contadores del dashboard
        cargarDatosEnMemoria();
        cargarContadores();
        
        // Navegación del menú
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemNuevaCita().addActionListener(e -> abrirNuevaCita());
        vista.getMenuItemListaClientes().addActionListener(e -> abrirListaClientes());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());

        // Evento de cerrar sesión desde label
        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cerrarSesion();
            }
        });
    }

    /**
     * Recupera todas las entidades necesarias desde la base de datos y las almacena
     * en las listas locales de la clase para optimizar el acceso a la información.
     */
    private void cargarDatosEnMemoria() {
        try {
            todasCitas = acceso.recogeCitas(c);
            todosTalleres = acceso.recogeTalleres(c);
            todosClientes = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes    = acceso.recogeTrajes(c);

            // Copias para filtrado en interfaz
            citasFiltradas    = new ArrayList<>(todasCitas);
            clientesFiltrados = new ArrayList<>(todosClientes);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Calcula estadísticas en tiempo real (citas de hoy, de la semana, citas propias)
     * a partir de los datos en memoria y actualiza las etiquetas de la vista.
     */
    private void cargarContadores() {
        try {
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));

            // Citas asignadas al oficial actual
            long misCitas = todasCitas.stream()
                    .filter(ci -> ci.getId_empleado() == empleado.getId_empleado())
                    .count();
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(misCitas));

            // Citas del día actual
            java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
            long citasHoy = todasCitas.stream()
                    .filter(ci -> ci.getFecha().toString().equals(hoy.toString()))
                    .count();
            vista.getLblCitasHoy().setText(String.valueOf(citasHoy));

            // Citas de la semana actual
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            Date ini = cal.getTime();
            cal.add(Calendar.DAY_OF_WEEK, 6);
            Date fin = cal.getTime();

            long semana = todasCitas.stream()
                    .filter(ci -> !ci.getFecha().before(ini) && !ci.getFecha().after(fin))
                    .count();

            vista.getLblCitasSemana().setText(String.valueOf(semana));

            // Próxima cita
            String proxima = todasCitas.stream()
                    .filter(ci -> !ci.getFecha().before(hoy))
                    .map(ci -> ci.getFecha() + " " + ci.getHora_inicio())
                    .min(String::compareTo)
                    .orElse("—");

            vista.getLblProximaCita().setText(proxima);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Instancia el controlador de la lista de citas, abre su ventana correspondiente
     * y cierra la ventana actual del dashboard.
     */
    private void abrirListaCitas() {
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            ArrayList<Cita_Aprendiz> aprendices = acceso.recogeCitasAprendiz(c);

            ListaCitas vistaLista = new ListaCitas();
            new ControladorListaCitas(vistaLista, acceso, c, citas, aprendices, empleado);

            vistaLista.setVisible(true);
            vista.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre el formulario de creación/gestión de citas para el Oficial.
     */
    private void abrirNuevaCita() {
        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm, acceso, null, null, vista, c, empleado,
                null, null, null, null, null, null, null);

        vistaForm.setVisible(true);
    }

    /**
     * Abre la lista de clientes. Se deshabilitan los botones de edición
     * para asegurar que el Oficial solo tenga acceso de lectura.
     */
    private void abrirListaClientes() {
        try {
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);

            ListaClientes vistaLista = new ListaClientes();
            new ControladorListaClientes(vistaLista, acceso, c, clientes, trajes, empleado);

            vistaLista.deshabilitarBotones(); // modo solo lectura
            vistaLista.setVisible(true);
            vista.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre la lista de talleres registrados. Al igual que con clientes,
     * se configura en modo de solo lectura para este perfil.
     */
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);

        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);

        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);

        vistaLista.deshabilitarBotones(); // modo solo lectura
        vistaLista.setVisible(true);
        vista.dispose();
    }

    /**
     * Cierra la conexión actual con la base de datos, destruye la ventana principal
     * y redirige al usuario a la pantalla de inicio de sesión.
     */
    private void cerrarSesion() {
        acceso.cerrarConexion(c);
        vista.dispose();

        try {
            Connection nc = acceso.abrirConexion();
            ArrayList<Empleado> emps = acceso.recogeEmpleados(nc);
            acceso.cerrarConexion(nc);

            InicioSesion is = new InicioSesion();
            new ControladorInicioSesion(is, acceso, emps);
            is.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}