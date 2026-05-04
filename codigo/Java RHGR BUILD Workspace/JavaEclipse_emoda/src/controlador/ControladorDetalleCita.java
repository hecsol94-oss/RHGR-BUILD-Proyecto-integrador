package controlador;

import modelo.*;
import vista.DetalleCita;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorDetalleCita {

    private final DetalleCita vista;
    private final Cita cita;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final String[] aprendices;

    public ControladorDetalleCita(DetalleCita vista, Cita cita, AccesoBBDD acceso, Connection c, String[] aprendices) {
        this.vista = vista;
        this.cita = cita;
        this.acceso = acceso;
        this.c = c;
        this.aprendices = aprendices;

        // Rellena la vista al crear el controlador
        rellenarCampos();

        // Botón volver: simplemente cierra la ventana
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    // Constructor alternativo (sin acceso a BD → muestra IDs)
    public ControladorDetalleCita(DetalleCita vista, Cita cita, String[] aprendices) {
        this(vista, cita, null, null, aprendices);
    }

    private void rellenarCampos() {

        // Datos básicos de la cita
        vista.setFecha(String.valueOf(cita.getFecha()));
        vista.setHora(String.valueOf(cita.getHora_inicio()));
        vista.setDuracion(cita.getDuracion() + " h");

        // Si no hay conexión a BD, mostramos IDs directamente
        if (acceso == null || c == null) {
            vista.setCliente("ID " + cita.getId_cliente());
            vista.setTraje("ID " + cita.getId_traje());
            vista.setTaller("ID " + cita.getId_sala());
            vista.setOficial("ID " + cita.getId_empleado());

            // Evitar posibles NullPointerException en aprendices
            vista.setAprendices(
                    (aprendices[0] != null ? aprendices[0] : "—") + "\n" +
                    (aprendices[1] != null ? aprendices[1] : "")
            );

            return;
        }

        try {
<<<<<<< HEAD:codigo/Java RHGR BUILD Workspace/JavaEclipse_emoda/src/controlador/ControladorDetalleCita.java
            // Cargar listas completas desde BD para resolver IDs → nombres
            ArrayList<Cliente>  clientes  = acceso.recogeClientes(c);
            ArrayList<Traje>    trajes    = acceso.recogeTrajes(c);
            ArrayList<Taller>   talleres  = acceso.recogeTalleres(c);
=======
            // Resolución de nombres (punto 6)
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            ArrayList<Taller> talleres = acceso.recogeTalleres(c);
>>>>>>> d245656e3395d1d7de26ffddcc920efb1fb59a29:codigo/Java_EdnaModa/controlador/ControladorDetalleCita.java
            ArrayList<Empleado> empleados = acceso.recogeEmpleados(c);

            // Resolución de nombres
            vista.setCliente(nombreCliente(clientes, cita.getId_cliente()));
            vista.setTraje(nombreTraje(trajes, cita.getId_traje()));
            vista.setTaller(nombreTaller(talleres, cita.getId_sala()));
            vista.setOficial(nombreEmpleado(empleados, cita.getId_empleado()));

            // Construcción de texto de aprendices
            StringBuilder texto = new StringBuilder();

            if (aprendices[0] != null && !aprendices[0].isEmpty()) {
                texto.append(aprendices[0]);
            }

            if (aprendices[1] != null && !aprendices[1].isEmpty()) {
                if (texto.length() > 0) texto.append("\n");
                texto.append(aprendices[1]);
            }

            vista.setAprendices(texto.length() == 0 ? "—" : texto.toString());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Resolución de cliente por ID
    private String nombreCliente(ArrayList<Cliente> lista, int id) {
        for (Cliente x : lista)
            if (x.getId_cliente() == id)
                return x.getNombre();
        return "ID " + id;
    }

    // Resolución de traje por ID
    private String nombreTraje(ArrayList<Traje> lista, int id) {
        for (Traje x : lista)
            if (x.getId_traje() == id)
                return x.getNombre_traje();
        return "ID " + id;
    }

    // Resolución de taller por ID
    private String nombreTaller(ArrayList<Taller> lista, int id) {
        for (Taller x : lista)
            if (x.getId_sala() == id)
                return x.getNombre() + " (" + x.getTipo() + ")";
        return "ID " + id;
    }

    // Resolución de empleado por ID
    private String nombreEmpleado(ArrayList<Empleado> lista, int id) {
        for (Empleado x : lista)
            if (x.getId_empleado() == id)
                return x.getNombre() + " " + x.getApellido();
        return "ID " + id;
    }
}