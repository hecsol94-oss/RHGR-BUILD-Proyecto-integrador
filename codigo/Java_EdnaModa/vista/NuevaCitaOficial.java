package vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * NuevaCitaOficial — punto 10: mismo proceso que Maestro (dos fases).
 * Esta clase queda para compatibilidad; el controlador puede reutilizar NuevaCitaMaestro.
 * Se elimina el JTextArea y se muestra el resumen con campos etiquetados (punto 8).
 */
public class NuevaCitaOficial extends JFrame {

    private JLabel lblFechaVal, lblHoraVal, lblDuracionVal;
    private JLabel lblClienteVal, lblTrajeVal, lblTallerVal, lblOficialVal;
    private JComboBox<String> cbAprendiz1;
    private JComboBox<String> cbAprendiz2;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public NuevaCitaOficial() {
        setTitle("Nueva Cita — Fase 2");
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel("DATOS DE LA CITA");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        titulo.setBounds(20, 10, 280, 25);
        getContentPane().add(titulo);

        JLabel sub = new JLabel("Te han asignado a una nueva cita");
        sub.setFont(new Font("Tahoma", Font.ITALIC, 11));
        sub.setBounds(20, 32, 280, 16);
        getContentPane().add(sub);

        int y = 58, dy = 28;
        lblFechaVal = addField("Fecha:", y); y += dy;
        lblHoraVal = addField("Hora:", y); y += dy;
        lblDuracionVal = addField("Duración:", y); y += dy;
        lblClienteVal = addField("Cliente:", y); y += dy;
        lblTrajeVal = addField("Traje:", y); y += dy;
        lblTallerVal = addField("Taller:", y); y += dy;
        lblOficialVal = addField("Oficial:", y); y += dy + 10;

        JSeparator sep = new JSeparator(); sep.setBounds(20, y, 405, 2); getContentPane().add(sep); y += 10;

        JLabel lblApr = new JLabel("APRENDICES (opcional)");
        lblApr.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblApr.setBounds(20, y, 220, 18);
        getContentPane().add(lblApr);
        y += 24;

        getContentPane().add(lbl("Aprendiz 1:", 20, y));
        cbAprendiz1 = new JComboBox<>();
        cbAprendiz1.setBounds(120, y, 295, 25);
        getContentPane().add(cbAprendiz1);
        y += 32;

        getContentPane().add(lbl("Aprendiz 2:", 20, y));
        cbAprendiz2 = new JComboBox<>();
        cbAprendiz2.setBounds(120, y, 295, 25);
        getContentPane().add(cbAprendiz2);
        y += 45;

        btnGuardar = new JButton("Guardar Cita");
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnGuardar.setBounds(240, y, 160, 35);
        getContentPane().add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(60, y, 140, 35);
        getContentPane().add(btnCancelar);
    }

    private JLabel lbl(String t, int x, int y) { JLabel l = new JLabel(t); l.setBounds(x,y,100,22); return l; }

    private JLabel addField(String etiqueta, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl.setBounds(20, y, 100, 22);
        getContentPane().add(lbl);
        JLabel val = new JLabel("—");
        val.setFont(new Font("Tahoma", Font.PLAIN, 11));
        val.setBounds(125, y, 300, 22);
        val.setBorder(new CompoundBorder(new LineBorder(new Color(200,200,200)), new EmptyBorder(0,4,0,0)));
        val.setOpaque(true); val.setBackground(new Color(248,248,248));
        getContentPane().add(val);
        return val;
    }

    public void setFecha(String v) {
    	lblFechaVal.setText(v);
    }
    public void setHora(String v) {
    	lblHoraVal.setText(v);
    }
    public void setDuracion(String v) {
    	lblDuracionVal.setText(v);
    }
    public void setCliente(String v) {
    	lblClienteVal.setText(v);
    }
    public void setTraje(String v) {
    	lblTrajeVal.setText(v);
    }
    public void setTaller(String v) {
    	lblTallerVal.setText(v);
    }
    public void setOficial(String v) {
    	lblOficialVal.setText(v);
    }

    public JComboBox<String> getCbAprendiz1() {
    	return cbAprendiz1;
    }
    public JComboBox<String> getCbAprendiz2() {
    	return cbAprendiz2;
    }
    public JButton getBtnGuardar() {
    	return btnGuardar;
    }
    public JButton getBtnCancelar() {
    	return btnCancelar;
    }
    // Legacy compat
    public JTextArea getTxtDetalles() {
    	return null;
    }
}
