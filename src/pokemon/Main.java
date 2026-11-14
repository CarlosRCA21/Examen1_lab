package pokemon;

import pokemon.vista.Estilo;
import pokemon.vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        Estilo.aplicarFuentes();
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}
