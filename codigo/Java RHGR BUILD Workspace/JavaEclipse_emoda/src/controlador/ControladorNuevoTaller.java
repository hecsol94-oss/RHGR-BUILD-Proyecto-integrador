package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.AccesoBBDD;
import modelo.Empleado;
import modelo.Taller;
import vista.DetalleClientes;
import vista.ListaTalleres;
import vista.NuevaCita;
import vista.NuevoTaller;
import vista.VentanaMaestro;

/**
 * Controlador encargado de gestionar la creación y edición de talleres.
 * Administra la comunicación entre la vista de formulario de taller y la base de datos,
 * manejando el flujo de navegación según la ventana de origen.
 */
public class ControladorNuevoTaller {

	/**
	 * Referencias a vistas, modelo y datos
	 */
	private NuevoTaller vista;
	private ListaTalleres ventanaTaller;
	private VentanaMaestro ventanaMaestro;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Taller> talleres;
	private Taller tallerEditar;
	private Empleado emp;

	/**
	 * Constructor de la clase ControladorNuevoTaller.
	 * Enlaza la vista con el modelo, inicializa los datos de contexto y asigna los eventos a los botones.
	 * 
	 * @param vista Ventana del formulario de taller.
	 * @param ventanaTaller Referencia a la lista de talleres (si la operación viene de allí).
	 * @param ventanaMaestro Referencia a la ventana del maestro (si la operación viene de allí).
	 * @param acceso Objeto para gestionar el acceso a la base de datos.
	 * @param conexion Conexión activa a la base de datos SQL.
	 * @param tallerEditar Objeto Taller a modificar, o null si se está creando uno nuevo.
	 * @param talleres Lista actual de talleres cargada en memoria.
	 * @param emp Empleado que está realizando la operación.
	 */
	public ControladorNuevoTaller(NuevoTaller vista, ListaTalleres ventanaTaller, VentanaMaestro ventanaMaestro, AccesoBBDD acceso, Connection conexion, Taller tallerEditar, ArrayList<Taller> talleres, Empleado emp) {
		this.vista = vista;
		this.ventanaTaller = ventanaTaller;
		this.ventanaMaestro = ventanaMaestro;
		this.acceso = acceso;
		this.c = conexion;
		this.tallerEditar = tallerEditar;
		this.talleres = talleres;
		this.emp = emp;

		/**
		 * Eventos de botones
		 */
		this.vista.getBtnGuardar().addActionListener(e -> pulsarBtnGuardar());
		this.vista.getBtnCancelar().addActionListener(e -> pulsarBtnCancelar());
	}
	
	/**
	 * Gestiona la acción de guardar los datos del taller.
	 * Dependiendo de si se está editando o creando, valida los campos e inserta o 
	 * actualiza la información en la base de datos, redirigiendo a la vista correspondiente.
	 */
	private void pulsarBtnGuardar() {

		String nombreIntroducido = vista.getNombreSala();
		String tipoIntroducido = vista.getTipoSala();
		
		/** ===== CASO: CREAR NUEVO TALLER ===== */
		if (tallerEditar == null) {
			
			if (!nombreIntroducido.isEmpty()) {
				
				/** Desde ListaTalleres */
				if (ventanaMaestro == null && ventanaTaller != null) {

					Taller nuevoTaller = new Taller((talleres.size() + 1), nombreIntroducido, tipoIntroducido);

					acceso.insertarNuevoTaller(c, nuevoTaller);

					talleres = acceso.recogeTalleres(c);

					ListaTalleres lt = new ListaTalleres();
					lt.recogerDatos(talleres);

					JOptionPane.showMessageDialog(vista, "Taller creado correctamente.");

					new ControladorListaTalleres(lt, acceso, c, talleres, emp);
					lt.setVisible(true);

					vista.dispose();
					ventanaTaller.dispose();
				}

				/** Desde VentanaMaestro */
				else if (ventanaMaestro != null && ventanaTaller == null) {

					Taller nuevoTaller = new Taller((talleres.size() + 1), nombreIntroducido, tipoIntroducido);

					acceso.insertarNuevoTaller(c, nuevoTaller);

					talleres = acceso.recogeTalleres(c);

					ListaTalleres lt = new ListaTalleres();
					lt.recogerDatos(talleres);

					JOptionPane.showMessageDialog(vista, "Taller creado correctamente.");

					vista.dispose();
				}

			} else {
				JOptionPane.showConfirmDialog(vista, 
						"por favor, rellene los campos necesarios para crear un nuevo taller", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		/** ===== CASO: EDITAR TALLER ===== */
		else {
			
			if (!nombreIntroducido.isEmpty()) {

				Taller nuevoTaller = new Taller((talleres.size() + 1), nombreIntroducido, tipoIntroducido);

				acceso.ActualizarTaller(c, tallerEditar.getId_sala(), nuevoTaller);

				talleres = acceso.recogeTalleres(c);

				ListaTalleres lt = new ListaTalleres();
				lt.recogerDatos(talleres);

				JOptionPane.showMessageDialog(vista, "Taller editado correctamente.");

				new ControladorListaTalleres(lt, acceso, c, talleres, emp);
				lt.setVisible(true);

				vista.dispose();
				ventanaTaller.dispose();

			} else {
				JOptionPane.showConfirmDialog(vista, 
						"por favor, rellene los campos necesarios para crear un nuevo taller", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}		
	}

	/**
	 * Cancela la operación actual y cierra la ventana del formulario.
	 * No realiza cambios en la base de datos ni actualiza las listas.
	 */
	private void pulsarBtnCancelar() {

		if (ventanaMaestro == null && ventanaTaller != null) {
			vista.dispose();
		}
		if (ventanaMaestro != null && ventanaTaller == null) {
			vista.dispose();
		}
	}
}