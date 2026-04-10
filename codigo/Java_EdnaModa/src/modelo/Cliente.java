package modelo;

public class Cliente {
	
	// Atributos privados que representan las columnas de la tabla Cliente en la BBDD
	private int id_cliente;
	private String nombre;
	private String tipo_heroe;
	private String superpoder;
	private String color;
	
	// Constructor para instanciar el objeto con todos los datos (incluyendo el ID autogenerado)
	public Cliente(int id_cliente, String nombre, String tipo_heroe, String superpoder, String color) {
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.tipo_heroe = tipo_heroe;
		this.superpoder = superpoder;
		this.color = color;
		
	}

	// Métodos Getter y Setter para el acceso controlado a los atributos del cliente
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo_heroe() {
		return tipo_heroe;
	}

	public void setTipo_heroe(String tipo_heroe) {
		this.tipo_heroe = tipo_heroe;
	}

	public String getSuperpoder() {
		return superpoder;
	}

	public void setSuperpoder(String superpoder) {
		this.superpoder = superpoder;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	
}