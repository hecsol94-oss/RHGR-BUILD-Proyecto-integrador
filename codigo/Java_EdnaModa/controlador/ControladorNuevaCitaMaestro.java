package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ControladorNuevaCitaMaestro {

    private final NuevaCitaMaestro vista;
    private final AccesoBBDD acceso;
    private final Connection c;
    private final Empleado empleado;

    private ArrayList<Cliente>  listaClientes;
    private ArrayList<Taller>   listaTalleres;       // todos los talleres
    private ArrayList<Taller>   listaTalleresFiltrados; // los que se muestran en el combo
    private ArrayList<Traje>    listaTrajes;
    private ArrayList<Empleado> listaAprendices;
    private ArrayList<Empleado> listaOficiales;      // maestros + oficiales

    // true cuando el traje seleccionado fue RECIÉN creado (estado=diseño), false si ya existía
    private boolean trajeRecienCreado = false;

    public ControladorNuevaCitaMaestro(NuevaCitaMaestro vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.c        = c;
        this.empleado = empleado;

        cargarDatosIniciales();
        asignarListeners();
    }

    // ── Carga inicial ─────────────────────────────────────────────────────────
    private void cargarDatosIniciales() {
        try {
            // Talleres — se carga PRIMERO porque actualizarComboTrajes lo necesita
            listaTalleres = acceso.recogeTalleres(c);

            // Clientes
            listaClientes = acceso.recogeClientes(c);
            vista.getCbCliente().removeAllItems();
            for (Cliente cl : listaClientes) vista.getCbCliente().addItem(cl.getNombre());

            // Trajes (filtra por cliente) — ya podemos llamar esto porque listaTalleres está cargado
            listaTrajes = acceso.recogeTrajes(c);
            actualizarComboTrajes();

            // Oficiales: todos los empleados que sean maestro u oficial
            listaOficiales = new ArrayList<>();
            for (Empleado e : acceso.recogeEmpleados(c)) {
                String cat = e.getCategoria().toLowerCase();
                if (cat.equals("maestro") || cat.equals("oficial")) listaOficiales.add(e);
            }
            vista.getCbOficial().removeAllItems();
            for (Empleado e : listaOficiales)
                vista.getCbOficial().addItem(e.getNombre() + " " + e.getApellido() + " (" + e.getCategoria() + ")");
            // Pre-seleccionar el empleado logado si está en la lista
            for (int i = 0; i < listaOficiales.size(); i++) {
                if (listaOficiales.get(i).getId_empleado() == empleado.getId_empleado()) {
                    vista.getCbOficial().setSelectedIndex(i);
                    break;
                }
            }

            // Aprendices
            listaAprendices = acceso.recogeAprendices(c);
            cargarCombosAprendices(-1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Actualizar combo trajes según cliente seleccionado ────────────────────
    private void actualizarComboTrajes() {
        vista.getCbTraje().removeAllItems();
        int idx = vista.getCbCliente().getSelectedIndex();
        if (idx < 0 || listaClientes == null || listaClientes.isEmpty()) return;
        int idCliente = listaClientes.get(idx).getId_cliente();
        for (Traje t : listaTrajes)
            if (t.getId_cliente() == idCliente) vista.getCbTraje().addItem(t.getNombre_traje());
        // Al cambiar de cliente, el traje ya no es recién creado
        trajeRecienCreado = false;
        actualizarComboTalleres();
    }

    /**
     * Actualiza el combo de talleres.
     * Si trajeRecienCreado=true → solo talleres de tipo "diseño".
     * Si el traje ya existía → todos los talleres disponibles.
     */
    private void actualizarComboTalleres() {
        listaTalleresFiltrados = new ArrayList<>();
        vista.getCbTaller().removeAllItems();
        if (listaTalleres == null) return;

        for (Taller t : listaTalleres) {
            if (trajeRecienCreado) {
                if (t.getTipo().equalsIgnoreCase("diseño")) {
                    listaTalleresFiltrados.add(t);
                    vista.getCbTaller().addItem(t.getNombre() + " (" + t.getTipo() + ")");
                }
            } else {
                listaTalleresFiltrados.add(t);
                vista.getCbTaller().addItem(t.getNombre() + " (" + t.getTipo() + ")");
            }
        }
    }

    // ── Combos aprendices con exclusión mutua ─────────────────────────────────
    private void cargarCombosAprendices(int excluirDeApr2) {
        vista.getCbAprendiz1().removeAllItems();
        vista.getCbAprendiz1().addItem("— Ninguno —");
        for (Empleado e : listaAprendices)
            vista.getCbAprendiz1().addItem(e.getNombre() + " " + e.getApellido());

        vista.getCbAprendiz2().removeAllItems();
        vista.getCbAprendiz2().addItem("— Ninguno —");
        for (int i = 0; i < listaAprendices.size(); i++) {
            if (i == excluirDeApr2) continue;
            Empleado e = listaAprendices.get(i);
            vista.getCbAprendiz2().addItem(e.getNombre() + " " + e.getApellido());
        }
    }

    // ── Listeners ─────────────────────────────────────────────────────────────
    private void asignarListeners() {

        // Cambio de cliente → actualizar trajes y talleres
        vista.getCbCliente().addActionListener(e -> actualizarComboTrajes());

        // Nuevo cliente
        vista.getBtnNuevoCliente().addActionListener(e -> {
            NuevoCliente vc = new NuevoCliente();
            new ControladorNuevoCliente(vc, acceso, c, null);
            vc.setVisible(true);
            vc.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent ev) { cargarDatosIniciales(); }
            });
        });

        // Nuevo traje — siempre estado diseño, y al volver filtra talleres a solo diseño
        vista.getBtnNuevoTraje().addActionListener(e -> {
            int idx = vista.getCbCliente().getSelectedIndex();
            if (idx < 0 || listaClientes.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona primero un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Cliente cliente = listaClientes.get(idx);
            NuevoTraje vt = new NuevoTraje();
            new ControladorNuevoTraje(vt, acceso, c, cliente);
            vt.setVisible(true);
            vt.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent ev) {
                    // Recargar trajes y marcar que el traje es nuevo → solo talleres diseño
                    try { listaTrajes = acceso.recogeTrajes(c); } catch (SQLException ex) { ex.printStackTrace(); }
                    trajeRecienCreado = true;
                    actualizarComboTrajes();
                    // Seleccionar el último traje del cliente (el recién creado)
                    int idxCli = vista.getCbCliente().getSelectedIndex();
                    if (idxCli >= 0) {
                        int idCli = listaClientes.get(idxCli).getId_cliente();
                        int ultimoIdx = -1, contador = 0;
                        for (Traje t : listaTrajes) {
                            if (t.getId_cliente() == idCli) { ultimoIdx = contador; contador++; }
                        }
                        if (ultimoIdx >= 0) vista.getCbTraje().setSelectedIndex(ultimoIdx);
                    }
                }
            });
        });

        // Cambio en combo traje → si cambia manualmente, ya NO es traje recién creado
        vista.getCbTraje().addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged") && !trajeRecienCreado) {
                actualizarComboTalleres();
            }
        });

        // Aprendiz 1 cambia → actualizar Aprendiz 2 sin ese aprendiz
        vista.getCbAprendiz1().addActionListener(e -> {
            int idxApr1 = vista.getCbAprendiz1().getSelectedIndex() - 1;
            cargarCombosAprendices(idxApr1);
            vista.getCbAprendiz1().setSelectedIndex(idxApr1 + 1);
        });

        // Siguiente → fase 2
        vista.getBtnSiguiente().addActionListener(e -> avanzarFase2());

        // Cancelar
        vista.getBtnCancelar().addActionListener(e -> vista.dispose());

        // Volver a fase 1
        vista.getBtnAtras().addActionListener(e -> vista.volverFase1());

        // Guardar
        vista.getBtnGuardar().addActionListener(e -> guardarCita());
    }

    // ── Avanzar a fase 2 ──────────────────────────────────────────────────────
    private void avanzarFase2() {
        String fecha    = vista.getTxtFecha().getText().trim();
        String hora     = vista.getTxtHora().getText().trim();
        String duracion = vista.getTxtDuracion().getText().trim();
        int idxCliente  = vista.getCbCliente().getSelectedIndex();
        int idxTraje    = vista.getCbTraje().getSelectedIndex();
        int idxTaller   = vista.getCbTaller().getSelectedIndex();
        int idxOficial  = vista.getCbOficial().getSelectedIndex();

        if (fecha.isEmpty() || hora.isEmpty() || duracion.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Rellena Fecha, Hora y Duración.", "Campos incompletos", JOptionPane.WARNING_MESSAGE); return;
        }
        if (idxCliente < 0) { JOptionPane.showMessageDialog(vista, "Selecciona un cliente."); return; }
        if (idxTraje   < 0) { JOptionPane.showMessageDialog(vista, "El cliente no tiene trajes. Créa uno primero."); return; }
        if (idxTaller  < 0) { JOptionPane.showMessageDialog(vista, "Selecciona un taller."); return; }
        if (idxOficial < 0) { JOptionPane.showMessageDialog(vista, "Selecciona un oficial responsable."); return; }

        String clienteNombre = listaClientes.get(idxCliente).getNombre();
        String trajeNombre   = (String) vista.getCbTraje().getSelectedItem();
        String tallerNombre  = listaTalleresFiltrados.get(idxTaller).getNombre();
        String oficialNombre = listaOficiales.get(idxOficial).getNombre() + " " + listaOficiales.get(idxOficial).getApellido();

        cargarCombosAprendices(-1);
        vista.mostrarFase2(fecha, hora, duracion, clienteNombre, trajeNombre, tallerNombre, oficialNombre);
    }

    // ── Guardar cita ──────────────────────────────────────────────────────────
    private void guardarCita() {
        String strFecha    = vista.getTxtFecha().getText().trim();
        String strHora     = vista.getTxtHora().getText().trim();
        String strDuracion = vista.getTxtDuracion().getText().trim();
        int idxCliente = vista.getCbCliente().getSelectedIndex();
        int idxTraje   = vista.getCbTraje().getSelectedIndex();
        int idxTaller  = vista.getCbTaller().getSelectedIndex();
        int idxOficial = vista.getCbOficial().getSelectedIndex();

        Date fecha; Time hora; int duracion;
        try { fecha = Date.valueOf(strFecha); } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha incorrecto (yyyy-MM-dd).", "Error", JOptionPane.ERROR_MESSAGE); return;
        }
        try { hora = Time.valueOf(strHora + ":00"); } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de hora incorrecto (HH:mm).", "Error", JOptionPane.ERROR_MESSAGE); return;
        }
        try { duracion = Integer.parseInt(strDuracion); if (duracion <= 0) throw new NumberFormatException(); } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Duración debe ser un entero positivo.", "Error", JOptionPane.ERROR_MESSAGE); return;
        }

        int idCliente = listaClientes.get(idxCliente).getId_cliente();
        int idSala    = listaTalleresFiltrados.get(idxTaller).getId_sala();
        int idTraje   = obtenerIdTraje(idCliente, idxTraje);
        if (idTraje == -1) { JOptionPane.showMessageDialog(vista, "No se pudo identificar el traje."); return; }

        // Usar el id_empleado del oficial seleccionado (NO el del empleado logado)
        int idOficial = listaOficiales.get(idxOficial).getId_empleado();

        Cita nuevaCita = new Cita(0, fecha, hora, duracion, idOficial, idCliente, idSala, idTraje);
        acceso.insertarNuevaCita(c, nuevaCita);

        // Aprendices
        try {
            ArrayList<Cita> citas = acceso.recogeCitas(c);
            int idCitaNueva = citas.get(citas.size() - 1).getId_cita();

            int idxApr1 = vista.getCbAprendiz1().getSelectedIndex(); // 0=ninguno
            int idxApr2 = vista.getCbAprendiz2().getSelectedIndex();

            if (idxApr1 > 0 && idxApr1 - 1 < listaAprendices.size()) {
                Empleado apr1 = listaAprendices.get(idxApr1 - 1);
                acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, idCitaNueva, apr1.getId_empleado()));
            }

            if (idxApr2 > 0) {
                // Reconstruir índice real en listaAprendices (compensar el excluido del combo2)
                int excluido = idxApr1 - 1;
                int contador = 0, realIdx = -1;
                for (int i = 0; i < listaAprendices.size(); i++) {
                    if (i == excluido) continue;
                    contador++;
                    if (contador == idxApr2) { realIdx = i; break; }
                }
                if (realIdx >= 0) {
                    Empleado apr2 = listaAprendices.get(realIdx);
                    // Seguridad: no duplicar
                    boolean duplicado = idxApr1 > 0 && apr2.getId_empleado() == listaAprendices.get(idxApr1 - 1).getId_empleado();
                    if (!duplicado) {
                        acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, idCitaNueva, apr2.getId_empleado()));
                    }
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }

        JOptionPane.showMessageDialog(vista, "Cita guardada correctamente.");
        vista.dispose();
    }

    private int obtenerIdTraje(int idCliente, int indexCombo) {
        int contador = 0;
        for (Traje t : listaTrajes) {
            if (t.getId_cliente() == idCliente) {
                if (contador == indexCombo) return t.getId_traje();
                contador++;
            }
        }
        return -1;
    }
}