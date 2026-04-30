package controlador;

import modelo.*;
import vista.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
        
        vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
        vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());

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

    private void abrirListaCitas() {
        try {
        	ArrayList<Cita> citas = acceso.recogeCitas(c);
            ArrayList<Cita_Aprendiz> aprendices = acceso.recogeCitasAprendiz(c);
            ListaCitas vistaLista = new ListaCitas();
            vistaLista.deshabilitarBotones();
            new ControladorListaCitas(vistaLista, acceso, c, citas, aprendices, empleado);
            vistaLista.deshabilitarBotones(); // solo lectura para Aprendiz
            vistaLista.setVisible(true);
            vista.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Abre la lista de talleres en modo solo lectura.
     */
    private void abrirListaTalleres() {
        ArrayList<Taller> talleres = acceso.recogeTalleres(c);
        ListaTalleres vistaLista = new ListaTalleres();
        vistaLista.recogerDatos(talleres);
        new ControladorListaTalleres(vistaLista, acceso, c, talleres, empleado);
        vistaLista.deshabilitarBotones();
        vistaLista.setVisible(true);
        vista.dispose();
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