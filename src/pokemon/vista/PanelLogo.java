package pokemon.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import pokemon.modelo.TipoPokemon;

public class PanelLogo extends JPanel {

    public PanelLogo() {
        setOpaque(false);
        setPreferredSize(new Dimension(160, 160));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int lado = Math.min(getWidth(), getHeight()) - 10;
        int x = (getWidth() - lado) / 2;
        int y = (getHeight() - lado) / 2;
        int centroX = x + lado / 2;
        int centroY = y + lado / 2;

        g2.setColor(Estilo.colorTipo(TipoPokemon.ELECTRICO));
        g2.fillArc(x, y, lado, lado, 0, 90);
        g2.setColor(Estilo.colorTipo(TipoPokemon.FUEGO));
        g2.fillArc(x, y, lado, lado, 90, 90);
        g2.setColor(Estilo.colorTipo(TipoPokemon.PLANTA));
        g2.fillArc(x, y, lado, lado, 180, 90);
        g2.setColor(Estilo.colorTipo(TipoPokemon.AGUA));
        g2.fillArc(x, y, lado, lado, 270, 90);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(8));
        g2.drawLine(centroX, y, centroX, y + lado);
        g2.drawLine(x, centroY, x + lado, centroY);

        g2.setColor(Estilo.AZUL_OSCURO);
        g2.setStroke(new BasicStroke(6));
        g2.drawOval(x, y, lado, lado);

        int radio = lado / 4;
        g2.setColor(Color.WHITE);
        g2.fillOval(centroX - radio, centroY - radio, radio * 2, radio * 2);
        g2.setColor(Estilo.AZUL_OSCURO);
        g2.drawOval(centroX - radio, centroY - radio, radio * 2, radio * 2);
        int punto = lado / 10;
        g2.fillOval(centroX - punto, centroY - punto, punto * 2, punto * 2);
        g2.dispose();
    }
}
