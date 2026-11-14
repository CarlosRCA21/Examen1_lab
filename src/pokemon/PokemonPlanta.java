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
    public PokemonPlanta(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.PLANTA, ps, ataque, defensa, velocidad);
    }
    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        return "¡Drenadoras ejecutadas! Absorbe vida del rival";
    }
}
