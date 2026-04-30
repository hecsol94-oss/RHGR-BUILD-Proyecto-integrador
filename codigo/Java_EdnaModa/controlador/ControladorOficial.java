
package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.stream.Collectors;

public class ControladorOficial {

    private final VentanaOficial vista;
    private final AccesoBBDD     acceso;
    private final Connection     c;
    private final Empleado       empleado;

    private ArrayList<Cita>    todasCitas;
    private ArrayList<Cita>    citasFiltradas;
    private ArrayList<Cliente> todosClientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Taller>  todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Traje>   listaTrajes;

    /**
     * 
     * @param vista
     * @param acceso
     * @param c
     * @param empleado
     */
    public ControladorOficial(VentanaOficial vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.c        = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")");
        cargarDatosEnMemoria();
        cargarContadores();

        vista.getMenuItemListaCitas().addActionListener(e    -> mostrarListaCitas());
        vista.getMenuItemListaClientes().addActionListener(e -> mostrarListaClientes());
        vista.getMenuItemListaTalleres().addActionListener(e -> mostrarListaTalleres());
        vista.getMenuItemNuevaCita().addActionListener(e     -> abrirNuevaCita());

        vista.getBtnVolverCitas().addActionListener(e    -> vista.mostrarCard(VentanaOficial.CARD_DASHBOARD));
        vista.getBtnVolverClientes().addActionListener(e -> vista.mostrarCard(VentanaOficial.CARD_DASHBOARD));
        vista.getBtnVolverTalleres().addActionListener(e -> vista.mostrarCard(VentanaOficial.CARD_DASHBOARD));

        vista.getBtnTodasCitas().addActionListener(e   -> { citasFiltradas = new ArrayList<>(todasCitas); cargarTablaCitas(citasFiltradas); });
        vista.getBtnDisenoCitas().addActionListener(e  -> filtrarCitasPorTipo("diseño"));
        vista.getBtnCosturaCitas().addActionListener(e -> filtrarCitasPorTipo("costura"));
        vista.getBtnPruebasCitas().addActionListener(e -> filtrarCitasPorTipo("pruebas"));
        vista.getBtnBuscarCitas().addActionListener(e  -> buscarCitas());
        vista.getBtnVerDetallesCitas().addActionListener(e -> verDetalleCita());
        vista.getBtnEditarCitas().addActionListener(e  -> editarCita());
        vista.getBtnEliminarCitas().addActionListener(e -> eliminarCita());
        vista.getBtnNuevaCitaEmb().addActionListener(e -> abrirNuevaCita());

        vista.getBtnTodosClientes().addActionListener(e   -> { clientesFiltrados = new ArrayList<>(todosClientes); cargarTablaClientes(clientesFiltrados); });
        vista.getBtnHeroeClientes().addActionListener(e   -> filtrarClientesPorTipo("superhéroe"));
        vista.getBtnVillanoClientes().addActionListener(e -> filtrarClientesPorTipo("villano"));
        vista.getBtnBuscarClientes().addActionListener(e  -> buscarClientes());
        vista.getBtnDetalleClientes().addActionListener(e -> verDetalleCliente());
        // Oficial no puede editar/eliminar/crear clientes
        vista.deshabilitarBotonesClientes();

