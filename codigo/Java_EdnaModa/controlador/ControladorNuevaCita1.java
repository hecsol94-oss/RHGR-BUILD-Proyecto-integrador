/**
 * 
 */
package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import modelo.Traje;
import vista.NuevaCita2;
import vista.NuevaCita1;
import vista.NuevoCliente;
import vista.NuevoTaller;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

// Controlador de NuevaCitaMaestro
public class ControladorNuevaCita1 {

	private final NuevaCita1 vista;
	private final AccesoBBDD acceso;
	private final Connection c;
	private final Empleado empleado;

	private ArrayList<Cliente> listaClientes;
	private ArrayList<Taller> listaTalleres;
	private ArrayList<Traje> listaTrajes;

	// Creamos el constructor

	/**
	 * 
	 * @param vista
	 * @param acceso
	 * @param c
	 * @param empleado
	 */
	public ControladorNuevaCita1(NuevaCita1 vista, AccesoBBDD acceso, Connection c, Empleado empleado) {

		this.vista = vista;
		this.acceso = acceso;
		this.c = c;
		this.empleado = empleado;

		cargarDatosIniciales();
		asignarListeners();
	}

    // Rellenamos con la informacion de la base de datos
	private void cargarDatosIniciales() {
		try {

			listaClientes = acceso.recogeClientes(c);
			vista.getCbCliente().removeAllItems();
			for (Cliente cl : listaClientes) {
				vista.getCbCliente().addItem(cl.getNombre());
			}

			listaTalleres = acceso.recogeTalleres(c);
			vista.getCbTaller().removeAllItems();
			for (Taller t : listaTalleres) {
				vista.getCbTaller().addItem(t.getNombre());
			}

			listaTrajes = acceso.recogeTrajes(c);
			actualizarComboTrajes();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(vista, "Error al cargar los datos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Rellena el ComboBox de trajes con los trajes que pertenecen al cliente
	 */
	private void actualizarComboTrajes() {
		vista.getCbTraje().removeAllItems();

		int indexCliente = vista.getCbCliente().getSelectedIndex();
		if (indexCliente < 0 || listaClientes.isEmpty())
			return;

		int idClienteSeleccionado = listaClientes.get(indexCliente).getId_cliente();

		for (Traje t : listaTrajes) {
			if (t.getId_cliente() == idClienteSeleccionado) {
				vista.getCbTraje().addItem(t.getNombre_traje());
			}
		}
	}

	// Asignamos los listeners

	private void asignarListeners() {

		// Cuando cambia el cliente, se actualizan sus trajes
		vista.getCbCliente().addActionListener(e -> actualizarComboTrajes());

		// Guardar cita
		vista.getBtnGuardar().addActionListener(e -> guardarCita());

		// Cancelar cita
		vista.getBtnCancelar().addActionListener(e -> vista.dispose());

		// Abrir NuevoCliente para registrar un cliente que no existe
		vista.getBtnCliente().addActionListener(e -> abrirNuevoCliente());

		// Abrir NuevoTaller para añadir un taller que no existe
		vista.getBtnTaller().addActionListener(e -> abrirNuevoTaller());
	}

	/**
	 * Valida los campos del formulario ycsi todo es correcto, construye un objeto
	 * Cita.
	 */
	private void guardarCita() {

		String strFecha = vista.getTxtFecha().getText().trim();
		String strHora = vista.getTxtHora().getText().trim();
		String strDuracion = vista.getTxtDuracion().getText().trim();

		int indexCliente = vista.getCbCliente().getSelectedIndex();
		int indexTraje = vista.getCbTraje().getSelectedIndex();
		int indexTaller = vista.getCbTaller().getSelectedIndex();

		if (strFecha.isEmpty() || strHora.isEmpty() || strDuracion.isEmpty()) {
			JOptionPane.showMessageDialog(vista, "Por favor, rellena los campos de : Fecha, Hora y Duración.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (indexCliente < 0) {
			JOptionPane.showMessageDialog(vista, "Selecciona un cliente.", "Sin cliente", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (indexTraje < 0) {
			JOptionPane.showMessageDialog(vista, "El cliente seleccionado no tiene trajes disponibles.", "Sin traje", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (indexTaller < 0) {
			JOptionPane.showMessageDialog(vista, "Selecciona un taller.", "Sin taller", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Date fecha;
		Time hora;
		int duracion;

		try {
			fecha = Date.valueOf(strFecha);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(vista, "Formato de fecha incorrecto. Usa yyyy-MM-dd.", "Error de formato", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			hora = Time.valueOf(strHora + ":00");
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(vista, "Formato de hora incorrecto. Usa HH:mm.", "Error de formato", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			duracion = Integer.parseInt(strDuracion);
			if (duracion <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(vista, "La duración debe ser un número entero positivo.", "Error de formato", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int idCliente = listaClientes.get(indexCliente).getId_cliente();
		int idSala = listaTalleres.get(indexTaller).getId_sala();

		int idTraje = obtenerIdTrajeSeleccionado(idCliente, indexTraje);
		if (idTraje == -1) {
			JOptionPane.showMessageDialog(vista, "No se pudo identificar el traje seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int idEmpleado = empleado.getId_empleado();

		Cita nuevaCita = new Cita(0, fecha, hora, duracion, idEmpleado, idCliente, idSala, idTraje);
//		acceso.insertarNuevaCita(c, nuevaCita);
		
		NuevaCita2 nc2 = new NuevaCita2();
		new ControladorNuevaCita2(nc2, acceso, c, empleado, nuevaCita);
		nc2.setVisible(true);

//		JOptionPane.showMessageDialog(vista, "Cita guardada correctamente.");
		vista.dispose();
	}

	/**
	 * Devuelve el id_traje del traje que ocupa la posición dentro de los trajes
	 * filtrados para el cliente dado.
	 *
	 */
	/**
	 * 
	 * @param idCliente
	 * @param indexEnCombo
	 * @return
	 */
	private int obtenerIdTrajeSeleccionado(int idCliente, int indexEnCombo) {
		int contador = 0;
		for (Traje t : listaTrajes) {
			if (t.getId_cliente() == idCliente) {
				if (contador == indexEnCombo) {
					return t.getId_traje();
				}
				contador++;
			}
		}
		return -1;
	}

	/**
	 * Abre el formulario NuevoCliente para registrar un cliente que aun no existe
	 * en el sistema.
	 */
	private void abrirNuevoCliente() {
		NuevoCliente vistaCliente = new NuevoCliente();
		new ControladorNuevoCliente(vistaCliente, acceso, c, null, null, listaClientes, listaTrajes, empleado);
		vistaCliente.setVisible(true);
        vista.dispose();

		vistaCliente.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosed(java.awt.event.WindowEvent e) {
				cargarDatosIniciales();
			}
		});
	}

	//Abre el formulario NuevoTaller para añadir un taller que aun no existe
	 
	private void abrirNuevoTaller() {
		ArrayList<Taller> talleres = acceso.recogeTalleres(c);
		NuevoTaller vistaTaller = new NuevoTaller();
		new ControladorNuevoTaller(vistaTaller, acceso, c, null, talleres, empleado);
		vistaTaller.setVisible(true);
        vista.dispose();

		vistaTaller.addWindowListener(new java.awt.event.WindowAdapter() {
		
			public void windowClosed(java.awt.event.WindowEvent e) {
				cargarDatosIniciales();
			}
		});
	}
}