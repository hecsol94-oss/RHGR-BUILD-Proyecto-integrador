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

// Controlador para gestionar la lógica de creación de un taller individual
public class ControladorNuevoTaller {

	// Referencias a la vista de formulario y objetos de datos
	private NuevoTaller vista;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Taller> talleres;
	private Taller tallerEditar;
	private Empleado emp;

	// Constructor: vincula la vista con el modelo y asigna los eventos a los botones
	public ControladorNuevoTaller(NuevoTaller vista, AccesoBBDD acceso, Connection conexion, Taller tallerEditar, ArrayList<Taller> talleres, Empleado emp) {
		this.vista = vista;
		this.acceso = acceso;
		this.c = conexion;
		this.tallerEditar = tallerEditar;
		this.talleres = talleres;
		this.emp = emp;

		// Asignación de manejadores de eventos para Guardar y Cancelar
		this.vista.getBtnGuardar().addActionListener(e -> pulsarBtnGuardar());
		this.vista.getBtnCancelar().addActionListener(e -> pulsarBtnCancelar());

	}
	
	

	// Lógica para procesar y salvar los datos del nuevo taller
	private void pulsarBtnGuardar() {
		String nombreIntroducido = vista.getNombreSala();
		String tipoIntroducido = vista.getTipoSala();
		
		if (tallerEditar == null) {
			
			if (!nombreIntroducido.isEmpty()) {
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
			
			} else {
				JOptionPane.showConfirmDialog(vista, 
		            	"por favor, rellene los campos necesarios para crear un nuevo taller", 
		            	"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			
			if (!nombreIntroducido.isEmpty()) {

			    Taller nuevoTaller = new Taller((talleres.size() + 1), nombreIntroducido, tipoIntroducido);
			
			    acceso.ActualizarTaller(c, tallerEditar.getId_sala(), nuevoTaller);
			    
				talleres = acceso.recogeTalleres(c);

				ListaTalleres lt = new ListaTalleres();
				lt.recogerDatos(talleres); // Carga la tabla/lista con los datos actualizados

			    new ControladorListaTalleres(lt, acceso, c, talleres, emp);
				lt.setVisible(true);
			
				// Cierra la ventana actual de formulario
				vista.dispose();
					
		    } else {
		    	JOptionPane.showConfirmDialog(vista, 
		            	"por favor, rellene los campos necesarios para crear un nuevo taller", 
		            	"Error", JOptionPane.ERROR_MESSAGE);
		    }
		}		
	}

	// Lógica para abortar la operación y volver a la pantalla anterior
	private void pulsarBtnCancelar() {
		// Simplemente crea la vista de lista y su controlador sin guardar cambios
		ListaTalleres lt = new ListaTalleres();
		lt.recogerDatos(talleres);
		new ControladorListaTalleres(lt, acceso, c, talleres, emp);
		lt.setVisible(true);
        vista.dispose();
	}

}
