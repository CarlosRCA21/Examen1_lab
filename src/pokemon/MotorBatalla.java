/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
import java.util.List;

public class MotorBatalla {
    private Entrenador jugador;
    private Entrenador rivalCPU;
    private Pokemon activoJugador;
    private Pokemon activoRival;
    private int numeroRonda;
    private EstadoBatalla estado;
    
    public MotorBatalla(Entrenador jugador, List<Pokemon> catalogoGlobal) {
        this.jugador = jugador;
        this.numeroRonda = 1;
        this.estado = EstadoBatalla.PREPARACION;
        generarRival(catalogoGlobal);
    }
    private void generarRival(List<Pokemon> catalogo) {
        this.rivalCPU = new Entrenador("Rival CPU");
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            int indexAleatorio = (int) (Math.random() * catalogo.size());
            rivalCPU.agregarPokemon(catalogo.get(indexAleatorio).clone());
        }
        this.activoJugador = jugador.getEquipo().get(0);
        this.activoRival = rivalCPU.getEquipo().get(0);
    }

   
    public int calcularDano(Pokemon atacante, Pokemon defensor, int potencia) {
        double mult = atacante.getTipo().getEfectividadContra(defensor.getTipo());
        double calculo = (atacante.getAtaque() + atacante.getNivel() + potencia - (defensor.getDefensa() / 2.0)) * mult;
        return Math.max(1, (int) Math.round(calculo)); 
    }

 
   
    public int calcularPSRestantesRecursivo(List<Pokemon> equipo, int indice) {
        if (indice >= equipo.size()) {
            return 0; 
        }
        return equipo.get(indice).getPsActuales() + calcularPSRestantesRecursivo(equipo, indice + 1);
    }

    public Pokemon buscarSiguienteDisponibleRecursivo(List<Pokemon> equipo, int indice) {
        if (indice >= equipo.size()) {
            return null; 
        }
        if (!equipo.get(indice).estaDebilitado()) {
            return equipo.get(indice);
        }
        return buscarSiguienteDisponibleRecursivo(equipo, indice + 1);
    }


    public EstadoBatalla getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoBatalla estado) {
        this.estado = estado;
    }

    
}
