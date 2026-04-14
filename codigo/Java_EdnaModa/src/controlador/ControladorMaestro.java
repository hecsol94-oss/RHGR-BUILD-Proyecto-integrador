package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import vista.VentanaMaestro;
import vista.InicioSesion;
import vista.ListaCitas;
import vista.ListaClientes;
import vista.ListaTalleres;
import vista.NuevaCitaMaestro;
import vista.NuevoCliente;
import vista.NuevoTaller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorMaestro {

	private VentanaMaestro vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Empleado empleado;

    // Constructor: inicializa la vista y asigna todos los listeners del menú
    public ControladorMaestro(VentanaMaestro vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        // Muestra el nombre del empleado en la barra superior
        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo());

        // Carga los contadores del dashboard
        cargarContadores();

        // Listeners del menú — acceso completo
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

    // Carga los contadores del dashboard
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

    // Abre la lista de todas las citas con edición habilitada
    private void abrirListaCitas() {
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            ListaCitas vistaLista = new ListaCitas();
            new ControladorListaCitas(vistaLista, acceso, c, citas, true);
            vistaLista.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Abre el formulario completo para crear una nueva cita (versión Maestro)
    private void abrirNuevaCita() {
        try {
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Taller> talleres = acceso.recogeTalleres(c);
            NuevaCitaMaestro vistaForm = new NuevaCitaMaestro();
            new ControladorNuevaCitaMaestro(vistaForm, acceso, c, clientes, talleres);
            vistaForm.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Abre la lista de clientes con edición habilitada
    private void abrirListaClientes() {
        try {
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ListaClientes vistaLista = new ListaClientes();
            new ControladorListaClientes(vistaLista, acceso, c, clientes, true);
            vistaLista.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Abre el formulario para crear un nuevo cliente
    private void abrirNuevoCliente() {
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, acceso, c, null);
        vistaForm.setVisible(true);
    }

    // Abre la lista de talleres con edición habilitada
    private void abrirListaTalleres() {
        try {
            ArrayList<Taller> talleres = acceso.recogeTalleres(c);
            ListaTalleres vistaLista = new ListaTalleres();
            new ControladorListaTalleres(vistaLista, acceso, c, talleres, true);
            vistaLista.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Abre el formulario para crear un nuevo taller
    private void abrirNuevoTaller() {
        NuevoTaller vistaForm = new NuevoTaller();
        new ControladorNuevoTaller(vistaForm, acceso, c, null);
        vistaForm.setVisible(true);
    }

    // Cierra la sesión y vuelve al inicio de sesión
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
