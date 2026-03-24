package excepciones;

import controlador.InicioSesionXVentanas;
import vista.InicioSesion;

public class UsuarioInexistente extends Exception {

	public UsuarioInexistente() {
		super("No existe ningun usuario con esas credenciales");
		// TODO Auto-generated constructor stub
	}

}
