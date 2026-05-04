package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador principal para la interfaz del rol Aprendiz.
 * Gestiona el panel de mando (dashboard), calcula estadísticas de citas en tiempo real
 * y coordina el acceso restringido (solo lectura) a las listas de citas y talleres.
 */
public class ControladorAprendiz {

    private final VentanaAprendiz vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Empleado empleado;

    // Colecciones de datos en memoria para optimizar la visualización del dashboard
    private ArrayList<Cita> todasCitas;
    private ArrayList<Cita> citasFiltradas;
    private ArrayList<Taller> todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Cliente> todosClientes;

    /**
     * Constructor del controlador para el rol Aprendiz.
     * Configura la información del usuario en la vista, inicializa los datos de la base de datos
     * y establece los escuchadores para la navegación y el cierre de sesión.
     * 
     * @param vista    Ventana principal del aprendiz.
     * @param acceso   Clase de acceso a datos.
     * @param c        Conexión JDBC activa.
     * @param empleado Entidad del empleado logueado con rol de aprendiz.
     */
    public ControladorAprendiz(VentanaAprendiz vista, AccesoBBDD acceso, Connection c, Empleado empleado) {

        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        // Personalización de la cabecera con datos del usuario
        vista.getLblUsuario().setText(
                "Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")"
        );

        cargarDatosEnMemoria();
        cargarContadores();

        // Configuración de la navegación por menús
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());

        // Configuración del botón de salida (Logout)
        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cerrarSesion();
            }
        });
    }

    /**
     * Recupera todas las entidades necesarias de la base de datos para evitar consultas
     * redundantes durante el cálculo de estadísticas del dashboard.
     */
    private void cargarDatosEnMemoria() {
        try {
            todasCitas = acceso.recogeCitas(c);
            todosTalleres = acceso.recogeTalleres(c);
            todosClientes = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes = acceso.recogeTrajes(c);
            citasFiltradas = new ArrayList<>(todasCitas);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Calcula y muestra las métricas del aprendiz en la vista:
     * - Total de citas en el sistema.
     * - Número de talleres disponibles.
     * - Citas específicas asignadas al aprendiz logueado.
     * - Citas programadas para la fecha actual.
     * - Fecha y hora de la próxima cita más cercana.
     */
    private void cargarContadores() {
        try {
            // Contadores generales
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));

            // Cálculo de citas personales (Relación N:M filtrada por ID de empleado)
            ArrayList<Cita_Aprendiz> rel = acceso.recogeCitasAprendiz(c);
            long misCitas = rel.stream()
                    .filter(ca -> ca.getId_empleado() == empleado.getId_empleado())
                    .count();
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(misCitas));

            // Cálculo de citas para el día de hoy
            java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
            long citasHoy = todasCitas.stream()
                    .filter(ci -> ci.getFecha().toString().equals(hoy.toString()))
                    .count();
            vista.getLblCitasHoy().setText(String.valueOf(citasHoy));

            // Identificación de la próxima cita (mínima fecha >= hoy)
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
     * Abre la ventana de lista de citas. 
     * Como el rol es Aprendiz, se invocan métodos de deshabilitación en la vista 
     * para asegurar que la información sea de solo lectura.
     */
    private void abrirListaCitas() {
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            ArrayList<Cita_Aprendiz> aprendices = acceso.recogeCitasAprendiz(c);

            ListaCitas vistaLista = new ListaCitas();
            
            // Se bloquean las funciones de edición/eliminación para el aprendiz
            vistaLista.deshabilitarBotones();
            new ControladorListaCitas(vistaLista, acceso, c, citas, aprendices, empleado);
            
            vistaLista.setVisible(true);
            vista.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre la ventana de lista de talleres en modo consulta.
     * Deshabilita los botones de gestión de talleres para cumplir con las 
     * restricciones del rol Aprendiz.
     */
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();

        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);

        // Bloqueo de seguridad para el rol correspondiente
        vistaLista.deshabilitarBotones();

        vistaLista.setVisible(true);
        vista.dispose();
    }

    /**
     * Finaliza la sesión del aprendiz, cierra la conexión actual a la base de datos
     * y redirige al usuario a la pantalla de inicio de sesión.
     */
    private void cerrarSesion() {
        acceso.cerrarConexion(c);
        vista.dispose();

        try {
            // Se abre una conexión temporal para recargar la lista de empleados en el login
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