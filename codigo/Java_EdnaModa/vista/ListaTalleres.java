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
    // Lista alternativa para mostrar talleres
    private JList<String> lista;
    // Modelo de datos de la lista
    private DefaultListModel<String> modeloLista;
    // Panel principal con CardLayout
    private JPanel panel;
    // Scroll para la tabla
    private JScrollPane scrollpaneTable;
    // Scroll para la lista
    private JScrollPane scrollpaneList;
    // Layout para alternar entre tabla y lista
    private CardLayout cardlayout;
    // Botones de la interfaz
    private JButton btnNuevoTaller;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JButton btnConfirmar;
    
    // Constructor de la ventana
    public ListaTalleres() {
        
        // Configuración básica de la ventana
        setTitle("Sedes y Talleres - EDNA MODA"); // Título
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350); // Tamaño y posición
        getContentPane().setLayout(null);
        
        // Inicialización del layout tipo tarjetas
        cardlayout = new CardLayout();
        panel = new JPanel(cardlayout);
        panel.setBounds(20, 45, 400, 180);
        
        // Botón para añadir un nuevo taller
        btnNuevoTaller = new JButton("+ Nuevo");
        btnNuevoTaller.setBounds(10, 11, 89, 23);
        getContentPane().add(btnNuevoTaller);

        // Creación de la tabla
        
        // Modelo de la tabla
        String[] columnas = { "Sede / ciudad", "Tipo de sala"};
        modeloTabla = new DefaultTableModel(columnas, 0); 
        // Tabla asociada al modelo
        table = new JTable(modeloTabla);
        // Scroll para la tabla
        scrollpaneTable = new JScrollPane(table);
        
        // Modelo de la lista
        modeloLista = new DefaultListModel<>();
        // Lista asociada al modelo
        lista = new JList<>(modeloLista);
        // Scroll para la lista
        scrollpaneList = new JScrollPane(lista);
        
        // Añadir componentes al panel con CardLayout
        panel.add(scrollpaneTable, "TABLA");
        panel.add(scrollpaneList, "LISTA");
        getContentPane().add(panel);
        
        // Botón para editar el elemento seleccionado
        btnEditar = new JButton("Editar");
        // Cambia la vista a LISTA
        btnEditar.addActionListener(e -> cardlayout.show(panel, "LISTA"));
        btnEditar.setBounds(20, 240, 100, 30);
        getContentPane().add(btnEditar);

        // Botón para eliminar el elemento seleccionado
        btnEliminar = new JButton("Eliminar");
        // (Posible error: se añade listener a btnEditar en vez de btnEliminar)
        btnEditar.addActionListener(e -> cardlayout.show(panel, "LISTA"));
        btnEliminar.setBounds(169, 240, 100, 30);
        getContentPane().add(btnEliminar);
        
        // Botón para volver
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(315, 240, 100, 30);
        getContentPane().add(btnVolver);
        
        // Botón para confirmar acciones
        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(338, 11, 88, 22);
        getContentPane().add(btnConfirmar);
        btnConfirmar.setEnabled(false); // Inicialmente desactivado
        
    }
    
    // Getter del botón nuevo taller
    public JButton getBtnNuevoTaller() {
        // Cambia a vista TABLA al pulsar volver
        btnVolver.addActionListener(e -> cardlayout.show(panel, "TABLA"));
    	return btnNuevoTaller;
    }
    
    // Getter del botón editar
    public JButton getBtnEditar() {
    	
        // Cambia a vista LISTA
        btnEditar.addActionListener(e -> cardlayout.show(panel, "LISTA"));
        // Solo permite seleccionar un elemento
    	lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	return btnEditar;
    }
    
    // Getter del botón eliminar
    public JButton getBtnEliminar() {

        // Cambia a vista LISTA
        btnEliminar.addActionListener(e -> cardlayout.show(panel, "LISTA"));
        // Permite seleccionar múltiples elementos
    	lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	return btnEliminar;
    }
    
    // Getter del botón volver
    public JButton getBtnVolver() {
        // Reactiva botones
    	btnEditar.setEnabled(true);
    	btnEliminar.setEnabled(true);
    	btnNuevoTaller.setEnabled(true);
        // Cambia a vista TABLA
        btnVolver.addActionListener(e -> cardlayout.show(panel, "TABLA"));
    	return btnVolver;
    }
    
    public JButton getBtnConfirmar() {
    	return btnConfirmar;
    }
    
    // Devuelve la tabla
    public JTable getTable() {
    	return table;
    }
    
    // Devuelve la lista
    public JList<String> getLista() {
        return lista;
    }
    
    // Añade una fila a tabla y lista
    public void agregarFila(Taller t) {
        modeloTabla.addRow(new String[]{ t.getNombre(), t.getTipo() });
        modeloLista.addElement(t.getNombre() + " - " + t.getTipo());
       
    }
    
    // Devuelve el elemento seleccionado en la lista
    public String getOpcionSeleccionada() {
    	return lista.getSelectedValue();
    }
    
    // Desactiva botones principales
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
    	btnConfirmar.setEnabled(true);

    }
    
    // Devuelve string de acción editar
    public String editarString() {
    	return "editar";
    }
    
    // Devuelve string de acción eliminar
    public String eliminarString() {
    	return "eliminar";
    }
    
    // Carga datos desde lista de talleres
    public void recogerDatos(ArrayList<Taller> talleres) {
    	for (Taller t : talleres) {
    		agregarFila(t);
    	}
    }
}