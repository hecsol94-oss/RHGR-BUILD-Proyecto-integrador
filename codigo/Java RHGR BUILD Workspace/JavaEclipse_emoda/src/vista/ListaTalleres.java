package vista;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Taller;

public class ListaTalleres extends JFrame {
    
    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private JTable table;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollpaneTable;
    private JButton btnNuevoTaller, btnEditar, btnEliminar, btnVolver;
    
    public ListaTalleres() {
        setTitle("CENTRO DE OPERACIONES: SEDES Y TALLERES - EDNA MODA");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1000, 700));

        JPanel contentPane = new JPanel(new BorderLayout(20, 20));
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new EmptyBorder(40, 50, 40, 50)); /** Margen externo elegante */
        setContentPane(contentPane);

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("GESTIÓN DE SEDES ESTRATÉGICAS");
        lblTitulo.setFont(new Font("Impact", Font.PLAIN, 38));
        lblTitulo.setForeground(ROJO_HEROE);
        panelNorte.add(lblTitulo, BorderLayout.WEST);

        btnNuevoTaller = new JButton("+ REGISTRAR SEDE");
        estilizarBoton(btnNuevoTaller, 220, 45);
        btnNuevoTaller.setBackground(ROJO_HEROE);
        btnNuevoTaller.setForeground(Color.WHITE);
        panelNorte.add(btnNuevoTaller, BorderLayout.EAST);

        contentPane.add(panelNorte, BorderLayout.NORTH);

        table = new JTable();
        estilizarTabla(table);
        modeloTabla = new DefaultTableModel(
                new Object[][] {},
                new String[] { "NOMBRE DE LA SEDE MANTENIMIENTO", "TIPO DE INSTALACIÓN TÉCNICA" }
            );
        table.setModel(modeloTabla);
        table.setDefaultEditor(Object.class, null);
        
        scrollpaneTable = new JScrollPane(table);
        scrollpaneTable.setBorder(new LineBorder(NEGRO_ELITE, 2));
        contentPane.add(scrollpaneTable, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);

        JPanel panelAccionesIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelAccionesIzquierda.setOpaque(false);
        
        btnEditar = new JButton("EDITAR SEDE");
        estilizarBoton(btnEditar, 180, 55);
        btnEliminar = new JButton("ELIMINAR");
        estilizarBoton(btnEliminar, 180, 55);
        
        panelAccionesIzquierda.add(btnEditar);
        panelAccionesIzquierda.add(btnEliminar);

        btnVolver = new JButton("← VOLVER");
        estilizarBoton(btnVolver, 160, 55);
        
        panelSur.add(panelAccionesIzquierda, BorderLayout.WEST);
        panelSur.add(btnVolver, BorderLayout.EAST);
        
        contentPane.add(panelSur, BorderLayout.SOUTH);
    }

    /** --- Métodos de Estética Actualizados --- */

    /**
     * Aplica un estilo visual estándar a un botón de la interfaz.
     * Define tamaño, colores, fuente, borde y comportamiento del cursor
     * para mantener una estética uniforme tipo "Elite".
     *
     * @param btn botón al que se le aplicará el estilo
     * @param w ancho del botón en píxeles
     * @param h alto del botón en píxeles
     */
    private void estilizarBoton(JButton btn, int w, int h) {
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBackground(NEGRO_ELITE);
        btn.setForeground(AMARILLO_POWER);
        btn.setFont(new Font("Impact", Font.PLAIN, 18));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(AMARILLO_POWER, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Aplica un estilo visual estándar a una tabla (JTable).
     * Mejora  legibilidad configurando altura de filas, fuentes,
     * colores de selección, líneas de rejilla y estilo del encabezado.
     *
     * @param tabla tabla a la que se le aplicará el estilo
     */
    private void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(45); /** Filas más altas para legibilidad */
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tabla.setSelectionBackground(AMARILLO_POWER);
        tabla.setSelectionForeground(NEGRO_ELITE);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(NEGRO_ELITE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Impact", Font.PLAIN, 20));
        header.setPreferredSize(new Dimension(0, 50));
        header.setReorderingAllowed(false);
    }

    /** --- MÉTODOS PARA EL CONTROLADOR (SE MANTIENEN IGUAL) --- */

 /** Getter del botón nuevo taller */
    
    /**
     * 
     * @return
     */
    public JButton getBtnNuevoTaller() {
    	return btnNuevoTaller;
    }
    
    /** Getter del botón editar */
    
    /**
     * 
     * @return
     */
    public JButton getBtnEditar() {
    	return btnEditar;
    }
    
    /** Getter del botón eliminar */
    
    /**
     * 
     * @return
     */
    public JButton getBtnEliminar() {
    	return btnEliminar;
    }
    
    /** Getter del botón volver */
    
    /**
     * 
     * @return
     */
    public JButton getBtnVolver() {
    	return btnVolver;
    }
    
    /** Devuelve la tabla */
    
    /**
     * 
     * @return
     */
    public JTable getTable() {
    	return table;
    }
    
    /** Añade una fila a tabla y lista */
    
    /**
     * 
     * @param t
     */
    public void agregarFila(Taller t) {
        modeloTabla.addRow(new Object[]{t.getNombre(), t.getTipo() });       
    }
    
    /** Carga datos desde lista de talleres */
    
    /**
     * 
     * @param talleres
     */
    public void recogerDatos(ArrayList<Taller> talleres) {
    	
    	modeloTabla.setRowCount(0);
    	
    	for (Taller t : talleres) {
    		agregarFila(t);
    	}
    }
    
    public void deshabilitarBotones() {
    	btnNuevoTaller.setEnabled(false);
    	btnEditar.setEnabled(false);
    	btnEliminar.setEnabled(false);

    }
}