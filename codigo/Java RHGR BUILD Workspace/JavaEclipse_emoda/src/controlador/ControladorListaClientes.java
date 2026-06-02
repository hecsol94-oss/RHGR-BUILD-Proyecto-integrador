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

/**
 * Controlador para la gestión y visualización de la lista de clientes.
 * Permite filtrar clientes por tipo, realizar búsquedas por nombre, ver detalles
 * específicos y realizar operaciones de creación, edición y borrado.
 */
public class ControladorListaClientes {

    private ListaClientes vista;
    private AccesoBBDD acceso;
    private Connection c;
    private ArrayList<Cliente> clientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Traje> trajes;
    private ArrayList<Traje> trajesFiltrados;
    private Empleado empleado;

    /**
     * Constructor del controlador de la lista de clientes.
     * Inicializa los datos y configura todos los escuchadores de eventos para la interfaz.
     * 
     * @param vista    La ventana que muestra la tabla de clientes.
     * @param acceso   Objeto de acceso a datos para operaciones en BD.
     * @param c        Conexión activa a la base de datos.
     * @param clientes Lista completa de clientes registrados.
     * @param trajes   Lista completa de trajes asociados a clientes.
     * @param empleado Empleado que está utilizando la aplicación (para control de roles).
     */
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

        /**
         *  Evento para resetear filtros y mostrar todos los clientes
         */
        vista.getBtnTodos().addActionListener(e -> {
            clientesFiltrados = new ArrayList<>(clientes);
            cargarTabla(clientesFiltrados, trajesFiltrados);
        });

        /**
         *  Configuración de botones de filtrado y búsqueda
         */
        vista.getBtnHeroe().addActionListener(e -> filtrarPorTipo("superhéroe", "superheroína"));
        vista.getBtnVillano().addActionListener(e -> filtrarPorTipo("supervillano", "supervillana"));
        vista.getBtnBuscar().addActionListener(e -> buscar());

        /**
         *  Configuración de acciones CRUD y navegación
         */
        vista.getBtnDetalle().addActionListener(e -> verDetalle());
        vista.getBtnEditar().addActionListener(e -> editarCliente());
        vista.getBtnNuevo().addActionListener(e -> nuevoCliente());
        vista.getBtnEliminar().addActionListener(e -> eliminarCliente());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    /**
     * Actualiza el modelo de la tabla en la vista con los datos proporcionados.
     * Concatena los nombres de los trajes que pertenecen a cada cliente para mostrarlos en una sola celda.
     * 
     * @param clientes Lista de clientes a mostrar.
     * @param trajes   Lista de trajes para cruzar información de propiedad.
     */
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

    /**
     * Filtra la lista actual basándose en la categoría (Héroe/Villano) y su variante de género.
     * 
     * @param tipoHombre Etiqueta para el género masculino (ej. "superhéroe").
     * @param tipoMujer  Etiqueta para el género femenino (ej. "superheroína").
     */
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

    /**
     * Realiza una búsqueda parcial en la lista de clientes basándose en el 
     * texto introducido en el campo de búsqueda de la vista.
     */
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

    /**
     * Obtiene el cliente seleccionado y abre la ventana de detalles.
     * Carga específicamente los trajes pertenecientes a dicho cliente desde la BD.
     */
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

    /**
     * Abre el formulario de edición para el cliente seleccionado en la tabla.
     */
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

    /**
     * Abre un formulario vacío para registrar un nuevo cliente en el sistema.
     */
    private void nuevoCliente() {
        NuevoCliente vistaForm = new NuevoCliente();
        new ControladorNuevoCliente(vistaForm, vista, null, null, acceso, c, null, clientes, empleado);
        vistaForm.setVisible(true);
    }

    /**
     * Elimina el cliente seleccionado tanto de la base de datos como de las listas en memoria.
     * Requiere confirmación por parte del usuario.
     */
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

    /**
     * Cierra la vista actual y regresa a la ventana principal del empleado,
     * determinando cuál abrir mediante su categoría (Maestro u Oficial).
     */
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
                case "aprendiz":
                	VentanaAprendiz va = new VentanaAprendiz();
                	new ControladorAprendiz(va, acceso, c, empleado);
                	va.setVisible(true);
                	break;
            }
            vista.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}