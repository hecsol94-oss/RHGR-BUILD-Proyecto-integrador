package main;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;

import controlador.ControladorInicioSesion;
import vista.InicioSesion;

public class Main {

    public static void main(String[] args) {
    	
    	AccesoBBDD acceso = new AccesoBBDD();
	    Connection c = acceso.abrirConexion();
	    
	    ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Empleado> empleados = new ArrayList<>();
        ArrayList<Taller> talleres = new ArrayList<>();
        ArrayList<Traje> trajes = new ArrayList<>();
        ArrayList<Cita> citas = new ArrayList<>();
        ArrayList<Cita_Aprendiz> citasAprendiz = new ArrayList<>();
        

	    try {
	    	// Secuencia lógica: Limpiar -> Insertar datos -> guardarlos en ArrayLists
	    	acceso.limpiarTablas(c);
	        
	        acceso.insertarClientes(c);
	        clientes = acceso.recogeClientes(c);
	        
	        acceso.insertarEmpleados(c);
	        empleados = acceso.recogeEmpleados(c);
	       
	        acceso.insertarTalleres(c);
	        talleres = acceso.recogeTalleres(c);
	        
	        acceso.insertarTrajes(c);
	        trajes = acceso.recogeTrajes(c);
	        
	        acceso.insertarCitas(c);
	        citas = acceso.recogeCitas(c);
	        
	        acceso.insertarCitasAprendiz(c);
	        citasAprendiz = acceso.recogeCitasAprendiz(c);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    

	    acceso.cerrarConexion(c);

        InicioSesion sesion = new InicioSesion();
        
        new ControladorInicioSesion(sesion, acceso, empleados);
        
        sesion.setVisible(true);
    }
}