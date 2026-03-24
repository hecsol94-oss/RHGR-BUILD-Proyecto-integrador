package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import excepciones.UsuarioInexistente;
import vista.InicioSesion;
import vista.VentanaAprendiz;
import vista.VentanaMaestro;
import vista.VentanaOficial;

public class InicioSesionXVentanas {
	
	private InicioSesion inicioSesion;
	private VentanaMaestro ventanaMaestro;
	private VentanaOficial ventanaOficial;
	private VentanaAprendiz ventanaAprendiz1;
	private VentanaAprendiz ventanaAprendiz2;

	
	public InicioSesionXVentanas (InicioSesion inicioSesion2) {
		inicioSesion = inicioSesion2;
	}
	
	public void ActionPerformed (ActionEvent e) {
		String nombre = inicioSesion.getInfoNombre();
		char[] contrasenia = inicioSesion.getInfoContrasenia();
		
		try {
			if (nombre.equals("MrMaestro") &&  contrasenia.equals("MrMaestro1234")) {
				String respuesta = "¡Bienvenido, maestro!";
				inicioSesion.setVisible(false);
				ventanaMaestro.setVisible(true);
				
			} else if (nombre.equals("MrOficial") &&  contrasenia.equals("MrOficial1234")) {
				String respuesta = "¡Bienvenido, oficial!";
				inicioSesion.setVisible(false);
				ventanaOficial.setVisible(true);
				
			} else if (nombre.equals("MrAprendiz1") &&  contrasenia.equals("MrAprendiz11234")) {
				String respuesta = "¡Bienvenido, aprendiz 1!";
				inicioSesion.setVisible(false);
				ventanaAprendiz1.setVisible(true);
				
			} else if (nombre.equals("MrMaestro2") &&  contrasenia.equals("MrAprendiz21234")) {
				String respuesta = "¡Bienvenido, aprendiz 2!";
				inicioSesion.setVisible(false);
				ventanaAprendiz2.setVisible(true);
				
			} 
		} catch(NumberFormatException ue) {
			String respuesta = ue.getMessage();
			inicioSesion.setRespuesta(respuesta);
			
		}

	}
	
	
	
	

}
