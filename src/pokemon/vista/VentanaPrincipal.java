package pokemon.vista;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import pokemon.CatalogoPorDefecto;
import pokemon.Entrenador;
import pokemon.EstadoBatalla;
import pokemon.GestorSerializacion;
import pokemon.MotorBatalla;
import pokemon.Pokemon;

public class VentanaPrincipal extends JFrame {

    private static final String INICIO = "inicio";
    private static final String EQUIPO = "equipo";
    private static final String BATALLA = "batalla";
    private static final String RESULTADO = "resultado";

    private final CardLayout cartas = new CardLayout();
    private final JPanel contenedor = new JPanel(cartas);
    private final List<Pokemon> catalogo;
    private Entrenador entrenador = null;
    private MotorBatalla motor = null;
    private byte[] equipoGuardado = null;

    private final PanelInicio panelInicio;
    private final PanelEquipo panelEquipo;
    private final PanelBatalla panelBatalla;
    private final PanelResultado panelResultado;

    public VentanaPrincipal() {
        super("Batallas Pokémon");
        catalogo = CatalogoPorDefecto.obtenerCatalogoCompleto();

        panelInicio = new PanelInicio(this);
        panelEquipo = new PanelEquipo(this);
        panelBatalla = new PanelBatalla(this);
        panelResultado = new PanelResultado(this);

        contenedor.add(panelInicio, INICIO);
        contenedor.add(panelEquipo, EQUIPO);
        contenedor.add(panelBatalla, BATALLA);
        contenedor.add(panelResultado, RESULTADO);
        add(contenedor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 740);
        setMinimumSize(new Dimension(1000, 680));
        setLocationRelativeTo(null);
        cartas.show(contenedor, INICIO);
    }

    public List<Pokemon> getCatalogo() {
        return catalogo;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void iniciarConEntrenador(Entrenador nuevo) {
        entrenador = nuevo;
        equipoGuardado = null;
        panelEquipo.actualizar();
        cartas.show(contenedor, EQUIPO);
    }

    public boolean hayEquipoGuardado() {
        return equipoGuardado != null;
    }

    public void guardarEquipo() throws IOException {
        equipoGuardado = GestorSerializacion.guardarEquipoEnMemoria(entrenador);
    }

    public void restaurarEquipo() throws IOException, ClassNotFoundException {
        entrenador = GestorSerializacion.restaurarEquipoDesdeMemoria(equipoGuardado);
    }

    public void comenzarBatalla() {
        motor = new MotorBatalla(entrenador, catalogo);
        motor.setEstado(EstadoBatalla.ESPERANDO_ACCION);
        panelBatalla.iniciar(motor);
        cartas.show(contenedor, BATALLA);
    }

    public void mostrarResultado() {
        panelResultado.mostrar(motor);
        cartas.show(contenedor, RESULTADO);
    }

    public void nuevaPartida() {
        entrenador = new Entrenador(entrenador.getNombre());
        motor = null;
        panelEquipo.actualizar();
        cartas.show(contenedor, EQUIPO);
    }

    public void mostrarInstrucciones() {
        String texto = "OBJETIVO\n"
                + "Arma un equipo de 1 a 6 Pokémon y derrota al equipo del Rival CPU.\n\n"
                + "COMO SE JUEGA\n"
                + "1. Escribe tu nombre de entrenador (3 a 10 caracteres: letras, números y espacios).\n"
                + "2. Elige Pokémon del catálogo y agrégalos a tu equipo. Puedes repetir el mismo "
                + "Pokémon hasta 2 veces. El primero de la lista sale primero al combate.\n"
                + "3. Con Guardar equipo y Restaurar equipo conservas una copia en memoria.\n"
                + "4. Pulsa Comenzar batalla. En cada ronda eliges una acción:\n"
                + "   - Atacar: ataque básico.\n"
                + "   - Habilidad especial: más daño y un efecto propio (3 usos por Pokémon).\n"
                + "   - Recuperar: devuelve el 25% de los PS máximos (2 usos por Pokémon).\n"
                + "   - Cambiar Pokémon: entra otro de tu equipo y se ejecuta primero.\n"
                + "5. Si tu Pokémon se debilita, eliges su reemplazo.\n\n"
                + "TIPOS\n"
                + "Es muy eficaz (x2): Fuego a Planta, Agua a Fuego, Planta a Agua y Eléctrico a Agua.\n"
                + "No es muy eficaz (x0,5): contra el mismo tipo, Fuego a Agua, Agua a Planta, "
                + "Planta a Fuego y Eléctrico a Planta.\n\n"
                + "FIN DE LA BATALLA\n"
                + "Gana quien deja sin PS a todos los Pokémon del rival. Si se llega a 50 rondas, "
                + "gana el equipo con más PS restantes.";
        JTextArea area = new JTextArea(texto, 22, 46);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(area);
        JOptionPane.showMessageDialog(this, scroll, "Instrucciones", JOptionPane.INFORMATION_MESSAGE);
    }

    public void confirmarSalida() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir del juego?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
