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
    public ControladorInicioSesion(InicioSesion vista, AccesoBBDD acceso, ArrayList<Empleado> empleados) {
        this.vista = vista;
        this.acceso = acceso;
        this.empleados = empleados;

        // Listener del botón entrar
        this.vista.getBtnEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    // Lógica de login
    private void iniciarSesion() {

        String usuario = vista.getInfoNombre().trim();
        String password = new String(vista.getInfoContrasenia()).trim();

        Empleado empleadoAutenticado = null;

        // Buscar usuario en la lista
        for (Empleado emp : empleados) {
            if (emp.getUsuario().equals(usuario) &&
                emp.getContrasena().equals(password)) {
                empleadoAutenticado = emp;
                break;
            }
        }

        if (empleadoAutenticado == null) {
            vista.setRespuesta("Usuario o contraseña incorrectos");
            return;
        }

        // Abrir ventana según rol
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
                vista.setRespuesta("Categoría no reconocida: " + categoria);
                break;
        }
    }
}