package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Empleado;
import modelo.Taller;
import vista.VentanaAprendiz;
import vista.InicioSesion;
import vista.ListaCitas;
import vista.ListaTalleres;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de la ventana principal del APRENDIZ.
 * Permisos: ver lista de citas (solo lectura) y ver lista de talleres (solo lectura).
 * Sin acceso a Clientes, sin formularios de creación ni edición.
 */
public class ControladorAprendiz {

    private VentanaAprendiz vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Empleado empleado;

    public ControladorAprendiz(VentanaAprendiz vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo());

        cargarContadores();

        // Solo estos dos listeners existen para el Aprendiz
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());

        // Logout
        vista.getLblSalir().setText("Salir");
        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cerrarSesion();
            }
        });
    }

    private void cargarContadores() {
        try {
            ArrayList<Cita> todasCitas = acceso.recogeCitas(c);
            ArrayList<Taller> talleres = acceso.recogeTalleres(c);

            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(talleres.size()));
            // El Aprendiz no tiene citas propias; se muestra el total como referencia
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(todasCitas.size()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre la lista de citas en modo solo lectura.
     */
    private void abrirListaCitas() {
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            ListaCitas vistaLista = new ListaCitas();
            // CORRECCIÓN: pasar argumentos reales; el controlador recibe los datos
            new ControladorListaCitas(vistaLista, acceso, c, citas, empleado, false);
            vistaLista.deshabilitarBotones(); // solo lectura para Aprendiz
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre la lista de talleres en modo solo lectura.
     */
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
        vistaLista.deshabilitarBotones();
        vistaLista.setVisible(true);
        vista.dispose();
    }

    private void cerrarSesion() {
        acceso.cerrarConexion(c);
        vista.dispose();

        try {
            Connection nuevaConexion = acceso.abrirConexion();
            ArrayList<Empleado> empleados = acceso.recogeEmpleados(nuevaConexion);
            acceso.cerrarConexion(nuevaConexion);

            InicioSesion inicioSesion = new InicioSesion();
            new ControladorInicioSesion(inicioSesion, acceso, empleados);
            inicioSesion.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}