package pokemon.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import pokemon.control.MotorBatalla;
import pokemon.AccionBatalla;
import pokemon.EstadoBatalla;
import pokemon.Pokemon;

public class PanelBatalla extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private MotorBatalla motor = null;

    private final JLabel etiquetaEntrenador = new JLabel(" ");
    private final JLabel etiquetaRonda = new JLabel(" ", JLabel.CENTER);
    private final JLabel etiquetaRival = new JLabel(" ", JLabel.RIGHT);
    private final JLabel etiquetaTurno = new JLabel(" ", JLabel.CENTER);
    private final JLabel etiquetaMensaje = new JLabel(" ", JLabel.CENTER);
    private final TarjetaPokemon tarjetaJugador = new TarjetaPokemon("Tu Pokémon");
    private final TarjetaPokemon tarjetaRival = new TarjetaPokemon("Pokémon rival");
    private final JTextArea areaMensajes = new JTextArea(8, 40);
    private final DefaultListModel<String> modeloEquipo = new DefaultListModel<String>();
    private final JList<String> listaEquipo = new JList<String>(modeloEquipo);
    private final JTextArea areaRival = new JTextArea();

    private final JButton botonAtacar = Estilo.crearBoton("Atacar", Estilo.ROJO);
    private final JButton botonHabilidad = Estilo.crearBoton("Habilidad especial", Estilo.MORADO);
    private final JButton botonRecuperar = Estilo.crearBoton("Recuperar", Estilo.VERDE);
    private final JButton botonCambiar = Estilo.crearBoton("Cambiar Pokémon", Estilo.AZUL);
    private final JButton botonReemplazo = Estilo.crearBoton("Enviar reemplazo", Estilo.NARANJA);
    private final JButton botonResultado = Estilo.crearBoton("Ver resultado", Estilo.AZUL_OSCURO);

    public PanelBatalla(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 8));
        setBackground(Estilo.FONDO);
        setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        etiquetaEntrenador.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        etiquetaEntrenador.setForeground(Color.WHITE);
        etiquetaRonda.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        etiquetaRonda.setForeground(Color.WHITE);
        etiquetaRival.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        etiquetaRival.setForeground(Color.WHITE);
        JPanel barra = new JPanel(new GridLayout(1, 3));
        barra.setBackground(Estilo.AZUL_OSCURO);
        barra.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        barra.add(etiquetaEntrenador);
        barra.add(etiquetaRonda);
        barra.add(etiquetaRival);

        etiquetaTurno.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        etiquetaTurno.setForeground(Estilo.AZUL_OSCURO);
        JPanel norte = new JPanel(new BorderLayout(0, 6));
        norte.setOpaque(false);
        norte.add(barra, BorderLayout.NORTH);
        norte.add(etiquetaTurno, BorderLayout.SOUTH);

        JPanel tarjetas = new JPanel(new GridLayout(1, 2, 12, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(tarjetaJugador);
        tarjetas.add(tarjetaRival);

        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        JScrollPane scrollMensajes = new JScrollPane(areaMensajes);
        scrollMensajes.setBorder(BorderFactory.createTitledBorder("Mensajes de la batalla"));

        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setOpaque(false);
        centro.add(tarjetas, BorderLayout.NORTH);
        centro.add(scrollMensajes, BorderLayout.CENTER);

        listaEquipo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEquipo.setFixedCellHeight(28);
        areaRival.setEditable(false);
        areaRival.setFocusable(false);
        areaRival.setMargin(new Insets(4, 6, 4, 6));
        JScrollPane scrollEquipo = new JScrollPane(listaEquipo);
        scrollEquipo.setBorder(BorderFactory.createTitledBorder("Tu equipo (elige para cambiar)"));
        JScrollPane scrollRival = new JScrollPane(areaRival);
        scrollRival.setBorder(BorderFactory.createTitledBorder("Equipo rival"));
        JPanel este = new JPanel(new GridLayout(2, 1, 0, 8));
        este.setOpaque(false);
        este.setPreferredSize(new Dimension(330, 100));
        este.add(scrollEquipo);
        este.add(scrollRival);

        etiquetaMensaje.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botones.setOpaque(false);
        botones.add(botonAtacar);
        botones.add(botonHabilidad);
        botones.add(botonRecuperar);
        botones.add(botonCambiar);
        botones.add(botonReemplazo);
        botones.add(botonResultado);
        JPanel sur = new JPanel(new BorderLayout(0, 6));
        sur.setOpaque(false);
        sur.add(etiquetaMensaje, BorderLayout.NORTH);
        sur.add(botones, BorderLayout.CENTER);

        add(norte, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(este, BorderLayout.EAST);
        add(sur, BorderLayout.SOUTH);

        botonAtacar.setToolTipText("Ataque básico (potencia 10)");
        botonHabilidad.setToolTipText("Ataque con la habilidad especial del Pokémon");
        botonRecuperar.setToolTipText("Recupera el 25% de los PS máximos");
        botonCambiar.setToolTipText("Primero selecciona un Pokémon en Tu equipo");
        botonReemplazo.setToolTipText("Selecciona en Tu equipo al Pokémon que entra en combate");

        botonAtacar.addActionListener(this);
        botonHabilidad.addActionListener(this);
        botonRecuperar.addActionListener(this);
        botonCambiar.addActionListener(this);
        botonReemplazo.addActionListener(this);
        botonResultado.addActionListener(this);
    }

    public void iniciar(MotorBatalla motor) {
        this.motor = motor;
        areaMensajes.setText("");
        etiquetaEntrenador.setText("Entrenador: " + motor.getJugador().getNombre());
        etiquetaRival.setText(motor.getRival().getNombre());
        Estilo.limpiarMensaje(etiquetaMensaje);
        agregarLinea("¡Comienza la batalla! " + motor.getJugador().getNombre() + " vs "
                + motor.getRival().getNombre() + ".");
        refrescar();
    }

    private void refrescar() {
        Pokemon miActivo = motor.getActivoJugador();
        Pokemon rivalActivo = motor.getActivoRival();
        tarjetaJugador.mostrar(miActivo);
        tarjetaRival.mostrar(rivalActivo);
        etiquetaRonda.setText("RONDA " + motor.getNumeroRonda());

        llenarLista(motor.getJugador().getEquipo(), miActivo);
        llenarAreaRival(motor.getRival().getEquipo(), rivalActivo);

        boolean atacar = false;
        boolean habilidad = false;
        boolean recuperar = false;
        boolean cambiar = false;
        boolean reemplazo = false;
        boolean terminada = false;
        String turno = "";

        EstadoBatalla estado = motor.getEstado();
        switch (estado) {
            case ESPERANDO_ACCION:
                atacar = motor.esAccionValida(AccionBatalla.ATACAR);
                habilidad = motor.esAccionValida(AccionBatalla.HABILIDAD_ESPECIAL);
                recuperar = motor.esAccionValida(AccionBatalla.RECUPERAR);
                cambiar = motor.esAccionValida(AccionBatalla.CAMBIAR_POKEMON);
                turno = "Turno de " + motor.getJugador().getNombre() + ": elige una acción";
                break;
            case ELEGIR_REEMPLAZO:
                reemplazo = true;
                turno = "Tu Pokémon fue debilitado: selecciona en Tu equipo a su reemplazo";
                break;
            case VICTORIA:
            case DERROTA:
            case EMPATE:
                terminada = true;
                turno = "La batalla terminó. Pulsa «Ver resultado»";
                break;
            default:
                turno = "Resolviendo ronda...";
                break;
        }
        etiquetaTurno.setText(turno);

        botonHabilidad.setText("Habilidad especial (" + miActivo.getUsosHabilidadEspecial() + ")");
        botonRecuperar.setText("Recuperar (" + miActivo.getUsosRecuperar() + ")");
        Estilo.ponerEstado(botonAtacar, atacar, Estilo.ROJO);
        Estilo.ponerEstado(botonHabilidad, habilidad, Estilo.MORADO);
        Estilo.ponerEstado(botonRecuperar, recuperar, Estilo.VERDE);
        Estilo.ponerEstado(botonCambiar, cambiar, Estilo.AZUL);
        Estilo.ponerEstado(botonReemplazo, reemplazo, Estilo.NARANJA);
        Estilo.ponerEstado(botonResultado, terminada, Estilo.AZUL_OSCURO);
    }

    private void llenarLista(List<Pokemon> equipo, Pokemon activo) {
        modeloEquipo.clear();
        for (int i = 0; i < equipo.size(); i++) {
            modeloEquipo.addElement(describir(i, equipo.get(i), activo));
        }
    }

    private void llenarAreaRival(List<Pokemon> equipo, Pokemon activo) {
        String texto = "";
        for (int i = 0; i < equipo.size(); i++) {
            texto = texto + describir(i, equipo.get(i), activo) + "\n";
        }
        areaRival.setText(texto);
    }

    private String describir(int posicion, Pokemon pokemon, Pokemon activo) {
        String texto = (posicion + 1) + ". " + pokemon.getNombre() + "  PS " + pokemon.getPsActuales()
                + "/" + pokemon.getPsMaximos();
        if (pokemon.estaDebilitado()) {
            texto = texto + "  (debilitado)";
        } else if (pokemon == activo) {
            texto = texto + "  (en combate)";
        }
        return texto;
    }

    private void agregarLinea(String texto) {
        areaMensajes.append(texto + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }

    private void agregarMensajes(List<String> mensajes) {
        for (int i = 0; i < mensajes.size(); i++) {
            agregarLinea(mensajes.get(i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAtacar) {
            jugar(AccionBatalla.ATACAR, -1);
        } else if (e.getSource() == botonHabilidad) {
            jugar(AccionBatalla.HABILIDAD_ESPECIAL, -1);
        } else if (e.getSource() == botonRecuperar) {
            jugar(AccionBatalla.RECUPERAR, -1);
        } else if (e.getSource() == botonCambiar) {
            cambiar();
        } else if (e.getSource() == botonReemplazo) {
            reemplazar();
        } else if (e.getSource() == botonResultado) {
            ventana.mostrarResultado();
        }
    }

    private void cambiar() {
        int indice = listaEquipo.getSelectedIndex();
        if (indice < 0) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Selecciona en Tu equipo el Pokémon al que quieres cambiar.", true);
            return;
        }
        jugar(AccionBatalla.CAMBIAR_POKEMON, indice);
    }

    private void jugar(AccionBatalla accion, int indice) {
        int ronda = motor.getNumeroRonda();
        try {
            List<String> mensajes = motor.jugarRonda(accion, indice);
            agregarLinea("--- Ronda " + ronda + " ---");
            agregarMensajes(mensajes);
            Estilo.limpiarMensaje(etiquetaMensaje);
        } catch (IllegalArgumentException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        } catch (IllegalStateException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        }
        refrescar();
    }

    private void reemplazar() {
        int indice = listaEquipo.getSelectedIndex();
        if (indice < 0) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Selecciona en Tu equipo el Pokémon que entra en combate.", true);
            return;
        }
        try {
            List<String> mensajes = motor.elegirReemplazo(indice);
            agregarMensajes(mensajes);
            Estilo.limpiarMensaje(etiquetaMensaje);
        } catch (IllegalArgumentException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        } catch (IllegalStateException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        }
        refrescar();
    }
}
