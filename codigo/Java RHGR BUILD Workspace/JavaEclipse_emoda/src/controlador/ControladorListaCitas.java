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
 * Controlador para la gestión y visualización de la lista de citas.
 * Administra el filtrado por tipo de taller (diseño, costura, pruebas), la búsqueda
 * de citas por fecha o cliente, y coordina las operaciones CRUD (Crear, Leer, Actualizar, Borrar).
 */
public class ControladorListaCitas {

    private final ListaCitas vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private ArrayList<Cita> citas;
    private ArrayList<Cita> citasFiltradas;
    private ArrayList<Cita_Aprendiz> aprendices;
    private final Empleado empleado;

    /**
     * Listas auxiliares para la resolución de nombres (IDs -> Strings legibles)
     */
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Taller> listaTalleres;
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Empleado> listaEmpleados;

    /**
     * Constructor del controlador de la lista de citas.
     * Inicializa los datos, carga las listas auxiliares de la BD y configura los eventos de la vista.
     * 
     * @param vista Ventana que contiene la tabla de citas.
     * @param acceso Objeto de acceso a la base de datos.
     * @param c Conexión activa.
     * @param citas Lista inicial de citas.
     * @param aprendices Relación de aprendices asignados a las citas.
     * @param empleado Usuario actualmente logado en el sistema.
     */
    public ControladorListaCitas(ListaCitas vista, AccesoBBDD acceso, Connection c,
                                  ArrayList<Cita> citas, ArrayList<Cita_Aprendiz> aprendices, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.citas = citas;
        this.citasFiltradas = new ArrayList<>(citas);
        this.aprendices = aprendices;
        this.empleado = empleado;

        cargarListas();
        cargarTabla(citasFiltradas);

        /**
         * Configuración de botones de filtrado rápido
         */
        vista.getBtnTodas().addActionListener(e -> {
            citasFiltradas = new ArrayList<>(citas);
            cargarTabla(citasFiltradas);
        });

        vista.getBtnDiseno().addActionListener(e -> filtrarPorTipo("diseño"));
        vista.getBtnCostura().addActionListener(e -> filtrarPorTipo("costura"));
        vista.getBtnPruebas().addActionListener(e -> filtrarPorTipo("pruebas"));

        /**
         * Configuración de acciones y navegación
         */
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnVerDetalles().addActionListener(e -> verDetalle());
        vista.getBtnEditar().addActionListener(e -> editarCita());
        vista.getBtnEliminar().addActionListener(e -> eliminarCita());
        vista.getBtnNuevaCita().addActionListener(e -> nuevaCita());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    /**
     * Recupera de la base de datos todas las entidades necesarias (clientes, talleres, trajes, empleados)
     * para poder mostrar nombres en lugar de IDs numéricos en la tabla.
     */
    private void cargarListas() {
        try {
            listaClientes = acceso.recogeClientes(c);
            listaTalleres = acceso.recogeTalleres(c);
            listaTrajes = acceso.recogeTrajes(c);
            listaEmpleados = acceso.recogeEmpleados(c);
            aprendices = acceso.recogeCitasAprendiz(c);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rellena el modelo de la tabla en la vista con la lista de citas proporcionada.
     * Realiza la traducción de IDs a nombres en tiempo real para cada fila.
     * 
     * @param lista Lista de citas a visualizar.
     */
    private void cargarTabla(ArrayList<Cita> lista) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTableCitas().getModel();
        modelo.setRowCount(0);

        for (Cita cita : lista) {

            if (empleado.getCategoria().equals("aprendiz")) {

                for (Cita_Aprendiz aprendiz : aprendices) {
                    if (aprendiz.getId_empleado() == empleado.getId_empleado() &&
                        aprendiz.getId_cita() == cita.getId_cita()) {

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

            } else if (empleado.getCategoria().equals("oficial") || empleado.getCategoria().equals("maestro")) {

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
    }

    /**
     * Filtra las citas basándose en la especialidad del taller (sala) asignado.
     * 
     * @param tipo El tipo de taller por el que filtrar (diseño, costura o pruebas).
     */
    private void filtrarPorTipo(String tipo) {
        citasFiltradas = citas.stream()
                .filter(cita -> {
                    for (Taller t : listaTalleres)
                        if (t.getId_sala() == cita.getId_sala())
                            return t.getTipo().equalsIgnoreCase(tipo);
                    return false;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        if (citasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "No hay citas de tipo '" + tipo + "'.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        cargarTabla(citasFiltradas);
    }

    /**
     * Realiza una búsqueda en la lista de citas comparando el texto ingresado
     * con la fecha de la cita o el nombre del cliente.
     */
    private void buscar() {
        String texto = vista.getTextField().getText().trim().toLowerCase();
        citasFiltradas = new ArrayList<>();

        for (Cita cita : citas) {
            String clienteNombre = nombreCliente(cita.getId_cliente()).toLowerCase();
            if (cita.getFecha().toString().contains(texto)
                    || clienteNombre.contains(texto)) {
                citasFiltradas.add(cita);
            }
        }

        cargarTabla(citasFiltradas);
    }

    /**
     * Abre una ventana de detalle para la cita seleccionada, mostrando información
     * extendida incluyendo los nombres de los aprendices asignados.
     */
    private void verDetalle() {
        int fila = vista.getTableCitas().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una cita.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cita cita = citasFiltradas.get(fila);
        int contador = 0;
        String[] aprs = new String[]{"", ""};
        if(empleado.getCategoria().equals("aprendiz")) {
        	aprs[0] = empleado.getNombre() + " " + empleado.getApellido();
        	contador++;
        }

        for (Cita_Aprendiz a : aprendices) {
            if (a.getId_cita() == cita.getId_cita()) {
                for (Empleado e : listaEmpleados) {
                    if (a.getId_empleado() == e.getId_empleado()) {
                        if (contador < 2) aprs[contador] = e.getNombre() + " " + e.getApellido();
                        contador++;
                    }
                }
            }
        }

        DetalleCita vistaDetalle = new DetalleCita();
        new ControladorDetalleCita(vistaDetalle, cita, acceso, c, aprs);
        vistaDetalle.setVisible(true);
    }

    /**
     * Prepara y abre el formulario de edición (NuevaCita) cargando los datos
     * de la cita seleccionada y sus aprendices relacionados.
     */
    private void editarCita() {
        int fila = vista.getTableCitas().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
            return;
        }

        Cita citaEditable = citasFiltradas.get(fila);
        String clienteEditable = nombreCliente(citaEditable.getId_cliente());
        String trajeEditable = nombreTraje(citaEditable.getId_traje());

        String tallerEditable = "";
        for (Taller t : listaTalleres) {
            if (t.getId_sala() == citaEditable.getId_sala()) {
                tallerEditable = t.getNombre() + " (" + t.getTipo() + ")";
            }
        }

        

        Cita_Aprendiz a1 = null;
        Cita_Aprendiz a2 = null;

        for (Cita_Aprendiz a : aprendices) {
            if (a.getId_cita() == citaEditable.getId_cita()) {
                if (a1 == null) a1 = a;
                else a2 = a;
            }
        }
        
        String empleadoEditable = "";
        for (Empleado e : listaEmpleados) {
            if (e.getId_empleado() == citaEditable.getId_empleado()) {
                empleadoEditable = e.getNombre() + " " + e.getApellido() + " (" + e.getCategoria() + ")";
                if(empleado.getCategoria().equals("oficial") && empleado.getId_empleado() == citaEditable.getId_empleado()) {
                	NuevaCita vistaForm = new NuevaCita();
                    new ControladorNuevaCita(vistaForm, acceso, vista, null, null, c,
                            empleado, citaEditable, clienteEditable, trajeEditable,
                            tallerEditable, empleadoEditable, a1, a2);

                    vistaForm.setVisible(true);
                } else if (empleado.getCategoria().equals("maestro")){
                	NuevaCita vistaForm = new NuevaCita();
                    new ControladorNuevaCita(vistaForm, acceso, vista, null, null, c,
                            empleado, citaEditable, clienteEditable, trajeEditable,
                            tallerEditable, empleadoEditable, a1, a2);

                    vistaForm.setVisible(true);
                } else {
                	JOptionPane.showMessageDialog(vista, "No puedes editar citas de otros oficiales o maestros",
                			"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Elimina la cita seleccionada de la base de datos y actualiza la vista.
     */
    private void eliminarCita() {
        int fila = vista.getTableCitas().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
            return;
        }

        Cita cita = citasFiltradas.get(fila);
        
        for (Empleado e : listaEmpleados) {
            if (e.getId_empleado() == cita.getId_empleado()) {
                if(empleado.getCategoria().equals("oficial") && empleado.getId_empleado() == cita.getId_empleado()) {
                	int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Eliminar esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        try {
                            acceso.eliminarCita(c, cita.getId_cita());
                            citas = acceso.recogeCitas(c);
                            citasFiltradas = new ArrayList<>(citas);
                            cargarTabla(citasFiltradas);
                            ListaCitas lc = new ListaCitas();
                            new ControladorListaCitas(lc, acceso, c, citas, aprendices, empleado);
                            lc.setVisible(true);
                            vista.dispose();
                        } catch (SQLException sqle) {
                        	sqle.printStackTrace();
                        }
                    }
                } else if (empleado.getCategoria().equals("maestro")){
                	int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Eliminar esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        try {
                            acceso.eliminarCita(c, cita.getId_cita());
                            citas = acceso.recogeCitas(c);
                            citasFiltradas = new ArrayList<>(citas);
                            cargarTabla(citasFiltradas);
                            ListaCitas lc = new ListaCitas();
                            new ControladorListaCitas(lc, acceso, c, citas, aprendices, empleado);
                            lc.setVisible(true);
                            vista.dispose();
                        } catch (SQLException sqle) {
                        	sqle.printStackTrace();
                        }
                    }
                } else {
                	JOptionPane.showMessageDialog(vista, "No puedes eliminar citas de otros oficiales o maestros",
                			"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Abre un formulario vacío para crear una nueva cita.
     */
    private void nuevaCita() {
        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm, acceso, vista, null, null, c, empleado,
                null, null, null, null, null, null, null);
        vistaForm.setVisible(true);
    }

    /**
     * Cierra la vista actual y redirige a la ventana principal del empleado
     * dependiendo de su categoría (Maestro, Oficial o Aprendiz).
     */
    private void volver() {
        try {
            String rol = empleado.getCategoria().toLowerCase();
            switch (rol) {
                case "maestro":
                    VentanaMaestro vm = new VentanaMaestro();
                    new ControladorMaestro(vm, acceso, c, empleado);
                    vm.setVisible(true);
                    break;
                case "oficial":
                    VentanaOficial vo = new VentanaOficial();
                    new ControladorOficial(vo, acceso, c, empleado);
                    vo.setVisible(true);
                    break;
                case "aprendiz":
                    VentanaAprendiz va = new VentanaAprendiz();
                    new ControladorAprendiz(va, acceso, c, empleado);
                    va.setVisible(true);
                    break;
            }
            vista.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** --- Métodos de resolución de nombres (Traducción ID -> String) --- */

    private String nombreCliente(int id) {
        if (listaClientes == null) return String.valueOf(id);
        for (Cliente x : listaClientes) if (x.getId_cliente() == id) return x.getNombre();
        return String.valueOf(id);
    }

    private String nombreTraje(int id) {
        if (listaTrajes == null) return String.valueOf(id);
        for (Traje x : listaTrajes) if (x.getId_traje() == id) return x.getNombre_traje();
        return String.valueOf(id);
    }

    private String nombreTaller(int id) {
        if (listaTalleres == null) return String.valueOf(id);
        for (Taller x : listaTalleres) if (x.getId_sala() == id) return x.getNombre();
        return String.valueOf(id);
    }

    private String nombreEmpleado(int id) {
        if (listaEmpleados == null) return String.valueOf(id);
        for (Empleado x : listaEmpleados) if (x.getId_empleado() == id) return x.getNombre() + " " + x.getApellido();
        return String.valueOf(id);
    }
}