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

/**
 * Controlador para la gestión detallada de un cliente específico.
 * Administra la visualización de la información personal del cliente y permite
 * realizar operaciones de creación, edición y eliminación de sus trajes asociados.
 */
public class ControladorDetalleClientes {

    /** Vista principal de detalle de cliente */
    private DetalleClientes vista;

    /** Objeto para realizar operaciones en la base de datos */
    private AccesoBBDD acceso;

    /** Conexión activa a la base de datos */
    private Connection c;

    /** Entidad Cliente cuyos detalles se están visualizando */
    private Cliente cliente;

    /** Lista de trajes pertenecientes al cliente actual */
    private ArrayList<Traje> trajes;

    /** Empleado autenticado que realiza las operaciones */
    private Empleado empleado;

    /**
     * Constructor del controlador de detalle de clientes.
     * Inicializa los componentes, carga la información del cliente en los campos de la vista
     * y vincula los eventos de los botones a sus funciones correspondientes.
     * 
     * @param vista    Interfaz gráfica de detalles del cliente.
     * @param acceso   Clase de acceso a datos (DAO).
     * @param c        Conexión SQL activa.
     * @param cliente  Objeto Cliente a mostrar.
     * @param trajes   Lista inicial de trajes del cliente.
     * @param empleado Usuario actual del sistema.
     */
    public ControladorDetalleClientes(DetalleClientes vista, AccesoBBDD acceso, Connection c, Cliente cliente, ArrayList<Traje> trajes, Empleado empleado) {

        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.cliente = cliente;
        this.trajes = trajes;
        this.empleado = empleado;
        
        /**
         *  Inicializar listeners de las acciones de la interfaz
         */
        this.vista.getBtnEditar().addActionListener(e -> irAEditarTraje());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarTrajeSeleccionado());
        this.vista.getBtnVolver().addActionListener(e -> volverALista());
        this.vista.getBtnNuevoTraje().addActionListener(e -> abrirNuevoTraje());
        
        /**
         *  Población de datos del cliente en los componentes de la vista
         */
        vista.getNombreCliente().setText(cliente.getNombre());
        vista.getTipoHeroeCliente().setText(cliente.getTipo_heroe());
        vista.getSuperpoderCliente().setText(cliente.getSuperpoder());
        vista.getColorCliente().setText(cliente.getColor());
    }

    /**
     * Identifica el traje seleccionado en la lista de la interfaz y abre 
     * el formulario de edición (NuevoTraje) cargando sus datos actuales.
     */
    private void irAEditarTraje() {

        Traje trajeSeleccionado = null;

        /**
         *  Recuperar el texto seleccionado en el JList
         */
        String seleccionado = vista.getListTrajes().getSelectedValue();

        /**
         *  Búsqueda del objeto Traje que coincide con la selección visual
         */
        if (seleccionado != null) {
            for (Traje t : trajes) {
                String texto = t.getNombre_traje() + " - " + t.getEstado();
                if (texto.equals(seleccionado)) {
                    trajeSeleccionado = t;
                    break;
                }
            }
        }

        /**
         *  Si se encontró una coincidencia, se lanza el controlador de edición
         */
        if (trajeSeleccionado != null) {
            NuevoTraje vistaNuevo = new NuevoTraje();
            new ControladorNuevoTraje(vistaNuevo, acceso, c, cliente, trajes, trajeSeleccionado, empleado, vista, null);
            vistaNuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un traje para editar.");
        }
    }

    /**
     * Elimina el traje seleccionado de la base de datos y de la interfaz.
     * Muestra una advertencia sobre la eliminación en cascada de las citas asociadas 
     * antes de proceder con la operación.
     */
    private void eliminarTrajeSeleccionado() {

        int index = vista.getListTrajes().getSelectedIndex();

        if (index != -1) {
            /**
             *  Confirmación de seguridad
             */
            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Estás seguro de que quieres eliminar este traje?\n" +
                    "Si lo eliminas, las citas asociadas a él se eliminarán también.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                /**
                 *  Obtener el objeto antes de removerlo visualmente
                 */
                Traje traje = trajes.get(index);

                /**
                 *  Persistencia: eliminación en BD
                 */
                acceso.eliminarTraje(c, traje.getId_traje());

                /**
                 *  Sincronización: recargar lista y actualizar vista
                 */
                trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());
                vista.recogerDatos(trajes);

                JOptionPane.showMessageDialog(vista, "Traje eliminado correctamente.");
            }

        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un traje para eliminar.");
        }
    }

    /**
     * Abre el formulario para registrar un nuevo traje asociado al cliente actual.
     */
    private void abrirNuevoTraje() {
        NuevoTraje vistaNuevo = new NuevoTraje();
        /**
         *  Se pasa la referencia de la vista actual para que el nuevo controlador pueda refrescarla al terminar
         */
        new ControladorNuevoTraje(vistaNuevo, acceso, c, cliente, trajes, null, empleado, this.vista, null);
        vistaNuevo.setVisible(true);
    }

    /**
     * Cierra la ventana actual de detalles y regresa a la lista general de clientes.
     * Recarga los datos de clientes y trajes para asegurar que la lista esté actualizada.
     */
    private void volverALista() {

        ArrayList<Cliente> clientes;
        ArrayList<Traje> trajesPorCliente;

        try {
            /**
             *  Sincronización de datos globales antes de volver
             */
            clientes = acceso.recogeClientes(c);
            trajesPorCliente = acceso.recogeTrajes(c);

            ListaClientes lista = new ListaClientes();
            new ControladorListaClientes(lista, acceso, c, clientes, trajesPorCliente, empleado);

            lista.setVisible(true);
            vista.dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al recargar la lista de clientes.");
        }
    }
}