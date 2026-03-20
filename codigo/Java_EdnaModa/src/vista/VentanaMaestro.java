package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SpringLayout;

public class VentanaMaestro extends JFrame {
	public VentanaMaestro() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JMenuBar menuBar = new JMenuBar();
		springLayout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, menuBar, 138, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, menuBar, 285, SpringLayout.WEST, getContentPane());
		
		JMenu Citas = new JMenu("Citas");
		menuBar.add(Citas);
		
		JMenuItem VerCitas = new JMenuItem("Ver citas");
		Citas.add(VerCitas);
		
		JMenuItem reservarCitas = new JMenuItem("reservar citas");
		Citas.add(reservarCitas);
		
		JMenuItem misCitas = new JMenuItem("Mis citas");
		Citas.add(misCitas);
		
		JMenu Talleres = new JMenu("Talleres");
		menuBar.add(Talleres);
		
		JMenuItem misTalleres = new JMenuItem("Mis talleres");
		Talleres.add(misTalleres);
		
		JMenu verTalleres = new JMenu("verTalleres");
		Talleres.add(verTalleres);
		
		JMenuItem nuevoTaller = new JMenuItem("Nuevo Taller");
		Talleres.add(nuevoTaller);
		
		JMenu Clientes = new JMenu("Clientes");
		menuBar.add(Clientes);
		
		JMenuItem verClientes = new JMenuItem("verClientes");
		Clientes.add(verClientes);
		
		JMenuItem misClientes = new JMenuItem("Mis clientes");
		Clientes.add(misClientes);
		
		JMenuItem nuevoCliente = new JMenuItem("Nuevo cliente");
		Clientes.add(nuevoCliente);
		getContentPane().add(menuBar);
		
		JLabel Imagen = new JLabel("");
		Imagen.setForeground(new Color(255, 255, 255));
		Imagen.setIcon(new ImageIcon(InicioSesion.class.getResource("/img/RGHR_(1).png")));
		Imagen.setBounds(0, 0, 4000, 1000);
		getContentPane().add(Imagen);
	}
}
