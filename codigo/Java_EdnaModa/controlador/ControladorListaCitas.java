package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Empleado;
import vista.ListaCitas;
import vista.DetalleCita;
import vista.NuevaCita1;
import vista.NuevaCita2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.stream.Collectors;

// Controlador de la ventana ListaCitas
public class ControladorListaCitas {

    private ListaCitas vista;
    private AccesoBBDD acceso;
    private Connection c;
    private ArrayList<Cita> citas;
    private ArrayList<Cita> citasFiltradas;
    private Empleado empleado;
    private boolean editable;

    public ControladorListaCitas(ListaCitas vista, AccesoBBDD acceso, Connection c,
                                  ArrayList<Cita> citas, Empleado empleado, boolean editable) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.citas = citas;
        this.citasFiltradas = new ArrayList<>(citas);
        this.empleado = empleado;
        this.editable = editable;

        if (!editable) {
            vista.getBtnNuevaCita().setVisible(false);
            vista.getBtnEditar().setVisible(false);
        }

        cargarTabla(citasFiltradas);

        vista.getBtnTodas().addActionListener(e -> {
            citasFiltradas = new ArrayList<>(citas); //actualiza citasFiltradas
            cargarTabla(citasFiltradas);
        });
        vista.getBtnDiseno().addActionListener(e -> filtrarPorTipo("diseño"));
        vista.getBtnCostura().addActionListener(e -> filtrarPorTipo("costura"));
        vista.getBtnPruebas().addActionListener(e -> filtrarPorTipo("pruebas"));

        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnVerDetalles().addActionListener(e -> verDetalle());
        vista.getBtnEditar().addActionListener(e -> editarCita());
        vista.getBtnNuevaCita().addActionListener(e -> nuevaCita());
        vista.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    private void cargarTabla(ArrayList<Cita> lista) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTableCitas().getModel();
        modelo.setRowCount(0);

        for (Cita cita : lista) {
            modelo.addRow(new Object[]{
                cita.getFecha() + " " + cita.getHora_inicio(),
                "Cliente ID: " + cita.getId_cliente(),
                "Sala ID: " + cita.getId_sala(),
                cita.getDuracion() + "h"
            });
        }
    }

    //filtra realmente por tipo usando el nombre del taller
    private void filtrarPorTipo(String tipo) {
        citasFiltradas = citas.stream()
            .filter(cita -> {
                // TODO: cruzar cita.getId_sala() con la lista de talleres para obtener el tipo real
                // Por ahora se deja preparado; descomentar la línea de abajo cuando esté disponible:
                // return obtenerTipoSala(cita.getId_sala()).equalsIgnoreCase(tipo);
                return false; // placeholder hasta integrar la lista de talleres
            })
            .collect(Collectors.toCollection(ArrayList::new));

        if (citasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "No se encontraron citas de tipo '" + tipo + "'. Integra la lista de talleres para activar este filtro.",
                "Información", JOptionPane.INFORMATION_MESSAGE);
            citasFiltradas = new ArrayList<>(citas); // evita dejar la tabla vacía
        }

        cargarTabla(citasFiltradas);
    }

    private void buscar() {
        String texto = vista.getTextField().getText().trim().toLowerCase();
        citasFiltradas = new ArrayList<>(); // actualiza citasFiltradas

        for (Cita cita : citas) {
            if (cita.getFecha().toString().contains(texto) ||
                String.valueOf(cita.getId_cliente()).contains(texto)) {
                citasFiltradas.add(cita);
            }
        }
        cargarTabla(citasFiltradas);
    }

    private void verDetalle() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una cita para ver los detalles.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //usa citasFiltradas (lista visible), no la lista original
        Cita cita = citasFiltradas.get(fila);
        DetalleCita vistaDetalle = new DetalleCita();
        new ControladorDetalleCita(vistaDetalle, cita);
        vistaDetalle.setVisible(true);
        vista.dispose();
    }

    private void editarCita() {
        if (!editable) return;

        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una cita para editar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //usa citasFiltradas (lista visible), no la lista original
        Cita cita = citasFiltradas.get(fila);
        NuevaCita1 vistaForm = new NuevaCita1();
        new ControladorNuevaCita1(vistaForm, acceso, c, empleado);
        vistaForm.setVisible(true);
        vista.dispose();
    }

    private void nuevaCita() {
        if (!editable) return;
        
       	NuevaCita1 vistaForm = new NuevaCita1();
        new ControladorNuevaCita1(vistaForm, acceso, c, empleado);
        vistaForm.setVisible(true);
        vista.dispose();

    }
}
