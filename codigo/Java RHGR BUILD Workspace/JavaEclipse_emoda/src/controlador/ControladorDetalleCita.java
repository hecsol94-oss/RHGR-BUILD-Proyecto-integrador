package controlador;

import modelo.*;
import vista.DetalleCita;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador para la visualización detallada de una cita programada.
 * Se encarga de poblar la interfaz gráfica con los datos de la cita, convirtiendo
 * los identificadores (IDs) de clientes, trajes, talleres y empleados en sus 
 * respectivos nombres legibles mediante consultas a la base de datos.
 */
public class ControladorDetalleCita {

    /** Interfaz gráfica que muestra los detalles de la cita */
    private final DetalleCita vista;

    /** Objeto que contiene los datos de la cita a mostrar */
    private final Cita cita;

    /** Objeto de acceso a datos para la resolución de nombres */
    private final AccesoBBDD acceso;

    /** Conexión activa a la base de datos SQL */
    private final Connection c;

    /** Array que contiene los nombres de los aprendices asignados a la cita */
    private final String[] aprendices;
    

    /**
     * Constructor principal del controlador.
     * Inicializa las dependencias, vincula el evento del botón volver y rellena 
     * automáticamente los campos de la vista al instanciarse.
     * 
     * @param vista Ventana de detalle de la cita.
     * @param cita Modelo de la cita seleccionada.
     * @param acceso Clase DAO para acceso a datos.
     * @param c Conexión JDBC.
     * @param aprendices Nombres de los aprendices asociados.
     */
    public ControladorDetalleCita(DetalleCita vista, Cita cita, AccesoBBDD acceso, Connection c, String[] aprendices) {
        this.vista = vista;
        this.cita = cita;
        this.acceso = acceso;
        this.c = c;
        this.aprendices = aprendices;

        /**
         * Población de la interfaz
         */
        rellenarCampos();

        /**
         * Configuración de eventos
         */
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    /**
     * Constructor alternativo para visualización rápida.
     * Útil cuando no se dispone de conexión a base de datos; en este caso, 
     * la vista mostrará los IDs numéricos en lugar de los nombres.
     * 
     * @param vista Ventana de detalle de la cita.
     * @param cita Modelo de la cita seleccionada.
     * @param aprendices Nombres de los aprendices asociados.
     */
    public ControladorDetalleCita(DetalleCita vista, Cita cita, String[] aprendices) {
        this(vista, cita, null, null, aprendices);
    }

    /**
     * Método interno que distribuye los datos de la entidad Cita en los componentes de la vista.
     * Si existe conexión a la BD, intenta resolver los IDs. Si no, muestra los valores ID crudos.
     */
    private void rellenarCampos() {

        /**
         * Asignación de datos temporales básicos
         */
        vista.setFecha(String.valueOf(cita.getFecha()));
        vista.setHora(String.valueOf(cita.getHora_inicio()));
        vista.setDuracion(cita.getDuracion() + " h");

        /**
         * Modo offline o sin acceso: Mostrar solo identificadores numéricos
         */
        if (acceso == null || c == null) {
            vista.setCliente("ID " + cita.getId_cliente());
            vista.setTraje("ID " + cita.getId_traje());
            vista.setTaller("ID " + cita.getId_sala());
            vista.setOficial("ID " + cita.getId_empleado());
            vista.setAprendices(
                    (aprendices[0] != null ? aprendices[0] : "—") + "\n" +
                    (aprendices[1] != null ? aprendices[1] : "")
            );
            
            return;
        }
            

        

        /**
         * Modo online: Resolución de nombres mediante listas de la BD
         */
        try {
            ArrayList<Cliente>  clientes  = acceso.recogeClientes(c);
            ArrayList<Traje>    trajes    = acceso.recogeTrajes(c);
            ArrayList<Taller>   talleres  = acceso.recogeTalleres(c);
            ArrayList<Empleado> empleados = acceso.recogeEmpleados(c);

            /**
             * Poblar vista con nombres reales
             */
            vista.setCliente(nombreCliente(clientes, cita.getId_cliente()));
            vista.setTraje(nombreTraje(trajes, cita.getId_traje()));
            vista.setTaller(nombreTaller(talleres, cita.getId_sala()));
            vista.setOficial(nombreEmpleado(empleados, cita.getId_empleado()));

            /**
             * Formateo del bloque de texto para aprendices
             */
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

    /**
     * Busca el nombre de un cliente dado su ID en una lista proporcionada.
     * @param lista Lista de clientes disponibles.
     * @param id Identificador a buscar.
     * @return Nombre del cliente o "ID x" si no se encuentra.
     */
    private String nombreCliente(ArrayList<Cliente> lista, int id) {
        for (Cliente x : lista)
            if (x.getId_cliente() == id)
                return x.getNombre();
        return "ID " + id;
    }

    /**
     * Busca el nombre de un traje dado su ID en una lista proporcionada.
     * @param lista Lista de trajes disponibles.
     * @param id Identificador a buscar.
     * @return Nombre del traje o "ID x" si no se encuentra.
     */
    private String nombreTraje(ArrayList<Traje> lista, int id) {
        for (Traje x : lista)
            if (x.getId_traje() == id)
                return x.getNombre_traje();
        return "ID " + id;
    }

    /**
     * Busca el nombre y tipo de un taller dado su ID en una lista proporcionada.
     * @param lista Lista de talleres disponibles.
     * @param id Identificador a buscar.
     * @return Nombre formateado del taller o "ID x" si no se encuentra.
     */
    private String nombreTaller(ArrayList<Taller> lista, int id) {
        for (Taller x : lista)
            if (x.getId_sala() == id)
                return x.getNombre() + " (" + x.getTipo() + ")";
        return "ID " + id;
    }

    /**
     * Busca el nombre completo de un empleado dado su ID en una lista proporcionada.
     * @param lista Lista de empleados disponibles.
     * @param id Identificador a buscar.
     * @return Nombre y apellido del empleado o "ID x" si no se encuentra.
     */
    private String nombreEmpleado(ArrayList<Empleado> lista, int id) {
        for (Empleado x : lista)
            if (x.getId_empleado() == id)
                return x.getNombre() + " " + x.getApellido();
        return "ID " + id;
    }
}