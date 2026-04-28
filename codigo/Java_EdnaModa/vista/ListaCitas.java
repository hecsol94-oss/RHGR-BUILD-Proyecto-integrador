package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Lista de citas — columnas con nombres, columna "Duración" en lugar de "Estado".
 * Filtros diseño/costura/pruebas mediante botones de tipo.
 */
public class ListaCitas extends JFrame {

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
        setBounds(100, 100, 640, 430);
        getContentPane().setLayout(null);

        // Filtros tipo de taller
        btnTodas   = new JButton("Todas");  
        btnTodas.setBounds(10,   11, 100, 28); 
        getContentPane().add(btnTodas);
        btnDiseno  = new JButton("Diseño");  
        btnDiseno.setBounds(120,  11, 100, 28); 
        getContentPane().add(btnDiseno);
        btnCostura = new JButton("Costura"); 
        btnCostura.setBounds(230, 11, 100, 28); 
        getContentPane().add(btnCostura);
        btnPruebas = new JButton("Pruebas"); 
        btnPruebas.setBounds(340, 11, 100, 28); 
        getContentPane().add(btnPruebas);

        // Búsqueda
        textField = new JTextField(); 
        textField.setColumns(10); 
        textField.setBounds(33, 50, 240, 25); 
        getContentPane().add(textField);
        btnBuscar = new JButton("Buscar"); 
        btnBuscar.setBounds(283, 50, 89, 25); 
        getContentPane().add(btnBuscar);
        btnNuevaCita = new JButton("+ Nueva Cita");
        btnNuevaCita.setBounds(500, 50, 114, 25); 
        getContentPane().add(btnNuevaCita);

        // Tabla
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 85, 610, 250);
        getContentPane().add(scrollPane);

        tableCitas = new JTable();
        tableCitas.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Fecha/Hora", "Cliente", "Traje", "Taller", "Oficial", "Duración (h)"}
        ));
        scrollPane.setViewportView(tableCitas);

        btnVerDetalles = new JButton("Ver detalles"); 
        btnVerDetalles.setBounds(10,  348, 120, 30); 
        getContentPane().add(btnVerDetalles);
        btnEditar      = new JButton("Editar");      
        btnEditar.setBounds(140,      348, 100, 30); 
        getContentPane().add(btnEditar);
        btnVolver      = new JButton("Volver");      
        btnVolver.setBounds(504,      348, 100, 30); 
        getContentPane().add(btnVolver);
    }

    public JTable getTableCitas()      {
    	return tableCitas; 
    	}
    
    public JTextField getTextField()   {
    	return textField;
    	}
    
    public JButton getBtnNuevaCita()   {
    	return btnNuevaCita;
    	}
    
    public JButton getBtnVerDetalles() {
    	return btnVerDetalles; 
    	}
    
    public JButton getBtnEditar()      {
    	return btnEditar; 
    	}
    
    public JButton getBtnBuscar()      {
    	return btnBuscar;
    	}
    
    public JButton getBtnTodas()       {
    	return btnTodas; 
    	}
    
    public JButton getBtnDiseno()      {
    	return btnDiseno; 
    	}
    
    public JButton getBtnCostura()     {
    	return btnCostura; 
    	}
    
    public JButton getBtnPruebas()     {
    	return btnPruebas; 
    	}
    
    public JButton getBtnVolver()      {
    	return btnVolver; 
    	}

    public void deshabilitarBotones() {
        btnEditar.setEnabled(false);
        btnNuevaCita.setEnabled(false);
    }
}