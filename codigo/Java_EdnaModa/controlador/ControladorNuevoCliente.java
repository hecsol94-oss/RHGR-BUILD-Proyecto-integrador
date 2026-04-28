package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import vista.NuevoCliente;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

// Controlador del formulario NuevoCliente
// Puede usarse tanto para crear un nuevo cliente como para editar uno existente
public class ControladorNuevoCliente {

    private NuevoCliente vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Cliente clienteEditar; // null si es un cliente nuevo

    // Constructor: si clienteEditar es null, se crea un cliente nuevo
    /**
     * 
     * @param vista
     * @param acceso
     * @param c
     * @param clienteEditar
     */
    public ControladorNuevoCliente(NuevoCliente vista, AccesoBBDD acceso, Connection c, Cliente clienteEditar) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.clienteEditar = clienteEditar;

        // Si se está editando, precarga los campos con los datos del cliente
        if (clienteEditar != null) {
            precargarDatos();
        }

        // Listener del botón "Guardar Cliente"
        vista.getBtnGuardar().addActionListener(e -> guardarCliente());

        // Listener del botón "Cancelar"
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }

    // Precarga los campos con los datos del cliente a editar
    private void precargarDatos() {
        vista.getTxtNombre().setText(clienteEditar.getNombre());
        vista.getTxtSuperpoder().setText(clienteEditar.getSuperpoder());
        vista.getTxtColor().setText(clienteEditar.getColor());
        vista.getTxtTipo().setText(clienteEditar.getTipo_heroe());
    }

    // Valida y guarda el cliente en la BBDD
    private void guardarCliente() {
        String nombre = vista.getTxtNombre().getText().trim();
        String superpoder = vista.getTxtSuperpoder().getText().trim();
        String color = vista.getTxtColor().getText().trim();
        String tipo = vista.getTxtTipo().getText().trim();
        String nombreTraje = vista.getTxtNombreTraje().getText().trim();
        String estadoTraje = vista.getCbEstado().getSelectedItem() != null
            ? vista.getCbEstado().getSelectedItem().toString() : "";

        // Validación básica de campos obligatorios
        if (nombre.isEmpty() || superpoder.isEmpty() || color.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, rellena todos los campos del cliente.",
                "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (clienteEditar == null) {
                // Inserción de nuevo cliente
                acceso.insertarNuevoCliente(c, nombre, tipo, superpoder, color);
                // Si se ha introducido un traje, se inserta también
                if (!nombreTraje.isEmpty()) {
                    // Se obtiene el ID del cliente recién insertado para asociarle el traje
                    java.util.ArrayList<Cliente> clientes = acceso.recogeClientes(c);
                    int idNuevo = clientes.get(clientes.size() - 1).getId_cliente();
                    acceso.insertarNuevoTraje(c, nombreTraje, estadoTraje, idNuevo);
                }
                JOptionPane.showMessageDialog(vista, "Cliente creado correctamente.");
            } else {
                // Actualización de cliente existente
                acceso.actualizarCliente(c, clienteEditar.getId_cliente(), nombre, tipo, superpoder, color);
                JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente.");
            }
            vista.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al guardar el cliente en la base de datos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}