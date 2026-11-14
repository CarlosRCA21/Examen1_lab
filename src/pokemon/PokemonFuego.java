/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
public class PokemonFuego extends Pokemon{
    private static final long serialVersionUID = 1L;
    public PokemonFuego(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.FUEGO, ps, ataque, defensa, velocidad);
    }
        @Override
            
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int dano = calcularDano(objetivo, 20);
        objetivo.recibirDano(dano);
        return nombre + " usa Llamarada y causa " + dano + " de daño.";
    }
}
