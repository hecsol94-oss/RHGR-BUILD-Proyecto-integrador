package main;

import modelo.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import vista.InicioSesion;

public class Main {

    public static void main(String[] args) {
    	
    	AccesoBBDD acceso = new AccesoBBDD();
	    Connection c = acceso.abrirConexion();
	    
	    try {
	    	// Secuencia lógica: Limpiar -> Insertar datos -> guardarlos en ArrayLists
	    	acceso.limpiarTablas(c);
	        
	        acceso.insertarClientes(c);
	        ArrayList<Cliente> clientes = acceso.recogeClientes(c);
	        
	        acceso.insertarEmpleados(c);
	        ArrayList<Empleado> empleados = acceso.recogeEmpleados(c);
	       
	        acceso.insertarTalleres(c);
	        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
	        
	        acceso.insertarTrajes(c);
	        ArrayList<Traje> trajes = acceso.recogeTrajes(c);
	        
	        acceso.insertarCitas(c);
	        ArrayList<Cita> citas = acceso.recogeCitas(c);
	        
	        acceso.insertarCitasAprendiz(c);
	        ArrayList<Cita_Aprendiz> citasAprendiz = acceso.recogeCitasAprendiz(c);
	        	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    

	    acceso.cerrarConexion(c);

        // Lanzamiento de la interfaz gráfica una vez preparada la base de datos
//        InicioSesion sesion = new InicioSesion();
//        sesion.setVisible(true);
    }
}