package pokemon.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import pokemon.Pokemon;

public class TarjetaPokemon extends JPanel {

    private final String titulo;
    private final PanelAvatar avatar = new PanelAvatar();
    private final JLabel etiquetaNombre = new JLabel(" ");
    private final JLabel etiquetaTipo = new JLabel(" ");
    private final JProgressBar barraPs = new JProgressBar();
    private final JLabel etiquetaStats = new JLabel(" ");
    private final JTextArea areaHabilidad = new JTextArea(2, 18);
    private final JLabel etiquetaUsos = new JLabel(" ");

    public TarjetaPokemon(String titulo) {
        this.titulo = titulo;
        setLayout(new BorderLayout(12, 0));
        setBackground(Color.WHITE);

        etiquetaNombre.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        etiquetaNombre.setForeground(Estilo.TEXTO);
        etiquetaTipo.setForeground(Estilo.TEXTO);
        etiquetaStats.setForeground(Estilo.TEXTO);
        etiquetaUsos.setForeground(Estilo.TEXTO);

        barraPs.setMinimum(0);
        barraPs.setStringPainted(true);

        areaHabilidad.setEditable(false);
        areaHabilidad.setFocusable(false);
        areaHabilidad.setLineWrap(true);
        areaHabilidad.setWrapStyleWord(true);
        areaHabilidad.setOpaque(false);
        areaHabilidad.setForeground(Estilo.TEXTO);

        JPanel izquierda = new JPanel(new BorderLayout());
        izquierda.setOpaque(false);
        izquierda.add(avatar, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridLayout(4, 1, 0, 4));
        datos.setOpaque(false);
        datos.add(etiquetaNombre);
        datos.add(etiquetaTipo);
        datos.add(barraPs);
        datos.add(etiquetaStats);

        JPanel centro = new JPanel(new BorderLayout(0, 4));
        centro.setOpaque(false);
        centro.add(datos, BorderLayout.NORTH);
        centro.add(areaHabilidad, BorderLayout.CENTER);

        add(izquierda, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
        add(etiquetaUsos, BorderLayout.SOUTH);
        limpiar();
    }

    public void mostrar(Pokemon pokemon) {
        Color color = Estilo.colorTipo(pokemon.getTipo());
        if (pokemon.estaDebilitado()) {
            color = Estilo.GRIS;
        }
        ponerBorde(color);
        avatar.mostrar(pokemon);

        String nombre = pokemon.getNombre();
        if (pokemon.estaDebilitado()) {
            nombre = nombre + " (debilitado)";
        }
        etiquetaNombre.setText(nombre);
        etiquetaTipo.setText("Tipo " + Estilo.nombreTipo(pokemon.getTipo()) + "   |   Nivel " + pokemon.getNivel());

        barraPs.setMaximum(pokemon.getPsMaximos());
        barraPs.setValue(pokemon.getPsActuales());
        barraPs.setString("PS " + pokemon.getPsActuales() + " / " + pokemon.getPsMaximos());
        barraPs.setForeground(colorBarra(pokemon));

        etiquetaStats.setText("ATQ " + pokemon.getAtaque() + "   DEF " + pokemon.getDefensa()
                + "   VEL " + pokemon.getVelocidad());
        areaHabilidad.setText("Habilidad: " + pokemon.getDescripcionHabilidad());
        etiquetaUsos.setText("Usos restantes:  habilidad " + pokemon.getUsosHabilidadEspecial()
                + "  |  recuperar " + pokemon.getUsosRecuperar());
    }

    public void limpiar() {
        ponerBorde(Estilo.GRIS_CLARO);
        avatar.limpiar();
        etiquetaNombre.setText("Sin Pokémon");
        etiquetaTipo.setText(" ");
        barraPs.setMaximum(1);
        barraPs.setValue(0);
        barraPs.setString("PS -");
        etiquetaStats.setText(" ");
        areaHabilidad.setText("");
        etiquetaUsos.setText(" ");
    }

    private void ponerBorde(Color color) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color, 3), titulo),
                BorderFactory.createEmptyBorder(6, 8, 8, 8)));
    }

    private Color colorBarra(Pokemon pokemon) {
        int porcentaje = 0;
        if (pokemon.getPsMaximos() > 0) {
            porcentaje = pokemon.getPsActuales() * 100 / pokemon.getPsMaximos();
        }
        Color color = Estilo.VERDE;
        if (porcentaje <= 25) {
            color = Estilo.ROJO;
        } else if (porcentaje <= 50) {
            color = Estilo.AMARILLO;
        }
        return color;
    }
}
