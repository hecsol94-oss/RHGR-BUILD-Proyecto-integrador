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
// Puede usarse tanto para crear un nuevo cliente como para editar uno existente
public class ControladorNuevoCliente {

    private NuevoCliente vista;
    private ListaClientes vistaClientes;
    private VentanaMaestro vistaMaestro;
    private NuevaCita vistaCita;
    private AccesoBBDD acceso;
    private Connection c;
    private Cliente clienteEditar; // null si es un cliente nuevo
    private ArrayList<Cliente> clientes;
    private Empleado empleado;

    // Constructor: si clienteEditar es null, se crea un cliente nuevo
    public ControladorNuevoCliente(NuevoCliente vista, ListaClientes vistaClientes, VentanaMaestro vistaMaestro, NuevaCita vistaCita, AccesoBBDD acceso, Connection c, Cliente clienteEditar, ArrayList<Cliente> clientes, Empleado empleado) {
        this.vista = vista;
        this.vistaClientes = vistaClientes;
        this.vistaMaestro = vistaMaestro;
        this.vistaCita = vistaCita;
        this.acceso = acceso;
        this.c = c;
        this.clienteEditar = clienteEditar;
        this.clientes = clientes;
        this.empleado = empleado;

        // Si se está editando, precarga los campos con los datos del cliente
        if (clienteEditar != null) {
            precargarDatos();
        }

        // Listener del botón "Guardar Cliente"
        vista.getBtnGuardar().addActionListener(e -> guardarCliente());

        // Listener del botón "Cancelar"
        vista.getBtnCancelar().addActionListener(e -> cancelar());
    }

    // Precarga los campos con los datos del cliente a editar
    private void precargarDatos() {
        vista.getTxtNombre().setText(clienteEditar.getNombre());
        vista.getTxtSuperpoder().setText(clienteEditar.getSuperpoder());
        vista.getTxtColor().setText(clienteEditar.getColor());
        vista.setCbTipo(clienteEditar.getTipo_heroe());
        
    }

    // Valida y guarda el cliente en la BBDD
    private void guardarCliente() {
        String nombre = vista.getTxtNombre().getText().trim();
        String superpoder = vista.getTxtSuperpoder().getText().trim();
        String color = vista.getTxtColor().getText().trim();
        String tipo = vista.getCbTipo().toString();
        
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
                clientes = acceso.recogeClientes(c);
                JOptionPane.showMessageDialog(vista, "Cliente creado correctamente.");
                
            } else {
                // Actualización de cliente existente
                acceso.actualizarCliente(c, clienteEditar.getId_cliente(), nombre, tipo, superpoder, color);
                clientes = acceso.recogeClientes(c);
                JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente.");
            }
            
            if (vistaCita == null && (vistaClientes != null || vistaMaestro != null)) {
            	ListaClientes lc = new ListaClientes();
                ArrayList<Traje> trajes= acceso.recogeTrajes(c);
            	new ControladorListaClientes(lc, acceso, c, clientes, trajes, empleado);
            	lc.setVisible(true);
                vista.dispose();
            } else if (vistaCita != null && vistaClientes == null) {
            	vista.dispose();
            }
//            ListaClientes lc = new ListaClientes();
//            ArrayList<Traje> trajes= acceso.recogeTrajes(c);
//        	new ControladorListaClientes(lc, acceso, c, clientes, trajes, empleado);
//        	lc.setVisible(true);
//            vista.dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al guardar el cliente en la base de datos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancelar() {
    	
        if (vistaCita == null && (vistaClientes != null || vistaMaestro != null)) {
        	ListaClientes lc = new ListaClientes();
            ArrayList<Traje> trajes;
    		try {
    			trajes = acceso.recogeTrajes(c);
    			new ControladorListaClientes(lc, acceso, c, clientes, trajes, empleado);
    	    	lc.setVisible(true);
    	        vista.dispose();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } else if (vistaCita != null && vistaClientes == null) {
        	vista.dispose();
        }
    	
    }
}