package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NuevaCita extends JFrame {
    private JTextField txtFecha, txtHora, txtDuracion;

    public NuevaCita() {
        setTitle("Nueva / Editar Cita");
        setBounds(100, 100, 450, 350);
        getContentPane().setLayout(null);

        getContentPane().add(new JLabel("Cliente:")).setBounds(20, 20, 80, 20);
        JComboBox cbCliente = new JComboBox(new String[] {"Seleccionar...", "Elastic Girl", "Frozone"});
        cbCliente.setBounds(20, 40, 180, 25);
        getContentPane().add(cbCliente);

        getContentPane().add(new JLabel("Traje:")).setBounds(230, 20, 80, 20);
        JComboBox cbTraje = new JComboBox(new String[] {"Prototipo A", "Súper Gala"});
        cbTraje.setBounds(230, 40, 180, 25);
        getContentPane().add(cbTraje);

        getContentPane().add(new JLabel("Taller:")).setBounds(20, 80, 80, 20);
        JComboBox cbTaller = new JComboBox(new String[] {"París", "Madrid"});
        cbTaller.setBounds(20, 100, 180, 25);
        getContentPane().add(cbTaller);

        getContentPane().add(new JLabel("Oficial Resp.:")).setBounds(230, 80, 120, 20);
        JTextField txtOficial = new JTextField();
        txtOficial.setBounds(230, 100, 180, 25);
        getContentPane().add(txtOficial);

        getContentPane().add(new JLabel("Fecha:")).setBounds(20, 140, 80, 20);
        txtFecha = new JTextField();
        txtFecha.setBounds(20, 160, 100, 25);
        getContentPane().add(txtFecha);

        JButton btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setBounds(20, 250, 150, 40);
        getContentPane().add(btnGuardar);
    }
}