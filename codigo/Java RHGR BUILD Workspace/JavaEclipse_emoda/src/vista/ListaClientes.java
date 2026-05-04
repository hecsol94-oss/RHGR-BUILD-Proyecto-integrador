package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ListaClientes extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);

    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTable table;
    private JButton btnBuscar, btnNuevo, btnDetalle, btnEditar, btnVolver, btnTodos, btnHeroe, btnVillano, btnEliminar;

    public ListaClientes() {
        setTitle("DATABASE: HEROES & VILLAINS - EDNA MODA");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1000, 700));

        contentPane = new JPanel(new BorderLayout(20, 20));
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new EmptyBorder(30, 40, 30, 40)); 
        setContentPane(contentPane);

        JPanel panelNorte = new JPanel(new BorderLayout(0, 20));
        panelNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("REGISTRO ESTRATÉGICO DE CLIENTES");
        lblTitulo.setFont(new Font("Impact", Font.PLAIN, 36));
        lblTitulo.setForeground(ROJO_HEROE);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFiltrosYBusqueda = new JPanel(new BorderLayout(15, 10));
        panelFiltrosYBusqueda.setOpaque(false);

        JPanel panelFiltrosTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFiltrosTipo.setOpaque(false);
        btnTodos = new JButton("TODOS"); estilizarBoton(btnTodos, 120, 35);
        btnHeroe = new JButton("HÉROES"); estilizarBoton(btnHeroe, 120, 35);
        btnVillano = new JButton("VILLANOS"); estilizarBoton(btnVillano, 120, 35);
        panelFiltrosTipo.add(btnTodos); 
        panelFiltrosTipo.add(btnHeroe); 
        panelFiltrosTipo.add(btnVillano);

        JPanel panelBusquedaAccion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBusquedaAccion.setOpaque(false);
        
        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(350, 35));
        txtBuscar.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtBuscar.setBorder(new LineBorder(NEGRO_ELITE, 1));
        
        btnBuscar = new JButton("BUSCAR"); estilizarBoton(btnBuscar, 130, 35);
        btnNuevo = new JButton("+ NUEVO CLIENTE"); 
        estilizarBoton(btnNuevo, 200, 35);
        btnNuevo.setBackground(ROJO_HEROE);
        btnNuevo.setForeground(Color.WHITE);

        panelBusquedaAccion.add(new JLabel("BUSCAR:"));
        panelBusquedaAccion.add(txtBuscar);
        panelBusquedaAccion.add(btnBuscar);
        panelBusquedaAccion.add(btnNuevo);

        panelFiltrosYBusqueda.add(panelFiltrosTipo, BorderLayout.WEST);
        panelFiltrosYBusqueda.add(panelBusquedaAccion, BorderLayout.EAST);
        
        panelNorte.add(panelFiltrosYBusqueda, BorderLayout.CENTER);
        contentPane.add(panelNorte, BorderLayout.NORTH);

        table = new JTable();
        estilizarTabla(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "NOMBRE DEL CLIENTE", "CAPACIDAD ESPECIAL", "CATEGORÍA", "EXPEDIENTE TRAJES" }
        ));
        table.setDefaultEditor(Object.class, null);

        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(NEGRO_ELITE, 2));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);

        JPanel panelAccionesIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelAccionesIzquierda.setOpaque(false);
        
        btnDetalle = new JButton("VER EXPEDIENTE"); estilizarBoton(btnDetalle, 180, 50);
        btnEditar = new JButton("MODIFICAR"); estilizarBoton(btnEditar, 150, 50);
        btnEliminar = new JButton("ELIMINAR"); estilizarBoton(btnEliminar, 150, 50);
        
        panelAccionesIzquierda.add(btnDetalle);
        panelAccionesIzquierda.add(btnEditar);
        panelAccionesIzquierda.add(btnEliminar);

        btnVolver = new JButton("← VOLVER");
        estilizarBoton(btnVolver, 150, 50);
        
        panelSur.add(panelAccionesIzquierda, BorderLayout.WEST);
        panelSur.add(btnVolver, BorderLayout.EAST);
        contentPane.add(panelSur, BorderLayout.SOUTH);
    }

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
        btn.setFont(new Font("Impact", Font.PLAIN, 16));
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
        tabla.setRowHeight(45); // Filas más altas para pantalla grande
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 15));
        tabla.setSelectionBackground(AMARILLO_POWER);
        tabla.setSelectionForeground(NEGRO_ELITE);
        tabla.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(NEGRO_ELITE);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Impact", Font.PLAIN, 18));
        header.setPreferredSize(new Dimension(0, 50));
        header.setReorderingAllowed(false);
    }

    // --- MÉTODOS PARA EL CONTROLADOR (SE MANTIENEN IGUAL) ---
    /**
	 * 
	 * @return
	 */
	public JTextField getTxtBuscar() {
		return txtBuscar; 
	}
	
	/**
	 * 
	 * @return
	 */
	public JTable getTable() {
		return table; 
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
	public JButton getBtnNuevo() {
		return btnNuevo; 
	}
	
	/**
	 * 
	 * @return
	 */
	public JButton getBtnDetalle() {
		return btnDetalle; 
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
	public JButton getBtnVolver() {
		return btnVolver; 
	}
	
	/**
	 * 
	 * @return
	 */
	public JButton getBtnTodos() {
		return btnTodos; 
	}
	
	/**
	 * 
	 * @return
	 */
	public JButton getBtnHeroe() {
		return btnHeroe; 
	}
	
	/**
	 * 
	 * @return
	 */
	public JButton getBtnVillano() {
		return btnVillano; 
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
	 */
	public void deshabilitarBotones() {
    	btnEditar.setEnabled(false);
    	btnEliminar.setEnabled(false);
    	btnNuevo.setEnabled(false);

    }
}