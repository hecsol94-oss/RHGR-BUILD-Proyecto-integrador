package controlador;

import modelo.AccesoBBDD;
import modelo.Cita;
import modelo.Empleado;
import modelo.Taller;
import vista.VentanaAprendiz;
import vista.InicioSesion;
import vista.ListaCitas;
import vista.ListaTalleres;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


//Controlador de la ventana principal del Aprendiz
//El aprendiz solo puede ver la lista de sus citas y la lista de talleres

public class ControladorAprendiz {

  private VentanaAprendiz vista;
  private AccesoBBDD acceso;
  private Connection c;
  private Empleado empleado;

  // Constructor: inicializa la vista con los datos del empleado y asigna los listeners
  public ControladorAprendiz(VentanaAprendiz vista, AccesoBBDD acceso, Connection c, Empleado empleado) {
      this.vista = vista;
      this.acceso = acceso;
      this.c = c;
      this.empleado = empleado;

      // Muestra el nombre del empleado en la barra superior
      vista.getLblUsuario().setText("Usuario: " + empleado.getApodo());

      // Carga los contadores del dashboard
      cargarContadores();

      // Asignación de listeners al menú
      vista.getMenuItemListaCitas().addActionListener(e -> abrirListaCitas());
      vista.getMenuItemListaTalleres().addActionListener(e -> abrirListaTalleres());

      // Logout
      vista.getLblSalir().setText("Salir");
      vista.getLblSalir().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
      vista.getLblSalir().addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseClicked(java.awt.event.MouseEvent e) {
              cerrarSesion();
          }
      });
  }

  // Carga el número total de citas y talleres en el dashboard
  private void cargarContadores() {
      try {
          ArrayList<Cita> todasCitas = acceso.recogeCitas(c);
          ArrayList<Taller> talleres = acceso.recogeTalleres(c);

          vista.getLblTodasLasCitas().setText(String.valueOf(todasCitas.size()));
          vista.getLblNumeroDeTalleres().setText(String.valueOf(talleres.size()));

          // El aprendiz no tiene citas propias asignadas directamente (sin tabla de asignación),
          // por lo que se muestra el total de citas como referencia
          vista.getLblNumeroDeMisCitas().setText(String.valueOf(todasCitas.size()));

      } catch (SQLException ex) {
          ex.printStackTrace();
      }
  }

  // Abre la ventana de lista de citas en modo solo lectura
  private void abrirListaCitas() {
      try {
          ArrayList<Cita> citas = acceso.recogeCitas(c);
          ListaCitas vistaLista = new ListaCitas();
          new ControladorListaCitas(vistaLista, acceso, c, citas, false);
          vistaLista.setVisible(true);
      } catch (SQLException ex) {
          ex.printStackTrace();
      }
  }

  // Abre la ventana de lista de talleres en modo solo lectura
  private void abrirListaTalleres() {
      try {
          ArrayList<Taller> talleres = acceso.recogeTalleres(c);
          ListaTalleres vistaLista = new ListaTalleres();
          new ControladorListaTalleres(vistaLista, acceso, c, talleres, false);
          vistaLista.setVisible(true);
      } catch (SQLException ex) {
          ex.printStackTrace();
      }
  }

  // Cierra la sesión: cierra la conexión y vuelve al inicio de sesión
  private void cerrarSesion() {
      acceso.cerrarConexion(c);
      vista.dispose();

      try {
          ArrayList<Empleado> empleados = new ArrayList<>();
          Connection nuevaConexion = acceso.abrirConexion();
          empleados = acceso.recogeEmpleados(nuevaConexion);
          acceso.cerrarConexion(nuevaConexion);

          InicioSesion inicioSesion = new InicioSesion();
          new ControladorInicioSesion(inicioSesion, acceso, empleados);
          inicioSesion.setVisible(true);
      } catch (SQLException ex) {
          ex.printStackTrace();
      }
  }
}
