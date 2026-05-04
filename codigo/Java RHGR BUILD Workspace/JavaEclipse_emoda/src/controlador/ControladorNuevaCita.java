package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
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
    private final String clienteEditable;
    private final String trajeEditable;
    private final String tallerEditable;
    private final String empleadoEditable;
    private final Cita_Aprendiz aprendizAEditar1;
    private final Cita_Aprendiz aprendizAEditar2;


    private ArrayList<Cliente> listaClientes;
    private ArrayList<Taller> listaTalleres; // todos los talleres
    private ArrayList<Taller> listaTalleresFiltrados; // los que se muestran en el combo
    private ArrayList<Traje> listaTrajes;
    private ArrayList<Empleado> listaAprendices;
    private ArrayList<Empleado> listaOficiales; // maestros + oficiales
    private ArrayList<Empleado> listaEmpleados;

    // true cuando el traje seleccionado fue RECIÉN creado (estado=diseño), false si ya existía
    private boolean trajeRecienCreado = false;

    /**
     * Constructor principal del controlador.
     * Inicializa las dependencias, carga los catálogos iniciales de la base de datos,
     * configura los eventos de la interfaz y precarga datos si se trata de una edición.
     */
    public ControladorNuevaCita(NuevaCita vista, AccesoBBDD acceso, ListaCitas ventanaCita, VentanaMaestro ventanaMaestro, VentanaOficial ventanaOficial, Connection c, Empleado empleado, Cita citaAEditar, String clienteEditable, String trajeEditable, String tallerEditable, String empleadoEditable, Cita_Aprendiz aprendizAEditar1, Cita_Aprendiz aprendizAEditar2) {
        this.vista    = vista;
        this.acceso   = acceso;
        this.ventanaCita = ventanaCita;
        this.ventanaMaestro = ventanaMaestro;
        this.ventanaOficial = ventanaOficial;
        this.c        = c;
        this.empleado = empleado;
        this.citaAEditar = citaAEditar;
        this.clienteEditable = clienteEditable;
        this.trajeEditable = trajeEditable;
        this.tallerEditable = tallerEditable;
        this.empleadoEditable = empleadoEditable;
        this.aprendizAEditar1 = aprendizAEditar1;
        this.aprendizAEditar2 = aprendizAEditar2;
        
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

            listaEmpleados = acceso.recogeEmpleados(c);
            
            // Aprendices
            listaAprendices = acceso.recogeAprendices(c);
            cargarCombosAprendices(-1);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            if (t.getId_cliente() == idCliente) vista.getCbTraje().addItem(t.getNombre_traje());
        // Al cambiar de cliente, el traje ya no es recién creado
        trajeRecienCreado = false;
        actualizarComboTalleres();
    }

    /**
     * Actualiza el combo de talleres.
     * Si trajeRecienCreado es verdadero, solo muestra talleres de tipo "diseño".
     * En caso contrario, muestra todos los talleres disponibles.
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
     * 
     * @param excluirDeApr2 Índice del aprendiz seleccionado en el primer combo para omitirlo en el segundo.
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
     * Rellena los campos de la vista con los datos de la cita que se desea editar.
     */
    private void precargarDatos() {
        vista.setCbCliente(clienteEditable);
        vista.setCbTraje(trajeEditable);
        vista.setCbTaller(tallerEditable);
        vista.setCbOficial(empleadoEditable);
        vista.getTxtFecha().setText(citaAEditar.getFecha().toString());
        vista.getTxtHora().setText(citaAEditar.getHora_inicio().toString());
        vista.getTxtDuracion().setText(Integer.toString(citaAEditar.getDuracion()));        
    }

    /**
     * Asigna los eventos de escucha (listeners) a los componentes de la interfaz,
     * como cambios de selección en combos y clics en botones.
     */
    private void asignarListeners() {

        // Cambio de cliente → actualizar trajes y talleres
        vista.getCbCliente().addActionListener(e -> actualizarComboTrajes());

        vista.getBtnCancelar().addActionListener(e -> cancelar());
        // Nuevo cliente
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

        // Nuevo traje — siempre estado diseño, y al volver filtra talleres a solo diseño
        vista.getBtnNuevoTraje().addActionListener(e -> {
        	
            int idx = vista.getCbCliente().getSelectedIndex();
            if (idx < 0 || listaClientes.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona primero un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Cliente cliente = listaClientes.get(idx);
            ArrayList<Traje> trajesXCliente = acceso.getTrajesPorCliente(c, cliente.getId_cliente());
            NuevoTraje vt = new NuevoTraje();
            new ControladorNuevoTraje(vt, acceso, c, cliente, trajesXCliente, null, empleado, null, vista);
            vt.setVisible(true);
            vt.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent ev) {
                    // Recargar trajes y marcar que el traje es nuevo → solo talleres diseño
                    try { listaTrajes = acceso.recogeTrajes(c);
                    } catch (SQLException ex) {
                    	ex.printStackTrace();
                    }
                    trajeRecienCreado = true;
                    actualizarComboTrajes();
                    // Seleccionar el último traje del cliente (el recién creado)
                    int idxCli = vista.getCbCliente().getSelectedIndex();
                    if (idxCli >= 0) {
                        int idCli = listaClientes.get(idxCli).getId_cliente();
                        int ultimoIdx = -1, contador = 0;
                        for (Traje t : listaTrajes) {
                            if (t.getId_cliente() == idCli) {
                            	ultimoIdx = contador; contador++;
                            }
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
        	return; }
        if (idxOficial < 0) {
        	JOptionPane.showMessageDialog(vista, "Selecciona un oficial responsable.");
        	return;
        }

        String clienteNombre = listaClientes.get(idxCliente).getNombre();
        String trajeNombre = (String) vista.getCbTraje().getSelectedItem();
        String tallerNombre = listaTalleresFiltrados.get(idxTaller).getNombre();
        String oficialNombre = listaOficiales.get(idxOficial).getNombre() + " " + listaOficiales.get(idxOficial).getApellido();

        cargarCombosAprendices(-1);
        vista.mostrarFase2(fecha, hora, duracion, clienteNombre, trajeNombre, tallerNombre, oficialNombre);
    }

    /**
     * Recoge todos los datos del formulario, realiza las validaciones de negocio finales
     * (como asegurar que la fecha/hora no sean pasadas) y persiste la cita y los 
     * aprendices en la base de datos.
     */
    private void guardarCita() {
        String strFecha = vista.getTxtFecha().getText().trim();
        String strHora = vista.getTxtHora().getText().trim();
        String strDuracion = vista.getTxtDuracion().getText().trim();
        int idxCliente = vista.getCbCliente().getSelectedIndex();
        int idxTraje = vista.getCbTraje().getSelectedIndex();
        int idxTaller = vista.getCbTaller().getSelectedIndex();
        int idxOficial = vista.getCbOficial().getSelectedIndex();

        Date fecha;
        Time hora;
        int duracion;
        try { 
            fecha = Date.valueOf(strFecha); 
            hora = Time.valueOf(strHora + ":00");
            
            // VALIDACIÓN DE FECHA ANTERIOR
            // Obtenemos la fecha de hoy a las 00:00 para comparar solo días
            long milisHoy = System.currentTimeMillis();
            Date hoy = new Date(milisHoy);
            Time horaActual = new Time(milisHoy);
            
            // Si la fecha introducida es estrictamente anterior a hoy
            if (fecha.before(hoy)) {
                JOptionPane.showMessageDialog(vista, 
                    "No se puede programar una cita en una fecha anterior a la actual.", 
                    "Fecha Inválida", 
                    JOptionPane.WARNING_MESSAGE);
                return; // Cortamos la ejecución para que no guarde nada
            }
            
            if (fecha.toString().equals(hoy.toString())) { // Comparamos si es el mismo día
                if (hora.before(horaActual)) {
                    JOptionPane.showMessageDialog(vista, "La hora de inicio no puede ser anterior a la hora actual para citas de hoy.", "Hora Inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha incorrecto (yyyy-MM-dd).", "Error", JOptionPane.ERROR_MESSAGE); 
            return;
        }
        try { hora = Time.valueOf(strHora + ":00");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de hora incorrecto (HH:mm).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try { 
        	duracion = Integer.parseInt(strDuracion);
        	if (duracion <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Duración debe ser un entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCliente = listaClientes.get(idxCliente).getId_cliente();
        int idSala = listaTalleresFiltrados.get(idxTaller).getId_sala();
        int idTraje = obtenerIdTraje(idCliente, idxTraje);
        if (idTraje == -1) {
        	JOptionPane.showMessageDialog(vista, "No se pudo identificar el traje.");
        	return;
        }

        // Usar el id_empleado del oficial seleccionado (NO el del empleado logado)
        int idOficial = listaOficiales.get(idxOficial).getId_empleado();
        
        if (citaAEditar == null) {
                    	
            	Cita nuevaCita = new Cita(0, fecha, hora, duracion, idOficial, idCliente, idSala, idTraje);
                acceso.insertarNuevaCita(c, nuevaCita);
				
        } else {
	
				Cita nuevaCita = new Cita(0, fecha, hora, duracion, idOficial, idCliente, idSala, idTraje);
	        	acceso.actualizarCita(c, citaAEditar.getId_cita(), nuevaCita);


        }

        

        // Aprendices
        try {
            // 1. Obtener el ID de la cita recién creada (el último ID real)
            ArrayList<Cita> citasActuales = acceso.recogeCitas(c);
            int idCitaReal = citasActuales.size();

            int idxApr1 = vista.getCbAprendiz1().getSelectedIndex(); 
            int idxApr2 = vista.getCbAprendiz2().getSelectedIndex();
            String nombreApr1 = (String) vista.getCbAprendiz1().getSelectedItem();
            String nombreApr2 = (String) vista.getCbAprendiz2().getSelectedItem();

            // --- PROCESAR APRENDIZ 1 ---
            if (idxApr1 > 0) {
                for (Empleado emp : listaEmpleados) {
                    if ((emp.getNombre() + " " + emp.getApellido()).equals(nombreApr1)) {
                    	if (aprendizAEditar1 == null && citaAEditar == null) {
                    		acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, idCitaReal, emp.getId_empleado()));
                            break;
                    	} else if (aprendizAEditar1 == null && citaAEditar != null) {
                    		acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, citaAEditar.getId_cita(), emp.getId_empleado()));
                    		break;
                    	} else {
                    		acceso.actualizarCitaAprendiz(c, aprendizAEditar1.getId_aprendiz(), new Cita_Aprendiz(0, citaAEditar.getId_cita(), emp.getId_empleado()));
                    		break;
                    	}
                    }
                }
            }

            // --- PROCESAR APRENDIZ 2 (Sin 'else', para que también se ejecute) ---
            if (idxApr2 > 0) {
                for (Empleado emp : listaEmpleados) {
                    if ((emp.getNombre() + " " + emp.getApellido()).equals(nombreApr2)) {
                    	if (aprendizAEditar2 == null && citaAEditar == null) {
                    		acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, idCitaReal, emp.getId_empleado()));
                            break;
                    	} else if (aprendizAEditar2 == null && citaAEditar != null) {
                    		acceso.insertarNuevaCita_Aprendiz(c, new Cita_Aprendiz(0, citaAEditar.getId_cita(), emp.getId_empleado()));
                    		break;
                    	} else {
                    		acceso.actualizarCitaAprendiz(c, aprendizAEditar2.getId_aprendiz(), new Cita_Aprendiz(0, citaAEditar.getId_cita(), emp.getId_empleado()));
                    		break;
                    		
                    	}
                    }
                }
            }

            // --- VALIDACIÓN Y CIERRE ---
            if (idxApr1 <= 0 && idxApr2 <= 0) {
                JOptionPane.showMessageDialog(vista, "Por favor, asigna al menos un aprendiz.");
            } else {
                JOptionPane.showMessageDialog(vista, "Cita y aprendices guardados correctamente.");
                
                if (ventanaCita != null) {
                	ArrayList<Cita_Aprendiz> aprendicesConCita = acceso.recogeCitasAprendiz(c);
                    citasActuales = acceso.recogeCitas(c);
                    ListaCitas lc = new ListaCitas();
                    new ControladorListaCitas(lc, acceso, c, citasActuales, aprendicesConCita, empleado);
                    lc.setVisible(true);
                    vista.dispose();
                    ventanaCita.dispose();
                    
                } else {
                    vista.dispose();
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recupera el ID único de un traje en función de su cliente y su posición en la lista.
     * 
     * @param idCliente ID del cliente propietario.
     * @param indexCombo Índice seleccionado en el selector de trajes.
     * @return El ID del traje o -1 si no se encuentra.
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
			if (ventanaCita != null && ventanaMaestro == null && ventanaOficial == null) {
		    	vista.dispose();
			} else if (ventanaCita == null && (ventanaMaestro != null || ventanaOficial != null)) {
                vista.dispose();
            }	
    }
}