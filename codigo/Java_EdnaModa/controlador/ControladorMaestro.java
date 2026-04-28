
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

public class ControladorMaestro {

    private final VentanaMaestro vista;
    private final AccesoBBDD     acceso;
    private final Connection     c;
    private final Empleado       empleado;

    // Datos en memoria para las listas embebidas
    private ArrayList<Cita>    todasCitas;
    private ArrayList<Cita>    citasFiltradas;
    private ArrayList<Cliente> todosClientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Taller>  todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Traje>   listaTrajes;
    private boolean editable = true;

    /**
     * 
     * @param vista
     * @param acceso
     * @param c
     * @param empleado
     */
    public ControladorMaestro(VentanaMaestro vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.c        = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")");
        cargarDatosEnMemoria();
        cargarContadores();

        // Menú → listas embebidas (sin nueva ventana)
        vista.getMenuItemListaCitas().addActionListener(e    -> mostrarListaCitas());
        vista.getMenuItemListaClientes().addActionListener(e -> mostrarListaClientes());
        vista.getMenuItemListaTalleres().addActionListener(e -> mostrarListaTalleres());

        // Menú → ventanas nuevas (solo estas tres)
        vista.getMenuItemNuevaCita().addActionListener(e    -> abrirNuevaCita());
        vista.getMenuItemNuevoCliente().addActionListener(e -> abrirNuevoCliente());
        vista.getMenuItemNuevoTaller().addActionListener(e  -> abrirNuevoTaller());

        // Botones "Volver" en los paneles embebidos
        vista.getBtnVolverCitas().addActionListener(e     -> vista.mostrarCard(VentanaMaestro.CARD_DASHBOARD));
        vista.getBtnVolverClientes().addActionListener(e  -> vista.mostrarCard(VentanaMaestro.CARD_DASHBOARD));
        vista.getBtnVolverTalleres().addActionListener(e  -> vista.mostrarCard(VentanaMaestro.CARD_DASHBOARD));

        // Listeners lista citas embebida
        vista.getBtnTodasCitas().addActionListener(e   -> { citasFiltradas = new ArrayList<>(todasCitas); cargarTablaCitas(citasFiltradas); });
        vista.getBtnDisenoCitas().addActionListener(e  -> filtrarCitasPorTipo("diseño"));
        vista.getBtnCosturaCitas().addActionListener(e -> filtrarCitasPorTipo("costura"));
        vista.getBtnPruebasCitas().addActionListener(e -> filtrarCitasPorTipo("pruebas"));
        vista.getBtnBuscarCitas().addActionListener(e  -> buscarCitas());
        vista.getBtnVerDetallesCitas().addActionListener(e -> verDetalleCita());
        vista.getBtnEditarCitas().addActionListener(e  -> editarCita());
        vista.getBtnNuevaCitaEmb().addActionListener(e -> abrirNuevaCita());

        // Listeners lista clientes embebida
        vista.getBtnTodosClientes().addActionListener(e   -> { clientesFiltrados = new ArrayList<>(todosClientes); cargarTablaClientes(clientesFiltrados); });
        vista.getBtnHeroeClientes().addActionListener(e   -> filtrarClientesPorTipo("superhéroe"));
        vista.getBtnVillanoClientes().addActionListener(e -> filtrarClientesPorTipo("villano"));
        vista.getBtnBuscarClientes().addActionListener(e  -> buscarClientes());
        vista.getBtnDetalleClientes().addActionListener(e -> verDetalleCliente());
        vista.getBtnEditarClientes().addActionListener(e  -> editarCliente());
        vista.getBtnEliminarClientes().addActionListener(e-> eliminarCliente());
        vista.getBtnNuevoClienteEmb().addActionListener(e -> abrirNuevoCliente());

        // Listeners lista talleres embebida
        vista.getBtnNuevoTallerEmb().addActionListener(e    -> abrirNuevoTaller());
        vista.getBtnEditarTalleres().addActionListener(e    -> pulsarEditarTaller());
        vista.getBtnEliminarTalleres().addActionListener(e  -> pulsarEliminarTaller());
        vista.getBtnConfirmarTalleres().addActionListener(e -> pulsarConfirmarTaller());