        vista.getBtnNuevoTallerEmb().addActionListener(e    -> { /* Oficial no crea talleres */ });
        vista.getBtnEditarTalleres().addActionListener(e    -> { /* no permitido */ });
        vista.getBtnEliminarTalleres().addActionListener(e  -> { /* no permitido */ });
        vista.getBtnConfirmarTalleres().addActionListener(e -> { /* no permitido */ });
        vista.deshabilitarBotonesTalleres();

        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	cerrarSesion();
            }
        });
    }

    private void cargarDatosEnMemoria() {
        try {
            todasCitas     = acceso.recogeCitas(c);
            todosTalleres  = acceso.recogeTalleres(c);
            todosClientes  = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes    = acceso.recogeTrajes(c);
            citasFiltradas    = new ArrayList<>(todasCitas);
            clientesFiltrados = new ArrayList<>(todosClientes);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }

    private void cargarContadores() {
        try {
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));
            long misCitas = todasCitas.stream().filter(ci -> ci.getId_empleado() == empleado.getId_empleado()).count();
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(misCitas));
            java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
            long citasHoy = todasCitas.stream().filter(ci -> ci.getFecha().toString().equals(hoy.toString())).count();
            vista.getLblCitasHoy().setText(String.valueOf(citasHoy));
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            Date ini = cal.getTime(); cal.add(Calendar.DAY_OF_WEEK, 6); Date fin = cal.getTime();
            long semana = todasCitas.stream().filter(ci -> !ci.getFecha().before(ini) && !ci.getFecha().after(fin)).count();
            vista.getLblCitasSemana().setText(String.valueOf(semana));
            String proxima = todasCitas.stream().filter(ci -> !ci.getFecha().before(hoy))
                .map(ci -> ci.getFecha() + " " + ci.getHora_inicio()).min(String::compareTo).orElse("—");
            vista.getLblProximaCita().setText(proxima);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

    private void mostrarListaCitas() {
        try {
        	todasCitas = acceso.recogeCitas(c); listaEmpleados = acceso.recogeEmpleados(c); listaTrajes = acceso.recogeTrajes(c); todosTalleres = acceso.recogeTalleres(c);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        citasFiltradas = new ArrayList<>(todasCitas);
        cargarTablaCitas(citasFiltradas);
        vista.mostrarCard(VentanaOficial.CARD_LISTA_CITAS);
    }

    private void mostrarListaClientes() {
        try {
        	todosClientes = acceso.recogeClientes(c);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        clientesFiltrados = new ArrayList<>(todosClientes);
        cargarTablaClientes(clientesFiltrados);
        vista.mostrarCard(VentanaOficial.CARD_LISTA_CLIENTES);
    }

    private void mostrarListaTalleres() {
        try {
        	todosTalleres = acceso.recogeTalleres(c);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        cargarTablaTalleres();
        vista.mostrarCard(VentanaOficial.CARD_LISTA_TALLERES);
    }

    /**
     * 
     * @param lista
     */
    private void cargarTablaCitas(ArrayList<Cita> lista) {
        DefaultTableModel m = (DefaultTableModel) vista.getTableCitas().getModel();
        m.setRowCount(0);
        for (Cita cita : lista)
            m.addRow(new Object[]{ cita.getFecha() + " " + cita.getHora_inicio(), nombreCliente(cita.getId_cliente()), nombreTraje(cita.getId_traje()), nombreTaller(cita.getId_sala()), nombreEmpleado(cita.getId_empleado()), cita.getDuracion() + " h" });
    }

    /**
     * 
     * @param lista
     */
    private void cargarTablaClientes(ArrayList<Cliente> lista) {
        DefaultTableModel m = (DefaultTableModel) vista.getTableClientes().getModel();
        m.setRowCount(0);
        for (Cliente cl : lista)
            m.addRow(new Object[]{ cl.getNombre(), cl.getSuperpoder(), cl.getTipo_heroe(), "-" });
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

    private void filtrarClientesPorTipo(String tipo) {
        clientesFiltrados = new ArrayList<>();
        for (Cliente cl : todosClientes) if (cl.getTipo_heroe().equalsIgnoreCase(tipo)) clientesFiltrados.add(cl);
        cargarTablaClientes(clientesFiltrados);
    }

    private void buscarClientes() {
        String texto = vista.getTxtBuscarClientes().getText().trim().toLowerCase();
        clientesFiltrados = new ArrayList<>();
        for (Cliente cl : todosClientes) if (cl.getNombre().toLowerCase().contains(texto)) clientesFiltrados.add(cl);
        cargarTablaClientes(clientesFiltrados);
    }

    private void eliminarCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) {
        	JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        Cita cita = citasFiltradas.get(fila);
        int ok = JOptionPane.showConfirmDialog(vista,
            "¿Eliminar la cita del " + cita.getFecha() + " a las " + cita.getHora_inicio() + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) {
            acceso.eliminarCita(c, cita.getId_cita());
            todasCitas.remove(cita);
            citasFiltradas.remove(fila);
            cargarTablaCitas(citasFiltradas);
            cargarContadores();
            JOptionPane.showMessageDialog(vista, "Cita eliminada correctamente.");
        }
    }

    private void verDetalleCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) {
        	JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        Cita cita = citasFiltradas.get(fila);
        DetalleCita v = new DetalleCita();
        new ControladorDetalleCita(v, cita, acceso, c);
        v.setVisible(true);
    }

    private void editarCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) {
        	JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        NuevaCitaMaestro v = new NuevaCitaMaestro();
        new ControladorNuevaCitaMaestro(v, acceso, c, empleado);
        v.setVisible(true);
    }

    private void abrirNuevaCita() {
        NuevaCitaMaestro v = new NuevaCitaMaestro();
        new ControladorNuevaCitaMaestro(v, acceso, c, empleado);
        v.setVisible(true);
    }

    private void verDetalleCliente() {
        int fila = vista.getTableClientes().getSelectedRow();
        if (fila < 0) {
        	JOptionPane.showMessageDialog(vista, "Selecciona un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        Cliente cl = clientesFiltrados.get(fila);
        DetalleClientes v = new DetalleClientes();
        new ControladorDetalleClientes(v, acceso, c, cl, false);
        v.setVisible(true);
    }
/**
 * 
 * @param id
 * @return
 */
    private String nombreCliente(int id)  {
    	if(todosClientes==null) 
    		return ""+id; 
    	for(Cliente  x:todosClientes)  
    		if(x.getId_cliente()==id)  
    			return x.getNombre();
    				return ""+id; 
    				}
    /**
     * 
     * @param id
     * @return
     */
    private String nombreTraje(int id)    {
    	if(listaTrajes==null)   
    		return ""+id; 
    	for(Traje    x:listaTrajes)    
    		if(x.getId_traje()==id)   
    			return x.getNombre_traje();
    				return ""+id;
    				}
    /**
     * 
     * @param id
     * @return
     */
    private String nombreTaller(int id)   { 
    	if(todosTalleres==null) 
    		return ""+id; 
    	for(Taller   x:todosTalleres)
    		if(x.getId_sala()==id)   
    			return x.getNombre();
    				return ""+id; 
    				}
    /**
     * 
     * @param id
     * @return
     */
    private String nombreEmpleado(int id) {
    	if(listaEmpleados==null)
    		return ""+id; 
    	for(Empleado x:listaEmpleados)
    		if(x.getId_empleado()==id) 
    			return x.getNombre()+" "+x.getApellido();
    				return ""+id; 
    				}

         if(todosClientes==null)  
            return ""+id; for(Cliente  x:todosClientes) 
                 if(x.getId_cliente()==id) 
                     return x.getNombre();
                     return ""+id; 
                    }
    private String nombreTraje(int id)    {
         if(listaTrajes==null)   
             return ""+id; for(Traje    x:listaTrajes)  
              if(x.getId_traje()==id)   
                 return x.getNombre_traje();
                 return ""+id; 
                }
    private String nombreTaller(int id)   {
         if(todosTalleres==null)  
            return ""+id; for(Taller   x:todosTalleres) 
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
        } catch (SQLException ex) { 
        	ex.printStackTrace();
        }
    }
}

