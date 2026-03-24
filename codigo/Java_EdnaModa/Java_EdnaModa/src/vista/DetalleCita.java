package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class DetalleCita extends JFrame {
	
    public DetalleCita() {
        setTitle("Detalle de la Cita");
        setBounds(150, 150, 300, 400);
        getContentPane().setLayout(null);

        JTextArea txtDetalles = new JTextArea();
        txtDetalles.setText("ID: 001\nFecha: 24/03/2026\nHora: 10:00\nCliente: Mr. Increíble\nTraje: Classic Blue\nTaller: París\nProfesional: Edna\nAprendiz: Rafael");
        txtDetalles.setEditable(false);
        txtDetalles.setBounds(20, 20, 240, 250);
        getContentPane().add(txtDetalles);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(170, 310, 90, 30);
        getContentPane().add(btnVolver);
    }
}
