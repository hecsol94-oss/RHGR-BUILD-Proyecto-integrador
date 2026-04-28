package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import vista.NuevoTraje;

import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 * Controlador del formulario NuevoTraje.
 * Crea un traje asociado al cliente indicado.
 */
public class ControladorNuevoTraje {

    private final NuevoTraje vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Cliente cliente;

    /**
     * 
     * @param vista
     * @param acceso
     * @param c
     * @param cliente
     */
    public ControladorNuevoTraje(NuevoTraje vista, AccesoBBDD acceso, Connection c, Cliente cliente) {
        this.vista   = vista;
        this.acceso  = acceso;
        this.c       = c;
        this.cliente = cliente;

        vista.getBtnGuardar().addActionListener(e -> guardar());
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }

    private void guardar() {
        String nombre = vista.getNombreTraje();
        String estado = vista.getEstadoTraje();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre del traje no puede estar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        acceso.insertarNuevoTraje(c, nombre, estado, cliente.getId_cliente());
        JOptionPane.showMessageDialog(vista, "Traje \"" + nombre + "\" creado para " + cliente.getNombre() + ".");
        vista.dispose();
    }
}

