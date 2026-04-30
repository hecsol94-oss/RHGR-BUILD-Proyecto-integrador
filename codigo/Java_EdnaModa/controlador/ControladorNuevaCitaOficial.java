package controlador;

import modelo.*;
import vista.NuevaCitaOficial;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador NuevaCitaOficial — punto 10.
 * Ahora usa el mismo flujo que Maestro: NuevaCitaMaestro de dos fases.
 * Esta clase queda para compatibilidad con llamadas existentes.
 */
public class ControladorNuevaCitaOficial {
    private final NuevaCitaOficial vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Empleado empleado;
    private final Cita cita;
    private ArrayList<Empleado> listaAprendices;

    /**
     * 
     * @param vista
     * @param acceso
     * @param c
     * @param empleado
     * @param cita
     */
    public ControladorNuevaCitaOficial(NuevaCitaOficial vista, AccesoBBDD acceso, Connection c, Empleado empleado, Cita cita) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.c        = c;
        this.empleado = empleado;
        this.cita     = cita;

        cargarDatos();
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        vista.getBtnGuardar().addActionListener(e -> guardar());
    }

    private void cargarDatos() {
        try {
            listaAprendices = acceso.recogeAprendices(c);
            vista.getCbAprendiz1().removeAllItems();
            vista.getCbAprendiz2().removeAllItems();
            vista.getCbAprendiz1().addItem("— Ninguno —");
            vista.getCbAprendiz2().addItem("— Ninguno —");
            for (Empleado e : listaAprendices) {
                vista.getCbAprendiz1().addItem(e.getNombre() + " " + e.getApellido());
                vista.getCbAprendiz2().addItem(e.getNombre() + " " + e.getApellido());
            }
            if (cita != null) {
                ArrayList<Cliente> clientes = acceso.recogeClientes(c);
                ArrayList<Taller>  talleres = acceso.recogeTalleres(c);
                ArrayList<Traje>   trajes   = acceso.recogeTrajes(c);
                vista.setFecha(String.valueOf(cita.getFecha()));
                vista.setHora(String.valueOf(cita.getHora_inicio()));
                vista.setDuracion(cita.getDuracion() + " h");
                vista.setCliente(nombrar(clientes, cita.getId_cliente()));
                vista.setTraje(nombrarTraje(trajes, cita.getId_traje()));
                vista.setTaller(nombrarTaller(talleres, cita.getId_sala()));
                vista.setOficial(nombrarEmp(acceso.recogeEmpleados(c), cita.getId_empleado()));
            }
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }

    private void guardar() {
        JOptionPane.showMessageDialog(vista, "Aprendices asignados.");
        vista.dispose();
    }

    /**
     * 
     * @param l
     * @param id
     * @return
     */
    private String nombrar(ArrayList<Cliente> l, int id) {
    	for(Cliente x:l)   
    		if(x.getId_cliente()==id)  
    			return x.getNombre();
    				return ""+id; 
    }
    /**
     * 
     * @param l
     * @param id
     * @return
     */
    private String nombrarTraje(ArrayList<Traje> l, int id) {
    	for(Traje   x:l)
    		if(x.getId_traje()==id)  
    		return x.getNombre_traje();
    			return ""+id; 
    }
    /**
     * 
     * @param l
     * @param id
     * @return
     */
    private String nombrarTaller(ArrayList<Taller> l, int id){
    	for(Taller  x:l)   if(x.getId_sala()==id)    
    		return x.getNombre(); 
    			return ""+id; 
    	}
    /**
     * 
     * @param l
     * @param id
     * @return
     */
    private String nombrarEmp(ArrayList<Empleado> l, int id) {
    	for(Empleado x:l) 
    		if(x.getId_empleado()==id)
    			return x.getNombre()+" "+x.getApellido();
    				return ""+id; 
    }
}


