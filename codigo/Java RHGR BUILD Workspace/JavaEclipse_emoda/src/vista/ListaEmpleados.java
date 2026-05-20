package vista;

import java.awt.*;

import javax.swing.*;

public class ListaEmpleados extends JFrame {

	private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color GRIS_TECNICO = new Color(240, 240, 240);
    
    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTable table;
    private JButton btnBuscar, btnNuevo, btnDetalle, btnEditar, btnVolver, btnTodos, btnAprendiz, btnOficial, btnMaestro, btnEliminar;
}
