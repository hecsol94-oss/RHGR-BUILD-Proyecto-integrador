package vista;

// Importaciones necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.Taller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Clase duplicada que también representa la ventana de listado de talleres
public class ListaTalleres extends JFrame {
    
    // Tabla donde se mostrarán los datos
    private JTable table;
    private JList<String> lista;
    private JPanel panel;
    private JScrollPane scrollpaneTable;
    private CardLayout cardlayout;
    private JButton btnNuevoTaller;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JButton btnConfirmar;
    private Taller taller;
    
    // Constructor de la ventana
    public ListaTalleres() {
        
        // Configuración básica de la ventana
        setTitle("Sedes y Talleres - EDNA MODA"); // Título
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350); // Tamaño y posición
        getContentPane().setLayout(null);
        
        cardlayout = new CardLayout();
        panel = new JPanel(cardlayout);
        
        // Botón para añadir un nuevo taller
        btnNuevoTaller = new JButton("+ Nuevo");
        btnNuevoTaller.setBounds(10, 11, 89, 23);
        getContentPane().add(btnNuevoTaller);

        // Creación de la tabla
        
        // Modelo de la tabla
        String[] columnas = { "Sede / ciudad", "Tipo de sala"};
        Object[][] datos = new Object[0][0];
        
        table = new JTable(datos, columnas);
        scrollpaneTable = new JScrollPane(table);
        
        String[] items = {taller.getNombre() + " - " +taller.getTipo()};
        lista = new JList<>(items);
        JScrollPane scrollpaneList = new JScrollPane(lista);
        
        panel.add(scrollpaneTable, "TABLA");
        panel.add(scrollpaneList, "LISTA");
        
        getContentPane().add(panel);
        
        // Botón para editar el elemento seleccionado
        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> cardlayout.show(panel, "LISTA"));
        btnEditar.setBounds(20, 240, 100, 30);
        getContentPane().add(btnEditar);

        // Botón para eliminar el elemento seleccionado
        btnEliminar = new JButton("Eliminar");
        btnEditar.addActionListener(e -> cardlayout.show(panel, "LISTA"));
        btnEliminar.setBounds(169, 240, 100, 30);
        getContentPane().add(btnEliminar);
        
        // Botón para volver
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(315, 240, 100, 30);
        getContentPane().add(btnVolver);
        
        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(338, 11, 88, 22);
        getContentPane().add(btnConfirmar);
        btnConfirmar.setEnabled(false);
        
    }
    
    public JButton getBtnNuevoTaller() {
    	return btnNuevoTaller;
    }
    
    public JButton getBtnEditar() {
    	lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	return btnEditar;
    }
    
    public JButton getBtnEliminar() {
    	lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	return btnEliminar;
    }
    
    public JButton getBtnVolver() {
    	return btnVolver;
    }
    
    public JButton getBtnConfirmar() {
    	return btnConfirmar;
    }
    
    public String getOpcionSeleccionada(JButton button) {
    	return lista.getSelectedValue();
    }
    
//    public JTable insertarFila(Taller taller) {
//    	
//    }
//    
//    public JList insertarOpcion(Taller taller) {
//    	
//    }
    
    public void deshabilitarBotones() {
    	btnEditar.setEnabled(false);
    	btnEliminar.setEnabled(false);
    	btnNuevoTaller.setEnabled(false);

    }
    
    public void habilitarBotonEditar() {
    	btnEditar.setEnabled(true);

    }
    
    public void habilitarBotonEliminar() {
    	btnEliminar.setEnabled(true);

    }
    
    public void habilitarBotonConfirmar() {
    	btnEliminar.setEnabled(true);

    }
    
    public String editarString() {
    	return "editar";
    }
    
    public String eliminarString() {
    	return "eliminar";
    }
}