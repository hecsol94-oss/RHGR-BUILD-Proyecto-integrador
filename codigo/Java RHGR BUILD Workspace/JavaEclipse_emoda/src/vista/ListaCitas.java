package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ListaCitas extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private JTable tableCitas;
    private JTextField textField;
    private JButton btnNuevaCita, btnVerDetalles, btnEditar, btnEliminar, btnBuscar;
    private JButton btnTodas, btnDiseno, btnCostura, btnPruebas, btnVolver;

    public ListaCitas() {
        setTitle("SISTEMA DE LOGÍSTICA DE CITAS - EDNA MODA");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1000, 700));

        JPanel contentPane = new JPanel(new BorderLayout(0, 15));
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new EmptyBorder(20, 25, 20, 25)); 
        setContentPane(contentPane);

        JPanel panelNorte = new JPanel(new GridLayout(2, 1, 0, 10));
        panelNorte.setOpaque(false);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFiltros.setOpaque(false);
        JLabel lblFiltros = new JLabel("FILTRAR POR FASE: ");
        lblFiltros.setFont(new Font("Tahoma", Font.BOLD, 11));
        panelFiltros.add(lblFiltros);
        
        btnTodas = crearBotonFiltro("TODAS");
        btnDiseno = crearBotonFiltro("DISEÑO");
        btnCostura = crearBotonFiltro("COSTURA");
        btnPruebas = crearBotonFiltro("PRUEBAS");
        panelFiltros.add(btnTodas); panelFiltros.add(btnDiseno); 
        panelFiltros.add(btnCostura); panelFiltros.add(btnPruebas);
        panelNorte.add(panelFiltros);

        JPanel panelAccionesSup = new JPanel(new BorderLayout());
        panelAccionesSup.setOpaque(false);
        
        JPanel panelBusquedaCentral = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBusquedaCentral.setOpaque(false);
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setBorder(new LineBorder(NEGRO_ELITE));
        textField.setFont(new Font("Monospaced", Font.BOLD, 14));
        
        btnBuscar = new JButton("BUSCAR");
        estilizarBoton(btnBuscar, 110, 35, NEGRO_ELITE, AMARILLO_POWER);
        
        panelBusquedaCentral.add(textField);
        panelBusquedaCentral.add(Box.createRigidArea(new Dimension(15, 0))); 
        panelBusquedaCentral.add(btnBuscar);
        
        btnNuevaCita = new JButton("+ AGENDAR CITA");
        estilizarBoton(btnNuevaCita, 180, 35, ROJO_HEROE, Color.WHITE);
        
        panelAccionesSup.add(panelBusquedaCentral, BorderLayout.WEST);
        panelAccionesSup.add(btnNuevaCita, BorderLayout.EAST);
        panelNorte.add(panelAccionesSup);

        contentPane.add(panelNorte, BorderLayout.NORTH);

        tableCitas = new JTable();
        estilizarTabla(tableCitas);
        tableCitas.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"FECHA/HORA", "CLIENTE", "TRAJE", "TALLER", "OFICIAL/MAESTRO", "DURACIÓN (h)"}
        ));
        tableCitas.setDefaultEditor(Object.class, null);
        
        JScrollPane scrollPane = new JScrollPane(tableCitas);
        scrollPane.setBorder(new LineBorder(NEGRO_ELITE, 1));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        panelSur.setPreferredSize(new Dimension(0, 50));

        JPanel panelAccionesInf = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelAccionesInf.setOpaque(false);
        
        btnVerDetalles = new JButton("VER DOSSIER");
        btnEditar = new JButton("REPROGRAMAR");
        btnEliminar = new JButton("CANCELAR CITA");
        
        estilizarBoton(btnVerDetalles, 150, 40, NEGRO_ELITE, AMARILLO_POWER);
        estilizarBoton(btnEditar, 150, 40, NEGRO_ELITE, AMARILLO_POWER);
        estilizarBoton(btnEliminar, 150, 40, NEGRO_ELITE, AMARILLO_POWER);
        
        panelAccionesInf.add(btnVerDetalles);
        panelAccionesInf.add(btnEditar);
        panelAccionesInf.add(btnEliminar);

        btnVolver = new JButton("VOLVER");
        estilizarBoton(btnVolver, 150, 40, NEGRO_ELITE, AMARILLO_POWER);
        
        panelSur.add(panelAccionesInf, BorderLayout.WEST);
        panelSur.add(btnVolver, BorderLayout.EAST);

        contentPane.add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Crea un botón de filtro con estilo básico predefinido.
     * Se utiliza en la interfaz para botones pequeños de selección o filtrado.
     *
     * @param texto texto que se mostrará en el botón
     * @return JButton configurado con tamaño, fuente y bordes personalizados
     */
    private JButton crearBotonFiltro(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(110, 30));
        btn.setFont(new Font("Impact", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(NEGRO_ELITE));
        return btn;
    }

    /**
     * Aplica un estilo visual personalizado a un botón.
     * Permite definir tamaño, colores y comportamiento del cursor.
     *
     * @param btn botón a estilizar
     * @param w ancho del botón
     * @param h alto del botón
     * @param bg color de fondo del botón
     * @param fg color del texto del botón
     */
    private void estilizarBoton(JButton btn, int w, int h, Color bg, Color fg) {
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Impact", Font.PLAIN, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Aplica un estilo uniforme a una tabla JTable.
     * Mejora la legibilidad ajustando filas, fuentes y colores de selección.
     *
     * @param tabla JTable a la que se le aplicará el estilo
     */
    private void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(35);
        tabla.setSelectionBackground(AMARILLO_POWER);
        tabla.setSelectionForeground(NEGRO_ELITE);
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(NEGRO_ELITE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Tahoma", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 40));
    }

    /** Getters */
    
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
    public JTextField getTextField() {
    	return textField;
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnNuevaCita() {
    	return btnNuevaCita;
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnVerDetalles() {
    	return btnVerDetalles; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnEditar() {
    	return btnEditar; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnEliminar() {
    	return btnEliminar; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnBuscar() {
    	return btnBuscar;
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnTodas() {
    	return btnTodas; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnDiseno() {
    	return btnDiseno; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnCostura() {
    	return btnCostura; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnPruebas() {
    	return btnPruebas; 
    }
    
    /**
     * 
     * @return
     */
    public JButton getBtnVolver() {
    	return btnVolver; 
    }

    /**
     * 
     */
    public void deshabilitarBotones() {
        btnEditar.setEnabled(false);
        btnNuevaCita.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}