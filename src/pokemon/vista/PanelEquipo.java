package pokemon.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pokemon.Entrenador;
import pokemon.Pokemon;

public class PanelEquipo extends JPanel implements ActionListener, ListSelectionListener {

    private static final int MAXIMO_EQUIPO = 6;

    private final VentanaPrincipal ventana;
    private final DefaultListModel<String> modeloCatalogo = new DefaultListModel<String>();
    private final DefaultListModel<String> modeloEquipo = new DefaultListModel<String>();
    private final JList<String> listaCatalogo = new JList<String>(modeloCatalogo);
    private final JList<String> listaEquipo = new JList<String>(modeloEquipo);
    private final TarjetaPokemon tarjeta = new TarjetaPokemon("Ficha del Pokémon");
    private final JLabel etiquetaEntrenador = new JLabel(" ");
    private final JLabel etiquetaCantidad = new JLabel(" ");
    private final JLabel etiquetaMensaje = new JLabel(" ", JLabel.CENTER);
    private final JPanel panelCatalogo = new JPanel(new BorderLayout(0, 8));
    private final JButton botonAgregar = Estilo.crearBoton("Agregar al equipo  >>", Estilo.VERDE);
    private final JButton botonQuitar = Estilo.crearBoton("<<  Quitar del equipo", Estilo.ROJO);
    private final JButton botonGuardar = Estilo.crearBoton("Guardar equipo", Estilo.AZUL);
    private final JButton botonRestaurar = Estilo.crearBoton("Restaurar equipo", Estilo.MORADO);
    private final JButton botonBatalla = Estilo.crearBoton("Comenzar batalla", Estilo.VERDE);

