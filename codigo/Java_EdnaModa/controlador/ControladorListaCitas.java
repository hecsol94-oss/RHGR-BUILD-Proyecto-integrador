package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controlador ListaCitas.
 */
public class ControladorListaCitas {

    private final ListaCitas vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private ArrayList<Cita> citas;
    private ArrayList<Cita> citasFiltradas;
    private final Empleado empleado;
    private final boolean editable;

    // Listas para resolver nombres
    private ArrayList<Cliente>  listaClientes;
    private ArrayList<Taller>   listaTalleres;
    private ArrayList<Traje>    listaTrajes;
    private ArrayList<Empleado> listaEmpleados;

    public ControladorListaCitas(ListaCitas vista, AccesoBBDD acceso, Connection c,
                                  ArrayList<Cita> citas, Empleado empleado, boolean editable) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.c        = c;
        this.citas    = citas;
        this.citasFiltradas = new ArrayList<>(citas);
        this.empleado = empleado;
        this.editable = editable;

        cargarListas();

        if (!editable) {
            vista.getBtnNuevaCita().setVisible(false);
            vista.getBtnEditar().setVisible(false);
        }

        cargarTabla(citasFiltradas);

        vista.getBtnTodas().addActionListener(e  -> { citasFiltradas = new ArrayList<>(citas); cargarTabla(citasFiltradas); });
        vista.getBtnDiseno().addActionListener(e  -> filtrarPorTipo("diseño"));
        vista.getBtnCostura().addActionListener(e -> filtrarPorTipo("costura"));
        vista.getBtnPruebas().addActionListener(e -> filtrarPorTipo("pruebas"));
        vista.getBtnBuscar().addActionListener(e  -> buscar());
        vista.getBtnVerDetalles().addActionListener(e -> verDetalle());
        vista.getBtnEditar().addActionListener(e  -> editarCita());
        vista.getBtnNuevaCita().addActionListener(e -> nuevaCita());
        vista.getBtnVolver().addActionListener(e  -> vista.dispose());
    }

    private void cargarListas() {
        try {
            listaClientes  = acceso.recogeClientes(c);
            listaTalleres  = acceso.recogeTalleres(c);
            listaTrajes    = acceso.recogeTrajes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void cargarTabla(ArrayList<Cita> lista) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTableCitas().getModel();
        modelo.setRowCount(0);
        for (Cita cita : lista) {
            modelo.addRow(new Object[]{
                cita.getFecha() + " " + cita.getHora_inicio(),
                nombreCliente(cita.getId_cliente()),
                nombreTraje(cita.getId_traje()),
                nombreTaller(cita.getId_sala()),
                nombreEmpleado(cita.getId_empleado()),
                cita.getDuracion() + " h"
            });
        }
    }

    /** Filtro real usando el tipo del taller asociado a la cita. */
    private void filtrarPorTipo(String tipo) {
        citasFiltradas = citas.stream()
            .filter(cita -> {
                for (Taller t : listaTalleres)
                    if (t.getId_sala() == cita.getId_sala())
                        return t.getTipo().equalsIgnoreCase(tipo);
                return false;
            })
            .collect(Collectors.toCollection(ArrayList::new));

        if (citasFiltradas.isEmpty())
            JOptionPane.showMessageDialog(vista, "No hay citas de tipo '" + tipo + "'.", "Información", JOptionPane.INFORMATION_MESSAGE);
        cargarTabla(citasFiltradas);
    }

    private void buscar() {
        String texto = vista.getTextField().getText().trim().toLowerCase();
        citasFiltradas = new ArrayList<>();
        for (Cita cita : citas) {
            String clienteNombre = nombreCliente(cita.getId_cliente()).toLowerCase();
            if (cita.getFecha().toString().contains(texto) || clienteNombre.contains(texto))
                citasFiltradas.add(cita);
        }
        cargarTabla(citasFiltradas);
    }

    private void verDetalle() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Cita cita = citasFiltradas.get(fila);
        DetalleCita vistaDetalle = new DetalleCita();
        new ControladorDetalleCita(vistaDetalle, cita, acceso, c);
        vistaDetalle.setVisible(true);
    }

    private void editarCita() {
        if (!editable) return;
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        NuevaCitaMaestro vistaForm = new NuevaCitaMaestro();
        new ControladorNuevaCitaMaestro(vistaForm, acceso, c, empleado);
        vistaForm.setVisible(true);
    }

    private void nuevaCita() {
        if (!editable) return;
        // Maestro y Oficial usan el mismo proceso de dos fases
        NuevaCitaMaestro vistaForm = new NuevaCitaMaestro();
        new ControladorNuevaCitaMaestro(vistaForm, acceso, c, empleado);
        vistaForm.setVisible(true);
    }

    // Resolución de nombres (punto 6)
    private String nombreCliente(int id)  {
    	if(listaClientes==null) 
    		return ""+id; 
    	for(Cliente x:listaClientes) 
    		if(x.getId_cliente()==id) 
    			return x.getNombre(); return ""+id; 
    			}
    
    private String nombreTraje(int id)    {
    	if(listaTrajes==null)   
    		return ""+id; 
    	for(Traje   x:listaTrajes)   
    		if(x.getId_traje()==id)    
    			return x.getNombre_traje(); 
    	return ""+id; 
    	}
    
    private String nombreTaller(int id)   {
    	if(listaTalleres==null) 
    		return ""+id; 
    	for(Taller  x:listaTalleres) 
    		if(x.getId_sala()==id)     
    			return x.getNombre(); 
    				return ""+id; 
    }
    
    private String nombreEmpleado(int id) {
    	if(listaEmpleados==null)
    		return ""+id; 
    	for(Empleado x:listaEmpleados) 
    		if(x.getId_empleado()==id)
    			return x.getNombre()+" "+x.getApellido();
    	return ""+id; 
    	}
}
