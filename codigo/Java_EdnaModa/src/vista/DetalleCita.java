package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

// Clase que representa la ventana de detalle de una cita
public class DetalleCita extends JFrame {

    // Constructor de la ventana
    public DetalleCita() {
        // Configuración básica de la ventana
        setTitle("Detalle de la Cita"); // Título de la ventana
        setBounds(150, 150, 300, 400); // Posición y tamaño de la ventana
        getContentPane().setLayout(null); // Layout absoluto para colocar los componentes manualmente

        // Área de texto que muestra la información de la cita
        JTextArea txtDetalles = new JTextArea();
        txtDetalles.setText("ID: 001\nFecha: 24/03/2026\nHora: 10:00\nCliente: Mr. Increíble\nTraje: Classic Blue\nTaller: París\nProfesional: Edna\nAprendiz: Rafael");
        txtDetalles.setEditable(false); // Solo lectura, no editable por el usuario
        txtDetalles.setBounds(20, 20, 240, 250); // Posición y tamaño dentro de la ventana
        getContentPane().add(txtDetalles); // Se agrega al panel principal

        // Botón para volver a la ventana anterior
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(170, 310, 90, 30); // Posición y tamaño del botón
        getContentPane().add(btnVolver); // Se agrega al panel principal
    }
}