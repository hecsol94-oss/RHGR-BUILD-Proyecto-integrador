package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class NuevaCita extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private JComboBox<String> cbCliente, cbTraje, cbTaller, cbOficial;
    private JTextField txtFecha, txtHora, txtDuracion;
    private JButton btnNuevoCliente, btnNuevoTraje, btnSiguiente, btnCancelar;

    private JPanel panelFase2;
    private JLabel lblResumenFecha, lblResumenHora, lblResumenDuracion;
    private JLabel lblResumenCliente, lblResumenTraje, lblResumenTaller, lblResumenOficial;
    private JComboBox<String> cbAprendiz1, cbAprendiz2;
    private JButton btnGuardar, btnAtras;

    private JPanel panelFase1;
    private CardLayout cardLayout;
    private JPanel panelContenedor;

    public NuevaCita() {
        setTitle("PROTOCOLOS DE AGENDAMIENTO - RHGR");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(520, 550);
        setLocationRelativeTo(null); 

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(GRIS_TECNICO);
        getContentPane().add(panelContenedor);

        buildFase1();
        buildFase2();

        panelContenedor.add(panelFase1, "FASE1");
        panelContenedor.add(panelFase2, "FASE2");
        cardLayout.show(panelContenedor, "FASE1");
    }

    /**
     * Construye la interfaz correspondiente a la fase 1 de la creación de una cita.
     * En esta fase se introducen los datos básicos como cliente, traje,
     * taller, oficial, fecha, hora y duración.
     */
    private void buildFase1() {
        panelFase1 = new JPanel(null);
        panelFase1.setBackground(GRIS_TECNICO);
        panelFase1.setBorder(new LineBorder(NEGRO_ELITE, 2));

        JPanel header = crearHeader("FASE 1: ESPECIFICACIONES TÉCNICAS");
        panelFase1.add(header);

        panelFase1.add(lblNegrita("Sujeto / Cliente:", 25, 55));
        cbCliente = new JComboBox<>();
        cbCliente.setBounds(25, 75, 280, 28);
        panelFase1.add(cbCliente);
        
        btnNuevoCliente = new JButton("+ NUEVO");
        estilizarBotonSecundario(btnNuevoCliente, 315, 75, 155, 28);
        panelFase1.add(btnNuevoCliente);

        panelFase1.add(lblNegrita("Diseño / Traje:", 25, 110));
        cbTraje = new JComboBox<>();
        cbTraje.setBounds(25, 130, 280, 28);
        panelFase1.add(cbTraje);
        
        btnNuevoTraje = new JButton("+ NUEVO");
        estilizarBotonSecundario(btnNuevoTraje, 315, 130, 155, 28);
        panelFase1.add(btnNuevoTraje);

        panelFase1.add(lblNegrita("Instalación (Taller):", 25, 165));
        cbTaller = new JComboBox<>();
        cbTaller.setBounds(25, 185, 445, 28);
        panelFase1.add(cbTaller);

        panelFase1.add(lblNegrita("Oficial / Maestro Responsable:", 25, 220));
        cbOficial = new JComboBox<>();
        cbOficial.setBounds(25, 240, 445, 28);
        panelFase1.add(cbOficial);

        panelFase1.add(lblNegrita("Fecha (aaaa-mm-dd):", 25, 275));
        txtFecha = crearCampoEstilizado(25, 295, 130);
        panelFase1.add(txtFecha);

        panelFase1.add(lblNegrita("Hora (hh:mm):", 165, 275));
        txtHora = crearCampoEstilizado(165, 295, 100);
        panelFase1.add(txtHora);

        panelFase1.add(lblNegrita("Duración (h):", 280, 275));
        txtDuracion = crearCampoEstilizado(280, 295, 190);
        txtDuracion.setText("1");
        txtDuracion.setEditable(false);
        txtDuracion.setBackground(new Color(220, 220, 220));
        panelFase1.add(txtDuracion);

        btnSiguiente = new JButton("SIGUIENTE →");
        estilizarBotonAccion(btnSiguiente, 285, 360, 185, 40, ROJO_HEROE, Color.WHITE);
        panelFase1.add(btnSiguiente);

        btnCancelar = new JButton("CANCELAR");
        estilizarBotonAccion(btnCancelar, 25, 360, 150, 40, NEGRO_ELITE, AMARILLO_POWER);
        panelFase1.add(btnCancelar);
    }
    /**
     * Construye la interfaz correspondiente a la fase 2 de la creación de una cita.
     * Muestra un resumen de los datos introducidos en la fase 1 y permite
     * asignar aprendices de forma opcional antes de guardar la cita.
     */
    private void buildFase2() {
        panelFase2 = new JPanel(null);
        panelFase2.setBackground(GRIS_TECNICO);
        panelFase2.setBorder(new LineBorder(ROJO_HEROE, 2));

        JPanel header = crearHeader("FASE 2: REVISIÓN Y ASIGNACIÓN");
        panelFase2.add(header);

        int y = 55, dy = 30;
        lblResumenFecha    = addResumenField(panelFase2, "FECHA:",    y); y += dy;
        lblResumenHora     = addResumenField(panelFase2, "HORA:",     y); y += dy;
        lblResumenDuracion = addResumenField(panelFase2, "DURACIÓN:", y); y += dy;
        lblResumenCliente  = addResumenField(panelFase2, "SUJETO:",   y); y += dy;
        lblResumenTraje    = addResumenField(panelFase2, "DISEÑO:",   y); y += dy;
        lblResumenTaller   = addResumenField(panelFase2, "SEDE:",     y); y += dy;
        lblResumenOficial  = addResumenField(panelFase2, "MAESTRO:",  y); y += dy + 15;

        JSeparator sep = new JSeparator();
        sep.setBounds(25, y, 450, 2);
        panelFase2.add(sep);
        y += 10;

        panelFase2.add(lblNegrita("PERSONAL DE APOYO (APRENDICES):", 25, y));
        y += 25;

        cbAprendiz1 = new JComboBox<>();
        cbAprendiz1.setBounds(25, y, 450, 28);
        panelFase2.add(cbAprendiz1);
        y += 35;

        cbAprendiz2 = new JComboBox<>();
        cbAprendiz2.setBounds(25, y, 450, 28);
        panelFase2.add(cbAprendiz2);
        y += 45;

        btnAtras = new JButton("← ATRÁS");
        estilizarBotonAccion(btnAtras, 25, y, 140, 40, NEGRO_ELITE, Color.WHITE);
        panelFase2.add(btnAtras);

        btnGuardar = new JButton("CONFIRMAR CITA");
        estilizarBotonAccion(btnGuardar, 280, y, 195, 40, ROJO_HEROE, Color.WHITE);
        panelFase2.add(btnGuardar);
    }

    // --- MÉTODOS DE ESTILIZACIÓN ---

    private JPanel crearHeader(String titulo) {
        JPanel p = new JPanel(null);
        p.setBackground(NEGRO_ELITE);
        p.setBounds(0, 0, 520, 40);
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(AMARILLO_POWER);
        lbl.setFont(new Font("Impact", Font.PLAIN, 18));
        lbl.setBounds(20, 5, 400, 30);
        p.add(lbl);
        return p;
    }
    
    /**
     * Crea una etiqueta posicionada en coordenadas absolutas.
     *
     * @param texto texto que mostrará la etiqueta
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     * @return JLabel configurado con el texto y posición indicados
     */
    private JLabel lblNegrita(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Tahoma", Font.BOLD, 11));
        l.setBounds(x, y, 300, 16);
        return l;
    }

    private JTextField crearCampoEstilizado(int x, int y, int w) {
        JTextField t = new JTextField();
        t.setBounds(x, y, w, 28);
        t.setBorder(new LineBorder(NEGRO_ELITE));
        t.setFont(new Font("Monospaced", Font.BOLD, 14));
        return t;
    }

    private void estilizarBotonAccion(JButton b, int x, int y, int w, int h, Color bg, Color fg) {
        b.setBounds(x, y, w, h);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Impact", Font.PLAIN, 15));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new LineBorder(bg.darker(), 1));
    }

    private void estilizarBotonSecundario(JButton b, int x, int y, int w, int h) {
        b.setBounds(x, y, w, h);
        b.setBackground(Color.WHITE);
        b.setForeground(NEGRO_ELITE);
        b.setFont(new Font("Tahoma", Font.BOLD, 10));
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(NEGRO_ELITE));
    }

    /**
     * Añade un campo de resumen (etiqueta + valor) al panel indicado.
     * <p>
     * Se utiliza en la fase 2 para mostrar los datos introducidos
     * previamente de forma estructurada.
     * </p>
     *
     * @param p panel donde se añadirá el campo
     * @param etiqueta texto descriptivo del campo
     * @param y posición vertical en píxeles
     * @return JLabel que representa el valor asociado al campo
     */
    private JLabel addResumenField(JPanel p, String etiqueta, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 10));
        lbl.setBounds(25, y, 100, 22);
        p.add(lbl);
        JLabel val = new JLabel("—");
        val.setFont(new Font("Monospaced", Font.BOLD, 12));
        val.setBounds(110, y, 365, 22);
        val.setOpaque(true);
        val.setBackground(new Color(235, 235, 235));
        val.setBorder(new EmptyBorder(0, 10, 0, 0));
        p.add(val);
        return val;
    }

    /**
     * Muestra la fase 2 de la interfaz rellenando previamente
     * los datos del resumen de la cita.
     *
     * @param fecha fecha de la cita
     * @param hora hora de la cita
     * @param duracion duración de la cita
     * @param cliente nombre del cliente
     * @param traje tipo de traje
     * @param taller taller asignado
     * @param oficial oficial responsable
     */
    public void mostrarFase2(String fecha, String hora, String duracion, String cliente, String traje, String taller, String oficial) {
        lblResumenFecha.setText(fecha);
        lblResumenHora.setText(hora);
        lblResumenDuracion.setText(duracion + " h");
        lblResumenCliente.setText(cliente.toUpperCase());
        lblResumenTraje.setText(traje.toUpperCase());
        lblResumenTaller.setText(taller.toUpperCase());
        lblResumenOficial.setText(oficial.toUpperCase());
        cardLayout.show(panelContenedor, "FASE2");
    }

    /**
     * Cambia la vista actual a la fase 1 del formulario.
     */
    public void volverFase1() {
    	cardLayout.show(panelContenedor, "FASE1");
    }

    // Getters Fase 1
    /**
     * 
     * @return
     */
    public JComboBox<String> getCbCliente() {
    	return cbCliente;
    }
    /**
     * 
     * @return
     */
    public JComboBox<String> getCbTraje() {
    	return cbTraje;
    }
    /**
     * 
     * @return
     */
    public JComboBox<String> getCbTaller() {
    	return cbTaller;
    }
    /**
     * 
     * @return
     */
    public JComboBox<String> getCbOficial() {
    	return cbOficial;
    }
    /**
     * 
     * @return
     */
    public JTextField getTxtFecha() {
    	return txtFecha;
    }
    /**
     * 
     * @return
     */
    public JTextField getTxtHora() {
    	return txtHora;
    }
    /**
     * 
     * @return
     */
    public JTextField getTxtDuracion() {
    	return txtDuracion;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnNuevoCliente() {
    	return btnNuevoCliente;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnNuevoTraje() {
    	return btnNuevoTraje;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnSiguiente() {
    	return btnSiguiente;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnCancelar() {
    	return btnCancelar;
    }
    
    /**
     * 
     * @param tipo
     */
    public void setCbCliente(String tipo) {
    	cbCliente.setSelectedItem(tipo);
    }
    /**
     * 
     * @param tipo
     */
    public void setCbTraje(String tipo) {
    	cbTraje.setSelectedItem(tipo);
    }
    /**
     * 
     * @param tipo
     */
    public void setCbTaller(String tipo) {
    	cbTaller.setSelectedItem(tipo);
    }
    /**
     * 
     * @param tipo
     */
    public void setCbOficial(String tipo) {
    	cbOficial.setSelectedItem(tipo);
    }

    // Getters Fase 2
    /**
     * 
     * @return
     */
    public JComboBox<String> getCbAprendiz1() {
    	return cbAprendiz1;
    }
    /**
     * 
     * @return
     */
    public JComboBox<String> getCbAprendiz2() {
    	return cbAprendiz2;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnGuardar() {
    	return btnGuardar;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnAtras() {
    	return btnAtras;
    }

    // Compatibilidad
    /**
     * 
     * @return
     */
    public JButton getBtnCliente() {
    	return btnNuevoCliente;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnTaller() {
    	return null;
    }
    // Compatibilidad: getTxtOficial() devuelve null — ya no se usa
    /**
     * 
     * @return
     */
    public JTextField getTxtOficial() {
    	return null;
    }
}