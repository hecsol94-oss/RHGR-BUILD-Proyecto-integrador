package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import vista.VentanaOficial;
import vista.InicioSesion;
import vista.ListaCitas;
import vista.ListaClientes;
import vista.ListaTalleres;
import vista.NuevaCitaOficial;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

//Controlador de la ventana principal del Oficial
//El oficial puede ver todas las citas, crear/modificar las suyas, ver clientes y ver talleres
public class ControladorOficial {

	private VentanaOficial vista;
	private AccesoBBDD acceso;
	private Connection c;
	private Empleado empleado;

	// Constructor: inicializa la vista y asigna los listeners del menú
	public ControladorOficial(VentanaOficial vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
		this.vista = vista;
		this.acceso = acceso;
		this.c = c;
		this.empleado = empleado;

		// Muestra el nombre del empleado en la barra superior
		vista.getLblUsuario().setText("Usuario: " + empleado.getApodo());

		// Carga los contadores del dashboard
		cargarContadores();

		// Listeners del menú
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

	// Carga los contadores del dashboard
	private void cargarContadores() {
		try {
			ArrayList<Cita> todasCitas = acceso.recogeCitas(c);
			ArrayList<Taller> talleres = acceso.recogeTalleres(c);

			vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
			vista.getLblNumeroDeTalleres().setText(String.valueOf(talleres.size()));
			// Las "mis citas" del oficial son todas las citas (sin tabla de asignación
			// individual)
			vista.getLblNumeroDeMisCitas().setText(String.valueOf(todasCitas.size()));

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// Abre la lista de todas las citas con posibilidad de editar
	private void abrirListaCitas() {
		try {
			ArrayList<Cita> citas = acceso.recogeCitas(c);
			ListaCitas vistaLista = new ListaCitas();
			new ControladorListaCitas();
			vistaLista.setVisible(true);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// Abre el formulario para que el oficial complete la cita asignada por el
	// maestro
	private void abrirNuevaCita() {
		NuevaCitaOficial vistaForm = new NuevaCitaOficial();
		new ControladorNuevaCitaOficial();
		vistaForm.setVisible(true);
	}

	// Abre la lista de clientes en modo solo lectura
	private void abrirListaClientes() {
		try {
			ArrayList<Cliente> clientes = acceso.recogeClientes(c);
			ListaClientes vistaLista = new ListaClientes();
			new ControladorListaClientes();
			vistaLista.setVisible(true);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// Abre la lista de talleres en modo solo lectura
	private void abrirListaTalleres() {

		ArrayList<Taller> talleres = acceso.recogeTalleres(c);
		ListaTalleres vistaLista = new ListaTalleres();
		new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
		vistaLista.deshabilitarBotones();
		vistaLista.setVisible(true);

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
