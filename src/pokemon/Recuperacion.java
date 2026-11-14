package pokemon;

public class Recuperacion implements Movimiento {
    private static final long serialVersionUID = 1L;
    private String mensaje = "";

    @Override
    public String getNombre() {
        return "Recuperar";
    }

    @Override
    public int ejecutar(Pokemon atacante, Pokemon defensor) {
        int psAntes = atacante.getPsActuales();
        atacante.recuperarPS();
        int curado = atacante.getPsActuales() - psAntes;
        mensaje = atacante.getNombre() + " se recupera y gana " + curado + " PS.";
        return curado;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
