package modelo;

public class Taller {
	
	// Atributos que definen las salas disponibles en la mansión de Edna
	private int id_sala;
	private String nombre;
	private String tipo;

	// Constructor para mapear los datos de las salas de la BBDD
	/**
	 * 
	 * @param id_sala
	 * @param nombre
	 * @param tipo
	 */
	public Taller(int id_sala, String nombre, String tipo) {
		this.id_sala = id_sala;
		this.nombre = nombre;
		this.tipo = tipo;	
	}

	/**
	 * 
	 * @return
	 */
	// Métodos de acceso para el nombre y tipo de sala de trabajo
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
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return
	 */
	public int getId_sala() {
		return id_sala;
	}

	/**
	 * 
	 * @param id_sala
	 */
	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}
	
}