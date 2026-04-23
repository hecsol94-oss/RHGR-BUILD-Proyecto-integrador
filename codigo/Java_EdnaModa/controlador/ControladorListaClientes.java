package controlador;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Traje;
import vista.ListaClientes;
import vista.DetalleClientes;
import vista.NuevoCliente;
import vista.VentanaMaestro;
import vista.VentanaOficial;

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
    private ArrayList<Traje> trajes;
    private ArrayList<Traje> trajesFiltrados;
    private Empleado empleado;
    private boolean editable;

    // Constructor: carga la tabla y asigna los listeners
    public ControladorListaClientes(ListaClientes vista, AccesoBBDD acceso, Connection c, ArrayList<Cliente> clientes, ArrayList<Traje> trajes, Empleado empleado, boolean editable) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.clientes = clientes;
        this.trajes = trajes;
        this.clientesFiltrados = new ArrayList<>(clientes);
        this.trajesFiltrados = new ArrayList<>(trajes);
        this.empleado = empleado;
        this.editable = editable;

        // Oculta los botones de gestión si el usuario no tiene permisos
        if (!editable) {
            vista.getBtnNuevo().setVisible(false);
            vista.getBtnEditar().setVisible(false);
            vista.getBtnEliminar().setVisible(false);
        }

        // Carga inicial de la tabla
        cargarTabla(clientesFiltrados, trajesFiltrados);

        // Listeners de los botones de filtro por tipo
        vista.getBtnTodos().addActionListener(e -> {
            clientesFiltrados = new ArrayList<>(clientes);
            cargarTabla(clientesFiltrados, trajesFiltrados);
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
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    // Carga la lista de clientes en la tabla
    private void cargarTabla(ArrayList<Cliente> clientes, ArrayList<Traje> trajes) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTable().getModel();
        String nombreTraje = "";

        for (Cliente cliente : clientes) {
        	
        	for (Traje traje : trajes) {
        		
        		if (cliente.getId_cliente() == traje.getId_cliente()) {
                	nombreTraje = nombreTraje + (traje.getNombre_traje() + ", ");
        		}
        	}
        		
        	modelo.addRow(new Object[]{
                    cliente.getNombre(),
                    cliente.getSuperpoder(),
                    cliente.getTipo_heroe(),
                    nombreTraje
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
        cargarTabla(clientesFiltrados, trajesFiltrados);
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
        cargarTabla(clientesFiltrados, trajesFiltrados);
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
        Traje traje = new Traje(0, "", "", 0);
        String nombreTraje = (String) vista.getTable().getValueAt(fila, 3);
        for (Traje trajeFiltrado : trajesFiltrados) {
        	if (trajeFiltrado.getNombre_traje().equals(nombreTraje)) {
        		traje = trajeFiltrado;
        		
        	}
        }
        DetalleClientes vistaDetalle = new DetalleClientes();
        new ControladorDetalleClientes(vistaDetalle, acceso, c, cliente, traje, empleado, editable);
        vistaDetalle.setVisible(true);
        vista.dispose();

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
        Traje traje = new Traje(0, "", "", 0);
        String nombreTraje = (String) vista.getTable().getValueAt(fila, 3);
        for (Traje trajeFiltrado : trajesFiltrados) {
        	if (trajeFiltrado.getNombre_traje().equals(nombreTraje)) {
        		traje = trajeFiltrado;
        		
        	}
        }
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, acceso, c, cliente, traje, clientes, trajes, empleado);
        vistaForm.setVisible(true);
        vista.dispose();
    }

    // Abre el formulario para crear un nuevo cliente
    private void nuevoCliente() {
        if (!editable) return;
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, acceso, c, null, null, clientes, trajes, empleado);
        vistaForm.setVisible(true);
        vista.dispose();
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
        Traje traje = trajesFiltrados.get(fila);
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Estás seguro de que quieres eliminar a " + cliente.getNombre() + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
                acceso.eliminarCliente(c, cliente.getId_cliente());
                acceso.eliminarTraje(c, traje.getId_cliente());
                clientes.remove(cliente);
                trajes.remove(traje);
                clientesFiltrados.remove(cliente);
                trajesFiltrados.remove(traje);
                cargarTabla(clientesFiltrados, trajesFiltrados);
                JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente.");
            
        }
    }
    
    private void volver() {
    	
    	try {
			String rol = empleado.getCategoria().toLowerCase();

			switch (rol) {
			case "maestro":
				VentanaMaestro vMaestro = new VentanaMaestro();
				new ControladorMaestro(vMaestro, acceso, c, empleado);
				vMaestro.setVisible(true);
				vista.dispose();
				break;

			case "oficial":
				VentanaOficial vOficial = new VentanaOficial();
				new ControladorOficial(vOficial, acceso, c, empleado);
				vOficial.setVisible(true);
				vista.dispose();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    }
}
