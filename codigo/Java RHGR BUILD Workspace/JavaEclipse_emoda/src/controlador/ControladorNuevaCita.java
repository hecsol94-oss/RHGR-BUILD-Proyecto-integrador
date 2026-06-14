package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Controlador para la creación y edición de citas en el sistema.
 * Gestiona un formulario complejo de dos fases que incluye la validación de fechas,
 * filtrado dinámico de trajes por cliente, restricción de talleres según el estado 
 * del traje (diseño vs otros) y la asignación de aprendices con exclusión mutua.
 */
public class ControladorNuevaCita {

    private final NuevaCita vista;
    private final AccesoBBDD acceso;
    private ListaCitas ventanaCita;
    private VentanaMaestro ventanaMaestro;
    private VentanaOficial ventanaOficial;
    private final Connection c;
    private final Empleado empleado;
    private final Cita citaAEditar;

    /** Datos precargados para edición. */
    private final String clienteEditable;
    private final String trajeEditable;
    private final String tallerEditable;
    private final String empleadoEditable;

    /** Listas de datos. */
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Taller> listaTalleres;
    private ArrayList<Taller> listaTalleresFiltrados;
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Empleado> listaAprendices;
    private ArrayList<Empleado> listaOficiales;
    private ArrayList<Empleado> listaEmpleados;

    /** true cuando el traje seleccionado fue recién creado. */
    private boolean trajeRecienCreado = false;

    /**
     * Constructor principal del controlador (versión simplificada).
     * Mantiene tu estructura original pero elimina parámetros innecesarios.
     */
    public ControladorNuevaCita(NuevaCita vista, AccesoBBDD acceso, ListaCitas ventanaCita, VentanaMaestro ventanaMaestro, VentanaOficial ventanaOficial, Connection c, Empleado empleado, Cita citaAEditar, String clienteEditable, String trajeEditable, String tallerEditable, String empleadoEditable) {
        this.vista = vista;
        this.acceso = acceso;
        this.ventanaCita = ventanaCita;
        this.ventanaMaestro = ventanaMaestro;
        this.ventanaOficial = ventanaOficial;
        this.c = c;
        this.empleado = empleado;
        this.citaAEditar = citaAEditar;
        this.clienteEditable = clienteEditable;
        this.trajeEditable = trajeEditable;
        this.tallerEditable = tallerEditable;
        this.empleadoEditable = empleadoEditable;

        cargarDatosIniciales();
        asignarListeners();

        if (citaAEditar != null) {
            precargarDatos();
        }
    }

