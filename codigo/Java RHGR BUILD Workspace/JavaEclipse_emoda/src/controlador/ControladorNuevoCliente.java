package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Traje;
import vista.ListaClientes;
import vista.NuevaCita;
import vista.NuevoCliente;
import vista.VentanaMaestro;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

// Controlador del formulario NuevoCliente
// Permite crear un cliente nuevo o editar uno existente
public class ControladorNuevoCliente {

    // Referencias a vistas y capa de datos
    private NuevoCliente vista;
    private ListaClientes vistaClientes;
    private VentanaMaestro vistaMaestro;
    private NuevaCita vistaCita;
    private AccesoBBDD acceso;
    private Connection c;

    // Cliente en edición (null si es nuevo)
    private Cliente clienteEditar;

    // Lista de clientes en memoria
    private ArrayList<Cliente> clientes;

    private Empleado empleado;

    // Constructor principal del controlador
    public ControladorNuevoCliente(NuevoCliente vista, ListaClientes vistaClientes, VentanaMaestro vistaMaestro,
                                    NuevaCita vistaCita, AccesoBBDD acceso, Connection c,
                                    Cliente clienteEditar, ArrayList<Cliente> clientes, Empleado empleado) {

        this.vista = vista;
        this.vistaClientes = vistaClientes;
        this.vistaMaestro = vistaMaestro;
        this.vistaCita = vistaCita;
        this.acceso = acceso;
        this.c = c;
        this.clienteEditar = clienteEditar;
        this.clientes = clientes;
        this.empleado = empleado;

        // Si estamos en modo edición, precargamos datos
        if (clienteEditar != null) {
            precargarDatos();
        }

        // Eventos de botones
        vista.getBtnGuardar().addActionListener(e -> guardarCliente());
        vista.getBtnCancelar().addActionListener(e -> cancelar());
    }

    // Carga los datos del cliente en el formulario para edición
    private void precargarDatos() {
        vista.getTxtNombre().setText(clienteEditar.getNombre());
        vista.getTxtSuperpoder().setText(clienteEditar.getSuperpoder());
        vista.getTxtColor().setText(clienteEditar.getColor());
        vista.setCbTipo(clienteEditar.getTipo_heroe());
    }

    // Guarda o actualiza el cliente en la base de datos
    private void guardarCliente() {

        String nombre = vista.getTxtNombre().getText().trim();
        String superpoder = vista.getTxtSuperpoder().getText().trim();
        String color = vista.getTxtColor().getText().trim();
        String tipo = vista.getCbTipo().toString();

        // Validación de campos obligatorios
        if (nombre.isEmpty() || superpoder.isEmpty() || color.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor, rellena todos los campos del cliente.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            // ===== CASO: NUEVO CLIENTE =====
            if (clienteEditar == null) {

                acceso.insertarNuevoCliente(c, nombre, tipo, superpoder, color);
                clientes = acceso.recogeClientes(c);

                JOptionPane.showMessageDialog(vista, "Cliente creado correctamente.");

            // ===== CASO: EDITAR CLIENTE =====
            } else {

                acceso.actualizarCliente(c, clienteEditar.getId_cliente(), nombre, tipo, superpoder, color);
                clientes = acceso.recogeClientes(c);

                JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente.");
            }

            // ===== REDIRECCIÓN SEGÚN CONTEXTO =====

            // Desde ListaClientes
            if (vistaCita == null && (vistaClientes != null && vistaMaestro == null)) {

                ListaClientes lc = new ListaClientes();
                ArrayList<Traje> trajes = acceso.recogeTrajes(c);

                new ControladorListaClientes(lc, acceso, c, clientes, trajes, empleado);

                lc.setVisible(true);

                vista.dispose();
                vistaClientes.dispose();

            // Desde VentanaMaestro
            } else if (vistaCita == null && vistaClientes == null && vistaMaestro != null) {

                ListaClientes lc = new ListaClientes();
                ArrayList<Traje> trajes = acceso.recogeTrajes(c);

                new ControladorListaClientes(lc, acceso, c, clientes, trajes, empleado);

                vista.dispose();

            // Desde NuevaCita
            } else if (vistaCita != null && vistaClientes == null) {

                vista.dispose();
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(vista,
                    "Error al guardar el cliente en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cancela la operación y cierra la ventana actual
    private void cancelar() {

        if (vistaCita == null && (vistaClientes != null || vistaMaestro != null)) {
            vista.dispose();
        } else if (vistaCita != null && vistaClientes == null) {
            vista.dispose();
        }
    }
}