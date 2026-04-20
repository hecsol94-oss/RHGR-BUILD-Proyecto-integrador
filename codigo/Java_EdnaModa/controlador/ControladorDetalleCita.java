package controlador;

import modelo.Cita;
import vista.DetalleCita;

// Controlador de la ventana DetalleCita
// Muestra la información completa de una cita seleccionada
public class ControladorDetalleCita {

    private DetalleCita vista;
    private Cita cita;

    // Constructor: rellena el área de texto con los datos de la cita y asigna el botón volver
    public ControladorDetalleCita(DetalleCita vista, Cita cita) {
        this.vista = vista;
        this.cita = cita;

        // Rellena el área de texto con los datos reales de la cita
        vista.getTxtDetalles().setText(
            "ID:       " + cita.getId_cita() + "\n" +
            "Fecha:    " + cita.getFecha() + "\n" +
            "Hora:     " + cita.getHora_inicio() + "\n" +
            "Duración: " + cita.getDuracion() + "h\n" +
            "Cliente ID: " + cita.getId_cliente() + "\n" +
            "Sala ID:    " + cita.getId_sala()
        );

        // Listener del botón "Volver"
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }
}