    /**
     * Carga los datos necesarios desde la base de datos para rellenar los selectores (combos) 
     * de clientes, oficiales, aprendices y trajes.
     */
    private void cargarDatosIniciales() {
        try {
            /** Talleres. */
            listaTalleres = acceso.recogeTalleres(c);

            /** Clientes. */
            listaClientes = acceso.recogeClientes(c);
            vista.getCbCliente().removeAllItems();
            for (Cliente cl : listaClientes)
                vista.getCbCliente().addItem(cl.getNombre());

            /** Trajes. */
            listaTrajes = acceso.recogeTrajes(c);
            actualizarComboTrajes();

            /** Oficiales. */
            if (empleado.getCategoria().equals("maestro")) {
                listaOficiales = new ArrayList<>();
                for (Empleado e : acceso.recogeEmpleados(c)) {
                    String cat = e.getCategoria().toLowerCase();
                    if (cat.equals("maestro") || cat.equals("oficial"))
                        listaOficiales.add(e);
                }

                vista.getCbOficial().removeAllItems();
                for (Empleado e : listaOficiales)
                    vista.getCbOficial().addItem(e.getNombre() + " " + e.getApellido() + " (" + e.getCategoria() + ")");

                /** Preseleccionar el maestro logado */
                for (int i = 0; i < listaOficiales.size(); i++) {
                    if (listaOficiales.get(i).getId_empleado() == empleado.getId_empleado()) {
                        vista.getCbOficial().setSelectedIndex(i);
                        break;
                    }
                }

            } else {
            	/** Oficiales y aprendices solo ven su propio nombre. */
                vista.getCbOficial().addItem(
                        empleado.getNombre() + " " + empleado.getApellido() + " (" + empleado.getCategoria() + ")"
                );
            }

            /** Empleados. */
            listaEmpleados = acceso.recogeEmpleados(c);

            /** Aprendices. */
            listaAprendices = acceso.recogeAprendices(c);
            cargarCombosAprendices(-1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Asigna los eventos de escucha (listeners) a los componentes de la interfaz.
     */
    private void asignarListeners() {

        /** Cambio de cliente → actualizar trajes y talleres. */
        vista.getCbCliente().addActionListener(e -> actualizarComboTrajes());

        /** Cancelar. */
        vista.getBtnCancelar().addActionListener(e -> cancelar());

        /** Nuevo cliente. */
        vista.getBtnNuevoCliente().addActionListener(e -> {
            NuevoCliente vc = new NuevoCliente();
            new ControladorNuevoCliente(vc, null, null, vista, acceso, c, null, listaClientes, empleado);
            vc.setVisible(true);

            vc.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent ev) {
                    cargarDatosIniciales();
                }
            });
        });

        /** Nuevo traje. */
        vista.getBtnNuevoTraje().addActionListener(e -> {
            int idx = vista.getCbCliente().getSelectedIndex();
            if (idx < 0) {
                JOptionPane.showMessageDialog(vista, "Selecciona primero un cliente.");
                return;
            }

            Cliente cliente = listaClientes.get(idx);
            ArrayList<Traje> trajesXCliente = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

            NuevoTraje vt = new NuevoTraje();
            new ControladorNuevoTraje(vt, acceso, c, cliente, trajesXCliente, null, empleado, null, vista);
            vt.setVisible(true);

            vt.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent ev) {
                    try {
                        listaTrajes = acceso.recogeTrajes(c);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    trajeRecienCreado = true;
                    actualizarComboTrajes();
                }
            });
        });

        /** Cambio de traje. */
        vista.getCbTraje().addActionListener(e -> {
            if (!trajeRecienCreado)
                actualizarComboTalleres();
        });

        /** Exclusión mutua aprendices. */
        vista.getCbAprendiz1().addActionListener(e -> {
            int idxApr1 = vista.getCbAprendiz1().getSelectedIndex() - 1;
            cargarCombosAprendices(idxApr1);
            vista.getCbAprendiz1().setSelectedIndex(idxApr1 + 1);
        });

        /** Siguiente fase. */
        vista.getBtnSiguiente().addActionListener(e -> avanzarFase2());

        /** Volver fase 1. */
        vista.getBtnAtras().addActionListener(e -> vista.volverFase1());

        /** Guardar. */
        vista.getBtnGuardar().addActionListener(e -> guardarCita());
    }

    /**
     * Actualiza el selector de trajes basándose en el cliente seleccionado actualmente.
     */
    private void actualizarComboTrajes() {
        vista.getCbTraje().removeAllItems();
        int idx = vista.getCbCliente().getSelectedIndex();
        if (idx < 0 || listaClientes == null || listaClientes.isEmpty()) return;

        int idCliente = listaClientes.get(idx).getId_cliente();

        for (Traje t : listaTrajes)
            if (t.getId_cliente() == idCliente)
                vista.getCbTraje().addItem(t.getNombre_traje());

        /** Al cambiar de cliente, el traje ya no es recién creado. */
        trajeRecienCreado = false;
        actualizarComboTalleres();
    }

    /**
     * Actualiza el combo de talleres.
     * Si trajeRecienCreado es verdadero, solo muestra talleres de tipo "diseño".
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

    /**
     * Gestiona la lógica de selección de aprendices asegurando que no se pueda seleccionar
     * al mismo aprendiz en ambos selectores (exclusión mutua).
     */
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

    /**
     * Carga los aprendices asignados a la cita en edición.
     */
    private void cargarAprendicesAsignados() {

        if (citaAEditar == null) return;

        ArrayList<Integer> asignados = acceso.getAprendicesDeCita(c, citaAEditar.getId_cita());

        /** Aprendiz 1. */
        if (asignados.size() >= 1) {
            int idApr1 = asignados.get(0);
            for (int i = 0; i < listaAprendices.size(); i++) {
                if (listaAprendices.get(i).getId_empleado() == idApr1) {
                    vista.getCbAprendiz1().setSelectedIndex(i + 1); /** +1 por "— Ninguno —". */
                    break;
                }
            }
        }

        /** Aprendiz 2. */
        if (asignados.size() >= 2) {
            int idApr2 = asignados.get(1);
            for (int i = 0; i < listaAprendices.size(); i++) {
                if (listaAprendices.get(i).getId_empleado() == idApr2) {
                    vista.getCbAprendiz2().setSelectedIndex(i + 1);
                    break;
                }
            }
        }
    }

    /**
     * Rellena los campos de la vista con los datos de la cita que se desea editar.
     */
    private void precargarDatos() {
        vista.setCbCliente(clienteEditable);
        vista.setCbTraje(trajeEditable);
        vista.setCbTaller(tallerEditable);
        vista.setCbOficial(empleadoEditable);

        vista.getTxtFecha().setText(citaAEditar.getFecha().toString());

        String hora = citaAEditar.getHora_inicio().toString();
        String horaReal = hora.substring(0, hora.length() - 3);
        vista.getTxtHora().setText(horaReal);

        vista.getTxtDuracion().setText(Integer.toString(citaAEditar.getDuracion()));

        cargarAprendicesAsignados();
    }

    /**
     * Valida los datos de la primera fase y, si son correctos, muestra la segunda fase
     * del formulario de creación de cita.
     */
    private void avanzarFase2() {

        String fecha = vista.getTxtFecha().getText().trim();
        String hora = vista.getTxtHora().getText().trim();
        String duracion = vista.getTxtDuracion().getText().trim();

        int idxCliente = vista.getCbCliente().getSelectedIndex();
        int idxTraje = vista.getCbTraje().getSelectedIndex();
        int idxTaller = vista.getCbTaller().getSelectedIndex();
        int idxOficial = vista.getCbOficial().getSelectedIndex();

        if (fecha.isEmpty() || hora.isEmpty() || duracion.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Rellena Fecha, Hora y Duración.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fechaIntroducida;
        LocalTime horaIntroducida;

        try {
            fechaIntroducida = LocalDate.parse(fecha);
            horaIntroducida = LocalTime.parse(hora);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha u hora incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate hoy = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        if (fechaIntroducida.isBefore(hoy) ||
                (fechaIntroducida.equals(hoy) && horaIntroducida.isBefore(horaActual))) {
            JOptionPane.showMessageDialog(vista,
                    "No se puede programar una cita en una fecha u hora anterior a la actual.",
                    "Fecha Inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int duracionInt;
        try {
            duracionInt = Integer.parseInt(duracion);
            if (duracionInt <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Duración debe ser un entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idxCliente < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un cliente.");
            return;
        }
        if (idxTraje < 0) {
            JOptionPane.showMessageDialog(vista, "El cliente no tiene trajes. Créa uno primero.");
            return;
        }
        if (idxTaller < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un taller.");
            return;
        }
        if (idxOficial < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona un oficial responsable.");
            return;
        }

        String clienteNombre = listaClientes.get(idxCliente).getNombre();
        String trajeNombre = (String) vista.getCbTraje().getSelectedItem();
        String tallerNombre = listaTalleresFiltrados.get(idxTaller).getNombre();
        String oficialNombre = vista.getCbOficial().getSelectedItem().toString();

        vista.mostrarFase2(fecha, hora, duracion, clienteNombre, trajeNombre, tallerNombre, oficialNombre);
    }

    /**
     * Recoge todos los datos del formulario, realiza las validaciones de negocio finales
     * y persiste la cita y los aprendices en la base de datos.
     */
    private void guardarCita() {

        String strFecha = vista.getTxtFecha().getText().trim();
        String strHora = vista.getTxtHora().getText().trim();
        String strDuracion = vista.getTxtDuracion().getText().trim();

        Date fechaDate = Date.valueOf(strFecha);
        Time horaTime = Time.valueOf(strHora + ":00");
        int duracionInt = Integer.parseInt(strDuracion);

        int idxCliente = vista.getCbCliente().getSelectedIndex();
        int idxTraje = vista.getCbTraje().getSelectedIndex();
        int idxTaller = vista.getCbTaller().getSelectedIndex();
        int idxOficial = vista.getCbOficial().getSelectedIndex();

        int idCliente = listaClientes.get(idxCliente).getId_cliente();
        int idSala = listaTalleresFiltrados.get(idxTaller).getId_sala();
        int idTraje = obtenerIdTraje(idCliente, idxTraje);

        if (idTraje == -1) {
            JOptionPane.showMessageDialog(vista, "No se pudo identificar el traje.");
            return;
        }

        /** Usar el id_empleado del oficial seleccionado. */
        int idOficial = 0;
        if (empleado.getCategoria().equals("maestro")) {
            idOficial = listaOficiales.get(idxOficial).getId_empleado();
        } else {
            idOficial = empleado.getId_empleado();
        }

        /** Crear o actualizar cita. */
        if (citaAEditar == null) {
            Cita nuevaCita = new Cita(0, fechaDate, horaTime, duracionInt, idOficial, idCliente, idSala, idTraje);
            acceso.insertarNuevaCita(c, nuevaCita);
        } else {
            Cita nuevaCita = new Cita(0, fechaDate, horaTime, duracionInt, idOficial, idCliente, idSala, idTraje);
            acceso.actualizarCita(c, citaAEditar.getId_cita(), nuevaCita);
        }

        /** Guardar aprendices asignados. */
        int idCitaReal;

        if (citaAEditar == null) {
            try {
                idCitaReal = acceso.recogeCitas(c).size();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error obteniendo ID de la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            idCitaReal = citaAEditar.getId_cita();
        }

        try {
            /** Borrar aprendices antiguos. */
            PreparedStatement ps = c.prepareStatement("DELETE FROM Cita_Aprendiz WHERE id_cita = ?");
            ps.setInt(1, idCitaReal);
            ps.executeUpdate();

            /** Insertar aprendiz 1. */
            int idxApr1 = vista.getCbAprendiz1().getSelectedIndex();
            if (idxApr1 > 0) {
                Empleado apr1 = listaAprendices.get(idxApr1 - 1);
                acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, idCitaReal, apr1.getId_empleado()));
            }

            /** Insertar aprendiz 2. */
            int idxApr2 = vista.getCbAprendiz2().getSelectedIndex();
            if (idxApr2 > 0) {
                Empleado apr2 = listaAprendices.get(idxApr2 - 1);
                acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, idCitaReal, apr2.getId_empleado()));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(vista, "Cita y aprendices guardados correctamente.");

       /**
        * NAVEGACIÓN FINAL SEGÚN VENTANA DE ORIGEN.
        */

        try {
            ArrayList<Cita> citasActuales = acceso.recogeCitas(c);
            ArrayList<Cita_Aprendiz> aprendicesConCita = acceso.recogeCitasAprendiz(c);

            if (ventanaCita != null) {
                ListaCitas lc = new ListaCitas();
                new ControladorListaCitas(lc, acceso, c, citasActuales, aprendicesConCita, empleado);
                lc.setVisible(true);
                vista.dispose();
                ventanaCita.dispose();
                return;
            } else if (ventanaMaestro != null) {
                VentanaMaestro vm = new VentanaMaestro();
                new ControladorMaestro(vm, acceso, c, empleado);
                vm.setVisible(true);
                vista.dispose();
                ventanaMaestro.dispose();
                return;
            } else if (ventanaOficial != null) {
                VentanaOficial vo = new VentanaOficial();
                new ControladorOficial(vo, acceso, c, empleado);
                vo.setVisible(true);
                vista.dispose();
                ventanaOficial.dispose();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recupera el ID único de un traje en función de su cliente y su posición en la lista.
     */
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

    /**
     * Cierra la ventana actual de creación de cita.
     */
    private void cancelar() {
        vista.dispose();
    }
}
