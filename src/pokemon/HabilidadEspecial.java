package pokemon;

public class HabilidadEspecial implements Movimiento {
    private static final long serialVersionUID = 1L;
    private String mensaje = "";

    @Override
    public String getNombre() {
        return "Habilidad especial";
    }

    @Override
    public int ejecutar(Pokemon atacante, Pokemon defensor) {
        int psAntes = defensor.getPsActuales();
        atacante.consumirUsoHabilidad();
        mensaje = atacante.ejecutarHabilidadEspecial(defensor);
        return psAntes - defensor.getPsActuales();
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
