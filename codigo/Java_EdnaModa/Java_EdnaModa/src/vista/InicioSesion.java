package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import controlador.InicioSesionXVentanas;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class InicioSesion extends JFrame {
	private JTextField NomUsuario;
	private JPasswordField ContraseñaCampo;
	private InicioSesionXVentanas listener;
	private JButton entrar;
	
	public InicioSesion() {
		setTitle("RHGR EDNA MODA");
		setBounds(100, 100, 400, 600);
		getContentPane().setForeground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JLabel NombreUsuario = DefaultComponentFactory.getInstance().createLabel("Nombre de usuario");
		NombreUsuario.setForeground(new Color(0, 0, 0));
		NombreUsuario.setBackground(Color.WHITE);
		NombreUsuario.setBounds(72, 21, 115, 14);
		getContentPane().add(NombreUsuario);
		
		JLabel Contraseña = DefaultComponentFactory.getInstance().createLabel("Contraseña");
		Contraseña.setForeground(new Color(0, 0, 0));
		Contraseña.setBounds(72, 77, 96, 14);
		getContentPane().add(Contraseña);
		
		NomUsuario = new JTextField();
		NomUsuario.setBounds(72, 46, 233, 20);
		getContentPane().add(NomUsuario);
		NomUsuario.setColumns(10);
		
		ContraseñaCampo = new JPasswordField();
		ContraseñaCampo.setBounds(72, 102, 233, 20);
		getContentPane().add(ContraseñaCampo);
		
		entrar = new JButton("Entrar");
		entrar.setBounds(100, 526, 181, 22);
		getContentPane().add(entrar);
		listener = new InicioSesionXVentanas(this);
		entrar.addActionListener((ActionListener) listener);
		
		JLabel lblNombreAplicacion = DefaultComponentFactory.getInstance().createLabel("EDNA MODA");
		lblNombreAplicacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreAplicacion.setBounds(149, 143, 115, 14);
		getContentPane().add(lblNombreAplicacion);
		
		JLabel lblNewLabel = new JLabel("Sistema de Gestion de citas");
		lblNewLabel.setBounds(129, 168, 176, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(InicioSesion.class.getResource("/img/LOGO RHGR_BUILD.png")));
		lblLogo.setBounds(36, 193, 316, 322);
		getContentPane().add(lblLogo);
	}
	
	public String getInfoNombre() {
		return NomUsuario.getText();
		
	}
	
	public char[] getInfoContrasenia() {
		return ContraseñaCampo.getPassword();
	}
	
	public void setRespuesta(String respuesta) {
		javax.swing.JOptionPane.showMessageDialog(InicioSesion.this, 
	            respuesta,
	            respuesta, javax.swing.JOptionPane.ERROR_MESSAGE);
	}

}
