package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Traje;
import vista.DetalleClientes;
import vista.NuevoCliente;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

// Controlador de la ventana DetalleClientes
// Muestra la información completa de un cliente y su traje asociado
public class ControladorDetalleClientes {

    private DetalleClientes vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Cliente cliente;
    private boolean editable;

    // Constructor: rellena los campos con los datos del cliente y asigna los listeners
    public ControladorDetalleClientes(DetalleClientes vista, AccesoBBDD acceso, Connection c, Cliente cliente, boolean editable) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.cliente = cliente;
        this.editable = editable;

        // Rellena la sección de información personal
        vista.getTxtInfoPersonal().setText(
            "Nombre:            " + cliente.getNombre() + "\n" +
            "Superpoder:        " + cliente.getSuperpoder() + "\n" +
            "Colores:           " + cliente.getColor() + "\n" +
            "Tipo (Héroe/Villano): " + cliente.getTipo_heroe()
        );

        // Busca el traje del cliente y rellena la sección correspondiente
        cargarTraje();

        // Oculta los botones de edición/eliminación si no tiene permisos
        if (!editable) {
            vista.getBtnEditar().setVisible(false);
            vista.getBtnEliminar().setVisible(false);
        }

        // Listener del botón "Editar"
        vista.getBtnEditar().addActionListener(e -> editarCliente());

        // Listener del botón "Eliminar"
        vista.getBtnEliminar().addActionListener(e -> eliminarCliente());

        // Listener del botón "Volver"
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    // Busca el traje del cliente en la BBDD y lo muestra
    private void cargarTraje() {
        try {
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            StringBuilder sb = new StringBuilder();
            for (Traje traje : trajes) {
                if (traje.getId_cliente() == cliente.getId_cliente()) {
                    sb.append("Nombre del traje: ").append(traje.getNombre_traje()).append("\n");
                    sb.append("Estado:           ").append(traje.getEstado()).append("\n\n");
                }
            }
            if (sb.length() == 0) {
                sb.append("No hay trajes registrados para este cliente.");
            }
            vista.getTxtInfoTrajes().setText(sb.toString().trim());
        } catch (SQLException ex) {
            ex.printStackTrace();
            vista.getTxtInfoTrajes().setText("Error al cargar los trajes.");
        }
    }

    // Abre el formulario de edición para este cliente
    private void editarCliente() {
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, acceso, c, cliente);
        vistaForm.setVisible(true);
        vista.dispose();
    }

    // Elimina el cliente tras confirmación
    private void eliminarCliente() {
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Estás seguro de que quieres eliminar a " + cliente.getNombre() + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                acceso.eliminarCliente(c, cliente.getId_cliente());
                JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente.");
                vista.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al eliminar el cliente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
