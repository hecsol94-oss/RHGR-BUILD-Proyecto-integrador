package vista;

// Importaciones necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorListaTalleres;
import modelo.Taller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

// Clase duplicada que también representa la ventana de listado de talleres
public class ListaTalleres extends JFrame {
    
    // Tabla donde se mostrarán los datos
    private JTable table;
    // Modelo de datos de la tabla
    private DefaultTableModel modeloTabla;
    // Scroll para la tabla
    private JScrollPane scrollpaneTable;
    // Scroll para la lista
    private JButton btnNuevoTaller;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    
    // Constructor de la ventana
    public ListaTalleres() {
        
        // Configuración básica de la ventana
        setTitle("Sedes y Talleres - EDNA MODA"); // Título
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350); // Tamaño y posición
        getContentPane().setLayout(null);
        
     // ScrollPane para contener la tabla
        scrollpaneTable = new JScrollPane();
        scrollpaneTable.setBounds(10, 45, 414, 180);
        getContentPane().add(scrollpaneTable);

        // Creación de la tabla
        table = new JTable();
        
        // Modelo de la tabla con datos de ejemplo
        modeloTabla = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Nombre", "tipo" }
            );

        table.setModel(modeloTabla);
        table.setDefaultEditor(Object.class, null);
        
        // Se añade la tabla al scroll
        scrollpaneTable.setViewportView(table);
                
        // Botón para añadir un nuevo taller
        btnNuevoTaller = new JButton("+ Nuevo");
        btnNuevoTaller.setBounds(10, 11, 89, 23);
        getContentPane().add(btnNuevoTaller);
        
        
        // Botón para editar el elemento seleccionado
        btnEditar = new JButton("Editar");
        // Cambia la vista a LISTA
        btnEditar.setBounds(20, 240, 100, 30);
        getContentPane().add(btnEditar);

        // Botón para eliminar el elemento seleccionado
        btnEliminar = new JButton("Eliminar");
        // (Posible error: se añade listener a btnEditar en vez de btnEliminar)
        btnEliminar.setBounds(169, 240, 100, 30);
        getContentPane().add(btnEliminar);
        
        // Botón para volver
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(315, 240, 100, 30);
        getContentPane().add(btnVolver);        
        
        
    }
    
    // Getter del botón nuevo taller
    public JButton getBtnNuevoTaller() {
    	return btnNuevoTaller;
    }
    
    // Getter del botón editar
    public JButton getBtnEditar() {
    	return btnEditar;
    }
    
    // Getter del botón eliminar
    public JButton getBtnEliminar() {
    	return btnEliminar;
    }
    
    // Getter del botón volver
    public JButton getBtnVolver() {
    	return btnVolver;
    }
    
    // Devuelve la tabla
    public JTable getTable() {
    	return table;
    }
    
    // Añade una fila a tabla y lista
    public void agregarFila(Taller t) {
        modeloTabla.addRow(new Object[]{t.getNombre(), t.getTipo() });       
    }
    
    // Carga datos desde lista de talleres
    public void recogerDatos(ArrayList<Taller> talleres) {
    	
    	modeloTabla.setRowCount(0);
    	
    	for (Taller t : talleres) {
    		agregarFila(t);
    	}
    }
    
    public void deshabilitarBotones() {
    	btnNuevoTaller.setEnabled(false);
    	btnEditar.setEnabled(false);
    	btnEliminar.setEnabled(false);

    }
}