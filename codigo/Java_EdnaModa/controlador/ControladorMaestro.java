package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import modelo.Traje;
import vista.VentanaMaestro;
import vista.InicioSesion;
import vista.ListaCitas;
import vista.ListaClientes;
import vista.ListaTalleres;
import vista.NuevaCita1;
import vista.NuevoCliente;
import vista.NuevoTaller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de la ventana principal del MAESTRO (administrador).
 * Acceso total: Citas, Clientes y Talleres sin restricciones.
 */
public class ControladorMaestro {

    private VentanaMaestro vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Empleado empleado;

    public ControladorMaestro(VentanaMaestro vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo());

        cargarContadores();

        // Acceso completo al menú
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemNuevaCita().addActionListener(e -> abrirNuevaCita());
        vista.getMenuItemListaClientes().addActionListener(e -> abrirListaClientes());
        vista.getMenuItemNuevoCliente().addActionListener(e -> abrirNuevoCliente());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());
        vista.getMenuItemNuevoTaller().addActionListener(e -> abrirNuevoTaller());

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

    
    private void abrirListaCitas() {
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            ListaCitas vistaLista = new ListaCitas();
            new ControladorListaCitas(vistaLista, acceso, c, citas, empleado, true);
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    private void abrirNuevaCita() {
        NuevaCita1 vistaForm = new NuevaCita1();
        new ControladorNuevaCita1(vistaForm, acceso, c, empleado);
        vistaForm.setVisible(true);
        vista.dispose();
    }

   
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

   
    private void abrirNuevoCliente() {
    	try {
    		NuevoCliente vistaForm = new NuevoCliente();
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            new ControladorNuevoCliente(vistaForm, acceso, c, null, clientes, empleado);
            vistaForm.setVisible(true);
            vista.dispose();
    	} catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Lista de talleres — acceso completo (botones habilitados por defecto)
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
        vistaLista.setVisible(true);
        vista.dispose();
    }

    // Nuevo taller — acceso completo
    private void abrirNuevoTaller() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        NuevoTaller vistaForm = new NuevoTaller();
        new ControladorNuevoTaller(vistaForm, acceso, c, null, talleres, empleado);
        vistaForm.setVisible(true);
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}