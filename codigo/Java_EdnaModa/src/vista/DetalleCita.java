package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;

// Clase que representa la ventana de detalle de una cita
public class DetalleCita extends JFrame {

    // componentes como campos privados para que el controlador pueda acceder mediante getters
    private JTextArea txtDetalles;
    private JButton btnVolver;

    public DetalleCita() {
        setTitle("Detalle de la Cita");
        setBounds(150, 150, 300, 350);
        getContentPane().setLayout(null);

        //texto vacío — el controlador rellenará los datos reales
        txtDetalles = new JTextArea();
        txtDetalles.setEditable(false);
        txtDetalles.setBounds(20, 47, 240, 155);
        getContentPane().add(txtDetalles);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(170, 254, 90, 30);
        getContentPane().add(btnVolver);

        JLabel lblInformacinDeLa = new JLabel("INFORMACIÓN DE LA CITA");
        lblInformacinDeLa.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblInformacinDeLa.setBounds(20, 11, 250, 25);
        getContentPane().add(lblInformacinDeLa);
    }

    // Getters necesarios para que el controlador acceda a los componentes
    public JTextArea getTxtDetalles() {
        return txtDetalles;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
}
