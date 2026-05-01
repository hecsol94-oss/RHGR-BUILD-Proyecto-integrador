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
 * Controlador ListaCitas.
 */
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

    public ControladorListaCitas(ListaCitas vista, AccesoBBDD acceso, Connection c, ArrayList<Cita> citas, ArrayList<Cita_Aprendiz> aprendices, Empleado empleado) {
        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.citas = citas;
        this.citasFiltradas = new ArrayList<>(citas);
        this.aprendices = aprendices;
        this.empleado = empleado;

        cargarListas();
        cargarTabla(citasFiltradas);

        vista.getBtnTodas().addActionListener(e -> { citasFiltradas = new ArrayList<>(citas); cargarTabla(citasFiltradas); });
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

    /** Filtro real usando el tipo del taller asociado a la cita. */
    private void filtrarPorTipo(String tipo) {
        citasFiltradas = citas.stream()
            .filter(cita -> {
                for (Taller t : listaTalleres)
                    if (t.getId_sala() == cita.getId_sala())
                        return t.getTipo().equalsIgnoreCase(tipo);
                return false;
            })
            .collect(Collectors.toCollection(ArrayList::new));

        if (citasFiltradas.isEmpty())
            JOptionPane.showMessageDialog(vista, "No hay citas de tipo '" + tipo + "'.", "Información", JOptionPane.INFORMATION_MESSAGE);
        cargarTabla(citasFiltradas);
    }

    private void buscar() {
        String texto = vista.getTextField().getText().trim().toLowerCase();
        citasFiltradas = new ArrayList<>();
        for (Cita cita : citas) {
            String clienteNombre = nombreCliente(cita.getId_cliente()).toLowerCase();
            if (cita.getFecha().toString().contains(texto) || clienteNombre.contains(texto))
                citasFiltradas.add(cita);
        }
        cargarTabla(citasFiltradas);
    }

    private void verDetalle() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila < 0) {
        	JOptionPane.showMessageDialog(vista, "Selecciona una cita.", "Aviso", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        Cita cita = citasFiltradas.get(fila);
        int contadorAprendices = 0;
        
        String[] aprs = new String[2];
        aprs[0] = "";
        aprs[1] = "";
        
        for (Cita_Aprendiz aprendiz : aprendices) {
        	if (aprendiz.getId_cita() == cita.getId_cita()) {
        		for (Empleado empleado : listaEmpleados) {
        			if (aprendiz.getId_empleado() == empleado.getId_empleado()) {
        				if (contadorAprendices == 0) {
        				    aprs[0] = empleado.getNombre() + " " + empleado.getApellido();
        				} else if (contadorAprendices == 1) {
        				    aprs[1] = empleado.getNombre() + " " + empleado.getApellido();
        				}
        				contadorAprendices++;
        			}
        		}
        	}
        }
        DetalleCita vistaDetalle = new DetalleCita();
        new ControladorDetalleCita(vistaDetalle, cita, acceso, c, aprs);
        vistaDetalle.setVisible(true);
    }

    private void editarCita() {
        int fila = vista.getTableCitas().getSelectedRow();
        if (fila <= -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
        	Cita citaEditable = citas.get(fila);
            
            String clienteEditable = (String) vista.getTableCitas().getValueAt(fila, 1);
            String trajeEditable = (String) vista.getTableCitas().getValueAt(fila, 2); 
            
            String tallerEditable = "";
            
            for (Taller taller : listaTalleres) {
            	if (taller.getNombre().equals((String) vista.getTableCitas().getValueAt(fila, 3))) {
            		tallerEditable = tallerEditable + taller.getNombre() + " (" + taller.getTipo() + ")";
            	}
            }
            
            String empleadoEditable = "";
            
            for (Empleado empleado : listaEmpleados) {
            	if ((empleado.getNombre() + " " + empleado.getApellido()).equals((String) vista.getTableCitas().getValueAt(fila, 4))) {
            		empleadoEditable = empleadoEditable + empleado.getNombre() + " " + empleado.getApellido() + " (" + empleado.getCategoria() + ")";
            	}
            }
            
            Cita_Aprendiz aprendizEditable1 = new Cita_Aprendiz(0, 0, 0);            
            Cita_Aprendiz aprendizEditable2 = new Cita_Aprendiz(0, 0, 0);
            
            for (Cita_Aprendiz aprendiz1 : aprendices) {
            	if (aprendiz1.getId_cita() == citaEditable.getId_cita()) {
            		aprendizEditable1 = aprendiz1;
            		for (Cita_Aprendiz aprendiz2 : aprendices) {
                    	if (aprendiz2.getId_cita() == citaEditable.getId_cita() && aprendiz2.getId_empleado() != aprendizEditable1.getId_empleado()) {
                    		aprendizEditable2 = aprendiz2;
                    	}
                    }
            	}
            }
            
            NuevaCita vistaForm = new NuevaCita();
    	    new ControladorNuevaCita(vistaForm, acceso, c, empleado, citaEditable, clienteEditable, trajeEditable, tallerEditable, empleadoEditable, aprendizEditable1, aprendizEditable2);
    	    vistaForm.setVisible(true);
    	    vista.dispose();
        }
    }
    
    private void eliminarCita() {
    	int fila = vista.getTableCitas().getSelectedRow();
        if (fila <= -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
        	Cita citaAEliminar = citas.get(fila);
        	int confirmacion = JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro de que quieres eliminar esta cita?.",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                	try {
                		acceso.eliminarCita(c, citaAEliminar.getId_cita());
						citas = acceso.recogeCitas(c);
						cargarTabla(citas);
		                JOptionPane.showMessageDialog(vista, "Cita eliminada correctamente.");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
        }
    }

    private void nuevaCita() {
        // Maestro y Oficial usan el mismo proceso de dos fases
        NuevaCita vistaForm = new NuevaCita();
        new ControladorNuevaCita(vistaForm, acceso, c, empleado, null, null, null, null, null, null, null);
        vistaForm.setVisible(true);
        vista.dispose();
    }
    
    private void volver() {
		try {
			String rol = empleado.getCategoria().toLowerCase();

			switch (rol) {
			case "maestro":
				VentanaMaestro vMaestro = new VentanaMaestro();
				new ControladorMaestro(vMaestro, acceso, c, empleado);
				vMaestro.setVisible(true);
				vista.dispose();
				break;
			case "oficial":
				VentanaOficial vOficial = new VentanaOficial();
				new ControladorOficial(vOficial, acceso, c, empleado);
				vOficial.setVisible(true);
				vista.dispose();
				break;
			case "aprendiz":
				VentanaAprendiz vAprendiz = new VentanaAprendiz();
				new ControladorAprendiz(vAprendiz, acceso, c, empleado);
				vAprendiz.setVisible(true);
				vista.dispose();
				break;
			}
						
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    // Resolución de nombres (punto 6)
    private String nombreCliente(int id) {
    	if(listaClientes==null) 
    		return ""+id; 
    	for(Cliente x:listaClientes) 
    		if(x.getId_cliente()==id) 
    			return x.getNombre(); return ""+id; 
    }
    
    private String nombreTraje(int id) {
    	if(listaTrajes==null)   
    		return ""+id; 
    	for(Traje   x:listaTrajes)   
    		if(x.getId_traje()==id)    
    			return x.getNombre_traje(); 
    	return ""+id; 
    }
    
    private String nombreTaller(int id) {
    	if(listaTalleres==null) 
    		return ""+id; 
    	for(Taller  x:listaTalleres) 
    		if(x.getId_sala()==id)     
    			return x.getNombre(); 
    				return ""+id; 
    }
    
    private String nombreEmpleado(int id) {
    	if(listaEmpleados==null)
    		return ""+id; 
    	for(Empleado x:listaEmpleados) 
    		if(x.getId_empleado()==id)
    			return x.getNombre()+" "+x.getApellido();
    	return ""+id; 
    }
    
    
    
}