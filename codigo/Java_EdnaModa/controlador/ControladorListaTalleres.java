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
    private String opcion;
    
    // Constructor que vincula vista y datos, y configura los listeners
    public ControladorListaTalleres(ListaTalleres vista, AccesoBBDD acceso, Connection c, ArrayList<Taller> talleres, Empleado emp) {
    	this.vista  = vista;
    	this.acceso = acceso;
    	this.c = c;
    	this.talleres = talleres;
    	this.emp = emp;
    	this.opcion = "";
    	
    	// Asignación de listeners a los botones de la vista
        vista.getBtnVolver().addActionListener(e -> pulsarBtnVolver());
        vista.getBtnNuevoTaller().addActionListener(e -> pulsarBtnNuevoTaller());
        vista.getBtnEditar().addActionListener(e -> pulsarBtnEditar());
        vista.getBtnEliminar().addActionListener(e -> pulsarBtnEliminar());
        vista.getBtnVolver().addActionListener(e -> pulsarBtnVolver());
        vista.getBtnConfirmar().addActionListener(e -> pulsarBtnConfirmar());
        
        // Listener para detectar selecciones en la JList
        vista.getLista().addListSelectionListener(e -> {
            // Se ejecuta solo cuando el usuario termina de seleccionar
            if (!e.getValueIsAdjusting()) {
                if (opcion.equals("editar")) {
                    pulsarOpcionSeleccionadaCasoEditar();
                } else if (opcion.equals("eliminar")) {
                    pulsarOpcionSeleccionadaCasoEliminar();
                }
            }
        });


    }
    
    // Lógica para abrir la ventana de creación de un taller
    private void pulsarBtnNuevoTaller() {
    	try {
    		NuevoTaller nv = new NuevoTaller();
    		new ControladorNuevoTaller(nv, acceso, c, talleres, emp);
    		nv.setVisible(true);
    	} catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Acción al seleccionar un elemento si estamos en modo edición
    private void pulsarOpcionSeleccionadaCasoEditar() {
    	try {
            	vista.habilitarBotonConfirmar();
            	opcion = vista.editarString(); // Setea "editar"
    	} catch (Exception ex) {
            ex.printStackTrace();
    	}
    }
    
    // Prepara la interfaz para editar (deshabilita botones y pone estado)
    private void pulsarBtnEditar() {
    		vista.deshabilitarBotones();
        	opcion = vista.editarString();
    }
    
    // Acción al seleccionar elementos si estamos en modo eliminación
    private void pulsarOpcionSeleccionadaCasoEliminar() {
    	try {
            	vista.habilitarBotonConfirmar();
            	opcion = vista.eliminarString(); // Setea "eliminar"
    	} catch (Exception ex) {
            ex.printStackTrace();
    	}
    	
    }
    
    // Prepara la interfaz para eliminar
    private void pulsarBtnEliminar() {
    		vista.deshabilitarBotones();
        	opcion = vista.eliminarString();
    }
    
    // Lógica central del botón confirmar según el estado de 'opcion'
    private void pulsarBtnConfirmar() {
    	try {
    		switch(opcion) {
        	case "editar":
                // Obtiene el índice del elemento seleccionado en la lista
                int index = vista.getLista().getSelectedIndex();
                Taller tallerAEditar = talleres.get(index);
                NuevoTaller nt = new NuevoTaller();
                // Carga los datos en el formulario
                nt.cargarDatos(tallerAEditar);
                new ControladorNuevoTaller(nt, acceso, c, talleres, emp);
                nt.setVisible(true);
        	break;
        	
        	case "eliminar":
                // Llama al método de borrado múltiple
                ejecutarEliminacionMultiple();
                break;
        }
        
        // Resetea la interfaz tras confirmar
        vista.getBtnConfirmar().setEnabled(false);
        opcion = "";
        
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(vista, "Error al procesar la operación.");
    }
    	
    }
    
    // Método para borrar varios talleres a la vez
    private void ejecutarEliminacionMultiple() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTable().getModel();
        DefaultListModel<String> modeloLista = (DefaultListModel<String>) vista.getLista().getModel();
        
        // Obtiene todos los índices marcados en la JList
        int[] indicesSeleccionados = vista.getLista().getSelectedIndices();   

        // Pide confirmación al usuario
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Eliminar estos " + indicesSeleccionados.length + " talleres?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            
            // Borra de atrás hacia adelante para no corromper los índices al eliminar
            for (int i = indicesSeleccionados.length - 1; i >= 0; i--) {
                int indice = indicesSeleccionados[i];
                Taller tallerEliminado = talleres.get(indice);
                
                acceso.borrarTaller(c, tallerEliminado);

                // Borra del modelo de lista
                modeloLista.remove(indice);

                // Borra del modelo de tabla si existe
                if (indice < modeloTabla.getRowCount()) {
                    modeloTabla.removeRow(indice);
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
                    break;

                case "oficial":
                    VentanaOficial vOficial = new VentanaOficial();
                    new ControladorOficial(vOficial, acceso, c, emp);
                    vOficial.setVisible(true);
                    break;
            }
    	} catch (Exception ex) {
            ex.printStackTrace();
    	}
    	
    	
    }
    

    
}