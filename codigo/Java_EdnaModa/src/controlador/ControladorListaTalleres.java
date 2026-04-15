package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.AccesoBBDD;
import modelo.Empleado;
import modelo.Taller;
import vista.ListaTalleres;
import vista.NuevoCliente;
import vista.NuevoTaller;
import vista.VentanaMaestro;
import vista.VentanaOficial;

public class ControladorListaTalleres {
	
	private ListaTalleres vista;
    private AccesoBBDD acceso;
    private Connection c;
    private ArrayList<Taller> talleres;
    private Empleado emp;
    private String opcion;
    
    public ControladorListaTalleres(ListaTalleres vista, AccesoBBDD acceso, Connection c, ArrayList<Taller> talleres, Empleado emp) {
    	this.vista  = vista;
    	this.acceso = acceso;
    	this.c = c;
    	this.talleres = talleres;
    	this.opcion = "";
    	
        vista.getBtnVolver().addActionListener(e -> pulsarBtnVolver());
        vista.getBtnNuevoTaller().addActionListener(e -> pulsarBtnNuevoTaller());
        vista.getBtnEditar().addActionListener(e -> pulsarBtnEditar());
        vista.getBtnEliminar().addActionListener(e -> pulsarBtnEliminar());
        vista.getBtnVolver().addActionListener(e -> pulsarBtnVolver());
        vista.getBtnConfirmar().addActionListener(e -> pulsarBtnConfirmar());


    }
    
 // Listeners de los botones

    
    private void pulsarBtnNuevoTaller() {
    	try {
    		NuevoTaller nv = new NuevoTaller();
    		new ControladorNuevoTaller();
    		nv.setVisible(true);
    	} catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void pulsarOpcionSeleccionadaCasoEditar() {
    	if(!vista.getOpcionSeleccionada(vista.getBtnEditar()).equals("")) {
        	vista.habilitarBotonConfirmar();
        	opcion = vista.editarString();
    	}
    }
    
    private void pulsarBtnEditar() {
    	vista.deshabilitarBotones();
    	
    	
    }
    
    private void pulsarOpcionSeleccionadaCasoEliminar() {
    	if(!vista.getOpcionSeleccionada(vista.getBtnEliminar()).equals("")) {
        	vista.habilitarBotonConfirmar();
        	opcion = vista.eliminarString();

    	}
    }
    
    private void pulsarBtnEliminar() {
    	vista.deshabilitarBotones();
    	
    }
    
    private void pulsarBtnConfirmar() {
    	switch(opcion) {
    	case "editar":
    		NuevoCliente nc = new NuevoCliente();
    		nc.setVisible(true);
    	break;
    	
    	case "eliminar":
    		//borrar columna de una tabla y opcion de una lista
    	}
    }
    
    
    private void pulsarBtnVolver() {
    	String rol = emp.getCategoria().toLowerCase();

        switch (rol) {
            case "maestro":
                VentanaMaestro vMaestro = new VentanaMaestro();
                // Opcional: Configurar el nombre en la etiqueta de la ventana
                // Aquí deberías inicializar el controlador de la ventana Maestro si tienes uno
                vMaestro.setVisible(true);
                break;

            case "oficial":
                VentanaOficial vOficial = new VentanaOficial();
                // Aquí deberías inicializar el controlador de la ventana Oficial si tienes uno
                vOficial.setVisible(true);
                break;
        }
    	
    }
    

    
}
