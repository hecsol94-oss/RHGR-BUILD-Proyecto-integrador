package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

// Controlador ListaCitas
public class ControladorListaCitas {

    private final ListaCitas vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private ArrayList<Cita> citas;
    private ArrayList<Cita> citasFiltradas;
    private ArrayList<Cita_Aprendiz> aprendices;
    private final Empleado empleado;

    // Listas para resolver nombres
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Taller> listaTalleres;
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Empleado> listaEmpleados;

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

        vista.getBtnTodas().addActionListener(e -> {
            citasFiltradas = new ArrayList<>(citas);
            cargarTabla(citasFiltradas);
        });

        vista.getBtnDiseno().addActionListener(e -> filtrarPorTipo("diseño"));
        vista.getBtnCostura().addActionListener(e -> filtrarPorTipo("costura"));
        vista.getBtnPruebas().addActionListener(e -> filtrarPorTipo("pruebas"));
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnVerDetalles().addActionListener(e -> verDetalle());
        vista.getBtnEditar().addActionListener(e -> editarCita());
        vista.getBtnEliminar().addActionListener(e -> eliminarCita());
        vista.getBtnNuevaCita().addActionListener(e -> nuevaCita());
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    // Carga de listas desde BD
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

    // Cargar tabla principal
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

    // Filtrar por tipo de taller
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

    // Buscar citas
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

    // Ver detalle de cita
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

        for (Cita_Aprendiz a : aprendices) {
            if (a.getId_cita() == cita.getId_cita()) {
                for (Empleado e : listaEmpleados) {
                    if (a.getId_empleado() == e.getId_empleado()) {
                        if (contador == 0) {
                            aprs[0] = e.getNombre() + " " + e.getApellido();
                        } else if (contador == 1) {
                            aprs[1] = e.getNombre() + " " + e.getApellido();
                        }
                        contador++;
                    }
                }
            }
        }

        DetalleCita vistaDetalle = new DetalleCita();
        new ControladorDetalleCita(vistaDetalle, cita, acceso, c, aprs);
        vistaDetalle.setVisible(true);
    }

    // Editar cita
    private void editarCita() {
        int fila = vista.getTableCitas().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
            return;
        }

        Cita citaEditable = citas.get(fila);

        String clienteEditable = (String) vista.getTableCitas().getValueAt(fila, 1);
        String trajeEditable = (String) vista.getTableCitas().getValueAt(fila, 2);

        String tallerEditable = "";
        for (Taller t : listaTalleres) {
            if (t.getNombre().equals(vista.getTableCitas().getValueAt(fila, 3))) {
                tallerEditable = t.getNombre() + " (" + t.getTipo() + ")";
            }
        }

        String empleadoEditable = "";
        for (Empleado e : listaEmpleados) {
            if ((e.getNombre() + " " + e.getApellido())
                    .equals(vista.getTableCitas().getValueAt(fila, 4))) {
                empleadoEditable = e.getNombre() + " " + e.getApellido()
                        + " (" + e.getCategoria() + ")";
            }
        }

        Cita_Aprendiz a1 = new Cita_Aprendiz(0, 0, 0);
        Cita_Aprendiz a2 = new Cita_Aprendiz(0, 0, 0);

        for (Cita_Aprendiz a : aprendices) {
            if (a.getId_cita() == citaEditable.getId_cita()) {
                a1 = a;
                for (Cita_Aprendiz b : aprendices) {
                    if (b.getId_cita() == citaEditable.getId_cita()
                            && b.getId_empleado() != a1.getId_empleado()) {
                        a2 = b;
                    }
                }
            }
        }

        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm, acceso, vista, null, null, c,
                empleado, citaEditable,
                clienteEditable, trajeEditable,
                tallerEditable, empleadoEditable,
                a1, a2);

        vistaForm.setVisible(true);
    }

    // Eliminar cita
    private void eliminarCita() {
        int fila = vista.getTableCitas().getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
            return;
        }

        Cita cita = citas.get(fila);

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Eliminar esta cita?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                acceso.eliminarCita(c, cita.getId_cita());
                citas = acceso.recogeCitas(c);
                cargarTabla(citas);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Nueva cita
    private void nuevaCita() {
        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm, acceso, vista,
                null, null, c, empleado,
                null, null, null, null, null, null, null);

        vistaForm.setVisible(true);
    }

    // Volver según rol
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

    // Resolución de nombres
    private String nombreCliente(int id) {
        if (listaClientes == null) return "" + id;
        for (Cliente x : listaClientes)
            if (x.getId_cliente() == id) return x.getNombre();
        return "" + id;
    }

    private String nombreTraje(int id) {
        if (listaTrajes == null) return "" + id;
        for (Traje x : listaTrajes)
            if (x.getId_traje() == id) return x.getNombre_traje();
        return "" + id;
    }

    private String nombreTaller(int id) {
        if (listaTalleres == null) return "" + id;
        for (Taller x : listaTalleres)
            if (x.getId_sala() == id) return x.getNombre();
        return "" + id;
    }

    private String nombreEmpleado(int id) {
        if (listaEmpleados == null) return "" + id;
        for (Empleado x : listaEmpleados)
            if (x.getId_empleado() == id)
                return x.getNombre() + " " + x.getApellido();
        return "" + id;
    }
}