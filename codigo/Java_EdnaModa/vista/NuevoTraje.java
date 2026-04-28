package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Formulario para crear un nuevo traje.
 * Estado siempre "diseño" — al crear un traje nuevo lo lógico es empezar por el diseño.
 */
public class NuevoTraje extends JFrame {

    private JTextField txtNombreTraje;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public NuevoTraje() {
        setTitle("Nuevo Traje");
        setBounds(150, 150, 400, 220);
        setResizable(false);
        JPanel contentPane = new JPanel(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel titulo = new JLabel("DATOS DEL TRAJE");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        titulo.setBounds(20, 10, 240, 25);
        contentPane.add(titulo);

        JLabel lblNombre = new JLabel("Nombre del traje:");
        lblNombre.setBounds(20, 50, 140, 16);
        contentPane.add(lblNombre);

        txtNombreTraje = new JTextField();
        txtNombreTraje.setBounds(20, 70, 340, 25);
        contentPane.add(txtNombreTraje);

        // Estado fijo: diseño (no se muestra combo)
        JLabel lblEstado = new JLabel("Estado: diseño");
        lblEstado.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblEstado.setForeground(new Color(80, 80, 150));
        lblEstado.setBounds(20, 105, 200, 16);
        contentPane.add(lblEstado);

        btnGuardar = new JButton("Guardar Traje");
        btnGuardar.setBounds(60, 150, 130, 35);
        contentPane.add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 150, 130, 35);
        contentPane.add(btnCancelar);
    }

    public String getNombreTraje()  {
    	return txtNombreTraje.getText().trim(); 
    	}
   
    public String getEstadoTraje()  {
    	return "diseño"; 
    	}  // siempre diseño
   
    public JButton getBtnGuardar()  {
    	return btnGuardar; 
    	}
   
    public JButton getBtnCancelar() {
    	return btnCancelar; 
    	}
}
