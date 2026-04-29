package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana principal APRENDIZ.
 * CardLayout: DASHBOARD / LISTA_CITAS / LISTA_TALLERES
 */
public class VentanaAprendiz extends JFrame {

    private CardLayout cardLayout;
    private JPanel     cardPane;

    private JMenuItem menuItemListaCitas;
    private JMenuItem menuItemListaTalleres;

    private JLabel lblUsuario;
    private JLabel lblSalir;

    // Dashboard
    private JLabel lblTodasLasCitas;
    private JLabel lblNumeroDeMisCitas;
    private JLabel lblNumeroDeTalleres;
    private JLabel lblCitasHoy;
    private JLabel lblProximaCita;

    // Lista Citas embebida
    private JTable     tableCitas;
    private JTextField txtBuscarCitas;
    private JButton    btnNuevaCitaEmb;
    private JButton    btnVerDetallesCitas;
    private JButton    btnEditarCitas;
    private JButton    btnEliminarCitas;
    private JButton    btnBuscarCitas;
    private JButton    btnTodasCitas;
    private JButton    btnDisenoCitas;
    private JButton    btnCosturaCitas;
    private JButton    btnPruebasCitas;
    private JButton    btnVolverCitas;

    // Lista Talleres embebida
    private JTable                   tableTalleres;
    private JList<String>            listaTalleres;
    private DefaultListModel<String> modeloListaTalleres;
    private JButton btnNuevoTallerEmb;
    private JButton btnEditarTalleres;
    private JButton btnEliminarTalleres;
    private JButton btnConfirmarTalleres;
    private JButton btnVolverTalleres;

    public static final String CARD_DASHBOARD      = "DASHBOARD";
    public static final String CARD_LISTA_CITAS    = "LISTA_CITAS";
    public static final String CARD_LISTA_TALLERES = "LISTA_TALLERES";

    public VentanaAprendiz() {
        setTitle("RGHR EDNA MODA - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 600);

        JMenuBar menuBar = new JMenuBar(); setJMenuBar(menuBar);
        JMenu menuCitas = new JMenu("Citas"); menuBar.add(menuCitas);
        menuItemListaCitas = new JMenuItem("Lista de citas"); menuCitas.add(menuItemListaCitas);
        JMenu menuTalleres = new JMenu("Talleres"); menuBar.add(menuTalleres);
        menuItemListaTalleres = new JMenuItem("Lista de talleres"); menuTalleres.add(menuItemListaTalleres);

        JPanel rootPane = new JPanel(new BorderLayout());
        rootPane.setBorder(new EmptyBorder(4, 4, 4, 4));
        setContentPane(rootPane);

        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(770, 38));
        header.setBackground(new Color(245, 245, 252));
        header.setBorder(new MatteBorder(0, 0, 1, 0, new Color(200, 200, 220)));
        lblSalir = new JLabel("Salir");
        lblSalir.setForeground(new Color(60, 90, 180));
        lblSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblSalir.setBounds(690, 8, 50, 20);
        header.add(lblSalir);
        lblUsuario = new JLabel("");
        lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblUsuario.setForeground(new Color(80, 80, 100));
        lblUsuario.setBounds(350, 10, 320, 18);
        header.add(lblUsuario);
        rootPane.add(header, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPane   = new JPanel(cardLayout);
        rootPane.add(cardPane, BorderLayout.CENTER);

        cardPane.add(construirDashboard(),       CARD_DASHBOARD);
        cardPane.add(construirPanelListaCitas(), CARD_LISTA_CITAS);
        cardPane.add(construirPanelListaTalleres(), CARD_LISTA_TALLERES);

        cardLayout.show(cardPane, CARD_DASHBOARD);
    }

    private JPanel construirDashboard() {
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);

        JLabel imagen = new JLabel("");
        imagen.setIcon(new ImageIcon(VentanaAprendiz.class.getResource("/img/LOGO RHGR_BUILD.png")));
        imagen.setBounds(220, 15, 316, 200);
        p.add(imagen);

        JLabel lblResumen = new JLabel("MI RESUMEN");
        lblResumen.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblResumen.setForeground(new Color(60, 60, 100));
        lblResumen.setBounds(20, 228, 200, 22);
        p.add(lblResumen);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(180, 180, 220));
        sep.setBounds(20, 252, 720, 2);
        p.add(sep);

