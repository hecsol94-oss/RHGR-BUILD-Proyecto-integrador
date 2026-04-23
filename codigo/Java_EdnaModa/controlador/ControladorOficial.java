package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import modelo.Traje;
import vista.VentanaOficial;
import vista.InicioSesion;
import vista.ListaCitas;
import vista.ListaClientes;
import vista.ListaTalleres;
import vista.NuevaCita2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de la ventana principal del OFICIAL.
 * Permisos:
 *   Citas    → ver lista + crear/gestionar citas propias (formulario Oficial)
 *   Clientes → solo lectura (sin crear, editar ni borrar)
 *   Talleres → solo lectura (sin crear ni editar)
 */
public class ControladorOficial {

    private VentanaOficial vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Empleado empleado;

    public ControladorOficial(VentanaOficial vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo());

        cargarContadores();

        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemNuevaCita().addActionListener(e -> abrirNuevaCita());
        vista.getMenuItemListaClientes().addActionListener(e -> abrirListaClientes());
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
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(todasCitas.size()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lista de citas — el Oficial puede ver todas pero no tiene acceso admin.
     */
    private void abrirListaCitas() {
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            ListaCitas vistaLista = new ListaCitas();
            new ControladorListaCitas(vistaLista, acceso, c, citas, empleado, false);
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Formulario para que el Oficial complete/gestione su cita asignada.
     */
    private void abrirNuevaCita() {
        NuevaCita2 vistaForm = new NuevaCita2();
//        new ControladorNuevaCitaOficial(vistaForm, empleado);
        vistaForm.setVisible(true);
        vista.dispose();
    }

    /**
     * Lista de clientes en modo solo lectura.
     */
    private void abrirListaClientes() {
        try {
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            ListaClientes vistaLista = new ListaClientes();
            new ControladorListaClientes(vistaLista, acceso, c, clientes, trajes, empleado, false);
            vistaLista.deshabilitarBotones(); // solo lectura para Oficial
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lista de talleres en modo solo lectura.
     */
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
        vistaLista.deshabilitarBotones(); // solo lectura para Oficial
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