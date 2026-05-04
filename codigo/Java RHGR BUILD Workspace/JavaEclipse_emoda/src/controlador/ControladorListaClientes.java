package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Traje;
import vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;

// Controlador de la lista de clientes
public class ControladorListaClientes {

    private ListaClientes vista;
    private AccesoBBDD acceso;
    private Connection c;
    private ArrayList<Cliente> clientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Traje> trajes;
    private ArrayList<Traje> trajesFiltrados;
    private Empleado empleado;

    public ControladorListaClientes(ListaClientes vista, AccesoBBDD acceso, Connection c,
                                    ArrayList<Cliente> clientes, ArrayList<Traje> trajes,
                                    Empleado empleado) {

        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.clientes = clientes;
        this.trajes = trajes;
        this.clientesFiltrados = new ArrayList<>(clientes);
        this.trajesFiltrados = new ArrayList<>(trajes);
        this.empleado = empleado;

        cargarTabla(clientesFiltrados, trajesFiltrados);

        vista.getBtnTodos().addActionListener(e -> {
            clientesFiltrados = new ArrayList<>(clientes);
            cargarTabla(clientesFiltrados, trajesFiltrados);
        });

        vista.getBtnHeroe().addActionListener(e -> filtrarPorTipo("superhéroe", "superheroína"));
        vista.getBtnVillano().addActionListener(e -> filtrarPorTipo("supervillano", "supervillana"));
        vista.getBtnBuscar().addActionListener(e -> buscar());

        vista.getBtnDetalle().addActionListener(e -> verDetalle());
        vista.getBtnEditar().addActionListener(e -> editarCliente());
        vista.getBtnNuevo().addActionListener(e -> nuevoCliente());
        vista.getBtnEliminar().addActionListener(e -> eliminarCliente());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    // Carga la tabla de clientes
    public void cargarTabla(ArrayList<Cliente> clientes, ArrayList<Traje> trajes) {

        DefaultTableModel modelo = (DefaultTableModel) vista.getTable().getModel();
        modelo.setRowCount(0);

        for (Cliente cliente : clientes) {

            StringBuilder nombreTraje = new StringBuilder();

            for (Traje traje : trajes) {
                if (cliente.getId_cliente() == traje.getId_cliente()) {
                    if (nombreTraje.length() > 0) nombreTraje.append(", ");
                    nombreTraje.append(traje.getNombre_traje());
                }
            }

            modelo.addRow(new Object[]{
                    cliente.getNombre(),
                    cliente.getSuperpoder(),
                    cliente.getTipo_heroe(),
                    nombreTraje.toString()
            });
        }
    }

    // Filtra por tipo de héroe o villano
    private void filtrarPorTipo(String tipoHombre, String tipoMujer) {

        clientesFiltrados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (cliente.getTipo_heroe().equalsIgnoreCase(tipoHombre)
                    || cliente.getTipo_heroe().equalsIgnoreCase(tipoMujer)) {
                clientesFiltrados.add(cliente);
            }
        }

        cargarTabla(clientesFiltrados, trajesFiltrados);
    }

    // Búsqueda por nombre
    private void buscar() {

        String texto = vista.getTxtBuscar().getText().trim().toLowerCase();

        ArrayList<Cliente> resultado = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(texto)) {
                resultado.add(cliente);
            }
        }

        clientesFiltrados = resultado;
        cargarTabla(clientesFiltrados, trajesFiltrados);
    }

    // Ver detalle del cliente
    private void verDetalle() {

        int fila = vista.getTable().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona un cliente para ver los detalles.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = clientesFiltrados.get(fila);

        DetalleClientes vistaDetalle = new DetalleClientes();
        ArrayList<Traje> trajesXCliente = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

        vistaDetalle.recogerDatos(trajesXCliente);

        new ControladorDetalleClientes(vistaDetalle, acceso, c, cliente, trajesXCliente, empleado);

        vistaDetalle.setVisible(true);
        vista.dispose();
    }

    // Editar cliente
    private void editarCliente() {

        int fila = vista.getTable().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona un cliente para editar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = clientesFiltrados.get(fila);

        NuevoCliente vistaForm = new NuevoCliente();

        new ControladorNuevoCliente(vistaForm, vista, null, null, acceso, c, cliente, clientes, empleado);

        vistaForm.setVisible(true);
    }

    // Nuevo cliente
    private void nuevoCliente() {

        NuevoCliente vistaForm = new NuevoCliente();

        new ControladorNuevoCliente(vistaForm, vista, null, null, acceso, c, null, clientes, empleado);

        vistaForm.setVisible(true);
    }

    // Eliminar cliente
    private void eliminarCliente() {

        int fila = vista.getTable().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona un cliente para eliminar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = clientesFiltrados.get(fila);

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Seguro que quieres eliminar a " + cliente.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            acceso.eliminarCliente(c, cliente.getId_cliente());

            clientes.remove(cliente);
            clientesFiltrados.remove(cliente);

            cargarTabla(clientesFiltrados, trajesFiltrados);

            JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente.");
        }
    }

    // Volver según rol
    private void volver() {

        try {
            String rol = empleado.getCategoria().toLowerCase();

            switch (rol) {

                case "maestro":
                    VentanaMaestro vm = new VentanaMaestro();
                    new ControladorMaestro(vm, acceso, c, empleado);
                    vm.setVisible(true);
                    break;

                case "oficial":
                    VentanaOficial vo = new VentanaOficial();
                    new ControladorOficial(vo, acceso, c, empleado);
                    vo.setVisible(true);
                    break;
            }

            vista.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}