package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Controlador para la VentanaMaestro.
 * Gestiona el panel principal para usuarios con rol de Maestro, mostrando estadísticas,
 * contadores de citas y permitiendo el acceso total a todas las funciones del sistema.
 */
public class ControladorMaestro {

    private final VentanaMaestro vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Empleado empleado;

    /**
     * Datos en memoria para las listas embebidas
     */
    private ArrayList<Cita> todasCitas;
    private ArrayList<Cita> citasFiltradas;
    private ArrayList<Cita> citasMias;
    private ArrayList<Cliente> todosClientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Taller> todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Empleado> empleadosFiltrados;
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Traje> trajesFiltrados;
    private boolean editable = true;

    /**
     * Constructor del controlador Maestro.
     * Inicializa la interfaz con los datos del usuario logado y configura los eventos de menú.
     * 
     * @param vista Ventana principal del maestro.
     * @param acceso Objeto de acceso a la base de datos.
     * @param c Conexión activa a la base de datos.
     * @param empleado Instancia del empleado maestro que ha iniciado sesión.
     */
    public ControladorMaestro(VentanaMaestro vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")");
        cargarDatosEnMemoria();
        cargarContadores();

        /**
         * Acceso completo al menú
         */
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemNuevaCita().addActionListener(e -> abrirNuevaCita());
        vista.getMenuItemListaClientes().addActionListener(e -> abrirListaClientes());
        vista.getMenuItemNuevoCliente().addActionListener(e -> abrirNuevoCliente());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());
        vista.getMenuItemNuevoTaller().addActionListener(e -> abrirNuevoTaller());
        vista.getMenuItemListaEmpleados().addActionListener(e -> abrirListaEmpleados());
        vista.getMenuItemNuevoEmpleado().addActionListener(e -> abrirNuevoEmpleado());

        /**
         *  Salir
         */
        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cerrarSesion();
            }
        });
    }

    /**
     * Recupera todos los datos necesarios de la base de datos (citas, talleres, clientes, etc.)
     * y los almacena en listas locales para agilizar el procesamiento de estadísticas.
     */
    private void cargarDatosEnMemoria() {
        try {
            todasCitas = acceso.recogeCitas(c);
            todosTalleres = acceso.recogeTalleres(c);
            todosClientes = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes = acceso.recogeTrajes(c);
            citasFiltradas = new ArrayList<>(todasCitas);
            citasMias = new ArrayList<>();
            clientesFiltrados = new ArrayList<>(todosClientes);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Calcula y muestra los contadores estadísticos en la vista principal:
     * total de clientes, talleres, citas globales, citas propias de hoy, 
     * citas de la semana y la fecha de la próxima cita programada.
     */
    private void cargarContadores() {
        try {
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));
            vista.getLblTotalClientes().setText(String.valueOf(todosClientes.size()));

            long misCitas = todasCitas.stream().filter(ci -> ci.getId_empleado() == empleado.getId_empleado()).count();
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(misCitas));

            for (Cita proximaCita : todasCitas) {
                if (proximaCita.getId_empleado() == empleado.getId_empleado()) {
                    citasMias.add(proximaCita);
                    java.sql.Date hoy = java.sql.Date.valueOf(java.time.LocalDate.now());
                    long citasHoy = citasMias.stream().filter(ci -> ci.getFecha().toString().equals(hoy.toString())).count();
                    vista.getLblCitasHoy().setText(String.valueOf(citasHoy));

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                    Date ini = cal.getTime(); cal.add(Calendar.DAY_OF_WEEK, 6); Date fin = cal.getTime();
                    long semana = citasMias.stream().filter(ci -> !ci.getFecha().before(ini) && !ci.getFecha().after(fin)).count();
                    vista.getLblCitasSemana().setText(String.valueOf(semana));
                    
                    
                    String proxima = citasMias.stream()
                          .filter(ci -> !ci.getFecha().before(hoy))
                          .map(ci -> ci.getFecha() + " " + ci.getHora_inicio())
                          .min(String::compareTo).orElse("—");
                    vista.getLblProximaCita().setText(proxima);
                }
            }    
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

    /**
     * Abre la ventana de gestión de citas y cierra la ventana actual.
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
     * Abre el formulario para la creación de una nueva cita.
     */
    private void abrirNuevaCita() {
        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm,  acceso, null, vista, null, c, empleado, null, null, null, null, null, null, null);
        vistaForm.setVisible(true);
    }

    /**
     * Abre la ventana de gestión de clientes y cierra la ventana actual.
     */
    private void abrirListaClientes() {
        try {
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            ListaClientes vistaLista = new ListaClientes();
            new ControladorListaClientes(vistaLista, acceso, c, clientes, trajes, empleado);
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre el formulario para registrar un nuevo cliente.
     */
    private void abrirNuevoCliente() {
        try {
            NuevoCliente vistaForm = new NuevoCliente();
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            new ControladorNuevoCliente(vistaForm, null, vista, null, acceso, c, null, clientes, empleado);
            vistaForm.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre la ventana de gestión de talleres y cierra la ventana actual.
     */
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
        vistaLista.setVisible(true);
        vista.dispose();
    }

    /**
     * Abre el formulario para registrar un nuevo taller.
     */
    private void abrirNuevoTaller() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        NuevoTaller vistaForm = new NuevoTaller();
        new ControladorNuevoTaller(vistaForm, null, vista, acceso, c, null, talleres, empleado);
        vistaForm.setVisible(true);
    }
    
    /**
     * Abre la ventana de gestión de empleados y cierra la ventana actual.
     */
    private void abrirListaEmpleados() {
    	try {
    		ArrayList<Empleado> empleados = acceso.recogeEmpleados(c);
        	ListaEmpleados vistaLista = new ListaEmpleados();
        	new ControladorListaEmpleados(vistaLista, acceso, c, empleados, empleado);
        	vistaLista.setVisible(true);
        	vista.dispose();
    	} catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    }
    
    /**
     * Abre el formulario para registrar un nuevo empleado.
     */
    private void abrirNuevoEmpleado() {
    	try {
    		NuevoEmpleado vistaForm = new NuevoEmpleado();
    		ArrayList<Empleado> empleados = acceso.recogeEmpleados(c);
    		new ControladorNuevoEmpleado(vistaForm, null, vista, null, acceso, c, empleado, empleados, empleado);
    		vistaForm.setVisible(true);
    	} catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    }

    /**
     * Cierra la conexión actual y la ventana principal, redirigiendo al usuario
     * a la pantalla de Inicio de Sesión.
     */
    private void cerrarSesion() {
        acceso.cerrarConexion(c);
        vista.dispose();
        try {
            Connection nc = acceso.abrirConexion();
            ArrayList<Empleado> empleados = acceso.recogeEmpleados(nc);
            acceso.cerrarConexion(nc);
            InicioSesion is = new InicioSesion();
            new ControladorInicioSesion(is, acceso, empleados);
            is.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}