        crearTarjeta(p, "CITAS TOTALES", 20,  265, 170, 78, lblTodasLasCitas    = new JLabel(""));
        crearTarjeta(p, "MIS CITAS",     205, 265, 170, 78, lblNumeroDeMisCitas = new JLabel(""));
        crearTarjeta(p, "TALLERES",      390, 265, 170, 78, lblNumeroDeTalleres = new JLabel(""));
        crearTarjeta(p, "CITAS HOY",     575, 265, 145, 78, lblCitasHoy         = new JLabel(""));

        crearTarjeta(p, "PRÓXIMA CITA", 20, 358, 700, 78, lblProximaCita = new JLabel(""));

        return p;
    }

    private void crearTarjeta(JPanel parent, String titulo, int x, int y, int w, int h, JLabel labelValor) {
        JPanel box = new JPanel(null);
        box.setBackground(new Color(245, 247, 255));
        box.setBorder(new CompoundBorder(new LineBorder(new Color(180, 190, 230), 1, true), new EmptyBorder(4, 8, 4, 8)));
        box.setBounds(x, y, w, h);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 10));
        lbl.setForeground(new Color(100, 100, 150));
        lbl.setBounds(8, 6, w - 16, 16);
        box.add(lbl);
        labelValor.setFont(new Font("Tahoma", Font.BOLD, 22));
        labelValor.setForeground(new Color(40, 60, 160));
        labelValor.setBounds(8, 28, w - 16, 36);
        box.add(labelValor);
        parent.add(box);
    }

    private JPanel construirPanelListaCitas() {
        JPanel p = new JPanel(null); p.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("Gestión de Citas");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15)); titulo.setForeground(new Color(60,60,100));
        titulo.setBounds(10, 8, 300, 22); p.add(titulo);
        btnTodasCitas   = new JButton("Todas");   btnTodasCitas.setBounds(10,  38, 90, 26); p.add(btnTodasCitas);
        btnDisenoCitas  = new JButton("Diseño");  btnDisenoCitas.setBounds(108, 38, 90, 26); p.add(btnDisenoCitas);
        btnCosturaCitas = new JButton("Costura"); btnCosturaCitas.setBounds(206, 38, 90, 26); p.add(btnCosturaCitas);
        btnPruebasCitas = new JButton("Pruebas"); btnPruebasCitas.setBounds(304, 38, 90, 26); p.add(btnPruebasCitas);
        txtBuscarCitas  = new JTextField(); txtBuscarCitas.setColumns(10); txtBuscarCitas.setBounds(10, 72, 220, 25); p.add(txtBuscarCitas);
        btnBuscarCitas  = new JButton("Buscar");       btnBuscarCitas.setBounds(238, 72, 80,  25); p.add(btnBuscarCitas);
        btnNuevaCitaEmb = new JButton("+ Nueva Cita"); btnNuevaCitaEmb.setBounds(600, 72, 130, 25); p.add(btnNuevaCitaEmb);
        JScrollPane scroll = new JScrollPane(); scroll.setBounds(10, 105, 730, 340); p.add(scroll);
        tableCitas = new JTable();
        tableCitas.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Fecha/Hora","Cliente","Traje","Taller","Oficial","Duración (h)"}));
        tableCitas.setRowHeight(22); scroll.setViewportView(tableCitas);
        btnVerDetallesCitas = new JButton("Ver detalles"); btnVerDetallesCitas.setBounds(10, 458, 120, 28); p.add(btnVerDetallesCitas);
        btnEditarCitas      = new JButton("Editar");       btnEditarCitas.setBounds(138,     458, 90,  28); p.add(btnEditarCitas);
        btnEliminarCitas    = new JButton("Eliminar");     btnEliminarCitas.setBounds(236,   458, 90,  28); p.add(btnEliminarCitas);
        btnVolverCitas      = new JButton("\u2190 Volver");btnVolverCitas.setBounds(630,     458, 110, 28); p.add(btnVolverCitas);
        return p;
    }

    private JPanel construirPanelListaTalleres() {
        JPanel p = new JPanel(null); p.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("Sedes y Talleres");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 15)); titulo.setForeground(new Color(60,60,100));
        titulo.setBounds(10, 8, 300, 22); p.add(titulo);
        btnNuevoTallerEmb    = new JButton("+ Nuevo");   btnNuevoTallerEmb.setBounds(10,  38, 90, 26); p.add(btnNuevoTallerEmb);
        btnEditarTalleres    = new JButton("Editar");    btnEditarTalleres.setBounds(108, 38, 90, 26); p.add(btnEditarTalleres);
        btnEliminarTalleres  = new JButton("Eliminar");  btnEliminarTalleres.setBounds(206, 38, 90, 26);p.add(btnEliminarTalleres);
        btnConfirmarTalleres = new JButton("Confirmar"); btnConfirmarTalleres.setBounds(304, 38, 90, 26);p.add(btnConfirmarTalleres);
        JScrollPane scrollTabla = new JScrollPane(); scrollTabla.setBounds(10, 75, 730, 230); p.add(scrollTabla);
        tableTalleres = new JTable();
        tableTalleres.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Sede / ciudad","Tipo de sala"}));
        tableTalleres.setRowHeight(22); scrollTabla.setViewportView(tableTalleres);
        modeloListaTalleres = new DefaultListModel<>();
        listaTalleres = new JList<>(modeloListaTalleres);
        JScrollPane scrollLista = new JScrollPane(listaTalleres); scrollLista.setBounds(10, 320, 730, 130); p.add(scrollLista);
        btnVolverTalleres = new JButton("\u2190 Volver"); btnVolverTalleres.setBounds(630, 462, 110, 28); p.add(btnVolverTalleres);
        return p;
    }

    public void mostrarCard(String card) {
    	cardLayout.show(cardPane, card);
    }

    public JMenuItem getMenuItemListaCitas() {
        return menuItemListaCitas; 
    }
    public JMenuItem getMenuItemListaTalleres() {
        return menuItemListaTalleres; 
    }
    public JLabel getLblUsuario() {
        return lblUsuario; 
    }
    public JLabel getLblSalir() {
        return lblSalir; 
    }
    public JLabel getLblTodasLasCitas() {
        return lblTodasLasCitas; 
    }
    public JLabel getLblNumeroDeMisCitas() {
        return lblNumeroDeMisCitas; 
    }
    public JLabel getLblNumeroDeTalleres() {
        return lblNumeroDeTalleres;
     }
    public JLabel getLblCitasHoy() {
        return lblCitasHoy; 
    }
    public JLabel getLblProximaCita() {
        return lblProximaCita; 
    }
    public JTable getTableCitas() {
        return tableCitas; 
    }
    public JTextField getTxtBuscarCitas() {
        return txtBuscarCitas; 
    }
    public JButton getBtnNuevaCitaEmb() {
        return btnNuevaCitaEmb;
     }
    public JButton getBtnVerDetallesCitas() {
        return btnVerDetallesCitas; 
    }
    public JButton getBtnEditarCitas() {
        return btnEditarCitas; 
    }
    public JButton getBtnEliminarCitas() {
        return btnEliminarCitas;
     }
    public JButton getBtnBuscarCitas() {
        return btnBuscarCitas;
     }
    public JButton getBtnTodasCitas() {
        return btnTodasCitas; 
    }
    public JButton getBtnDisenoCitas() {
        return btnDisenoCitas; 
    }
    public JButton getBtnCosturaCitas() {
        return btnCosturaCitas; 
    }
    public JButton getBtnPruebasCitas() {
        return btnPruebasCitas;
    }
    public JButton getBtnVolverCitas() {
        return btnVolverCitas; 
    }
    public JTable getTableTalleres() { 
        return tableTalleres; 
    }
    public JList<String> getLista() {
         return listaTalleres; 
    }
    public DefaultListModel<String> getModeloListaTalleres() {
         return modeloListaTalleres;
    }
    public JButton getBtnNuevoTallerEmb() {
         return btnNuevoTallerEmb; 
    }
    public JButton getBtnEditarTalleres() {
         return btnEditarTalleres; 
    }
    public JButton getBtnEliminarTalleres() {
         return btnEliminarTalleres; 
    }
    public JButton getBtnConfirmarTalleres() {
         return btnConfirmarTalleres; 
    }
    public JButton getBtnVolverTalleres() {
         return btnVolverTalleres; 
    }
    public void deshabilitarBotonesCitas() {
         btnEditarCitas.setEnabled(false);
         btnNuevaCitaEmb.setEnabled(false);
         btnEliminarCitas.setEnabled(false);
    }
    public void deshabilitarBotonesTalleres() {
         btnEditarTalleres.setEnabled(false); 
         btnEliminarTalleres.setEnabled(false);
          btnNuevoTallerEmb.setEnabled(false); 
    }
}
