package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.stream.Collectors;

public class ControladorAprendiz {

    private final VentanaAprendiz vista;
    private final AccesoBBDD      acceso;
    private final Connection      c;
    private final Empleado        empleado;

    private ArrayList<Cita>    todasCitas;
    private ArrayList<Cita>    citasFiltradas;
    private ArrayList<Taller>  todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Traje>   listaTrajes;
    private ArrayList<Cliente> todosClientes;

    public ControladorAprendiz(VentanaAprendiz vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.c        = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")");
        cargarDatosEnMemoria();
        cargarContadores();

        vista.getMenuItemListaCitas().addActionListener(e    -> mostrarListaCitas());
        vista.getMenuItemListaTalleres().addActionListener(e -> mostrarListaTalleres());

        vista.getBtnVolverCitas().addActionListener(e    -> vista.mostrarCard(VentanaAprendiz.CARD_DASHBOARD));
        vista.getBtnVolverTalleres().addActionListener(e -> vista.mostrarCard(VentanaAprendiz.CARD_DASHBOARD));

        // Aprendiz solo puede ver, no editar
        vista.deshabilitarBotonesCitas();
        vista.deshabilitarBotonesTalleres();

        vista.getBtnTodasCitas().addActionListener(e   -> { citasFiltradas = new ArrayList<>(todasCitas); cargarTablaCitas(citasFiltradas); });
        vista.getBtnDisenoCitas().addActionListener(e  -> filtrarCitasPorTipo("diseño"));
        vista.getBtnCosturaCitas().addActionListener(e -> filtrarCitasPorTipo("costura"));
        vista.getBtnPruebasCitas().addActionListener(e -> filtrarCitasPorTipo("pruebas"));
        vista.getBtnBuscarCitas().addActionListener(e  -> buscarCitas());
        vista.getBtnVerDetallesCitas().addActionListener(e -> verDetalleCita());

        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { cerrarSesion(); }
        });
    }

    private void cargarDatosEnMemoria() {
        try {
            todasCitas     = acceso.recogeCitas(c);
            todosTalleres  = acceso.recogeTalleres(c);
            todosClientes  = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes    = acceso.recogeTrajes(c);
            citasFiltradas = new ArrayList<>(todasCitas);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void cargarContadores() {
        try {
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));
            ArrayList<Cita_Aprendiz> rel = acceso.recogeCitasAprendiz(c);
            long misCitas = rel.stream().filter(ca -> ca.getId_empleado() == empleado.getId_empleado()).count();
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(misCitas));
            java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
            long citasHoy = todasCitas.stream().filter(ci -> ci.getFecha().toString().equals(hoy.toString())).count();
            vista.getLblCitasHoy().setText(String.valueOf(citasHoy));
            String proxima = todasCitas.stream().filter(ci -> !ci.getFecha().before(hoy))
                .map(ci -> ci.getFecha() + " " + ci.getHora_inicio()).min(String::compareTo).orElse("—");
            vista.getLblProximaCita().setText(proxima);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void mostrarListaCitas() {
        try { todasCitas = acceso.recogeCitas(c); todosTalleres = acceso.recogeTalleres(c); listaEmpleados = acceso.recogeEmpleados(c); listaTrajes = acceso.recogeTrajes(c); } catch (SQLException ex) { ex.printStackTrace(); }
        citasFiltradas = new ArrayList<>(todasCitas);
        cargarTablaCitas(citasFiltradas);
        vista.mostrarCard(VentanaAprendiz.CARD_LISTA_CITAS);
    }

    private void mostrarListaTalleres() {
        try { todosTalleres = acceso.recogeTalleres(c); } catch (Exception ex) { ex.printStackTrace(); }
        cargarTablaTalleres();
        vista.mostrarCard(VentanaAprendiz.CARD_LISTA_TALLERES);
    }

    private void cargarTablaCitas(ArrayList<Cita> lista) {
        DefaultTableModel m = (DefaultTableModel) vista.getTableCitas().getModel();
        m.setRowCount(0);
        for (Cita cita : lista)
            m.addRow(new Object[]{ cita.getFecha() + " " + cita.getHora_inicio(), nombreCliente(cita.getId_cliente()), nombreTraje(cita.getId_traje()), nombreTaller(cita.getId_sala()), nombreEmpleado(cita.getId_empleado()), cita.getDuracion() + " h" });
    }

    private void cargarTablaTalleres() {
        DefaultTableModel m = (DefaultTableModel) vista.getTableTalleres().getModel();
        m.setRowCount(0);
        vista.getModeloListaTalleres().clear();
        for (Taller t : todosTalleres) {
            m.addRow(new Object[]{ t.getNombre(), t.getTipo() });
            vista.getModeloListaTalleres().addElement(t.getNombre() + " (" + t.getTipo() + ")");
        }
    }

    private void filtrarCitasPorTipo(String tipo) {
        citasFiltradas = todasCitas.stream()
            .filter(cita -> todosTalleres.stream().anyMatch(t -> t.getId_sala() == cita.getId_sala() && t.getTipo().equalsIgnoreCase(tipo)))
            .collect(Collectors.toCollection(ArrayList::new));
        if (citasFiltradas.isEmpty()) JOptionPane.showMessageDialog(vista, "No hay citas de tipo '" + tipo + "'.", "Información", JOptionPane.INFORMATION_MESSAGE);
        cargarTablaCitas(citasFiltradas);
    }

    private void buscarCitas() {
        String texto = vista.getTxtBuscarCitas().getText().trim().toLowerCase();
        citasFiltradas = new ArrayList<>();
        for (Cita cita : todasCitas)
            if (cita.getFecha().toString().contains(texto) || nombreCliente(cita.getId_cliente()).toLowerCase().contains(texto)) citasFiltradas.add(cita);
        cargarTablaCitas(citasFiltradas);
    }

    private void verDetalleCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Cita cita = citasFiltradas.get(fila);
        DetalleCita v = new DetalleCita();
        new ControladorDetalleCita(v, cita, acceso, c);
        v.setVisible(true);
    }

    private String nombreCliente(int id)  {
    	if(todosClientes==null)  
    		return ""+id; 
    	
    	for(Cliente  x:todosClientes) 
    		if(x.getId_cliente()==id)  
    			return x.getNombre(); 
    	return ""+id; 
    }
    
    private String nombreTraje(int id)    {
    	if(listaTrajes==null)   
    		return ""+id; 
    	
    	for(Traje    x:listaTrajes)   
    		if(x.getId_traje()==id)   
    			return x.getNombre_traje();
    				return ""+id; 
    				}
    
    private String nombreTaller(int id)   {
    	if(todosTalleres==null) 
    		return ""+id; 
    			for(Taller   x:todosTalleres) 
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

    private void cerrarSesion() {
        acceso.cerrarConexion(c);
        vista.dispose();
        try {
            Connection nc = acceso.abrirConexion();
            ArrayList<Empleado> emps = acceso.recogeEmpleados(nc);
            acceso.cerrarConexion(nc);
            InicioSesion is = new InicioSesion();
            new ControladorInicioSesion(is, acceso, emps);
            is.setVisible(true);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}