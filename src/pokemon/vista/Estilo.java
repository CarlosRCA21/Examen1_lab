package pokemon.vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import pokemon.TipoPokemon;

public class Estilo {

    public static final Color FONDO = new Color(238, 242, 248);
    public static final Color AZUL_OSCURO = new Color(27, 42, 78);
    public static final Color TEXTO = new Color(40, 45, 60);
    public static final Color VERDE = new Color(46, 160, 90);
    public static final Color ROJO = new Color(200, 55, 55);
    public static final Color AMARILLO = new Color(230, 170, 20);
    public static final Color NARANJA = new Color(215, 120, 25);
    public static final Color MORADO = new Color(125, 70, 185);
    public static final Color AZUL = new Color(52, 110, 200);
    public static final Color GRIS = new Color(150, 156, 168);
    public static final Color GRIS_CLARO = new Color(214, 218, 226);

    private Estilo() {
    }

    public static void aplicarFuentes() {
        Font fuente = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        String[] claves = {"Label.font", "Button.font", "TextField.font", "List.font",
            "TextArea.font", "ProgressBar.font", "TitledBorder.font",
            "OptionPane.messageFont", "OptionPane.buttonFont"};
        for (int i = 0; i < claves.length; i++) {
            UIManager.put(claves[i], fuente);
        }
    }

    public static Color colorTipo(TipoPokemon tipo) {
        Color color = GRIS;
        switch (tipo) {
            case FUEGO:
                color = new Color(230, 90, 40);
                break;
            case AGUA:
                color = new Color(50, 120, 220);
                break;
            case PLANTA:
                color = new Color(60, 160, 70);
                break;
            case ELECTRICO:
                color = new Color(235, 185, 20);
                break;
            default:
                color = GRIS;
                break;
        }
        return color;
    }

    public static String nombreTipo(TipoPokemon tipo) {
        String nombre = "";
        switch (tipo) {
            case FUEGO:
                nombre = "Fuego";
                break;
            case AGUA:
                nombre = "Agua";
                break;
            case PLANTA:
                nombre = "Planta";
                break;
            case ELECTRICO:
                nombre = "Eléctrico";
                break;
            default:
                nombre = "Desconocido";
                break;
        }
        return nombre;
    }

    public static JButton crearBoton(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    public static void ponerEstado(JButton boton, boolean activo, Color fondo) {
        boton.setEnabled(activo);
        if (activo) {
            boton.setBackground(fondo);
            boton.setForeground(Color.WHITE);
        } else {
            boton.setBackground(GRIS_CLARO);
            boton.setForeground(GRIS);
        }
    }

    public static JLabel crearTitulo(String texto, int tamano, Color color) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setFont(new Font(Font.SANS_SERIF, Font.BOLD, tamano));
        etiqueta.setForeground(color);
        return etiqueta;
    }

    public static void mostrarMensaje(JLabel etiqueta, String texto, boolean esError) {
        String mensaje = texto;
        if (mensaje == null || mensaje.length() == 0) {
            mensaje = " ";
        }
        etiqueta.setText(mensaje);
        if (esError) {
            etiqueta.setForeground(ROJO);
        } else {
            etiqueta.setForeground(VERDE);
        }
    }

    public static void limpiarMensaje(JLabel etiqueta) {
        etiqueta.setText(" ");
    }
}
