package vista;

// Importaciones necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Clase que representa la ventana de gestión de citas
public class ListaCitas extends JFrame {
    
    // Tabla donde se mostrarán las citas
    private JTable tableCitas;
    
    // Campo de texto para búsqueda
    private JTextField textField;

    // Constructor de la ventana
    public ListaCitas() {
        
        // Configuración básica de la ventana
        setTitle("Gestión de Citas"); // Título
        setBounds(100, 100, 550, 400); // Tamaño y posición
        getContentPane().setLayout(null); // Layout absoluto

        // Botón para crear una nueva cita
        JButton btnNuevaCita = new JButton("+ Nueva Cita");
        btnNuevaCita.setBounds(410, 62, 114, 25);
        getContentPane().add(btnNuevaCita);

        // ScrollPane para la tabla (permite desplazamiento)
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 102, 514, 200);
        getContentPane().add(scrollPane);

        // Creación de la tabla de citas
        tableCitas = new JTable();
        
        // Modelo de la tabla (sin datos iniciales)
        tableCitas.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Fecha/Hora", "Cliente", "Taller", "Estado"} // Columnas
        ));
        
        // Se añade la tabla al scroll
        scrollPane.setViewportView(tableCitas);

        // Botón para ver detalles de la cita seleccionada
        JButton btnVerDetalles = new JButton("Ver detalles");
        btnVerDetalles.setBounds(10, 322, 120, 30);
        getContentPane().add(btnVerDetalles);
        
        // Botón para editar la cita seleccionada
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(140, 322, 100, 30);
        getContentPane().add(btnEditar);
        
        // Campo de texto para introducir búsqueda
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(33, 62, 250, 25);
        getContentPane().add(textField);
        
        // Botón para ejecutar la búsqueda
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(293, 62, 89, 25);
        getContentPane().add(btnBuscar);
        
        // Botón para mostrar todas las citas
        JButton btnTodas = new JButton("Todas");
        btnTodas.setBounds(10, 11, 120, 30);
        getContentPane().add(btnTodas);
        
        // Botón para filtrar por tipo "Diseño"
        JButton btnVerDetalles_2 = new JButton("Diseño");
        btnVerDetalles_2.setBounds(140, 11, 120, 30);
        getContentPane().add(btnVerDetalles_2);
        
        // Botón para filtrar por tipo "Costura"
        JButton btnVerDetalles_3 = new JButton("Costura");
        btnVerDetalles_3.setBounds(270, 11, 120, 30);
        getContentPane().add(btnVerDetalles_3);
        
        // Botón para filtrar por tipo "Pruebas"
        JButton btnVerDetalles_4 = new JButton("Pruebas");
        btnVerDetalles_4.setBounds(400, 11, 120, 30);
        getContentPane().add(btnVerDetalles_4);
        
        // Botón para volver a la ventana anterior
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(424, 322, 100, 30);
        getContentPane().add(btnVolver);
    }
}