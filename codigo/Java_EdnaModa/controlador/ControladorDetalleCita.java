package controlador;

import modelo.*;
import vista.DetalleCita;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de DetalleCita — punto 8: rellena campos etiquetados con nombres, no IDs.
 */
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

        rellenarCampos();
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    /** Constructor legacy sin acceso BBDD (muestra IDs como fallback). */
    public ControladorDetalleCita(DetalleCita vista, Cita cita, String[] aprendices) {
        this(vista, cita, null, null, aprendices);
    }

    private void rellenarCampos() {
        vista.setFecha(String.valueOf(cita.getFecha()));
        vista.setHora(String.valueOf(cita.getHora_inicio()));
        vista.setDuracion(cita.getDuracion() + " h");

        if (acceso == null || c == null) {
            vista.setCliente("ID " + cita.getId_cliente());
            vista.setTraje("ID " + cita.getId_traje());
            vista.setTaller("ID " + cita.getId_sala());
            vista.setOficial("ID " + cita.getId_empleado());
            vista.setAprendices("ID" + aprendices[0]);
            vista.setAprendices("ID" + aprendices[1]);
            return;
        }

        try {
            // Resolución de nombres (punto 6)
            ArrayList<Cliente> clientes = acceso.recogeClientes(c);
            ArrayList<Traje> trajes = acceso.recogeTrajes(c);
            ArrayList<Taller> talleres = acceso.recogeTalleres(c);
            ArrayList<Empleado> empleados = acceso.recogeEmpleados(c);

            vista.setCliente(nombreCliente(clientes, cita.getId_cliente()));
            vista.setTraje(nombreTraje(trajes, cita.getId_traje()));
            vista.setTaller(nombreTaller(talleres, cita.getId_sala()));
            vista.setOficial(nombreEmpleado(empleados, cita.getId_empleado()));

            // Aprendices
            String texto = "";

            if (!aprendices[0].isEmpty()) {
            	texto += aprendices[0];
            }
            if (!aprendices[1].isEmpty()) {
            	texto += "\n" + aprendices[1];
            }

            vista.setAprendices(texto.isEmpty() ? "—" : texto);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String nombreCliente(ArrayList<Cliente> lista, int id) {
        for (Cliente x : lista) if (x.getId_cliente() == id) return x.getNombre();
        return "ID " + id;
    }
    private String nombreTraje(ArrayList<Traje> lista, int id) {
        for (Traje x : lista) if (x.getId_traje() == id) return x.getNombre_traje();
        return "ID " + id;
    }
    private String nombreTaller(ArrayList<Taller> lista, int id) {
        for (Taller x : lista) if (x.getId_sala() == id) return x.getNombre() + " (" + x.getTipo() + ")";
        return "ID " + id;
    }
    private String nombreEmpleado(ArrayList<Empleado> lista, int id) {
        for (Empleado x : lista) if (x.getId_empleado() == id) return x.getNombre() + " " + x.getApellido();
        return "ID " + id;
    }
}