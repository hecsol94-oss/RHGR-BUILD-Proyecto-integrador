package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class ListaTalleres extends JFrame {
    private JTable table;

    public ListaTalleres() {
        setTitle("Sedes y Talleres - EDNA MODA");
        setBounds(100, 100, 450, 350);
        getContentPane().setLayout(null);

        JButton btnNuevoTaller = new JButton("+ Nuevo");
        btnNuevoTaller.setBounds(10, 11, 89, 23);
        getContentPane().add(btnNuevoTaller);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 45, 414, 180);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {
                {"París", "Alta Costura"},
                {"Milán", "Diseño"},
                {"Madrid", "Pruebas"},
                {"New York", "Costura"},
                {"Berlín", "Exámenes"}
            },
            new String[] {"Sede / Ciudad", "Tipo de Sala"}
        ));
        scrollPane.setViewportView(table);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(10, 240, 100, 30);
        getContentPane().add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(324, 240, 100, 30);
        getContentPane().add(btnEliminar);
        
        // Acción para abrir la ventana pequeña (Círculo 5)
        btnNuevoTaller.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre de la Sala:");
            String tipo = JOptionPane.showInputDialog("Tipo de sala:");
            if(nombre != null && tipo != null) {
                ((DefaultTableModel)table.getModel()).addRow(new Object[]{nombre, tipo});
            }
        });
    }
}
