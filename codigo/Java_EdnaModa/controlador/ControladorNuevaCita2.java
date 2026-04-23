/**
 * 
 */
package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cita_Aprendiz;
import modelo.Empleado;
import vista.NuevaCita2;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

// Controlador de NuevaCitaOficial
public class ControladorNuevaCita2 {
    private NuevaCita2 vista;
    private AccesoBBDD acceso;
    private Connection c;
    private Empleado empleado;
    private Cita cita;
    private ArrayList<Empleado> listaAprendices;

    public ControladorNuevaCita2(NuevaCita2 vista, AccesoBBDD acceso, Connection c, Empleado empleado, Cita cita) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;
        this.cita = cita;
        try {
			cargarDatosIniciales();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        asignarListeners();
    }

    private void cargarDatosIniciales() throws SQLException {
        try {
            listaAprendices = acceso.recogeAprendices(c);
            vista.getCbAprendiz1().removeAllItems();
            vista.getCbAprendiz2().removeAllItems();
            for (Empleado e : listaAprendices) {
                vista.getCbAprendiz1().addItem(e.getNombre());
                vista.getCbAprendiz2().addItem(e.getNombre());
            }
            // Cargar detalles de la cita en el textarea
            vista.getTxtDetalles().setText("Fecha: " + cita.getFecha() + "\nHora: " + cita.getHora_inicio()+ "\nCliente: " + cita.getId_cliente() + "\nTraje: " + cita.getId_traje() + "\nTaller: " + cita.getId_sala() + "\nDuracion: " + cita.getDuracion());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar los datos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }

    private void asignarListeners() {
        vista.getBtnGuardar().addActionListener(e -> guardarCita());
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }

    private void guardarCita() {
        // Obtener datos de la cita
        String detalles = vista.getTxtDetalles().getText();
        int indexAprendiz1 = vista.getCbAprendiz1().getSelectedIndex();
        int indexAprendiz2 = vista.getCbAprendiz2().getSelectedIndex();

        // Validar datos
        if (indexAprendiz1 < 0 && indexAprendiz2 < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione al menos un aprendiz.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
        	acceso.insertarNuevaCita(c, cita);
        }

        // Crear cita
        
        
        
        
//        ci.setDetalles(detalles);
//        ci.setIdAprendiz1(listaAprendices.get(indexAprendiz1).getId_empleado());
//        if (indexAprendiz2 >= 0) {
//            ci.setIdAprendiz2(listaAprendices.get(indexAprendiz2).getId_empleado());
    }

        // Guardar cita en la base de datos
//        acceso.insertarNuevaCita(c, ci);
//        JOptionPane.showMessageDialog(vista, "Cita guardada correctamente.");
//        vista.dispose();
}
