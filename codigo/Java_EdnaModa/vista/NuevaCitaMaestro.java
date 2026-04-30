package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class NuevaCitaMaestro extends JFrame {

    // ---- Fase 1 ----
    private JComboBox<String> cbCliente;
    private JComboBox<String> cbTraje;
    private JComboBox<String> cbTaller;
    private JComboBox<String> cbOficial;   // ← ahora es combo, no texto libre
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtDuracion;
    private JButton btnNuevoCliente;
    private JButton btnNuevoTraje;
    private JButton btnSiguiente;
    private JButton btnCancelar;

    // ---- Fase 2 ----
    private JPanel panelFase2;
    private JLabel lblResumenFecha, lblResumenHora, lblResumenDuracion;
    private JLabel lblResumenCliente, lblResumenTraje, lblResumenTaller, lblResumenOficial;
    private JComboBox<String> cbAprendiz1;
    private JComboBox<String> cbAprendiz2;
    private JButton btnGuardar;
    private JButton btnAtras;

    private JPanel panelFase1;
    private CardLayout cardLayout;
    private JPanel panelContenedor;

    public NuevaCitaMaestro() {
        setTitle("Nueva / Editar Cita");
        setBounds(100, 100, 500, 490);
        getContentPane().setLayout(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBounds(0, 0, 490, 470);
        getContentPane().add(panelContenedor);

        buildFase1();
        buildFase2();

        panelContenedor.add(panelFase1, "FASE1");
        panelContenedor.add(panelFase2, "FASE2");
        cardLayout.show(panelContenedor, "FASE1");
    }

    private void buildFase1() {
        panelFase1 = new JPanel(null);

        JLabel titulo = new JLabel("DATOS DE LA CITA");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        titulo.setBounds(20, 10, 250, 25);
        panelFase1.add(titulo);

        // Cliente
        panelFase1.add(lbl("Cliente:", 20, 45));
        cbCliente = new JComboBox<>();
        cbCliente.setBounds(20, 62, 280, 25);
        panelFase1.add(cbCliente);
        btnNuevoCliente = new JButton("+ Nuevo cliente");
        btnNuevoCliente.setBounds(310, 62, 155, 25);
        panelFase1.add(btnNuevoCliente);

        // Traje
        panelFase1.add(lbl("Traje:", 20, 98));
        cbTraje = new JComboBox<>();
        cbTraje.setBounds(20, 115, 280, 25);
        panelFase1.add(cbTraje);
        btnNuevoTraje = new JButton("+ Nuevo traje");
        btnNuevoTraje.setBounds(310, 115, 155, 25);
        panelFase1.add(btnNuevoTraje);

        // Taller — solo combo, ancho completo
        panelFase1.add(lbl("Taller:", 20, 151));
        cbTaller = new JComboBox<>();
        cbTaller.setBounds(20, 168, 445, 25);
        panelFase1.add(cbTaller);

        // Oficial responsable — ahora combo
        panelFase1.add(lbl("Oficial responsable:", 20, 204));
        cbOficial = new JComboBox<>();
        cbOficial.setBounds(20, 221, 445, 25);
        panelFase1.add(cbOficial);

        // Fecha / Hora / Duración
        panelFase1.add(lbl("Fecha (yyyy-MM-dd):", 20, 257));
        txtFecha = new JTextField();
        txtFecha.setBounds(20, 274, 130, 25);
        panelFase1.add(txtFecha);

        panelFase1.add(lbl("Hora (HH:mm):", 165, 257));
        txtHora = new JTextField();
        txtHora.setBounds(165, 274, 100, 25);
        panelFase1.add(txtHora);

        panelFase1.add(lbl("Duración (h):", 280, 257));
        txtDuracion = new JTextField();
        txtDuracion.setBounds(280, 274, 90, 25);
        panelFase1.add(txtDuracion);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 315, 455, 2);
        panelFase1.add(sep);

        btnSiguiente = new JButton("Siguiente →");
        btnSiguiente.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnSiguiente.setBounds(280, 330, 185, 35);
        panelFase1.add(btnSiguiente);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(90, 330, 150, 35);
        panelFase1.add(btnCancelar);
    }

    private void buildFase2() {
        panelFase2 = new JPanel(null);

        JLabel titulo = new JLabel("RESUMEN DE LA CITA");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        titulo.setBounds(20, 10, 300, 25);
        panelFase2.add(titulo);

        JLabel subTitulo = new JLabel("Revisa los datos y asigna aprendices (opcional)");
        subTitulo.setFont(new Font("Tahoma", Font.ITALIC, 11));
        subTitulo.setBounds(20, 32, 380, 18);
        panelFase2.add(subTitulo);

        int y = 58, dy = 28;
        lblResumenFecha = addResumenField(panelFase2, "Fecha:", y); y += dy;
        lblResumenHora = addResumenField(panelFase2, "Hora:", y); y += dy;
        lblResumenDuracion = addResumenField(panelFase2, "Duración:", y); y += dy;
        lblResumenCliente = addResumenField(panelFase2, "Cliente:", y); y += dy;
        lblResumenTraje = addResumenField(panelFase2, "Traje:", y); y += dy;
        lblResumenTaller = addResumenField(panelFase2, "Taller:", y); y += dy;
        lblResumenOficial = addResumenField(panelFase2, "Oficial:", y); y += dy + 10;

        JSeparator sep = new JSeparator();
        sep.setBounds(20, y, 450, 2);
        panelFase2.add(sep);
        y += 10;

        JLabel lblApr = new JLabel("APRENDICES (opcional)");
        lblApr.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblApr.setBounds(20, y, 200, 18);
        panelFase2.add(lblApr);
        y += 24;

        panelFase2.add(lbl("Aprendiz 1:", 20, y));
        cbAprendiz1 = new JComboBox<>();
        cbAprendiz1.setBounds(130, y, 310, 25);
        panelFase2.add(cbAprendiz1);
        y += 32;

        panelFase2.add(lbl("Aprendiz 2:", 20, y));
        cbAprendiz2 = new JComboBox<>();
        cbAprendiz2.setBounds(130, y, 310, 25);
        panelFase2.add(cbAprendiz2);
        y += 45;

        btnAtras = new JButton("← Atrás");
        btnAtras.setBounds(20, y, 140, 35);
        panelFase2.add(btnAtras);

        btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnGuardar.setBounds(310, y, 155, 35);
        panelFase2.add(btnGuardar);
    }

    private JLabel lbl(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, 200, 16);
        return l;
    }

    private JLabel addResumenField(JPanel p, String etiqueta, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl.setBounds(20, y, 110, 22);
        p.add(lbl);
        JLabel val = new JLabel("—");
        val.setFont(new Font("Tahoma", Font.PLAIN, 11));
        val.setBounds(135, y, 310, 22);
        val.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(0, 4, 0, 0)));
        val.setOpaque(true);
        val.setBackground(new Color(248, 248, 248));
        p.add(val);
        return val;
    }

    public void mostrarFase2(String fecha, String hora, String duracion, String cliente, String traje, String taller, String oficial) {
        lblResumenFecha.setText(fecha);
        lblResumenHora.setText(hora);
        lblResumenDuracion.setText(duracion);
        lblResumenCliente.setText(cliente);
        lblResumenTraje.setText(traje);
        lblResumenTaller.setText(taller);
        lblResumenOficial.setText(oficial);
        cardLayout.show(panelContenedor, "FASE2");
    }

    public void volverFase1() {
    	cardLayout.show(panelContenedor, "FASE1");
    }

    // Getters Fase 1
    public JComboBox<String> getCbCliente() {
    	return cbCliente;
    }
    public JComboBox<String> getCbTraje() {
    	return cbTraje;
    }
    public JComboBox<String> getCbTaller() {
    	return cbTaller;
    }
    public JComboBox<String> getCbOficial() {
    	return cbOficial;
    }
    public JTextField getTxtFecha() {
    	return txtFecha;
    }
    public JTextField getTxtHora() {
    	return txtHora;
    }
    public JTextField getTxtDuracion() {
    	return txtDuracion;
    }
    public JButton getBtnNuevoCliente() {
    	return btnNuevoCliente;
    }
    public JButton getBtnNuevoTraje() {
    	return btnNuevoTraje;
    }
    public JButton getBtnSiguiente() {
    	return btnSiguiente;
    }
    public JButton getBtnCancelar() {
    	return btnCancelar;
    }

    // Getters Fase 2
    public JComboBox<String> getCbAprendiz1() {
    	return cbAprendiz1;
    }
    public JComboBox<String> getCbAprendiz2() {
    	return cbAprendiz2;
    }
    public JButton getBtnGuardar() {
    	return btnGuardar;
    }
    public JButton getBtnAtras() {
    	return btnAtras;
    }

    // Compatibilidad
    public JButton getBtnCliente() {
    	return btnNuevoCliente;
    }
    public JButton getBtnTaller()  {
    	return null;
    }
    // Compatibilidad: getTxtOficial() devuelve null — ya no se usa
    public JTextField getTxtOficial() {
    	return null;
    }
}