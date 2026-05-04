package controlador;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.AccesoBBDD;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Taller;
import modelo.Traje;
import vista.DetalleClientes;
import vista.ListaTalleres;
import vista.NuevaCita;
import vista.NuevoTraje;

public class ControladorNuevoTraje {

    // Vista del formulario de trajes
    private NuevoTraje vista;

    // Acceso a base de datos
    private AccesoBBDD acceso;

    // Conexión activa
    private Connection c;

    // Cliente asociado al traje
    private Cliente cliente;

    // Lista de trajes del cliente
    private ArrayList<Traje> trajes;

    // Traje que se está editando (null si es nuevo)
    private Traje trajeAEditar;

    // Empleado logueado
    private Empleado empleado;

    // Vista origen: detalle de clientes
    private DetalleClientes vistaCliente;

    // Vista origen: nueva cita
    private NuevaCita vistaCita;

    public ControladorNuevoTraje(NuevoTraje vista, AccesoBBDD acceso, Connection c,
                                  Cliente cliente, ArrayList<Traje> trajes,
                                  Traje trajeAEditar, Empleado empleado,
                                  DetalleClientes vistaCliente, NuevaCita vistaCita) {

        this.vista = vista;
        this.acceso = acceso;
        this.c = c;
        this.cliente = cliente;
        this.trajes = trajes;
        this.trajeAEditar = trajeAEditar;
        this.empleado = empleado;
        this.vistaCliente = vistaCliente;
        this.vistaCita = vistaCita;

        // Si estamos editando un traje, cargamos datos en la vista
        if (trajeAEditar != null) {
            vista.getNombreTraje().setText(trajeAEditar.getNombre_traje());
            vista.setCbEstado(trajeAEditar.getEstado());
        }

        // Eventos de botones
        this.vista.getBtnGuardar().addActionListener(e -> guardarTraje());
        this.vista.getBtnCancelar().addActionListener(e -> cancelar());
    }

    // Guarda un traje nuevo o actualiza uno existente
    private void guardarTraje() {

        // Obtener datos introducidos por el usuario
        String nombre = vista.getNombreTraje().getText();
        String estado = vista.getCbEstado().toString();

        // Validación básica
        if (!nombre.isEmpty()) {

            // =========================
            // CASO: CREAR NUEVO TRAJE
            // =========================
            if (trajeAEditar == null) {

                // Insertar en base de datos
                acceso.insertarNuevoTraje(c, nombre, estado, cliente.getId_cliente());

                // Recargar lista de trajes del cliente
                trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

                // Si venimos desde DetalleClientes
                if (vistaCliente != null && vistaCita == null) {

                    DetalleClientes dt = new DetalleClientes();
                    dt.recogerDatos(trajes);

                    JOptionPane.showMessageDialog(vista, "Traje creado correctamente");

                    new ControladorDetalleClientes(dt, acceso, c, cliente, trajes, empleado);
                    dt.setVisible(true);

                    vista.dispose();
                    vistaCliente.dispose();

                // Si venimos desde NuevaCita
                } else if (vistaCliente == null && vistaCita != null) {

                    JOptionPane.showMessageDialog(vista, "Traje creado correctamente");
                    vista.dispose();
                }

            // =========================
            // CASO: EDITAR TRAJE
            // =========================
            } else {

                // Actualizar en base de datos
                acceso.actualizarTraje(c, trajeAEditar.getId_traje(), nombre, estado);

                // Recargar lista de trajes
                trajes = acceso.getTrajesPorCliente(c, cliente.getId_cliente());

                DetalleClientes dt = new DetalleClientes();
                dt.recogerDatos(trajes);

                JOptionPane.showMessageDialog(vista, "Traje editado correctamente");

                new ControladorDetalleClientes(dt, acceso, c, cliente, trajes, empleado);
                dt.setVisible(true);

                vista.dispose();
                vistaCliente.dispose();
            }

        } else {

            // Error si el nombre está vacío
            JOptionPane.showMessageDialog(vista,
                    "Por favor, rellene los campos obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cancela la operación actual
    private void cancelar() {

        // Cierra la ventana sin guardar cambios
        if (vistaCliente != null && vistaCita == null) {
            vista.dispose();
        } else if (vistaCliente == null && vistaCita != null) {
            vista.dispose();
        }
    }
}