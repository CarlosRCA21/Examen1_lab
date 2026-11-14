package pokemon.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import pokemon.Pokemon;

public class PanelAvatar extends JPanel {

    private Color colorFondo = Estilo.GRIS_CLARO;
    private String letra = "?";

    public PanelAvatar() {
        setOpaque(false);
        setPreferredSize(new Dimension(90, 90));
    }

    public void mostrar(Pokemon pokemon) {
        colorFondo = Estilo.colorTipo(pokemon.getTipo());
        if (pokemon.estaDebilitado()) {
            colorFondo = Estilo.GRIS;
        }
        letra = pokemon.getNombre().substring(0, 1).toUpperCase();
        repaint();
    }

    public void limpiar() {
        colorFondo = Estilo.GRIS_CLARO;
        letra = "?";
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int lado = Math.min(getWidth(), getHeight()) - 8;
        int x = (getWidth() - lado) / 2;
        int y = (getHeight() - lado) / 2;
        g2.setColor(colorFondo);
        g2.fillOval(x, y, lado, lado);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawOval(x + 6, y + 6, lado - 12, lado - 12);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, lado / 2));
        FontMetrics medidas = g2.getFontMetrics();
        int xLetra = x + (lado - medidas.stringWidth(letra)) / 2;
        int yLetra = y + (lado - medidas.getHeight()) / 2 + medidas.getAscent();
        g2.drawString(letra, xLetra, yLetra);
        g2.dispose();
    }
}
