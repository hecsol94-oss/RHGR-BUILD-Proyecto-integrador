package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.AccesoBBDD;
import modelo.Empleado;
import modelo.Taller;
import vista.ListaTalleres;
import vista.NuevoCliente;
import vista.NuevoTaller;
import vista.VentanaMaestro;
import vista.VentanaOficial;

// Controlador principal que gestiona la lógica de la lista de talleres
public class ControladorListaTalleres {

	// Referencias a la vista y modelos necesarios
	private ListaTalleres vista;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Taller> talleres;
	private Empleado emp;
	// Almacena el estado de la operación actual (editar o eliminar)

	// Constructor que vincula vista y datos, y configura los listeners
	public ControladorListaTalleres(ListaTalleres vista, AccesoBBDD acceso, Connection c, ArrayList<Taller> talleres,
			Empleado emp) {
		this.vista = vista;
		this.acceso = acceso;
		this.c = c;
		this.talleres = talleres;
		this.emp = emp;

		// Asignación de listeners a los botones de la vista
		vista.getBtnNuevoTaller().addActionListener(e -> pulsarBtnNuevoTaller());
		vista.getBtnEditar().addActionListener(e -> pulsarBtnEditar());
		vista.getBtnEliminar().addActionListener(e -> pulsarBtnEliminar());
		vista.getBtnVolver().addActionListener(e -> pulsarBtnVolver());

		// Listener para detectar selecciones en la JList

	}

	// Lógica para abrir la ventana de creación de un taller
	private void pulsarBtnNuevoTaller() {
		try {
			NuevoTaller nv = new NuevoTaller();
			new ControladorNuevoTaller(nv, acceso, c, null, talleres, emp);
			nv.setVisible(true);
			vista.dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Prepara la interfaz para editar (deshabilita botones y pone estado)
	private void pulsarBtnEditar() {
		int fila = vista.getTable().getSelectedRow();
		if (fila < 0) {
			JOptionPane.showMessageDialog(vista, "Selecciona un taller para editar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);			
		} else {
			
			Taller tallerAEditar = talleres.get(fila);
			NuevoTaller nt = new NuevoTaller();
			// Carga los datos en el formulario
			nt.cargarDatos(tallerAEditar);
			new ControladorNuevoTaller(nt, acceso, c, tallerAEditar, talleres, emp);
			nt.setVisible(true);
			vista.dispose();
		}
	}

	// Prepara la interfaz para eliminar
	private void pulsarBtnEliminar() {
		int fila = vista.getTable().getSelectedRow();
		if (fila < 0) {
			JOptionPane.showMessageDialog(vista, "Selecciona un taller para eliminar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		} else {
			DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTable().getModel();

			// Obtiene todos los índices marcados en la JList

			// Pide confirmación al usuario
			int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estas seguro de que quieres eliminar este taller?",
					"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {

				// Borra de atrás hacia adelante para no corromper los índices al eliminar

				Taller tallerEliminado = talleres.get(fila);

				acceso.borrarTaller(c, tallerEliminado.getId_sala());

				talleres = acceso.recogeTalleres(c);
				// Borra del modelo de lista

				// Borra del modelo de tabla si existe
				if (fila < modeloTabla.getRowCount()) {
					modeloTabla.removeRow(fila);
				}
			}
		}
	}



	// Lógica para retroceder a la ventana principal según el rol del empleado
	private void pulsarBtnVolver() {
		try {
			String rol = emp.getCategoria().toLowerCase();

			switch (rol) {
			case "maestro":
				VentanaMaestro vMaestro = new VentanaMaestro();
				new ControladorMaestro(vMaestro, acceso, c, emp);
				vMaestro.setVisible(true);
				vista.dispose();
				break;

			case "oficial":
				VentanaOficial vOficial = new VentanaOficial();
				new ControladorOficial(vOficial, acceso, c, emp);
				vOficial.setVisible(true);
				vista.dispose();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}