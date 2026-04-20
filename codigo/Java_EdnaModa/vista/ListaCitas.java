package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Clase que representa la ventana de gestión de citas
public class ListaCitas extends JFrame {

    //todos los componentes como campos privados para que el controlador pueda acceder mediante getters
    private JTable tableCitas;
    private JTextField textField;
    private JButton btnNuevaCita;
    private JButton btnVerDetalles;
    private JButton btnEditar;
    private JButton btnBuscar;
    private JButton btnTodas;
    private JButton btnDiseno;
    private JButton btnCostura;
    private JButton btnPruebas;
    private JButton btnVolver;

    public ListaCitas() {
        setTitle("Gestión de Citas");
        setBounds(100, 100, 550, 400);
        getContentPane().setLayout(null);

        btnNuevaCita = new JButton("+ Nueva Cita");
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

        btnVerDetalles = new JButton("Ver detalles");
        btnVerDetalles.setBounds(10, 322, 120, 30);
        getContentPane().add(btnVerDetalles);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(140, 322, 100, 30);
        getContentPane().add(btnEditar);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(33, 62, 250, 25);
        getContentPane().add(textField);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(293, 62, 89, 25);
        getContentPane().add(btnBuscar);

        btnTodas = new JButton("Todas");
        btnTodas.setBounds(10, 11, 120, 30);
        getContentPane().add(btnTodas);

        // nombres de variable 
        btnDiseno = new JButton("Diseño");
        btnDiseno.setBounds(140, 11, 120, 30);
        getContentPane().add(btnDiseno);

        btnCostura = new JButton("Costura");
        btnCostura.setBounds(270, 11, 120, 30);
        getContentPane().add(btnCostura);

        btnPruebas = new JButton("Pruebas");
        btnPruebas.setBounds(400, 11, 120, 30);
        getContentPane().add(btnPruebas);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(424, 322, 100, 30);
        getContentPane().add(btnVolver);
    }

    // Getters para que el controlador acceda a los componentes
    public JTable getTableCitas()       {
    	return tableCitas; 
    	}
    
    public JTextField getTextField()    {
    	return textField; 
    	}
    
    public JButton getBtnNuevaCita()    {
    	return btnNuevaCita; 
    	}
    
    public JButton getBtnVerDetalles()  {
    	return btnVerDetalles; 
    	}
    
    public JButton getBtnEditar()       {
    	return btnEditar; 
    	}
    
    public JButton getBtnBuscar()       {
    	return btnBuscar; 
    	}
    
    public JButton getBtnTodas()        {
    	return btnTodas; 
    	}
    
    public JButton getBtnDiseno()       {
    	return btnDiseno; 
    	}
    
    public JButton getBtnCostura()      {
    	return btnCostura; 
    	}
    
    public JButton getBtnPruebas()      {
    	return btnPruebas; 
    	}
    
    public JButton getBtnVolver()       {
    	return btnVolver; 
    	}
    
    public void deshabilitarBotones() {
    	btnEditar.setEnabled(false);
    	btnNuevaCita.setEnabled(false);

    }
}