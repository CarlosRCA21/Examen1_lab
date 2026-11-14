/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
public class PokemonElectrico extends Pokemon {
   public PokemonElectrico(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.ELECTRICO, ps, ataque, defensa, velocidad);
    }

    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        return "¡Atactrueno ejecutado! Posible golpe critico.";
    } 
}
