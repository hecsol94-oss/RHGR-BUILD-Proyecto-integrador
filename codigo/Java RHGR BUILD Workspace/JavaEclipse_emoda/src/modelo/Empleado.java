package modelo;

public class Empleado {
	
	// Datos de identificación y credenciales del personal del taller
	private int id_empleado;
	private String categoria;
	private String nombre;
	private String apellido;
	private String apodo;
	private String usuario;
	private String contrasena;
	
	// Constructor principal para crear el objeto Empleado con la información de la BBDD
	/**
	 * 
	 * @param id_empleado
	 * @param categoria
	 * @param nombre
	 * @param apellido
	 * @param apodo
	 * @param usuario
	 * @param contrasena
	 */
	public Empleado (int id_empleado, String categoria, String nombre, String apellido, String apodo, String usuario, String contrasena) {
		this.id_empleado = id_empleado;
		this.categoria = categoria;
		this.nombre = nombre;
		this.apellido = apellido;
		this.apodo = apodo;
		this.usuario = usuario;
		this.contrasena = contrasena;
		
	}
	
	// Getters y Setters para la gestión de la información del empleado
	/**
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 * @return
	 */
	public String getCategoria() {
		return categoria;
	}


	/**
	 * 
	 * @param categoria
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	/**
	 * 
	 * @return
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * 
	 * @param apellido
	 */

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	/**
	 * 
	 * @return
	 */
	public String getApodo() {
		return apodo;
	}


	public void setApodo(String apodo) {
		this.apodo = apodo;
	}


	/**
	 * 
	 * @return
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * 
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	/**
	 * 
	 * @return
	 */
	public String getContrasena() {
		return contrasena;
	}


	/**
	 * 
	 * @param contrasena
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * 
	 * @return
	 */
	public int getId_empleado() {
		return id_empleado;
	}

	/**
	 * 
	 * @param id_empleado
	 */
	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}
	
}