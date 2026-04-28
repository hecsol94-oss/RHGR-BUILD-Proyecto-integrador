package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * Detalle de cita.
 * El campo "Aprendices" ocupa dos líneas de altura para mostrar varios nombres.
 */
public class DetalleCita extends JFrame {

    private JLabel lblFechaVal, lblHoraVal, lblDuracionVal;
    private JLabel lblClienteVal, lblTrajeVal, lblTallerVal;
    private JLabel lblOficialVal;
    private JTextArea txtAprendices;   // ← cambiado a JTextArea para múltiples nombres
    private JButton btnVolver;

    public DetalleCita() {
        setTitle("Detalle de la Cita");
        setBounds(150, 150, 440, 430);
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel("INFORMACIÓN DE LA CITA");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        titulo.setBounds(20, 10, 300, 25);
        getContentPane().add(titulo);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 38, 395, 2);
        getContentPane().add(sep);

        int y = 50, dy = 32;
        lblFechaVal    = addField("Fecha:",        20, y); y += dy;
        lblHoraVal     = addField("Hora inicio:",  20, y); y += dy;
        lblDuracionVal = addField("Duración (h):", 20, y); y += dy;
        lblClienteVal  = addField("Cliente:",      20, y); y += dy;
        lblTrajeVal    = addField("Traje:",         20, y); y += dy;
        lblTallerVal   = addField("Taller:",        20, y); y += dy;
        lblOficialVal  = addField("Oficial:",       20, y); y += dy;

        // Etiqueta fija "Aprendices:"
        JLabel lblAprEtiq = new JLabel("Aprendices:");
        lblAprEtiq.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblAprEtiq.setBounds(20, y, 130, 22);
        getContentPane().add(lblAprEtiq);

        // JTextArea no editable para mostrar uno o varios aprendices en líneas separadas
        txtAprendices = new JTextArea("—");
        txtAprendices.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtAprendices.setEditable(false);
        txtAprendices.setLineWrap(true);
        txtAprendices.setWrapStyleWord(true);
        txtAprendices.setBackground(new Color(248, 248, 248));
        txtAprendices.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(2, 4, 2, 4)));

        JScrollPane scrollApr = new JScrollPane(txtAprendices);
        scrollApr.setBorder(null);
        scrollApr.setBounds(155, y, 260, 48);
        getContentPane().add(scrollApr);
        y += 58;

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(300, y + 5, 110, 30);
        getContentPane().add(btnVolver);
    }

    private JLabel addField(String etiqueta, int x, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl.setBounds(x, y, 130, 22);
        getContentPane().add(lbl);

        JLabel val = new JLabel("—");
        val.setFont(new Font("Tahoma", Font.PLAIN, 11));
        val.setBounds(x + 135, y, 255, 22);
        val.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(0, 4, 0, 0)));
        val.setOpaque(true);
        val.setBackground(new Color(248, 248, 248));
        getContentPane().add(val);
        return val;
    }

    public void setFecha(String v)      {
    	lblFechaVal.setText(v); 
    	}
    
    public void setHora(String v)       {
    	lblHoraVal.setText(v); 
    	}
    
    public void setDuracion(String v)   {
    	lblDuracionVal.setText(v); 
    	}
    
    public void setCliente(String v)    {
    	lblClienteVal.setText(v); 
    	}
    
    public void setTraje(String v)      {
    	lblTrajeVal.setText(v); 
    	}
    
    public void setTaller(String v)     {
    	lblTallerVal.setText(v); 
    	}
    
    public void setOficial(String v)    {
    	lblOficialVal.setText(v); 
    	}

    /**
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