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
    private static final long serialVersionUID = 1L;
   public PokemonElectrico(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.ELECTRICO, ps, ataque, defensa, velocidad);
    }

    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int dano = calcularDano(objetivo, 17);
        String extra = "";
        if (Math.random() < 0.25) {
            dano = (int) Math.round(dano * 1.5);
            extra = " ¡Golpe crítico!";
        }
        objetivo.recibirDano(dano);
        return nombre + " usa Impactrueno y causa " + dano + " de daño." + extra;
    } 
}
