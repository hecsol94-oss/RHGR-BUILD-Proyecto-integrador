package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import modelo.AccesoBBDD;
import modelo.Empleado;
import vista.ListaEmpleados;
import vista.NuevaCita;
import vista.NuevoEmpleado;
import vista.VentanaMaestro;

/**
 * Controlador del formulario NuevoEmpleado.
 * Permite gestionar la creación de un empleado nuevo o la edición de uno existente,
 * manejando la persistencia en la base de datos y la navegación entre vistas.
 */
public class ControladorNuevoEmpleado {

	// Referencias a vistas y capa de datos
	private NuevoEmpleado vista;
	private ListaEmpleados vistaEmpleados;
	private VentanaMaestro vistaMaestro;
	private NuevaCita vistaCita;
	private AccesoBBDD acceso;
	private Connection c;
	
	// Empleado en edición (null si es nuevo)
	private Empleado empleadoEditar;
	
	// Lista de empleados en memoria
	private ArrayList<Empleado> empleados;
	
	
	private Empleado empleado;
	
	 /**
     * Constructor principal del controlador.
     * Inicializa las referencias, carga los datos si es edición y configura los listeners de la vista.
     * 
     * @param vista Ventana del formulario NuevoEmpleado.
     * @param vistaEmpleados Referencia a la lista de empleados (ventana origen).
     * @param vistaMaestro Referencia a la ventana del maestro (ventana origen).
     * @param vistaCita Referencia a la ventana de nueva cita (ventana origen).
     * @param acceso Capa de acceso a la base de datos.
     * @param c Conenxión activa a la base de datos.
     * @param empleadoEditar Instancia del empleado a editar, o null si se crea uno nuevo.
     * @param empleados Lista actual de empleados en memoria.
     * @param empleado Empleado que está realizando la operación.
     */
	public ControladorNuevoEmpleado(NuevoEmpleado vista, ListaEmpleados vistaEmpleados, VentanaMaestro vistaMaestro,
			NuevaCita vistaCita, AccesoBBDD acceso, Connection c, Empleado empleadoEditar, ArrayList<Empleado> empleados,
			Empleado empleado) {
		
		this.vista = vista;
		this.vistaEmpleados = vistaEmpleados;
		this.vistaMaestro = vistaMaestro;
		this.vistaCita = vistaCita;
		this.acceso = acceso;
		this.c = c;
		this.empleadoEditar = empleadoEditar;
		this.empleados = empleados;
		this.empleado = empleado;
		
		// Si estamos en modo edición, precargamos datos
        if (empleadoEditar != null) {
            precargarDatos();
        }
        
        // Eventos de botones
        vista.getBtnGuardar().addActionListener(e -> guardarEmpleado());
        vista.getBtnCancelar().addActionListener(e -> cancelar());
	}
	
	/**
     * Carga los datos del empleado seleccionado en los campos del formulario.
     * Se utiliza exclusivamente cuando el controlador se inicia en modo edición.
     */
	private void precargarDatos() {
		vista.getTxtNombre().setText(empleadoEditar.getNombre());
		vista.getTxtApellido().setText(empleadoEditar.getApellido());
		vista.getTxtApodo().setText(empleadoEditar.getApodo());
		vista.getTxtUsuario().setText(empleadoEditar.getUsuario());
		vista.getContraseñaCampo().setText(empleadoEditar.getContrasena());
		vista.getConfirmarContraseña().setText(empleadoEditar.getContrasena());
		vista.setCbTipo(empleadoEditar.getCategoria());
	}
	
	/**
     * Procesa el guardado del empleado. 
     * Valida los campos, determina si es una inserción o una actualización en la base de datos
     * y gestiona el cierre de ventanas y refresco de listas según el contexto de navegación.
     */
	private void guardarEmpleado() {
		String apodo = vista.getTxtApodo().getText().trim();
		String nombre = vista.getTxtNombre().getText().trim();
		String apellido = vista.getTxtApellido().getText().trim();
		String usuario = vista.getTxtUsuario().getText().trim();
		String contraseña = vista.getContraseñaCampo().getText().trim();
		String confirmarContraseña = vista.getConfirmarContraseña().getText().trim();
		String categoria = vista.getCbTipo().toString();
		
		// Validación de campos obligatorios
		if (apodo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty() || categoria.isEmpty()) {
			JOptionPane.showMessageDialog(vista,
					"Por favor, rellena todos los campos del empleado.",
					"Campos incompletos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			// ===== CASO: NUEVO EMPLEADO =====
			if (empleadoEditar == null) {
				
				acceso.insertarNuevoEmpleado(c, categoria, nombre, apellido, apodo, usuario, contraseña, confirmarContraseña);
				empleados = acceso.recogeEmpleados(c);
				
				JOptionPane.showMessageDialog(vista, "Empleado creado correctamente");
				
			// ===== CASO: EDITAR EMPLEADO =====
			} else {
				
				acceso.actualizarEmpleado(c, empleadoEditar.getId_empleado(), categoria, nombre, apellido, apodo, usuario, contraseña, confirmarContraseña);
				empleados = acceso.recogeEmpleados(c);
				
				JOptionPane.showMessageDialog(vista, "Empleado actualizado correctamente.");
			}
			
			// ===== REDIRECCIÓN SEGÚN CONTEXTO =====
			
			// Desde ListaEmpleados
			if (vistaCita == null && (vistaEmpleados != null && vistaMaestro != null)) {
				
				ListaEmpleados le = new ListaEmpleados();
				
				new ControladorListaEmpleados(le, acceso, c, empleados, empleado);
				
				le.setVisible(true);
				
				vista.dispose();
				vistaEmpleados.dispose();
				
			// Desde VentanaMaestro
			} else if (vistaCita == null && vistaEmpleados == null && vistaMaestro != null) {
				
				ListaEmpleados le = new ListaEmpleados();
				
				new ControladorListaEmpleados(le, acceso, c, empleados, empleado);
				
				vista.dispose();
				
			// Desde NuevaCita
			} else if (vistaCita != null && vistaEmpleados == null) {
				
				vista.dispose();
			}
			
		} catch (SQLException ex) {
			
			ex.printStackTrace();
			
			JOptionPane.showMessageDialog(vista,
					"Error al guardar el empleado en la base de datos.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
     * Cancela la operación actual.
     * Cierra la ventana del formulario sin realizar cambios en la persistencia.
     */
	private void cancelar() {
		
		if (vistaCita == null && (vistaEmpleados != null || vistaMaestro != null)) {
			vista.dispose();
		} else if (vistaCita != null && vistaEmpleados == null) {
			vista.dispose();
		}
	}
}
