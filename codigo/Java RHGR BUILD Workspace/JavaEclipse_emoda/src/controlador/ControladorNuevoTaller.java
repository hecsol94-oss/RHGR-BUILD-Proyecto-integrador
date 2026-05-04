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

// Controlador encargado de gestionar la creación y edición de talleres
public class ControladorNuevoTaller {

	// Referencias a vistas, modelo y datos
	private NuevoTaller vista;
	private ListaTalleres ventanaTaller;
	private VentanaMaestro ventanaMaestro;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Taller> talleres;
	private Taller tallerEditar;
	private Empleado emp;

	// Constructor: enlaza vista, modelo y asigna eventos a botones
	public ControladorNuevoTaller(NuevoTaller vista, ListaTalleres ventanaTaller, VentanaMaestro ventanaMaestro, AccesoBBDD acceso, Connection conexion, Taller tallerEditar, ArrayList<Taller> talleres, Empleado emp) {
		this.vista = vista;
		this.ventanaTaller = ventanaTaller;
		this.ventanaMaestro = ventanaMaestro;
		this.acceso = acceso;
		this.c = conexion;
		this.tallerEditar = tallerEditar;
		this.talleres = talleres;
		this.emp = emp;

		// Eventos de botones
		this.vista.getBtnGuardar().addActionListener(e -> pulsarBtnGuardar());
		this.vista.getBtnCancelar().addActionListener(e -> pulsarBtnCancelar());
	}
	
	// Guarda o actualiza un taller según el contexto
	private void pulsarBtnGuardar() {

		String nombreIntroducido = vista.getNombreSala();
		String tipoIntroducido = vista.getTipoSala();
		
		// ===== CASO: CREAR NUEVO TALLER =====
		if (tallerEditar == null) {
			
			if (!nombreIntroducido.isEmpty()) {
				
				// Desde ListaTalleres
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

				// Desde VentanaMaestro
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

		// ===== CASO: EDITAR TALLER =====
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

	// Cancela la operación y cierra la ventana actual
	private void pulsarBtnCancelar() {

		if (ventanaMaestro == null && ventanaTaller != null) {
			vista.dispose();
		}
		if (ventanaMaestro != null && ventanaTaller == null) {
			vista.dispose();
		}
	}
}