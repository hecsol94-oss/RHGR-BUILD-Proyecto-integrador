package modelo;

public class Cita_Aprendiz {
	
	private int id_aprendiz;
	private int id_cita;
	private int id_empleado;
	
	public Cita_Aprendiz(int id_aprendiz, int id_cita, int id_empleado) {
		this.id_aprendiz = id_aprendiz;
		this.id_cita = id_cita;
		this.id_empleado = id_empleado;
	}

	public int getId_aprendiz() {
		return id_aprendiz;
	}

	public void setId_aprendiz(int id_aprendiz) {
		this.id_aprendiz = id_aprendiz;
	}

	public int getId_cita() {
		return id_cita;
	}

	public void setId_cita(int id_cita) {
		this.id_cita = id_cita;
	}

	public int getId_empleado() {
		return id_empleado;
	}

	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}
	
	

}
