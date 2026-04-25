package controlador;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import modelo.Traje;
import vista.DetalleClientes;
import vista.ListaTalleres;
import vista.NuevoTraje;

	public class ControladorNuevoTraje {

	    private NuevoTraje vista;
	    private AccesoBBDD acceso;
	    private Connection c;
	    private Cliente cliente;
		private ArrayList<Traje> trajes;
	    private Traje trajeAEditar;
	    private Empleado empleado;
	    private DetalleClientes vistaDetalleAnterior;

	    public ControladorNuevoTraje(NuevoTraje vista, AccesoBBDD acceso, Connection c, Cliente cliente, ArrayList<Traje> trajes, Traje trajeAEditar, Empleado empleado, DetalleClientes vistaDetalleAnterior) {
	        this.vista = vista;
	        this.acceso = acceso;
	        this.c = c;
	        this.cliente = cliente;
	        this.trajes = trajes;
	        this.trajeAEditar = trajeAEditar;
	        this.empleado = empleado;
	        this.vistaDetalleAnterior = vistaDetalleAnterior;

	        if (trajeAEditar != null) {
	            vista.getNombreTraje().setText(trajeAEditar.getNombre_traje());
	            vista.setCbEstado(trajeAEditar.getEstado());

	        }
	        
	        // Listeners en el constructor
	        this.vista.getBtnGuardar().addActionListener(e -> guardarTraje());
	        this.vista.getBtnCancelar().addActionListener(e -> cancelar());
	    }

	    private void guardarTraje() {
	    	String nombre = vista.getNombreTraje().getText();
	        String estado = vista.getCbEstado().toString();

	        if (trajeAEditar == null) {
	            // NUEVO
	            acceso.insertarNuevoTraje(c, nombre, estado, cliente.getId_cliente());
	            
	            trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

				// Crea la vista de la lista para mostrar los cambios
				DetalleClientes dt = new DetalleClientes();
				dt.recogerDatos(trajes); // Carga la tabla/lista con los datos actualizados
			
				// Inicializa el controlador de la lista y muestra la ventana
				new ControladorDetalleClientes(dt, acceso, c, cliente, trajes, empleado);
				dt.setVisible(true);
			
				// Cierra la ventana actual de formulario
				vista.dispose();
	        } else {
	            // EDITAR
	            acceso.actualizarTraje(c, trajeAEditar.getId_traje(), nombre, estado);
	            
	            trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

				// Crea la vista de la lista para mostrar los cambios
				DetalleClientes dt = new DetalleClientes();
				dt.recogerDatos(trajes); // Carga la tabla/lista con los datos actualizados
			
				// Inicializa el controlador de la lista y muestra la ventana
				new ControladorDetalleClientes(dt, acceso, c, cliente, trajes, empleado);
				dt.setVisible(true);
			
				// Cierra la ventana actual de formulario
				vista.dispose();
	        }

	        JOptionPane.showMessageDialog(vista, "Guardado correctamente");
	        vista.dispose();
	    }

	    private void cancelar() {
	        // Volver sin hacer cambios
	        vistaDetalleAnterior.setVisible(true);
	        vista.dispose();
	    }
	

}
