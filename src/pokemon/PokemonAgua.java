/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
public class PokemonAgua extends Pokemon {
    private static final long serialVersionUID = 1L;
    public PokemonAgua(String nombre, int ps, int ataque, int defensa, int velocidad) {
        super(nombre, TipoPokemon.AGUA, ps, ataque, defensa, velocidad);
    }
    @Override
    public String ejecutarHabilidadEspecial(Pokemon objetivo) {
        int dano = calcularDano(objetivo, 18);
        objetivo.recibirDano(dano);
        int recuperacion = (int) (psMaximos * 0.10);
        this.psActuales = Math.min(psMaximos, this.psActuales + recuperacion);
        return nombre + " usa Hidrobomba, causa " + dano + " de daño y recupera " + recuperacion + " PS.";
    }
}
