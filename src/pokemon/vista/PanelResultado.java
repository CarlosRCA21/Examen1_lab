package pokemon.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import pokemon.MotorBatalla;
import pokemon.Entrenador;
import pokemon.EstadoBatalla;
import pokemon.Pokemon;

public class PanelResultado extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JPanel banner = new JPanel(new GridLayout(2, 1, 0, 4));
    private final JLabel etiquetaTitulo = Estilo.crearTitulo(" ", 44, Color.WHITE);
    private final JLabel etiquetaDetalle = Estilo.crearTitulo(" ", 18, Color.WHITE);
    private final JLabel etiquetaRondas = Estilo.crearTitulo(" ", 20, Estilo.AZUL_OSCURO);
    private final JTextArea areaJugador = new JTextArea(10, 24);
    private final JTextArea areaRival = new JTextArea(10, 24);
    private final JScrollPane scrollJugador = new JScrollPane(areaJugador);
    private final JScrollPane scrollRival = new JScrollPane(areaRival);
    private final JButton botonNueva = Estilo.crearBoton("Nueva partida", Estilo.VERDE);
    private final JButton botonSalir = Estilo.crearBoton("Salir", Estilo.ROJO);

    public PanelResultado(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 15));
        setBackground(Estilo.FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        banner.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));
        banner.add(etiquetaTitulo);
        banner.add(etiquetaDetalle);

        configurarArea(areaJugador);
        configurarArea(areaRival);

        JPanel equipos = new JPanel(new GridLayout(1, 2, 20, 0));
        equipos.setOpaque(false);
        equipos.add(scrollJugador);
        equipos.add(scrollRival);

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setOpaque(false);
        centro.add(etiquetaRondas, BorderLayout.NORTH);
        centro.add(equipos, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botones.setOpaque(false);
        botones.add(botonNueva);
        botones.add(botonSalir);

        add(banner, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        botonNueva.addActionListener(this);
        botonSalir.addActionListener(this);
    }

    private void configurarArea(JTextArea area) {
        area.setEditable(false);
        area.setFocusable(false);
        area.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        area.setMargin(new Insets(8, 10, 8, 10));
    }

    public void mostrar(MotorBatalla motor) {
        EstadoBatalla estado = motor.getEstado();
        String nombre = motor.getJugador().getNombre();
        String rival = motor.getRival().getNombre();

        Color color = Estilo.AZUL;
        String titulo = "EMPATE";
        String detalle = "La batalla terminó empatada, " + nombre + ".";
        if (estado == EstadoBatalla.VICTORIA) {
            color = Estilo.VERDE;
            titulo = "¡VICTORIA!";
            detalle = nombre + ", ganaste la batalla contra " + rival + ".";
        } else if (estado == EstadoBatalla.DERROTA) {
            color = Estilo.ROJO;
            titulo = "DERROTA";
            detalle = nombre + ", perdiste la batalla contra " + rival + ".";
        }
        banner.setBackground(color);
        etiquetaTitulo.setText(titulo);
        etiquetaDetalle.setText(detalle);
        etiquetaRondas.setText("Rondas jugadas: " + motor.getNumeroRonda());

        scrollJugador.setBorder(BorderFactory.createTitledBorder("Equipo de " + nombre));
        scrollRival.setBorder(BorderFactory.createTitledBorder("Equipo de " + rival));
        areaJugador.setText(resumirEquipo(motor.getJugador()));
        areaRival.setText(resumirEquipo(motor.getRival()));
    }

    private String resumirEquipo(Entrenador entrenador) {
        List<Pokemon> equipo = entrenador.getEquipo();
        int debilitados = 0;
        int psTotales = 0;
        String enPie = "";
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon pokemon = equipo.get(i);
            if (pokemon.estaDebilitado()) {
                debilitados = debilitados + 1;
            } else {
                enPie = enPie + "   - " + pokemon.getNombre() + " (PS " + pokemon.getPsActuales()
                        + "/" + pokemon.getPsMaximos() + ")\n";
            }
            psTotales = psTotales + pokemon.getPsActuales();
        }
        if (enPie.length() == 0) {
            enPie = "   (ninguno)\n";
        }
        return "Debilitados: " + debilitados + " de " + equipo.size() + "\n"
                + "En pie: " + (equipo.size() - debilitados) + "\n"
                + enPie
                + "PS restantes en total: " + psTotales;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonNueva) {
            ventana.nuevaPartida();
        } else if (e.getSource() == botonSalir) {
            ventana.confirmarSalida();
        }
    }
}
