/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
public class PokemonPlanta extends Pokemon {
    private static final long serialVersionUID = 1L;
    public PokemonPlanta(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.PLANTA, ps, ataque, defensa, velocidad);
    }
    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int dano = calcularDano(objetivo, 18);
        int danoReal = Math.min(dano, objetivo.getPsActuales());
        objetivo.recibirDano(dano);
        int absorbido = danoReal / 2;
        curar(absorbido);
        return nombre + " usa Drenadoras, causa " + dano + " de daño y absorbe " + absorbido + " PS.";
    }
}
