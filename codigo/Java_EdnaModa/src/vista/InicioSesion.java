package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class InicioSesion extends JFrame {
	private JTextField NomUsuario;
	private JPasswordField passwordField;
	
	public InicioSesion() {
		getContentPane().setForeground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		
		JLabel Titulo = DefaultComponentFactory.getInstance().createTitle("Inicio de sesion de Edna Moda");
		Titulo.setForeground(Color.WHITE);
		Titulo.setBounds(114, 11, 191, 14);
		getContentPane().add(Titulo);
		
		JLabel NombreUsuario = DefaultComponentFactory.getInstance().createLabel("Nombre de usuario");
		NombreUsuario.setForeground(Color.WHITE);
		NombreUsuario.setBackground(Color.WHITE);
		NombreUsuario.setBounds(50, 78, 115, 14);
		getContentPane().add(NombreUsuario);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Contraseña");
		lblNewJgoodiesLabel.setForeground(Color.WHITE);
		lblNewJgoodiesLabel.setBounds(50, 120, 92, 14);
		getContentPane().add(lblNewJgoodiesLabel);
		
		NomUsuario = new JTextField();
		NomUsuario.setBounds(157, 75, 96, 20);
		getContentPane().add(NomUsuario);
		NomUsuario.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(114, 117, 96, 20);
		getContentPane().add(passwordField);
		
		JButton IniciarSesion = new JButton("Iniciar sesion");
		IniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    // 1. Extraemos los valores de los campos
			    String usuario = NomUsuario.getText();
			    String pass = new String(passwordField.getPassword());

			    // 2. Comparamos los strings extraídos
			    if (usuario.equals("admin") && pass.equals("1234")) {
			        System.out.println("¡Bienvenida, Edna! Sin capas, por favor.");
			        // Aquí abrirías la siguiente ventana
			    } else {
			        // 3. Mostramos la miniventana (asegúrate de importar javax.swing.JOptionPane)
			        javax.swing.JOptionPane.showMessageDialog(InicioSesion.this, 
			            "Usuario o contraseña incorrectos", 
			            "Error de Autenticación", 
			            javax.swing.JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		IniciarSesion.setBounds(50, 191, 115, 22);
		getContentPane().add(IniciarSesion);
		
		JLabel Imagen = new JLabel("");
		Imagen.setForeground(new Color(255, 255, 255));
		Imagen.setIcon(new ImageIcon(InicioSesion.class.getResource("/img/RGHR_(1).png")));
		Imagen.setBounds(0, 0, 4338, 1000);
		getContentPane().add(Imagen);
	}
}
