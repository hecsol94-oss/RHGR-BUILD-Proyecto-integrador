package controlador;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cliente;
import modelo.Empleado;
import vista.*;

/**
 * Controlador para la gestión y visualización de la lista de empleados.
 * Permite filtrar empleados por categoría, realizar búsquedas por nombre y
 * realizar operaciones de creación, edición y borrado.
 */
public class ControladorListaEmpleados {

	private ListaEmpleados vista;
	private AccesoBBDD acceso;
	private Connection c;
	private ArrayList<Empleado> empleados;
	private ArrayList<Empleado> empleadosFiltrados;
	private Empleado empleado;
	
	/**
     * Constructor del controlador de la lista de empleados.
     * Inicializa los datos y configura todos los escuchadores de eventos para la interfaz.
     * 
     * @param vista La ventana que muestra la tabla de empleados.
     * @param acceso Objeto de acceso a datos para operaciones en BD.
     * @param c Conexión activa a la base de datos.
     * @param empleados Lista completa de empleados registrados.
     * @param empleado Empleado que está utilizando la aplicación (para control de roles).
     */
	public ControladorListaEmpleados(ListaEmpleados vista, AccesoBBDD acceso, Connection c,
			ArrayList<Empleado> empleados, Empleado empleado) {
		
		this.vista = vista;
		this.acceso = acceso;
		this.c = c;
		this.empleados = empleados;
		this.empleadosFiltrados = new ArrayList<>(empleados);
		this.empleado = empleado;
		
		cargarTabla(empleadosFiltrados);
		
		/**
		 *  Evento para resetear filtros y mostrar todos los empleados
		 */
        vista.getBtnTodos().addActionListener(e -> {
            empleadosFiltrados = new ArrayList<>(empleados);
            cargarTabla(empleadosFiltrados);
        });
        
        /**
         *  Configuración de botones de filtrado y búsqueda
         */
        vista.getBtnAprendiz().addActionListener(e -> filtrarPorCategoria("aprendiz"));
        vista.getBtnOficial().addActionListener(e -> filtrarPorCategoria("oficial"));
        vista.getBtnMaestro().addActionListener(e -> filtrarPorCategoria("maestro"));
        vista.getBtnBuscar().addActionListener(e -> buscar());
        
        /**
         *  Configuración de acciones CRUD y navegación
         */
        vista.getBtnEditar().addActionListener(e -> editarEmpleado());
        vista.getBtnNuevo().addActionListener(e -> nuevoEmpleado());
        vista.getBtnEliminar().addActionListener(e -> eliminarEmpleado());
        vista.getBtnVolver().addActionListener(e -> volver());
	}
	
	/**
     * Actualiza el modelo de la tabla en la vista con los datos proporcionados.
     * 
     * @param empleados Lista de empleados a mostrar.
     */
	public void cargarTabla(ArrayList<Empleado> empleados) {
		
		DefaultTableModel modelo = (DefaultTableModel) vista.getTable().getModel();
        modelo.setRowCount(0);
        
        for (Empleado empleado : empleados) {
        	modelo.addRow(new Object[]{
            		empleado.getApodo(),
            		empleado.getNombre(),
            		empleado.getApellido(),
            		empleado.getCategoria()
            });
        }
	}
	
	/**
	 * Filtra la lista actual basándose en la categoría.
	 * 
	 * @param categoria Etiqueta para la categoría.
	 */
	private void filtrarPorCategoria(String categoria) {
        empleadosFiltrados = new ArrayList<>();

        for (Empleado empleado : empleados) {
            if (empleado.getCategoria().equalsIgnoreCase(categoria)
                    || empleado.getCategoria().equalsIgnoreCase(categoria)
            		|| empleado.getCategoria().equalsIgnoreCase(categoria)) {
                empleadosFiltrados.add(empleado);
            }
        }
        cargarTabla(empleadosFiltrados);
    }
	
	/**
     * Realiza una búsqueda parcial en la lista de empleados basándose en el 
     * texto introducido en el campo de búsqueda de la vista.
     */
    private void buscar() {
        String texto = vista.getTxtBuscar().getText().trim().toLowerCase();
        ArrayList<Empleado> resultado = new ArrayList<>();

        for (Empleado empleado : empleados) {
            if (empleado.getNombre().toLowerCase().contains(texto)) {
                resultado.add(empleado);
            }
        }

        empleadosFiltrados = resultado;
        cargarTabla(empleadosFiltrados);
    }
	
	/**
     * Abre el formulario de edición para el empleado seleccionado en la tabla.
     */
    private void editarEmpleado() {
        int fila = vista.getTable().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona un empleado para editar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Empleado empleadoEditar = empleadosFiltrados.get(fila);
        NuevoEmpleado vistaForm = new NuevoEmpleado();
        new ControladorNuevoEmpleado(vistaForm, vista, null, null, acceso, c, empleadoEditar, empleados, empleado);
        vistaForm.setVisible(true);
    }
    
    /**
     * Abre un formulario vacío para registrar un nuevo empleado en el sistema.
     */
    private void nuevoEmpleado() {
        NuevoEmpleado vistaForm = new NuevoEmpleado();
        new ControladorNuevoEmpleado(vistaForm, vista, null, null, acceso, c, null, empleados, empleado);
        vistaForm.setVisible(true);
    }
    
    /**
     * Elimina el empleado seleccionado tanto de la base de datos como de las listas en memoria.
     * Requiere confirmación por parte del usuario.
     */
    private void eliminarEmpleado() {
        int fila = vista.getTable().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona un empleado para eliminar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Empleado empleado = empleadosFiltrados.get(fila);
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Seguro que quieres eliminar a " + empleado.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            acceso.eliminarEmpleado(c, empleado.getId_empleado());
            empleados.remove(empleado);
            empleadosFiltrados.remove(empleado);
            cargarTabla(empleadosFiltrados);
            JOptionPane.showMessageDialog(vista, "Empleado eliminado correctamente.");
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
            }
            vista.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
