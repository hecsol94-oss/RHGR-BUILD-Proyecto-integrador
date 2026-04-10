package modelo;

import java.sql.Time;
import java.util.Date;

public class Cita {
	
	// Atributos que representan el registro de una sesión de pruebas o diseño
	private int id_cita;
	private Date fecha;
	private Time hora_inicio;
	private int duracion;
	private int id_cliente;
	private int id_sala;
	
	// Constructor completo para gestionar la agenda del taller
	public Cita(int id_cita, Date fecha, Time hora_inicio, int duracion, int id_cliente, int id_sala) {
		this.id_cita = id_cita;
		this.fecha = fecha;
		this.hora_inicio = hora_inicio;
		this.duracion = duracion;
		this.id_cliente = id_cliente;
		this.id_sala = id_sala;
	}

	// Métodos de acceso para la gestión temporal y espacial de la cita
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Time getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(Time hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_sala() {
		return id_sala;
	}

	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}

	public int getId_cita() {
		return id_cita;
	}

	public void setId_cita(int id_cita) {
		this.id_cita = id_cita;
	}
	
}