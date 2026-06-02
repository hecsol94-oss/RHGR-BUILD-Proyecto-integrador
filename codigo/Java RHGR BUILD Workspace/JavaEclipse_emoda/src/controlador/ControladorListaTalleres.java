package controlador;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.AccesoBBDD;
import modelo.Empleado;
import modelo.Taller;
import vista.ListaTalleres;
import vista.NuevoTaller;
import vista.VentanaAprendiz;
import vista.VentanaMaestro;
import vista.VentanaOficial;

/**
 * Controlador para la gestión de la lista de talleres.
 * Maneja las interacciones del usuario con la tabla de talleres, permitiendo
 * realizar operaciones CRUD y la navegación de retorno según el rol del empleado.
 */
public class ControladorListaTalleres {

	private ListaTalleres vista;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Taller> talleres;
	private Empleado emp;

	/**
	 * Constructor del controlador de la lista de talleres.
	 * Inicializa las referencias y asigna los escuchadores de eventos a los botones de la vista.
	 * 
	 * @param vista La ventana que muestra la lista de talleres.
	 * @param acceso Objeto para la gestión de operaciones con la base de datos.
	 * @param c Conexión activa a la base de datos.
	 * @param talleres Lista de objetos Taller cargados en memoria.
	 * @param emp Empleado que ha iniciado sesión para determinar permisos y navegación.
	 */
	public ControladorListaTalleres(ListaTalleres vista, AccesoBBDD acceso, Connection c,
			ArrayList<Taller> talleres, Empleado emp) {

		this.vista = vista;
		this.acceso = acceso;
		this.c = c;
		this.talleres = talleres;
		this.emp = emp;

		vista.getBtnNuevoTaller().addActionListener(e -> pulsarBtnNuevoTaller());
		vista.getBtnEditar().addActionListener(e -> pulsarBtnEditar());
		vista.getBtnEliminar().addActionListener(e -> pulsarBtnEliminar());
		vista.getBtnVolver().addActionListener(e -> pulsarBtnVolver());
	}

	/**
	 * Abre el formulario para la creación de un nuevo taller.
	 * Instancia el controlador correspondiente pasando las referencias necesarias.
	 */
	private void pulsarBtnNuevoTaller() {
		try {
			NuevoTaller nv = new NuevoTaller();
			new ControladorNuevoTaller(nv, vista, null, acceso, c, null, talleres, emp);
			nv.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gestiona la edición de un taller seleccionado en la tabla.
	 * Verifica que haya una fila seleccionada, recupera el objeto taller y abre el formulario
	 * de edición con los datos cargados.
	 */
	private void pulsarBtnEditar() {
		int fila = vista.getTable().getSelectedRow();

		if (fila < 0) {
			JOptionPane.showMessageDialog(vista,
					"Selecciona un taller para editar.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Taller tallerAEditar = talleres.get(fila);

		NuevoTaller nt = new NuevoTaller();
		nt.cargarDatos(tallerAEditar);

		new ControladorNuevoTaller(nt, vista, null, acceso, c, tallerAEditar, talleres, emp);
		nt.setVisible(true);
	}

	/**
	 * Gestiona la eliminación de un taller seleccionado.
	 * Solicita confirmación al usuario antes de proceder a borrar el registro en la base
	 * de datos y actualizar la tabla visual.
	 */
	private void pulsarBtnEliminar() {
		int fila = vista.getTable().getSelectedRow();

		if (fila < 0) {
			JOptionPane.showMessageDialog(vista,
					"Selecciona un taller para eliminar.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmacion = JOptionPane.showConfirmDialog(
				vista,
				"¿Seguro que quieres eliminar este taller?\nLas citas asociadas también se eliminarán.",
				"Confirmar eliminación",
				JOptionPane.YES_NO_OPTION
		);

		if (confirmacion == JOptionPane.YES_OPTION) {

			Taller tallerEliminado = talleres.get(fila);

			acceso.borrarTaller(c, tallerEliminado.getId_sala());

			talleres = acceso.recogeTalleres(c);

			DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTable().getModel();
			modeloTabla.removeRow(fila);

			JOptionPane.showMessageDialog(vista, "Taller eliminado correctamente.");
		}
	}

	/**
	 * Gestiona el retorno a la ventana principal correspondiente.
	 * Utiliza la categoría del empleado logado para instanciar la vista de Maestro,
	 * Oficial o Aprendiz segun corresponda.
	 */
	private void pulsarBtnVolver() {
		try {
			String rol = emp.getCategoria().toLowerCase();

			switch (rol) {
				case "maestro":
					VentanaMaestro vm = new VentanaMaestro();
					new ControladorMaestro(vm, acceso, c, emp);
					vm.setVisible(true);
					break;

				case "oficial":
					VentanaOficial vo = new VentanaOficial();
					new ControladorOficial(vo, acceso, c, emp);
					vo.setVisible(true);
					break;

				case "aprendiz":
					VentanaAprendiz va = new VentanaAprendiz();
					new ControladorAprendiz(va, acceso, c, emp);
					va.setVisible(true);
					break;
			}

			vista.dispose();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}