package controlador;

import modelo.AccesoBBDD;
import modelo.Empleado;
import vista.InicioSesion;
import vista.VentanaAprendiz;
import vista.VentanaOficial;
import vista.VentanaMaestro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class ControladorInicioSesion {

	private InicioSesion vista;
    private AccesoBBDD acceso;
    private ArrayList<Empleado> empleados;

    // Constructor: recibe la vista y la lista de empleados ya cargada desde la BBDD
    /**
     * 
     * @param vista
     * @param acceso
     * @param empleados
     */
    public ControladorInicioSesion(InicioSesion vista, AccesoBBDD acceso, ArrayList<Empleado> empleados) {
        this.vista = vista;
        this.acceso = acceso;
        this.empleados = empleados;

        // Asignación del listener al botón "Entrar"
        this.vista.getBtnEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    // Lógica de autenticación: busca el usuario y contraseña en la lista de empleados
    private void iniciarSesion() {
        String usuarioIntroducido = vista.getInfoNombre().trim();
        String contrasenaIntroducida = new String(vista.getInfoContrasenia()).trim();

        Empleado empleadoAutenticado = null;

        // Recorre la lista buscando coincidencia de usuario y contraseña
        for (Empleado emp : empleados) {
            if (emp.getUsuario().equals(usuarioIntroducido) &&
                emp.getContrasena().equals(contrasenaIntroducida)) {
                empleadoAutenticado = emp;
                break;
            }
        }

        if (empleadoAutenticado == null) {
            // Credenciales incorrectas
            vista.setRespuesta("Usuario o contraseña incorrectos");
            return;
        }

        // Abre la ventana correspondiente según la categoría del empleado
        String categoria = empleadoAutenticado.getCategoria();
        vista.setVisible(false);
        vista.dispose();

        Connection c = acceso.abrirConexion();

        switch (categoria) {
            case "aprendiz":
                VentanaAprendiz vAprendiz = new VentanaAprendiz();
                new ControladorAprendiz(vAprendiz, acceso, c, empleadoAutenticado);
                vAprendiz.setVisible(true);
                break;

            case "oficial":
                VentanaOficial vOficial = new VentanaOficial();
                new ControladorOficial(vOficial, acceso, c, empleadoAutenticado);
                vOficial.setVisible(true);
                break;

            case "maestro":
                VentanaMaestro vMaestro = new VentanaMaestro();
                new ControladorMaestro(vMaestro, acceso, c, empleadoAutenticado);
                vMaestro.setVisible(true);
                break;

            default:
                vista.setRespuesta("Categoría de empleado no reconocida: " + categoria);
                break;
        }
    }
}

