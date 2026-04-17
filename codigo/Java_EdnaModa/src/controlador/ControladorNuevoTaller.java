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
import vista.ListaTalleres;
import vista.NuevoTaller;

// Controlador para gestionar la lógica de creación de un taller individual
public class ControladorNuevoTaller {

	// Referencias a la vista de formulario y objetos de datos
	private NuevoTaller vista;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Taller> talleres;
	private Taller taller;
	private Empleado emp;

	// Constructor: vincula la vista con el modelo y asigna los eventos a los botones
	public ControladorNuevoTaller(NuevoTaller vista, AccesoBBDD acceso, Connection conexion, ArrayList<Taller> talleres,
			Empleado emp) {
		this.vista = vista;
		this.acceso = acceso;
		this.c = conexion;
		this.taller = new Taller(0, "", "");
		this.talleres = talleres;
		this.emp = emp;

		// Asignación de manejadores de eventos para Guardar y Cancelar
		this.vista.getBtnGuardar().addActionListener(e -> pulsarBtnGuardarCasoCrear());
//			if (vista.getNombreSala().isEmpty()) {
//				pulsarBtnGuardarCasoCrear();
//			} else  {
//				pulsarBtnGuardarCasoActualizar();
//			}
//			
//		});
		this.vista.getBtnCancelar().addActionListener(e -> pulsarBtnCancelar());

	}

	
//	private void pulsarBtnGuardarCasoActualizar() {
//		Taller viejoTaller = vista.devolverTallerViejo();
//		
//		String nombreIntroducido = vista.getNombreSala();
//		String tipoIntroducido = vista.getTipoSala();
//		
//		// Instancia un nuevo objeto Taller para reemplazar al viejo
//		Taller nuevoTaller = new Taller((talleres.indexOf(viejoTaller.getId_sala())), nombreIntroducido, tipoIntroducido);
//		
//		acceso.ActualizarTaller(c, viejoTaller, nuevoTaller);
//	}
	
	// Lógica para procesar y salvar los datos del nuevo taller
	private void pulsarBtnGuardarCasoCrear() {
		
		// Recupera la información escrita por el usuario en la interfaz
		String nombreIntroducido = vista.getNombreSala();
		String tipoIntroducido = vista.getTipoSala();
		
		// Instancia un nuevo objeto Taller calculando un ID provisional (tamaño actual + 1)
		Taller nuevoTaller = new Taller((talleres.size() + 1), nombreIntroducido, tipoIntroducido);
		
		// Llama al modelo para persistir los datos en la base de datos
		acceso.insertarNuevoTaller(c, nuevoTaller);
		
		// Actualiza la lista local de talleres recuperándola de nuevo de la BBDD
		talleres = acceso.recogeTalleres(c);

		// Crea la vista de la lista para mostrar los cambios
		ListaTalleres lt = new ListaTalleres();
		lt.recogerDatos(talleres); // Carga la tabla/lista con los datos actualizados
		
		// Inicializa el controlador de la lista y muestra la ventana
		new ControladorListaTalleres(lt, acceso, c, talleres, emp);
		lt.setVisible(true);
		
		// Cierra la ventana actual de formulario
		vista.dispose();

	}

	// Lógica para abortar la operación y volver a la pantalla anterior
	private void pulsarBtnCancelar() {
		// Simplemente crea la vista de lista y su controlador sin guardar cambios
		ListaTalleres lt = new ListaTalleres();
		new ControladorListaTalleres(lt, acceso, c, talleres, emp);
		lt.setVisible(true);
	}

}