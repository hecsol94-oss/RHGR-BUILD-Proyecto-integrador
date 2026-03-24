package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListaCitas extends JFrame {
    private JTable tableCitas;
    private JTextField textField;

    public ListaCitas() {
        setTitle("Gestión de Citas");
        setBounds(100, 100, 550, 400);
        getContentPane().setLayout(null);

        JButton btnNuevaCita = new JButton("+ Nueva Cita");
        btnNuevaCita.setBounds(410, 62, 114, 25);
        getContentPane().add(btnNuevaCita);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 102, 514, 200);
        getContentPane().add(scrollPane);

        tableCitas = new JTable();
        tableCitas.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Fecha/Hora", "Cliente", "Taller", "Estado"}
        ));
        scrollPane.setViewportView(tableCitas);

        JButton btnVerDetalles = new JButton("Ver detalles");
        btnVerDetalles.setBounds(10, 322, 120, 30);
        getContentPane().add(btnVerDetalles);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(140, 322, 100, 30);
        getContentPane().add(btnEditar);
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(33, 62, 250, 25);
        getContentPane().add(textField);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(293, 62, 89, 25);
        getContentPane().add(btnBuscar);
        
        JButton btnTodas = new JButton("Todas");
        btnTodas.setBounds(10, 11, 120, 30);
        getContentPane().add(btnTodas);
        
        JButton btnVerDetalles_2 = new JButton("Diseño");
        btnVerDetalles_2.setBounds(140, 11, 120, 30);
        getContentPane().add(btnVerDetalles_2);
        
        JButton btnVerDetalles_3 = new JButton("Costura");
        btnVerDetalles_3.setBounds(270, 11, 120, 30);
        getContentPane().add(btnVerDetalles_3);
        
        JButton btnVerDetalles_4 = new JButton("Pruebas");
        btnVerDetalles_4.setBounds(400, 11, 120, 30);
        getContentPane().add(btnVerDetalles_4);
    }
}
