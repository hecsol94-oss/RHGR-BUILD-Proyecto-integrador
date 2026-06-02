package vista;

import java.awt.*;

/**
 * Ventana principal APRENDIZ.
 * CardLayout: DASHBOARD / LISTA_CITAS / LISTA_TALLERES
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class VentanaAprendiz extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private CardLayout cardLayout;
    private JPanel cardPane;

    /**
     * --- Componentes originales (Todos mantenidos para el Controlador) ---
     */
    private JMenuItem menuItemListaCitas, menuItemNuevaCita, menuItemListaClientes, menuItemNuevoCliente, menuItemListaTalleres, menuItemNuevoTaller, menuItemListaEmpleados, menuItemNuevoEmpleado;
    private JLabel lblUsuario, lblSalir;
    private JLabel lblTodasLasCitas, lblNumeroDeMisCitas, lblNumeroDeTalleres, lblTotalClientes, lblCitasHoy, lblCitasSemana, lblProximaCita;
    private JTable tableCitas, tableClientes, tableTalleres;
    private JTextField txtBuscarCitas, txtBuscarClientes;
    private JButton btnNuevaCitaEmb, btnVerDetallesCitas, btnEditarCitas, btnBuscarCitas, btnTodasCitas, btnDisenoCitas, btnCosturaCitas, btnPruebasCitas, btnVolverCitas;
    private JList<String> listaTalleres;
    private DefaultListModel<String> modeloListaTalleres;
    private JButton btnNuevoTallerEmb, btnEditarTalleres, btnEliminarTalleres, btnConfirmarTalleres, btnVolverTalleres;
    private JButton btnNuevoEmpleadoEmb, btnEditarEmpleados, btnEliminarEmpleados, btnBuscarEmpleados, btnTodosEmpleados, btnAprendizEmpleados, btnOficialEmpleados, btnMaestroEmpleados, btnVolverEmpleados;

    public static final String CARD_DASHBOARD = "DASHBOARD";
    public static final String CARD_LISTA_CITAS = "LISTA_CITAS";
    public static final String CARD_LISTA_CLIENTES = "LISTA_CLIENTES";
    public static final String CARD_LISTA_TALLERES = "LISTA_TALLERES";

    /**
     * Construye la ventana principal para el rol de aprendiz.
     * 
     * Inicializa los componentes gráficos, el menú, el header
     * y el sistema de navegación mediante CardLayout.
     */
    public VentanaAprendiz() {
        lblTodasLasCitas = new JLabel("0");
        lblNumeroDeMisCitas = new JLabel("0");
        lblNumeroDeTalleres = new JLabel("0");
        lblTotalClientes = new JLabel("0");
        lblCitasHoy = new JLabel("0");
        lblCitasSemana = new JLabel("0");
        lblProximaCita = new JLabel("-");
        
        setTitle("SISTEMA DE OPERACIONES ELITE - EDNA MODA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1200, 800));

        JPanel panelFondo = new JPanel(new BorderLayout());
        panelFondo.setBackground(GRIS_TECNICO);
        setContentPane(panelFondo);

        configurarBarraMenu();
        panelFondo.add(construirHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPane = new JPanel(cardLayout);
        cardPane.setOpaque(false);
        panelFondo.add(cardPane, BorderLayout.CENTER);

        cardPane.add(construirDashboard(), CARD_DASHBOARD);
        cardPane.add(construirPanelListaCitas(), CARD_LISTA_CITAS);
        cardPane.add(new JPanel(), CARD_LISTA_CLIENTES); 
        cardPane.add(new JPanel(), CARD_LISTA_TALLERES);

        cardLayout.show(cardPane, CARD_DASHBOARD);
    }

    /**
     * Construye el panel superior (header) de la aplicación.
     * 
     * Contiene la información del usuario y la opción de cerrar sesión.
     *
     * @return JPanel configurado como cabecera
     */
    private JPanel construirHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 75));
        header.setBackground(ROJO_HEROE);
        header.setBorder(new MatteBorder(0, 0, 4, 0, AMARILLO_POWER));

        lblUsuario = new JLabel("  OPERADOR: EDNA MODA");
        lblUsuario.setFont(new Font("Impact", Font.PLAIN, 26));
        lblUsuario.setForeground(Color.WHITE);
        header.add(lblUsuario, BorderLayout.WEST);

        lblSalir = new JLabel("CERRAR SESIÓN  ");
        lblSalir.setForeground(AMARILLO_POWER);
        lblSalir.setFont(new Font("Impact", Font.PLAIN, 22));
        lblSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(lblSalir, BorderLayout.EAST);

        return header;
    }

    /**
     * Construye el panel de resumen (dashboard).
     * 
     * Muestra información básica relevante para el aprendiz,
     * como citas asignadas y próxima tarea.
     *
     * @return JPanel con la vista del dashboard
     */
    private JPanel construirDashboard() {
        /**
         * Contenedor principal con logo RHGR de fondo (Full Stretch)
         */
        JPanel p = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(VentanaMaestro.class.getResource("/img/LOGO RHGR_BUILD.png"));
                    Image img = icon.getImage();
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
                    g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null); 
                    g2d.dispose();
                } catch (Exception e) {}
            }
        };
        p.setBackground(GRIS_TECNICO);
        
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setOpaque(false);
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.fill = GridBagConstraints.HORIZONTAL;

        JLabel tituloResumen = new JLabel("RESUMEN DEL TALLER");
        tituloResumen.setFont(new Font("Impact", Font.PLAIN, 36)); 
        tituloResumen.setForeground(NEGRO_ELITE);
        tituloResumen.setHorizontalAlignment(SwingConstants.CENTER);
        gbcMain.gridy = 0;
        gbcMain.insets = new Insets(0, 0, 40, 0); 
        contenedorCentral.add(tituloResumen, gbcMain);

        /**
         * --- EL PANEL DE TARJETAS ---
         */
        JPanel panelTarjetas = new JPanel(new GridBagLayout());
        panelTarjetas.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0; 
        gbc.gridx = 0; panelTarjetas.add(crearTarjetaDashboard("TOTAL CITAS", lblTodasLasCitas), gbc);
        gbc.gridx = 1; panelTarjetas.add(crearTarjetaDashboard("ASIGNADAS", lblNumeroDeMisCitas), gbc);
        gbc.gridx = 2; panelTarjetas.add(crearTarjetaDashboard("TALLERES", lblNumeroDeTalleres), gbc);
        gbc.gridx = 3; panelTarjetas.add(crearTarjetaDashboard("CITAS HOY", lblCitasHoy), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; 
        gbc.gridwidth = 4; 
        panelTarjetas.add(crearTarjetaDashboard("PRÓXIMA MISIÓN CRÍTICA", lblProximaCita), gbc);

        gbcMain.gridy = 1;
        gbcMain.insets = new Insets(0, 0, 0, 0);
        contenedorCentral.add(panelTarjetas, gbcMain);

        p.add(contenedorCentral);
        return p;
    }
    
    /**
     * Crea una tarjeta visual para mostrar una métrica en el dashboard.
     *
     * @param titulo texto descriptivo de la métrica
     * @param labelValor etiqueta donde se mostrará el valor
     * @return JPanel configurado como tarjeta informativa
     */
    private JPanel crearTarjetaDashboard(String titulo, JLabel labelValor) {
        JPanel box = new JPanel(new BorderLayout());
        box.setPreferredSize(new Dimension(260, 180)); 
        box.setBackground(NEGRO_ELITE);
        box.setBorder(new CompoundBorder(new LineBorder(AMARILLO_POWER, 3), new EmptyBorder(20, 20, 20, 20)));
        
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Impact", Font.PLAIN, 18));
        lbl.setForeground(AMARILLO_POWER);
        box.add(lbl, BorderLayout.NORTH);
                
        labelValor.setFont(new Font("Monospaced", Font.BOLD, 52)); 
        labelValor.setForeground(Color.WHITE);
        labelValor.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(labelValor, BorderLayout.CENTER);
                
        return box;
    }

    /**
     * --- Otros métodos de construcción y estilo (Lista Citas, Menú, Botones, etc.) ---
     * Se mantienen idénticos a la versión anterior para asegurar compatibilidad total.
     * @return
     */

    private JPanel construirPanelListaCitas() {
        JPanel main = new JPanel(new BorderLayout(25, 25));
        main.setBackground(GRIS_TECNICO);
        main.setBorder(new EmptyBorder(40, 40, 40, 40));
        JPanel panelNorte = new JPanel(new BorderLayout(0, 25));
        panelNorte.setOpaque(false);
        JLabel t = new JLabel("PANEL TÁCTICO DE CITAS");
        t.setFont(new Font("Impact", Font.PLAIN, 40));
        panelNorte.add(t, BorderLayout.NORTH);
        JPanel barraAcciones = new JPanel(new BorderLayout());
        barraAcciones.setOpaque(false);
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filtros.setOpaque(false);
        btnTodasCitas = new JButton("TODAS"); estilizarBotonAccion(btnTodasCitas, 140, 45);
        btnDisenoCitas = new JButton("DISEÑO"); estilizarBotonAccion(btnDisenoCitas, 140, 45);
        btnCosturaCitas = new JButton("COSTURA"); estilizarBotonAccion(btnCosturaCitas, 140, 45);
        btnPruebasCitas = new JButton("PRUEBAS"); estilizarBotonAccion(btnPruebasCitas, 140, 45);
        filtros.add(btnTodasCitas); filtros.add(btnDisenoCitas); 
        filtros.add(btnCosturaCitas); filtros.add(btnPruebasCitas);
        barraAcciones.add(filtros, BorderLayout.WEST);
        btnNuevaCitaEmb = new JButton("+ AGENDAR");
        estilizarBotonAccion(btnNuevaCitaEmb, 250, 45);
        btnNuevaCitaEmb.setBackground(ROJO_HEROE); btnNuevaCitaEmb.setForeground(Color.WHITE);
        barraAcciones.add(btnNuevaCitaEmb, BorderLayout.EAST);
        panelNorte.add(barraAcciones, BorderLayout.CENTER);
        main.add(panelNorte, BorderLayout.NORTH);
        tableCitas = new JTable(); 
        estilizarTablaElite(tableCitas);
        tableCitas.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"FECHA/HORA","CLIENTE","TRAJE","TALLER","OFICIAL","HORAS"}));
        JScrollPane scroll = new JScrollPane(tableCitas);
        scroll.setBorder(new LineBorder(NEGRO_ELITE, 2));
        main.add(scroll, BorderLayout.CENTER);
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        JPanel accionesInferiores = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        accionesInferiores.setOpaque(false);
        btnVerDetallesCitas = new JButton("VER DOSSIER"); estilizarBotonAccion(btnVerDetallesCitas, 200, 55);
        btnEditarCitas = new JButton("EDITAR"); estilizarBotonAccion(btnEditarCitas, 150, 55);
        accionesInferiores.add(btnVerDetallesCitas); accionesInferiores.add(btnEditarCitas);
        btnVolverCitas = new JButton("VOLVER"); estilizarBotonAccion(btnVolverCitas, 160, 55);
        panelSur.add(accionesInferiores, BorderLayout.WEST);
        panelSur.add(btnVolverCitas, BorderLayout.EAST);
        main.add(panelSur, BorderLayout.SOUTH);
        return main;
    }

    /**
     * Construye el panel de gestión de citas.
     * 
     * Incluye filtros, tabla de datos y acciones disponibles
     * para el usuario aprendiz.
     *
     * @return JPanel con la vista de citas
     */
    private void estilizarBotonAccion(JButton btn, int w, int h) {
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(NEGRO_ELITE);
        btn.setForeground(AMARILLO_POWER);
        btn.setFont(new Font("Impact", Font.PLAIN, 20));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(AMARILLO_POWER, 2));
    }

    /**
     * Aplica estilo visual a una tabla.
     *
     * @param tabla tabla a estilizar
     */
    private void estilizarTablaElite(JTable tabla) {
        tabla.setRowHeight(45); 
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 16));
        JTableHeader h = tabla.getTableHeader();
        h.setBackground(NEGRO_ELITE); h.setForeground(Color.WHITE);
        h.setFont(new Font("Impact", Font.PLAIN, 20));
        h.setPreferredSize(new Dimension(0, 55));
    }

    /**
     * Configura la barra de menú principal.
     * 
     * Define las opciones disponibles para el rol aprendiz,
     * limitando funcionalidades según permisos.
     */
    private void configurarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(NEGRO_ELITE);
        menuBar.setBorder(new MatteBorder(0, 0, 1, 0, AMARILLO_POWER));
        setJMenuBar(menuBar);
        JMenu menuCitas = crearMenu("Citas");
        menuItemListaCitas = crearItem("Lista de citas");
        menuItemNuevaCita = crearItem("Nueva cita");
        menuCitas.add(menuItemListaCitas); menuCitas.add(menuItemNuevaCita);
        menuBar.add(menuCitas);
        JMenu menuClientes = crearMenu("Clientes");
        menuItemListaClientes = crearItem("Lista de clientes");
        menuItemNuevoCliente = crearItem("Nuevo cliente");
        menuClientes.add(menuItemListaClientes); menuClientes.add(menuItemNuevoCliente);
        menuBar.add(menuClientes);
        JMenu menuTalleres = crearMenu("Talleres");
        menuItemListaTalleres = crearItem("Lista de talleres");
        menuItemNuevoTaller = crearItem("Nuevo taller");
        menuTalleres.add(menuItemListaTalleres); menuTalleres.add(menuItemNuevoTaller);
        menuBar.add(menuTalleres);
        menuClientes.setEnabled(false);
        menuItemNuevoTaller.setEnabled(false);
        menuItemNuevaCita.setEnabled(false);
        JMenu menuEmpleados = crearMenu("Empleados");
        menuItemListaEmpleados = crearItem("Lista de empleados");
        menuItemNuevoEmpleado = crearItem("Nuevo empleado");
        menuEmpleados.add(menuItemListaEmpleados); menuEmpleados.add(menuItemNuevoEmpleado);
        menuBar.add(menuEmpleados);
        menuItemNuevoEmpleado.setEnabled(false);
    }

    /**
     * Crea un menú con el texto indicado y estilo básico aplicado.
     *
     * @param t texto que se mostrará como título del menú
     * @return JMenu configurado con el estilo de la aplicación
     */
    private JMenu crearMenu(String t) {
        JMenu m = new JMenu(t);
        m.setForeground(Color.WHITE);
        return m;
    }

    /**
     * Crea un elemento de menú con el texto indicado y estilo personalizado.
     *
     * @param t texto que se mostrará en el item del menú
     * @return JMenuItem configurado con colores de la interfaz
     */
    private JMenuItem crearItem(String t) {
        JMenuItem i = new JMenuItem(t);
        i.setBackground(NEGRO_ELITE);
        i.setForeground(Color.WHITE);
        return i;
    }

    /** --- GETTERS PARA EL CONTROLADOR (TODOS MANTENIDOS) --- */
    
    /**
     * 
     * @param card
     */
    public void mostrarCard(String card) {
    	cardLayout.show(cardPane, card);
    }

    /**
     * 
     * @return
     */
    public JLabel getLblUsuario() { 
    	return lblUsuario; 
    }
    /**
     * 
     * @return
     */
    public JLabel getLblSalir() { 
    	return lblSalir; 
    }
    /**
     * 
     * @return
     */
    public JLabel getLblTodasLasCitas() { 
    	return lblTodasLasCitas; 
    }
    /**
     * 
     * @return
     */
    public JLabel getLblNumeroDeMisCitas() {
    	return lblNumeroDeMisCitas;
    }
    /**
     * 
     * @return
     */
    public JLabel getLblNumeroDeTalleres() {
    	return lblNumeroDeTalleres;
    }
    /**
     * 
     * @return
     */
    public JLabel getLblCitasHoy() { 
    	return lblCitasHoy; 
    }
    /**
     * 
     * @return
     */
    public JLabel getLblProximaCita() { 
    	return lblProximaCita;
    }
    /**
     * 
     * @return
     */
    public JTable getTableCitas() { 
    	return tableCitas;
    }
    /**
     * 
     * @return
     */
    public JTextField getTxtBuscarCitas() { 
    	return txtBuscarCitas;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnNuevaCitaEmb() {
    	return btnNuevaCitaEmb; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnVerDetallesCitas() {
    	return btnVerDetallesCitas; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnEditarCitas() {
    	return btnEditarCitas; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnBuscarCitas() {
    	return btnBuscarCitas; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnTodasCitas() {
    	return btnTodasCitas;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnDisenoCitas() {
    	return btnDisenoCitas;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnCosturaCitas() {
    	return btnCosturaCitas; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnPruebasCitas() {
    	return btnPruebasCitas; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnVolverCitas() {
    	return btnVolverCitas; 
    }
    /**
     * 
     * @return
     */
    public JTable getTableTalleres() {
    	return tableTalleres;
    }
    /**
     * 
     * @return
     */
    public JList<String> getLista() {
    	return listaTalleres; 
    }
    /**
     * 
     * @return
     */
    public DefaultListModel<String> getModeloListaTalleres() {
    	return modeloListaTalleres;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnNuevoTallerEmb() {
    	return btnNuevoTallerEmb;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnEditarTalleres() { 
    	return btnEditarTalleres;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnEliminarTalleres() {
    	return btnEliminarTalleres;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnConfirmarTalleres() {
    	return btnConfirmarTalleres; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnVolverTalleres() {
    	return btnVolverTalleres; 
    }
    /**
     * 
     * @return
     */
    public JButton getBtnNuevoEmpleadoEmb() {
    	return btnNuevoEmpleadoEmb;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnEditarEmpleados() {
    	return btnEditarEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnEliminarEmpleados() {
    	return btnEliminarEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnBuscarEmpleados() {
    	return btnBuscarEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnTodosEmpleados() {
    	return btnTodosEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnAprendizEmpleados() {
    	return btnAprendizEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnOficialEmpleados() {
    	return btnOficialEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnMaestroEmpleados() {
    	return btnMaestroEmpleados;
    }
    /**
     * 
     * @return
     */
    public JButton getBtnVolverEmpleados() {
    	return btnVolverEmpleados;
    }
    public void deshabilitarBotonesCitas() {
    	btnEditarCitas.setEnabled(false); 
    	btnNuevaCitaEmb.setEnabled(false); 
    }
    public void deshabilitarBotonesTalleres() {
    	btnEditarTalleres.setEnabled(false);
    	btnEliminarTalleres.setEnabled(false);
    	btnNuevoTallerEmb.setEnabled(false); 
    }
    public void deshabilitarBotonesEmpleados() {
    	btnEliminarEmpleados.setEnabled(false);
    	btnNuevoEmpleadoEmb.setEnabled(false);
    }

    /** Getters para los items de menú */
    
    /**
     * 
     * @return
     */
	public JMenuItem getMenuItemListaCitas() { 
		return menuItemListaCitas; 
	}
	/**
	 * 
	 * @return
	 */
	public JMenuItem getMenuItemListaTalleres() { 
		return menuItemListaTalleres; 
	}
	/**
	 * 
	 * @return
	 */
	public JMenuItem getMenuItemListaEmpleados() {
		return menuItemListaTalleres;
	}
}
