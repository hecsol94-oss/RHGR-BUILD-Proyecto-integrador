package vista;

import javax.swing.*;
import javax.swing.border.*;

import modelo.Taller;
import modelo.Traje;
import java.awt.*;
import java.util.ArrayList;

public class DetalleClientes extends JFrame {

    private JPanel contentPane;
    // --- CAMBIO: Declarar componentes como atributos de clase ---
    private JTextField txtNombreCliente, txtHeroeCliente, txtSuperpoderCliente, txtColorCliente;
    private JList<String> listTrajes;
    private DefaultListModel<String> modeloLista;
    private JButton btnEditar, btnEliminar, btnVolver, btnNuevoTraje;

    public DetalleClientes() {
        setTitle("Detalles del Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- PANEL INFORMACIÓN PERSONAL ---
        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(new TitledBorder(null, "Información personal del cliente", TitledBorder.LEADING, TitledBorder.TOP));
        panelInfo.setBounds(20, 33, 390, 140);
        contentPane.add(panelInfo);
        panelInfo.setLayout(null);
        
        // (Labels omitidos para brevedad, se mantienen igual que en tu código)
        
        txtNombreCliente = new JTextField();
        txtNombreCliente.setBounds(144, 22, 215, 20);
        panelInfo.add(txtNombreCliente);
        txtNombreCliente.setEditable(false);
        
        txtHeroeCliente = new JTextField();
        txtHeroeCliente.setBounds(144, 47, 215, 20);
        panelInfo.add(txtHeroeCliente);
        txtHeroeCliente.setEditable(false);
        
        txtSuperpoderCliente = new JTextField();
        txtSuperpoderCliente.setBounds(144, 72, 215, 20);
        panelInfo.add(txtSuperpoderCliente);
        txtSuperpoderCliente.setEditable(false);

        txtColorCliente = new JTextField();
        txtColorCliente.setBounds(144, 97, 215, 20);
        panelInfo.add(txtColorCliente);
        txtColorCliente.setEditable(false);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(33, 25, 62, 14);
        panelInfo.add(lblNombre);
        
        JLabel lblTipo_Heroe = new JLabel("Tipo de heroe:");
        lblTipo_Heroe.setBounds(33, 50, 89, 14);
        panelInfo.add(lblTipo_Heroe);
        
        JLabel lblSuperopder = new JLabel("Superpoder:");
        lblSuperopder.setBounds(33, 75, 89, 14);
        panelInfo.add(lblSuperopder);
        
        JLabel lblColores = new JLabel("Colores:");
        lblColores.setBounds(33, 100, 62, 14);
        panelInfo.add(lblColores);

        // --- PANEL INFORMACIÓN TRAJES ---
        JPanel panelTraje = new JPanel();
        panelTraje.setBorder(new TitledBorder(null, "Información sobre los trajes", TitledBorder.LEADING, TitledBorder.TOP));
        panelTraje.setBounds(20, 180, 390, 150); 
        contentPane.add(panelTraje);
        panelTraje.setLayout(null);

        // --- CAMBIO: Usar los atributos de clase en lugar de variables locales ---
        modeloLista = new DefaultListModel<>();

        listTrajes = new JList<>(modeloLista);
        listTrajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(listTrajes);
        scrollPane.setBounds(15, 25, 360, 110); 
        panelTraje.add(scrollPane);

        // --- BOTONES INFERIORES ---
        btnEditar = new JButton("Editar traje");
        btnEditar.setBounds(30, 350, 100, 30);
        contentPane.add(btnEditar);

        btnEliminar = new JButton("Eliminar traje");
        btnEliminar.setBounds(140, 350, 150, 30);
        contentPane.add(btnEliminar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(300, 350, 100, 30);
        contentPane.add(btnVolver);
        
        btnNuevoTraje = new JButton("+ Nuevo traje");
        btnNuevoTraje.setBounds(300, 11, 110, 22);
        contentPane.add(btnNuevoTraje);
    }

    // --- MÉTODOS GETTER PARA EL CONTROLADOR ---

    public JTextField getNombreCliente() {
    	return txtNombreCliente;
    }
    
    public JTextField getTipoHeroeCliente() {
    	return txtHeroeCliente;
    }
    
    public JTextField getSuperpoderCliente() {
    	return txtSuperpoderCliente;
    }
    
    public JTextField getColorCliente() {
    	return txtColorCliente;
    }
    
    public JList<String> getListTrajes() {
        return listTrajes;
    }

    public DefaultListModel<String> getModeloLista() {
        return modeloLista;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
    
    public JButton getBtnNuevoTraje() {
        return btnNuevoTraje;
    }
    
    public void agregarOpcion(Traje t) {
        modeloLista.addElement(t.getNombre_traje() + " - " +t.getEstado());       
    }
    
    public void recogerDatos(ArrayList<Traje> trajes) {
        modeloLista.clear();

        for (Traje t : trajes) {
            agregarOpcion(t);
        }
    }
}