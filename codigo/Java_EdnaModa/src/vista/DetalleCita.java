package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;

// Clase que representa la ventana de detalle de una cita
public class DetalleCita extends JFrame {

    // Constructor de la ventana
    public DetalleCita() {
        // Configuración básica de la ventana
        setTitle("Detalle de la Cita"); // Título de la ventana
        setBounds(150, 150, 300, 350); // Posición y tamaño de la ventana
        getContentPane().setLayout(null); // Layout absoluto para colocar los componentes manualmente

        // Área de texto que muestra la información de la cita
        JTextArea txtDetalles = new JTextArea();
        txtDetalles.setText("ID: 001\nFecha: 24/03/2026\nHora: 10:00\nCliente: Mr. Increíble\nTraje: Classic Blue\nTaller: París\nProfesional: Edna\nAprendiz: Rafael");
        txtDetalles.setEditable(false); // Solo lectura, no editable por el usuario
        txtDetalles.setBounds(20, 47, 240, 155); // Posición y tamaño dentro de la ventana
        getContentPane().add(txtDetalles); // Se agrega al panel principal

        // Botón para volver a la ventana anterior
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(170, 254, 90, 30); // Posición y tamaño del botón
        getContentPane().add(btnVolver); // Se agrega al panel principal
        
        JLabel lblInformacinDeLa = new JLabel("INFORMACIÓN DE LA CITA");
        lblInformacinDeLa.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblInformacinDeLa.setBounds(20, 11, 250, 25);
        getContentPane().add(lblInformacinDeLa);
    }
}