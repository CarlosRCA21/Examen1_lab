package pokemon.vista;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokemon.Entrenador;

public class PanelInicio extends JPanel implements ActionListener {

    private final VentanaPrincipal ventana;
    private final JTextField campoNombre = new JTextField(14);
    private final JButton botonContinuar = Estilo.crearBoton("Continuar", Estilo.VERDE);
    private final JButton botonInstrucciones = Estilo.crearBoton("Instrucciones", Estilo.AZUL);
    private final JButton botonSalir = Estilo.crearBoton("Salir", Estilo.ROJO);
    private final JLabel etiquetaMensaje = new JLabel(" ", JLabel.CENTER);

    public PanelInicio(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        setBackground(Estilo.FONDO);

        JPanel tarjeta = new JPanel(new GridBagLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilo.AZUL_OSCURO, 3),
                BorderFactory.createEmptyBorder(20, 50, 20, 50)));

        JLabel titulo = Estilo.crearTitulo("BATALLAS POKÉMON", 34, Estilo.AZUL_OSCURO);
        JLabel subtitulo = Estilo.crearTitulo("Simulador de batallas por turnos", 16, Estilo.GRIS);
        JLabel pedirNombre = Estilo.crearTitulo("Nombre del entrenador", 16, Estilo.TEXTO);

        campoNombre.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        campoNombre.addActionListener(this);

        etiquetaMensaje.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(botonContinuar);
        botones.add(botonInstrucciones);
        botones.add(botonSalir);

        botonContinuar.addActionListener(this);
        botonInstrucciones.addActionListener(this);
        botonSalir.addActionListener(this);

        agregar(tarjeta, new PanelLogo(), 0);
        agregar(tarjeta, titulo, 1);
        agregar(tarjeta, subtitulo, 2);
        agregar(tarjeta, pedirNombre, 3);
        agregar(tarjeta, campoNombre, 4);
        agregar(tarjeta, etiquetaMensaje, 5);
        agregar(tarjeta, botones, 6);

        add(tarjeta);
    }

    private void agregar(JPanel tarjeta, JComponent componente, int fila) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.insets = new Insets(6, 0, 6, 0);
        tarjeta.add(componente, c);
    }

    public void limpiar() {
        campoNombre.setText("");
        Estilo.limpiarMensaje(etiquetaMensaje);
        campoNombre.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonContinuar || e.getSource() == campoNombre) {
            continuar();
        } else if (e.getSource() == botonInstrucciones) {
            ventana.mostrarInstrucciones();
        } else if (e.getSource() == botonSalir) {
            ventana.confirmarSalida();
        }
    }

    private void continuar() {
        try {
            Entrenador entrenador = new Entrenador(campoNombre.getText());
            Estilo.limpiarMensaje(etiquetaMensaje);
            ventana.iniciarConEntrenador(entrenador);
        } catch (IllegalArgumentException ex) {
            Estilo.mostrarMensaje(etiquetaMensaje, ex.getMessage(), true);
        }
    }
}