    public PanelEquipo(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 10));
        setBackground(Estilo.FONDO);
        setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        etiquetaEntrenador.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        etiquetaEntrenador.setForeground(Color.WHITE);
        etiquetaCantidad.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        etiquetaCantidad.setForeground(Color.WHITE);
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(Estilo.AZUL_OSCURO);
        barra.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        barra.add(etiquetaEntrenador, BorderLayout.WEST);
        barra.add(etiquetaCantidad, BorderLayout.EAST);

        listaCatalogo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCatalogo.setFixedCellHeight(30);
        listaCatalogo.addListSelectionListener(this);
        listaEquipo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEquipo.setFixedCellHeight(30);
        listaEquipo.addListSelectionListener(this);

        panelCatalogo.setOpaque(false);
        panelCatalogo.add(new JScrollPane(listaCatalogo), BorderLayout.CENTER);
        panelCatalogo.add(botonAgregar, BorderLayout.SOUTH);

        JPanel panelEquipo = new JPanel(new BorderLayout(0, 8));
        panelEquipo.setOpaque(false);
        panelEquipo.setBorder(BorderFactory.createTitledBorder("Mi equipo (el primero sale a combatir)"));
        panelEquipo.add(new JScrollPane(listaEquipo), BorderLayout.CENTER);
        panelEquipo.add(botonQuitar, BorderLayout.SOUTH);

        JTextArea ayuda = new JTextArea("Selecciona un Pokémon de cualquier lista para ver su ficha. "
                + "Puedes tener el mismo Pokémon hasta 2 veces y el equipo debe tener entre 1 y "
                + MAXIMO_EQUIPO + " integrantes.");
        ayuda.setEditable(false);
        ayuda.setFocusable(false);
        ayuda.setLineWrap(true);
        ayuda.setWrapStyleWord(true);
        ayuda.setOpaque(false);
        ayuda.setForeground(Estilo.TEXTO);

        JPanel panelFicha = new JPanel(new BorderLayout(0, 10));
        panelFicha.setOpaque(false);
        panelFicha.add(tarjeta, BorderLayout.NORTH);
        panelFicha.add(ayuda, BorderLayout.CENTER);

        JPanel centro = new JPanel(new GridLayout(1, 3, 15, 0));
        centro.setOpaque(false);
        centro.add(panelCatalogo);
        centro.add(panelFicha);
        centro.add(panelEquipo);

        etiquetaMensaje.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        botones.setOpaque(false);
        botones.add(botonGuardar);
        botones.add(botonRestaurar);
        botones.add(botonBatalla);

        JPanel sur = new JPanel(new BorderLayout(0, 6));
        sur.setOpaque(false);
        sur.add(etiquetaMensaje, BorderLayout.NORTH);
        sur.add(botones, BorderLayout.CENTER);

        add(barra, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        botonAgregar.addActionListener(this);
        botonQuitar.addActionListener(this);
        botonGuardar.addActionListener(this);
        botonRestaurar.addActionListener(this);
        botonBatalla.addActionListener(this);
    }

    public void actualizar() {
        List<Pokemon> catalogo = ventana.getCatalogo();
        modeloCatalogo.clear();
        for (int i = 0; i < catalogo.size(); i++) {
            Pokemon pokemon = catalogo.get(i);
            modeloCatalogo.addElement(pokemon.getNombre() + "   -   " + Estilo.nombreTipo(pokemon.getTipo()));
        }
        panelCatalogo.setBorder(BorderFactory.createTitledBorder("Catálogo (" + catalogo.size() + " Pokémon)"));
        tarjeta.limpiar();
        Estilo.limpiarMensaje(etiquetaMensaje);
        refrescarEquipo();
    }

    private void refrescarEquipo() {
        Entrenador entrenador = ventana.getEntrenador();
        List<Pokemon> equipo = entrenador.getEquipo();
        modeloEquipo.clear();
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon pokemon = equipo.get(i);
            modeloEquipo.addElement((i + 1) + ".  " + pokemon.getNombre() + "   -   "
                    + Estilo.nombreTipo(pokemon.getTipo()) + "  (Nv " + pokemon.getNivel() + ")");
        }
        etiquetaEntrenador.setText("Entrenador: " + entrenador.getNombre());
        etiquetaCantidad.setText("Equipo: " + equipo.size() + "/" + MAXIMO_EQUIPO);
        Estilo.ponerEstado(botonBatalla, equipo.size() >= 1, Estilo.VERDE);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        if (e.getSource() == listaCatalogo) {
            int indice = listaCatalogo.getSelectedIndex();
            if (indice >= 0) {
                listaEquipo.clearSelection();
                tarjeta.mostrar(ventana.getCatalogo().get(indice));
            }
        } else if (e.getSource() == listaEquipo) {
            int indice = listaEquipo.getSelectedIndex();
            if (indice >= 0) {
                listaCatalogo.clearSelection();
                tarjeta.mostrar(ventana.getEntrenador().getEquipo().get(indice));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonAgregar) {
            agregar();
        } else if (e.getSource() == botonQuitar) {
            quitar();
        } else if (e.getSource() == botonGuardar) {
            guardar();
        } else if (e.getSource() == botonRestaurar) {
            restaurar();
        } else if (e.getSource() == botonBatalla) {
            comenzar();
        }
    }

    private void agregar() {
        int indice = listaCatalogo.getSelectedIndex();
        if (indice < 0) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Selecciona un Pokémon del catálogo para agregarlo.", true);
            return;
        }
        Pokemon elegido = ventana.getCatalogo().get(indice);
        Entrenador entrenador = ventana.getEntrenador();
        if (entrenador.contarCopias(elegido.getNombre()) >= 2) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Ya tienes 2 copias de " + elegido.getNombre() + ".", true);
        } else if (entrenador.agregarPokemon(elegido.clone())) {
            Estilo.mostrarMensaje(etiquetaMensaje, elegido.getNombre() + " se agregó a tu equipo.", false);
        } else {
            Estilo.mostrarMensaje(etiquetaMensaje, "Tu equipo ya está lleno (" + MAXIMO_EQUIPO + "/" + MAXIMO_EQUIPO + ").", true);
        }
        refrescarEquipo();
    }

    private void quitar() {
        int indice = listaEquipo.getSelectedIndex();
        if (indice < 0) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Selecciona un Pokémon de Mi equipo para quitarlo.", true);
            return;
        }
        String nombre = ventana.getEntrenador().getEquipo().get(indice).getNombre();
        if (ventana.getEntrenador().quitarPokemon(indice)) {
            tarjeta.limpiar();
            Estilo.mostrarMensaje(etiquetaMensaje, nombre + " salió de tu equipo.", false);
        } else {
            Estilo.mostrarMensaje(etiquetaMensaje, "No se pudo quitar ese Pokémon.", true);
        }
        refrescarEquipo();
    }

    private void guardar() {
        if (ventana.getEntrenador().getEquipo().size() == 0) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Agrega al menos un Pokémon antes de guardar el equipo.", true);
            return;
        }
        try {
            ventana.guardarEquipo();
            Estilo.mostrarMensaje(etiquetaMensaje, "Equipo guardado en memoria.", false);
        } catch (IOException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, "No se pudo guardar el equipo: " + ex.getMessage(), true);
        }
    }

    private void restaurar() {
        if (!ventana.hayEquipoGuardado()) {
            Estilo.mostrarMensaje(etiquetaMensaje, "Todavía no has guardado ningún equipo.", true);
            return;
        }
        try {
            ventana.restaurarEquipo();
            tarjeta.limpiar();
            refrescarEquipo();
            Estilo.mostrarMensaje(etiquetaMensaje, "Equipo restaurado correctamente.", false);
        } catch (IOException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, "No se pudo restaurar el equipo: " + ex.getMessage(), true);
        } catch (ClassNotFoundException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, "No se pudo restaurar el equipo: " + ex.getMessage(), true);
        }
    }

    private void comenzar() {
        try {
            ventana.comenzarBatalla();
        } catch (IllegalArgumentException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        } catch (IllegalStateException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        }
    }
}
