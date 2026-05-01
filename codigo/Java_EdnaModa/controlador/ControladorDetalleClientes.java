package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Traje;
import vista.DetalleClientes;
import vista.ListaClientes;
import vista.NuevoTraje;

public class ControladorDetalleClientes {

    private DetalleClientes vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Cliente cliente;
    private ArrayList<Traje> trajes;
    private Empleado empleado;

    public ControladorDetalleClientes(DetalleClientes vista, AccesoBBDD acceso, Connection c, Cliente cliente, ArrayList<Traje> trajes, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.cliente = cliente;
        this.trajes = trajes;
        this.empleado = empleado;
        
        // Inicializar listeners en el constructor
        this.vista.getBtnEditar().addActionListener(e -> irAEditarTraje());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarTrajeSeleccionado());
        this.vista.getBtnVolver().addActionListener(e -> volverALista());
        this.vista.getBtnNuevoTraje().addActionListener(e -> abrirNuevoTraje());
        
        vista.getNombreCliente().setText(cliente.getNombre());
        vista.getTipoHeroeCliente().setText(cliente.getTipo_heroe());
        vista.getSuperpoderCliente().setText(cliente.getSuperpoder());
        vista.getColorCliente().setText(cliente.getColor());
        // Botón "Nuevo Traje" (aunque no esté físicamente, se define la lógica)
        // Si lo añades a la vista, descomenta la siguiente línea:
        // this.vista.getBtnNuevo().addActionListener(e -> abrirNuevoTraje());
    }

    private void irAEditarTraje() {

    	Traje trajeSeleccionado = new Traje(0, "", "", 0);
    	String seleccionado = vista.getListTrajes().getSelectedValue();

    	for (Traje t : trajes) {
    	    String texto = t.getNombre_traje() + " - " + t.getEstado();
    	    
    	    if (texto.equals(seleccionado)) {
    	        trajeSeleccionado = t;
    	        break;
    	    }
    	}

        if (trajeSeleccionado != null) {

            NuevoTraje vistaNuevo = new NuevoTraje();

            new ControladorNuevoTraje(vistaNuevo, acceso, c, cliente, trajes, trajeSeleccionado, empleado, vista, null);

            vistaNuevo.setVisible(true);
            
            vista.dispose();

        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un traje para editar.");
        }
        
    }

    private void eliminarTrajeSeleccionado() {
        int index = vista.getListTrajes().getSelectedIndex();
        if (index != -1) {
        	
        	int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estas seguro de que quieres eliminar este traje?, \nSi lo llegas a eliminar, las citas asociadas a el se eliminan tambien", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {
				vista.getModeloLista().remove(index);
	            Traje traje = trajes.get(index);
	            acceso.eliminarTraje(c, traje.getId_traje());
	            trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());
	            vista.recogerDatos(trajes);
                JOptionPane.showMessageDialog(vista, "Traje eliminado correctamente.");
	        } 
		} else {
            JOptionPane.showMessageDialog(vista, "Selecciona un traje para eliminar.");
        }   
    }

    private void abrirNuevoTraje() {
        NuevoTraje vistaNuevo = new NuevoTraje();
        // Pasamos la vista actual al siguiente controlador para poder actualizar la lista
        new ControladorNuevoTraje(vistaNuevo, acceso, c, cliente, trajes, null, empleado, this.vista, null);
        vistaNuevo.setVisible(true);
        vista.dispose();
    }

    private void volverALista() {
    	ArrayList<Cliente> clientes;
    	ArrayList<Traje> trajesPorCliente;
		try {
			clientes = acceso.recogeClientes(c);
			trajesPorCliente = acceso.recogeTrajes(c);
			ListaClientes lista = new ListaClientes();
	        new ControladorListaClientes(lista, acceso, c, clientes, trajesPorCliente, empleado);
	        // Aquí se instanciaría su controlador correspondiente si fuera necesario
	        lista.setVisible(true);
	        vista.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}