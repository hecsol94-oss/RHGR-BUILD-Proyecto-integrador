package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class DetalleCita extends JFrame {

    private final Color ROJO_HEROE = new Color(204, 0, 0);
    private final Color NEGRO_ELITE = new Color(25, 25, 25);
    private final Color AMARILLO_POWER = new Color(255, 204, 0);
    private final Color GRIS_TECNICO = new Color(230, 230, 230);

    private JLabel lblFechaVal, lblHoraVal, lblDuracionVal;
    private JLabel lblClienteVal, lblTrajeVal, lblTallerVal;
    private JLabel lblOficialVal;
    private JTextArea txtAprendices;
    private JButton btnVolver;

    public DetalleCita() {
        setTitle("DOSSIER TÉCNICO DE CITA - RHGR");
        setResizable(false);
        setBounds(150, 150, 460, 520);
        
        JPanel contentPane = new JPanel(null);
        contentPane.setBackground(GRIS_TECNICO);
        contentPane.setBorder(new LineBorder(NEGRO_ELITE, 3));
        setContentPane(contentPane);

        JPanel header = new JPanel(null);
        header.setBackground(NEGRO_ELITE);
        header.setBounds(0, 0, 460, 45);
        contentPane.add(header);

        JLabel titulo = new JLabel("DETALLES DEL PROTOCOLO");
        titulo.setForeground(AMARILLO_POWER);
        titulo.setFont(new Font("Impact", Font.PLAIN, 20));
        titulo.setBounds(20, 10, 300, 25);
        header.add(titulo);

        JLabel marcaAgua = new JLabel("CONFIDENCIAL");
        marcaAgua.setFont(new Font("Tahoma", Font.BOLD, 10));
        marcaAgua.setForeground(ROJO_HEROE);
        marcaAgua.setBounds(340, 15, 100, 15);
        header.add(marcaAgua);

        int y = 65, dy = 38;
        lblFechaVal    = addField(contentPane, "FECHA:",        20, y); y += dy;
        lblHoraVal     = addField(contentPane, "HORA INICIO:",  20, y); y += dy;
        lblDuracionVal = addField(contentPane, "DURACIÓN (H):", 20, y); y += dy;
        lblClienteVal  = addField(contentPane, "SUJETO:",       20, y); y += dy;
        lblTrajeVal    = addField(contentPane, "PROYECTO:",     20, y); y += dy;
        lblTallerVal   = addField(contentPane, "UBICACIÓN:",    20, y); y += dy;
        lblOficialVal  = addField(contentPane, "RESPONSABLE:",  20, y); y += dy;

        JLabel lblAprEtiq = new JLabel("PERSONAL DE APOYO:");
        lblAprEtiq.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblAprEtiq.setBounds(20, y, 150, 22);
        contentPane.add(lblAprEtiq);

        txtAprendices = new JTextArea("—");
        txtAprendices.setFont(new Font("Monospaced", Font.BOLD, 12));
        txtAprendices.setEditable(false);
        txtAprendices.setLineWrap(true);
        txtAprendices.setWrapStyleWord(true);
        txtAprendices.setBackground(new Color(210, 210, 210));
        txtAprendices.setForeground(NEGRO_ELITE);
        txtAprendices.setBorder(new EmptyBorder(5, 8, 5, 8));

        JScrollPane scrollApr = new JScrollPane(txtAprendices);
        scrollApr.setBorder(new LineBorder(NEGRO_ELITE, 1));
        scrollApr.setBounds(160, y, 265, 55);
        contentPane.add(scrollApr);
        
        btnVolver = new JButton("CERRAR");
        btnVolver.setFont(new Font("Impact", Font.PLAIN, 14));
        btnVolver.setBackground(NEGRO_ELITE);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBounds(315, 435, 110, 30);
        contentPane.add(btnVolver);
    }

    /**
     *
     * @param p panel donde se añadirán los componentes
     * @param etiqueta texto descriptivo del campo (label izquierdo)
     * @param x coordenada X donde se posicionará el conjunto
     * @param y coordenada Y donde se posicionará el conjunto
     * @return JLabel que contiene el valor del campo (puede actualizarse dinámicamente)
     */
    private JLabel addField(JPanel p, String etiqueta, int x, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl.setBounds(x, y, 130, 22);
        p.add(lbl);

        JLabel val = new JLabel("—");
        val.setFont(new Font("Monospaced", Font.BOLD, 13));
        val.setBounds(x + 140, y, 265, 26);
        val.setOpaque(true);
        val.setBackground(Color.WHITE);
        val.setForeground(ROJO_HEROE);
        val.setBorder(new CompoundBorder(
            new LineBorder(NEGRO_ELITE, 1),
            new EmptyBorder(0, 10, 0, 0)));
        p.add(val);
        return val;
    }

    // Setters (Mantenidos)
    /**
     * 
     * @param v
     */
    public void setFecha(String v) {
    	lblFechaVal.setText(v); 
    }
    
    /**
     * 
     * @param v
     */
    public void setHora(String v) {
    	lblHoraVal.setText(v); 
    }
    
    /**
     * 
     * @param v
     */
    public void setDuracion(String v) {
    	lblDuracionVal.setText(v); 
    }
    
    /**
     * 
     * @param v
     */
    public void setCliente(String v) {
    	lblClienteVal.setText(v); 
    }
    
    /**
     * 
     * @param v
     */
    public void setTraje(String v) {
    	lblTrajeVal.setText(v); 
    }
    
    /**
     * 
     * @param v
     */
    public void setTaller(String v) {
    	lblTallerVal.setText(v); 
    }
    
    /**
     * 
     * @param v
     */
    public void setOficial(String v) {
    	lblOficialVal.setText(v); 
    }


    /**
     * 
     * @param v
     * Acepta una cadena con los aprendices separados por "  " o "\n".
     * Normaliza a líneas para mostrarlas en el JTextArea.
     */
    public void setAprendices(String v) {
        if (v == null || v.isBlank() || v.equals("—")) {
            txtAprendices.setText("—");
        } else {
            String texto = v.trim().replace("  ", "\n").replace("  ", "\n");
            txtAprendices.setText(texto);
        }
    }

    public JButton getBtnVolver() { 
    	return btnVolver; 
    }
}