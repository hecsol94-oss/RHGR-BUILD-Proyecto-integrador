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

    // Vista principal de detalle de cliente
    private DetalleClientes vista;

    // Acceso a base de datos
    private AccesoBBDD acceso;

    // Conexión activa
    private Connection c;

    // Cliente actual que se está mostrando
    private Cliente cliente;

    // Lista de trajes del cliente
    private ArrayList<Traje> trajes;

    // Empleado logueado
    private Empleado empleado;

    public ControladorDetalleClientes(DetalleClientes vista, AccesoBBDD acceso, Connection c, Cliente cliente, ArrayList<Traje> trajes, Empleado empleado) {

        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.cliente = cliente;
        this.trajes = trajes;
        this.empleado = empleado;
        
        // Inicializar listeners en el constructor (acciones de botones)
        this.vista.getBtnEditar().addActionListener(e -> irAEditarTraje());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarTrajeSeleccionado());
        this.vista.getBtnVolver().addActionListener(e -> volverALista());
        this.vista.getBtnNuevoTraje().addActionListener(e -> abrirNuevoTraje());
        
        // Carga de datos del cliente en la vista
        vista.getNombreCliente().setText(cliente.getNombre());
        vista.getTipoHeroeCliente().setText(cliente.getTipo_heroe());
        vista.getSuperpoderCliente().setText(cliente.getSuperpoder());
        vista.getColorCliente().setText(cliente.getColor());
        
        // Botón "Nuevo Traje" (aunque no esté físicamente, se define la lógica)
        // Si lo añades a la vista, descomenta la siguiente línea:
        // this.vista.getBtnNuevo().addActionListener(e -> abrirNuevoTraje());
    }

    // Abre la pantalla de edición de traje seleccionado
    private void irAEditarTraje() {

        // Objeto por defecto
        Traje trajeSeleccionado = new Traje(0, "", "", 0);

        // Texto seleccionado en la lista
        String seleccionado = vista.getListTrajes().getSelectedValue();

        // Buscar el traje correspondiente
        for (Traje t : trajes) {

            String texto = t.getNombre_traje() + " - " + t.getEstado();
            
            if (texto.equals(seleccionado)) {
                trajeSeleccionado = t;
                break;
            }
        }

        // Si existe traje seleccionado, abrir formulario de edición
        if (trajeSeleccionado != null) {

            NuevoTraje vistaNuevo = new NuevoTraje();

            new ControladorNuevoTraje(vistaNuevo, acceso, c, cliente, trajes, trajeSeleccionado, empleado, vista, null);

            vistaNuevo.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un traje para editar.");
        }
    }

    // Elimina el traje seleccionado de la lista
    private void eliminarTrajeSeleccionado() {

        int index = vista.getListTrajes().getSelectedIndex();

        if (index != -1) {

            // Confirmación antes de borrar
            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Estas seguro de que quieres eliminar este traje?, \nSi lo llegas a eliminar, las citas asociadas a el se eliminan tambien",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {

                // Eliminar de la vista
                vista.getModeloLista().remove(index);

                // Obtener objeto traje
                Traje traje = trajes.get(index);

                // Eliminar en base de datos
                acceso.eliminarTraje(c, traje.getId_traje());

                // Recargar lista de trajes del cliente
                trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

                // Actualizar vista
                vista.recogerDatos(trajes);

                JOptionPane.showMessageDialog(vista, "Traje eliminado correctamente.");
            }

        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un traje para eliminar.");
        }
    }

    // Abre ventana para crear un nuevo traje
    private void abrirNuevoTraje() {

        NuevoTraje vistaNuevo = new NuevoTraje();

        // Se pasa la vista actual para poder actualizar datos al volver
        new ControladorNuevoTraje(vistaNuevo, acceso, c, cliente, trajes, null, empleado, this.vista, null);

        vistaNuevo.setVisible(true);
    }

    // Vuelve a la lista de clientes
    private void volverALista() {

        ArrayList<Cliente> clientes;
        ArrayList<Traje> trajesPorCliente;

        try {

            // Recarga datos desde BBDD
            clientes = acceso.recogeClientes(c);
            trajesPorCliente = acceso.recogeTrajes(c);

            // Abre lista de clientes
            ListaClientes lista = new ListaClientes();

            new ControladorListaClientes(lista, acceso, c, clientes, trajesPorCliente, empleado);

            lista.setVisible(true);

            // Cierra vista actual
            vista.dispose();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}