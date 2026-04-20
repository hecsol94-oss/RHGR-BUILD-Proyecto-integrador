package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Traje;
import vista.ListaClientes;
import vista.DetalleClientes;
import vista.NuevoCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

// Controlador de la ventana ListaClientes
// El parámetro 'editable' controla si el usuario puede crear/editar/eliminar clientes
public class ControladorListaClientes {

    private ListaClientes vista;
    private AccesoBBDD acceso;
    private Connection c;
    private ArrayList<Cliente> clientes;
    private ArrayList<Cliente> clientesFiltrados;
    private boolean editable;

    // Constructor: carga la tabla y asigna los listeners
    public ControladorListaClientes(ListaClientes vista, AccesoBBDD acceso, Connection c, ArrayList<Cliente> clientes, boolean editable) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.clientes = clientes;
        this.clientesFiltrados = new ArrayList<>(clientes);
        this.editable = editable;

        // Oculta los botones de gestión si el usuario no tiene permisos
        if (!editable) {
            vista.getBtnNuevo().setVisible(false);
            vista.getBtnEditar().setVisible(false);
            vista.getBtnEliminar().setVisible(false);
        }

        // Carga inicial de la tabla
        cargarTabla(clientesFiltrados);

        // Listeners de los botones de filtro por tipo
        vista.getBtnTodos().addActionListener(e -> {
            clientesFiltrados = new ArrayList<>(clientes);
            cargarTabla(clientesFiltrados);
        });
        vista.getBtnHeroe().addActionListener(e -> filtrarPorTipo("superhéroe"));
        vista.getBtnVillano().addActionListener(e -> filtrarPorTipo("villano"));

        // Listener de búsqueda
        vista.getBtnBuscar().addActionListener(e -> buscar());

        // Listener del botón "Ver detalle"
        vista.getBtnDetalle().addActionListener(e -> verDetalle());

        // Listener del botón "Editar"
        vista.getBtnEditar().addActionListener(e -> editarCliente());

        // Listener del botón "Nuevo"
        vista.getBtnNuevo().addActionListener(e -> nuevoCliente());

        // Listener del botón "Eliminar"
        vista.getBtnEliminar().addActionListener(e -> eliminarCliente());

        // Listener del botón "Volver"
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    // Carga la lista de clientes en la tabla
    private void cargarTabla(ArrayList<Cliente> lista) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTable().getModel();
        modelo.setRowCount(0);

        for (Cliente cliente : lista) {
            modelo.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getSuperpoder(),
                cliente.getTipo_heroe(),
                "-"  // Número de trajes: se puede cruzar con recogeTrajes si se necesita
            });
        }
    }

    // Filtra los clientes por tipo (superhéroe / villano)
    private void filtrarPorTipo(String tipo) {
        clientesFiltrados = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getTipo_heroe().equalsIgnoreCase(tipo)) {
                clientesFiltrados.add(cliente);
            }
        }
        cargarTabla(clientesFiltrados);
    }

    // Filtra los clientes por nombre introducido en el campo de búsqueda
    private void buscar() {
        String texto = vista.getTxtBuscar().getText().trim().toLowerCase();
        ArrayList<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(texto)) {
                resultado.add(cliente);
            }
        }
        clientesFiltrados = resultado;
        cargarTabla(clientesFiltrados);
    }

    // Abre la ventana de detalle del cliente seleccionado
    private void verDetalle() {
        int fila = vista.getTable().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un cliente para ver los detalles.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente cliente = clientesFiltrados.get(fila);
        DetalleClientes vistaDetalle = new DetalleClientes();
        new ControladorDetalleClientes(vistaDetalle, acceso, c, cliente, editable);
        vistaDetalle.setVisible(true);
    }

    // Abre el formulario de edición para el cliente seleccionado
    private void editarCliente() {
        if (!editable) return;
        int fila = vista.getTable().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un cliente para editar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente cliente = clientesFiltrados.get(fila);
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, acceso, c, cliente);
        vistaForm.setVisible(true);
    }

    // Abre el formulario para crear un nuevo cliente
    private void nuevoCliente() {
        if (!editable) return;
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, acceso, c, null);
        vistaForm.setVisible(true);
    }

    // Elimina el cliente seleccionado tras confirmación
    private void eliminarCliente() {
        if (!editable) return;
        int fila = vista.getTable().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un cliente para eliminar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente cliente = clientesFiltrados.get(fila);
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Estás seguro de que quieres eliminar a " + cliente.getNombre() + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
                acceso.eliminarCliente(c, cliente.getId_cliente());
                clientes.remove(cliente);
                clientesFiltrados.remove(cliente);
                cargarTabla(clientesFiltrados);
                JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente.");
            
        }
    }
}
