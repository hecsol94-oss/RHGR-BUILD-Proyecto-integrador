package vista;

// Importaciones necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

// Clase duplicada que también representa la ventana de listado de talleres
public class ListaTalleres extends JFrame {
    
    // Tabla donde se mostrarán los datos
    private JTable table;

    // Constructor de la ventana
    public ListaTalleres() {
        
        // Configuración básica de la ventana
        setTitle("Sedes y Talleres - EDNA MODA"); // Título
        setBounds(100, 100, 450, 350); // Tamaño y posición
        getContentPane().setLayout(null);
        
        // Botón para añadir un nuevo taller
        JButton btnNuevoTaller = new JButton("+ Nuevo");
        btnNuevoTaller.setBounds(10, 11, 89, 23);
        getContentPane().add(btnNuevoTaller);

        // ScrollPane para contener la tabla
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 45, 414, 180);
        getContentPane().add(scrollPane);

        // Creación de la tabla
        table = new JTable();
        
        // Modelo de la tabla
        table.setModel(new DefaultTableModel(
            new Object[][] {
                      {"París", "Diseño"},
                      {"Milán", "Diseño"},
                      {"Madrid", "Costura"},
                      {"New York", "Costura"},
                      {"Berlín", "Pruebas"}
            },
    		new String[] { "Sede / ciudad", "Tipo de sala"}
    	));
        
        // Se añade la tabla al scroll
        scrollPane.setViewportView(table);
        
        // Botón para editar el elemento seleccionado
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(20, 240, 100, 30);
        getContentPane().add(btnEditar);

        // Botón para eliminar el elemento seleccionado
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(169, 240, 100, 30);
        getContentPane().add(btnEliminar);
        
        // Botón para volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(315, 240, 100, 30);
        getContentPane().add(btnVolver);
        
        
    }
       
}