        // Salir
        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { cerrarSesion(); }
        });
    }

    // ── Carga inicial ────────────────────────────────────────────────────────
    private void cargarDatosEnMemoria() {
        try {
            todasCitas     = acceso.recogeCitas(c);
            todosTalleres  = acceso.recogeTalleres(c);
            todosClientes  = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes    = acceso.recogeTrajes(c);
            citasFiltradas    = new ArrayList<>(todasCitas);
            clientesFiltrados = new ArrayList<>(todosClientes);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void cargarContadores() {
        try {
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));
            vista.getLblTotalClientes().setText(String.valueOf(todosClientes.size()));

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

            String proxima = todasCitas.stream()
                .filter(ci -> !ci.getFecha().before(hoy))
                .map(ci -> ci.getFecha() + " " + ci.getHora_inicio())
                .min(String::compareTo).orElse("—");
            vista.getLblProximaCita().setText(proxima);
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    // ── Mostrar listas embebidas ──────────────────────────────────────────────
    private void mostrarListaCitas() {
        try {
            todasCitas     = acceso.recogeCitas(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes    = acceso.recogeTrajes(c);
            todosTalleres  = acceso.recogeTalleres(c);
        } catch (SQLException ex) { ex.printStackTrace(); }
        citasFiltradas = new ArrayList<>(todasCitas);
        cargarTablaCitas(citasFiltradas);
        vista.mostrarCard(VentanaMaestro.CARD_LISTA_CITAS);
    }

    private void mostrarListaClientes() {
        try { todosClientes = acceso.recogeClientes(c); } catch (SQLException ex) { ex.printStackTrace(); }
        clientesFiltrados = new ArrayList<>(todosClientes);
        cargarTablaClientes(clientesFiltrados);
        vista.mostrarCard(VentanaMaestro.CARD_LISTA_CLIENTES);
    }

    private void mostrarListaTalleres() {
        try { todosTalleres = acceso.recogeTalleres(c); } catch (Exception ex) { ex.printStackTrace(); }
        cargarTablaTalleres();
        vista.mostrarCard(VentanaMaestro.CARD_LISTA_TALLERES);
    }

    // ── Tablas embebidas ─────────────────────────────────────────────────────
    /**
     * 
     * @param lista
     */
    private void cargarTablaCitas(ArrayList<Cita> lista) {
        DefaultTableModel m = (DefaultTableModel) vista.getTableCitas().getModel();
        m.setRowCount(0);
        for (Cita cita : lista) {
            m.addRow(new Object[]{
                cita.getFecha() + " " + cita.getHora_inicio(),
                nombreCliente(cita.getId_cliente()),
                nombreTraje(cita.getId_traje()),
                nombreTaller(cita.getId_sala()),
                nombreEmpleado(cita.getId_empleado()),
                cita.getDuracion() + " h"
            });
        }
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

    // ── Filtros citas ────────────────────────────────────────────────────────
    /**
     * 
     * @param tipo
     */
    private void filtrarCitasPorTipo(String tipo) {
        citasFiltradas = todasCitas.stream()
            .filter(cita -> todosTalleres.stream().anyMatch(t -> t.getId_sala() == cita.getId_sala() && t.getTipo().equalsIgnoreCase(tipo)))
            .collect(Collectors.toCollection(ArrayList::new));
        if (citasFiltradas.isEmpty())
            JOptionPane.showMessageDialog(vista, "No hay citas de tipo '" + tipo + "'.", "Información", JOptionPane.INFORMATION_MESSAGE);
        cargarTablaCitas(citasFiltradas);
    }

    private void buscarCitas() {
        String texto = vista.getTxtBuscarCitas().getText().trim().toLowerCase();
        citasFiltradas = new ArrayList<>();
        for (Cita cita : todasCitas) {
            if (cita.getFecha().toString().contains(texto) || nombreCliente(cita.getId_cliente()).toLowerCase().contains(texto))
                citasFiltradas.add(cita);
        }
        cargarTablaCitas(citasFiltradas);
    }

    // ── Filtros clientes ──────────────────────────────────────────────────────
    /**
     * 
     * @param tipo
     */
    private void filtrarClientesPorTipo(String tipo) {
        clientesFiltrados = new ArrayList<>();
        for (Cliente cl : todosClientes)
            if (cl.getTipo_heroe().equalsIgnoreCase(tipo)) clientesFiltrados.add(cl);
        cargarTablaClientes(clientesFiltrados);
    }

    private void buscarClientes() {
        String texto = vista.getTxtBuscarClientes().getText().trim().toLowerCase();
        clientesFiltrados = new ArrayList<>();
        for (Cliente cl : todosClientes)
            if (cl.getNombre().toLowerCase().contains(texto)) clientesFiltrados.add(cl);
        cargarTablaClientes(clientesFiltrados);
    }

    // ── Acciones citas ────────────────────────────────────────────────────────
    private void verDetalleCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Cita cita = citasFiltradas.get(fila);
        DetalleCita v = new DetalleCita();
        new ControladorDetalleCita(v, cita, acceso, c);
        v.setVisible(true);
    }

    private void editarCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        NuevaCitaMaestro v = new NuevaCitaMaestro();
        new ControladorNuevaCitaMaestro(v, acceso, c, empleado);
        v.setVisible(true);
    }

    private void abrirNuevaCita() {
        NuevaCitaMaestro v = new NuevaCitaMaestro();
        new ControladorNuevaCitaMaestro(v, acceso, c, empleado);
        v.setVisible(true);
    }

    // ── Acciones clientes ─────────────────────────────────────────────────────
    private void verDetalleCliente() {
        int fila = vista.getTableClientes().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Cliente cl = clientesFiltrados.get(fila);
        DetalleClientes v = new DetalleClientes();
        new ControladorDetalleClientes(v, acceso, c, cl, editable);
        v.setVisible(true);
    }

    private void editarCliente() {
        int fila = vista.getTableClientes().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Cliente cl = clientesFiltrados.get(fila);
        NuevoCliente v = new NuevoCliente();
        new ControladorNuevoCliente(v, acceso, c, cl);
        v.setVisible(true);
    }

    private void eliminarCliente() {
        int fila = vista.getTableClientes().getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(vista, "Selecciona un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Cliente cl = clientesFiltrados.get(fila);
        int ok = JOptionPane.showConfirmDialog(vista, "¿Eliminar a " + cl.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            acceso.eliminarCliente(c, cl.getId_cliente());
            todosClientes.remove(cl); clientesFiltrados.remove(cl);
            cargarTablaClientes(clientesFiltrados);
            JOptionPane.showMessageDialog(vista, "Cliente eliminado.");
        }
    }

    private void abrirNuevoCliente() {
        NuevoCliente v = new NuevoCliente();
        new ControladorNuevoCliente(v, acceso, c, null);
        v.setVisible(true);
    }

    // ── Acciones talleres ─────────────────────────────────────────────────────
    private String opcionTaller = "";

    private void pulsarEditarTaller() {
        opcionTaller = "editar";
        vista.getLista().setEnabled(true);
        JOptionPane.showMessageDialog(vista, "Selecciona el taller a editar en la lista inferior.");
        vista.getLista().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && opcionTaller.equals("editar")) {
                int idx = vista.getLista().getSelectedIndex();
                if (idx >= 0 && idx < todosTalleres.size()) {
                    Taller t = todosTalleres.get(idx);
                    NuevoTaller v = new NuevoTaller();
                    new ControladorNuevoTaller(v, acceso, c, todosTalleres, empleado);
                    v.setVisible(true);
                    opcionTaller = "";
                }
            }
        });
    }

   
    private void pulsarEliminarTaller() {
        opcionTaller = "eliminar";
        JOptionPane.showMessageDialog(vista, "Selecciona el taller a eliminar en la lista inferior.");
        vista.getLista().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && opcionTaller.equals("eliminar")) {
                int idx = vista.getLista().getSelectedIndex();
                if (idx >= 0 && idx < todosTalleres.size()) {
                    Taller t = todosTalleres.get(idx);
                    int ok = JOptionPane.showConfirmDialog(vista, "¿Eliminar '" + t.getNombre() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (ok == JOptionPane.YES_OPTION) {
                        todosTalleres.remove(idx);
                        cargarTablaTalleres();
                        JOptionPane.showMessageDialog(vista, "Taller eliminado.");
                    }
                    opcionTaller = "";
                }
            }
        });
    }

    private void pulsarConfirmarTaller() {
        JOptionPane.showMessageDialog(vista, "Operación confirmada.");
        opcionTaller = "";
    }

    private void abrirNuevoTaller() {
        NuevoTaller v = new NuevoTaller();
        new ControladorNuevoTaller(v, acceso, c, todosTalleres, empleado);
        v.setVisible(true);
    }

    // ── Nombres ───────────────────────────────────────────────────────────────
    /**
     * 
     * @param id
     * @return
     */
    private String nombreCliente(int id)  { if(todosClientes==null)  return ""+id; for(Cliente  x:todosClientes)  if(x.getId_cliente()==id)  return x.getNombre(); return ""+id; }
    /**
     * 
     * @param id
     * @return
     */
    private String nombreTraje(int id)    { if(listaTrajes==null)    return ""+id; for(Traje    x:listaTrajes)    if(x.getId_traje()==id)    return x.getNombre_traje(); return ""+id; }
    /**
     * 
     * @param id
     * @return
     */
    private String nombreTaller(int id)   { if(todosTalleres==null)  return ""+id; for(Taller   x:todosTalleres)  if(x.getId_sala()==id)     return x.getNombre(); return ""+id; }
    /**
     * 
     * @param id
     * @return
     */
    private String nombreEmpleado(int id) { if(listaEmpleados==null) return ""+id; for(Empleado x:listaEmpleados) if(x.getId_empleado()==id) return x.getNombre()+" "+x.getApellido(); return ""+id; }

    // ── Cerrar sesión ─────────────────────────────────────────────────────────
    private void cerrarSesion() {
        acceso.cerrarConexion(c);
        vista.dispose();
        try {
            Connection nc = acceso.abrirConexion();
            ArrayList<Empleado> empleados = acceso.recogeEmpleados(nc);
            acceso.cerrarConexion(nc);
            InicioSesion is = new InicioSesion();
            new ControladorInicioSesion(is, acceso, empleados);
            is.setVisible(true);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}