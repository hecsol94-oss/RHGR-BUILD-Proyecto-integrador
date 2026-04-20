package modelo;

public class Traje {
	
	// Atributos del traje y su vinculación con el cliente (FK id_cliente)
	private int id_traje;
	private String nombre_traje;
	private String estado;
	private int id_cliente;
	
	// Constructor que permite asociar un traje específico a un superhéroe
	public Traje (int id_traje, String nombre, String estado, int id_cliente) {
		this.id_traje = id_traje;
		this.nombre_traje = nombre;
		this.estado = estado;
		this.id_cliente = id_cliente;
	}

	// Getters y Setters para monitorizar el estado y dueño de la prenda
	public String getNombre_traje() {
		return nombre_traje;
	}

	public void setNombre_traje(String nombre_traje) {
		this.nombre_traje = nombre_traje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_traje() {
		return id_traje;
	}

	public void setId_traje(int id_traje) {
		this.id_traje = id_traje;
	}
	
}