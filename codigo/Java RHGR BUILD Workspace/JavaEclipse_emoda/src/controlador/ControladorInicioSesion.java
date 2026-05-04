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

/**
 * Controlador para la gestión del inicio de sesión de los usuarios.
 * Se encarga de validar las credenciales introducidas en la vista contra la lista de empleados
 * y de redirigir al usuario a la ventana principal correspondiente a su rol profesional.
 */
public class ControladorInicioSesion {

    private InicioSesion vista;
    private AccesoBBDD acceso;
    private ArrayList<Empleado> empleados;

    /**
     * Constructor del controlador de inicio de sesión.
     * Configura la vista y el acceso a datos, además de inicializar el escuchador de eventos 
     * para el botón de acceso.
     * 
     * @param vista     La ventana de login (InicioSesion).
     * @param acceso    Objeto para la gestión de conexiones a la base de datos.
     * @param empleados Lista de empleados registrados para validar las credenciales.
     */
    public ControladorInicioSesion(InicioSesion vista, AccesoBBDD acceso, ArrayList<Empleado> empleados) {
        this.vista = vista;
        this.acceso = acceso;
        this.empleados = empleados;

        // Configura el listener del botón de entrada
        this.vista.getBtnEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    /**
     * Realiza el proceso de autenticación de usuario.
     * Recupera el nombre y la contraseña de la vista, busca una coincidencia en la lista de
     * empleados y, si es exitoso, cierra la vista de login para abrir el panel de control 
     * específico del rol (Aprendiz, Oficial o Maestro).
     */
    private void iniciarSesion() {

        String usuario = vista.getInfoNombre().trim();
        String password = new String(vista.getInfoContrasenia()).trim();

        Empleado empleadoAutenticado = null;

        // Lógica de búsqueda del empleado en la lista cargada
        for (Empleado emp : empleados) {
            if (emp.getUsuario().equals(usuario) &&
                emp.getContrasena().equals(password)) {
                empleadoAutenticado = emp;
                break;
            }
        }

        // Manejo de error en caso de credenciales inválidas
        if (empleadoAutenticado == null) {
            vista.setRespuesta("Usuario o contraseña incorrectos");
            return;
        }

        // Proceso de transición: cerrar login e iniciar sesión de trabajo
        String categoria = empleadoAutenticado.getCategoria();

        vista.setVisible(false);
        vista.dispose();

        // Se abre una conexión única que se pasará a los siguientes controladores
        Connection c = acceso.abrirConexion();

        /*
         * Selección de ventana principal según el rol profesional del empleado.
         * Se delega el control a los controladores específicos de cada ventana.
         */
        switch (categoria.toLowerCase()) {

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
                // En caso de que el rol no coincida con los esperados
                vista.setRespuesta("Categoría no reconocida: " + categoria);
                break;
        }
    }
}