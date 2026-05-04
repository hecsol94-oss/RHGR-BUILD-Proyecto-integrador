package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControladorMaestro {

    private final VentanaMaestro vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Empleado empleado;

    // Datos en memoria para las listas embebidas
    private ArrayList<Cita> todasCitas;
    private ArrayList<Cita> citasFiltradas;
    private ArrayList<Cita> citasMias;
    private ArrayList<Cliente> todosClientes;
    private ArrayList<Cliente> clientesFiltrados;
    private ArrayList<Taller> todosTalleres;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Traje> trajesFiltrados;
    private boolean editable = true;

    public ControladorMaestro(VentanaMaestro vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.empleado = empleado;

        vista.getLblUsuario().setText("Usuario: " + empleado.getApodo() + " (" + empleado.getCategoria() + ")");
        cargarDatosEnMemoria();
        cargarContadores();

        // Acceso completo al menú
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemNuevaCita().addActionListener(e -> abrirNuevaCita());
        vista.getMenuItemListaClientes().addActionListener(e -> abrirListaClientes());
        vista.getMenuItemNuevoCliente().addActionListener(e -> abrirNuevoCliente());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());
        vista.getMenuItemNuevoTaller().addActionListener(e -> abrirNuevoTaller());

        // Salir
        vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	cerrarSesion();
            }
        });
    }

    // ── Carga inicial ────────────────────────────────────────────────────────
    private void cargarDatosEnMemoria() {
        try {
            todasCitas = acceso.recogeCitas(c);
            todosTalleres = acceso.recogeTalleres(c);
            todosClientes = acceso.recogeClientes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            listaTrajes = acceso.recogeTrajes(c);
            citasFiltradas = new ArrayList<>(todasCitas);
            citasMias = new ArrayList<>();
            clientesFiltrados = new ArrayList<>(todosClientes);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }

    private void cargarContadores() {
        try {
            vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
            vista.getLblNumeroDeTalleres().setText(String.valueOf(todosTalleres.size()));
            vista.getLblTotalClientes().setText(String.valueOf(todosClientes.size()));

            long misCitas = todasCitas.stream().filter(ci -> ci.getId_empleado() == empleado.getId_empleado()).count();
            vista.getLblNumeroDeMisCitas().setText(String.valueOf(misCitas));

            for (Cita proximaCita : todasCitas) {
            	if (proximaCita.getId_empleado() == empleado.getId_empleado()) {
            		citasMias.add(proximaCita);
            		java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
                    long citasHoy = citasMias.stream().filter(ci -> ci.getFecha().toString().equals(hoy.toString())).count();
                    vista.getLblCitasHoy().setText(String.valueOf(citasHoy));

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                    Date ini = cal.getTime(); cal.add(Calendar.DAY_OF_WEEK, 6); Date fin = cal.getTime();
                    long semana = citasMias.stream().filter(ci -> !ci.getFecha().before(ini) && !ci.getFecha().after(fin)).count();
                    vista.getLblCitasSemana().setText(String.valueOf(semana));
                    
                    
                    String proxima = citasMias.stream()
                          .filter(ci -> !ci.getFecha().before(hoy))
                          .map(ci -> ci.getFecha() + " " + ci.getHora_inicio())
                          .min(String::compareTo).orElse("—");
                    vista.getLblProximaCita().setText(proxima);
            	}
            }	
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    // ── Mostrar listas embebidas ──────────────────────────────────────────────
    
    private void abrirListaCitas() {
        try {
        	ArrayList<Cita> citas = acceso.recogeCitas(c);
            ArrayList<Cita_Aprendiz> aprendices = acceso.recogeCitasAprendiz(c);
            ListaCitas vistaLista = new ListaCitas();
            new ControladorListaCitas(vistaLista, acceso, c, citas, aprendices, empleado);
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    
    private void abrirNuevaCita() {
        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm,  acceso, null, vista, null, c, empleado, null, null, null, null, null, null, null);
        vistaForm.setVisible(true);
    }

   
    private void abrirListaClientes() {
        try {
        	ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            ListaClientes vistaLista = new ListaClientes();
            new ControladorListaClientes(vistaLista, acceso, c, clientes, trajes, empleado);
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

   
    private void abrirNuevoCliente() {
    	try {
    		NuevoCliente vistaForm = new NuevoCliente();
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            new ControladorNuevoCliente(vistaForm, null, vista, null, acceso, c, null, clientes, empleado);
            vistaForm.setVisible(true);
    	} catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Lista de talleres — acceso completo (botones habilitados por defecto)
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
        vistaLista.setVisible(true);
        vista.dispose();
    }

    // Nuevo taller — acceso completo
    private void abrirNuevoTaller() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        NuevoTaller vistaForm = new NuevoTaller();
        new ControladorNuevoTaller(vistaForm, null, vista, acceso, c, null, talleres, empleado);
        vistaForm.setVisible(true);
    }

    
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
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }
}