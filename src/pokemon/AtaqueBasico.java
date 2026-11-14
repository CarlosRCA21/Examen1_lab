package pokemon;

public class AtaqueBasico implements Movimiento {
    private static final long serialVersionUID = 1L;
    private String mensaje = "";

    @Override
    public String getNombre() {
        return "Ataque básico";
    }

    @Override
    public int ejecutar(Pokemon atacante, Pokemon defensor) {
        int dano = atacante.calcularDano(defensor, 10);
        defensor.recibirDano(dano);
        mensaje = atacante.getNombre() + " ataca a " + defensor.getNombre() + " y causa " + dano + " de daño.";
        return dano;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
