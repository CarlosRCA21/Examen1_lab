/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
import java.util.ArrayList;
import java.util.List;
public class CatalogoPorDefecto {
 public static List<Pokemon> obtenerCatalogoCompleto() {
        List<Pokemon> catalogo = new ArrayList<>();

        catalogo.add(new PokemonFuego("Charmander", 90, 14, 8, 11)); 
        catalogo.add(new PokemonFuego("Vulpix", 85, 13, 9, 12)); 
        catalogo.add(new PokemonFuego("Charizard", 120, 18, 12, 15));
        
        catalogo.add(new PokemonAgua("Squirtle", 100, 11, 12, 9)); 
        catalogo.add(new PokemonAgua("Psyduck", 95, 12, 9, 10)); 
        catalogo.add(new PokemonAgua("Gyarados", 130, 17, 10, 14));
        
        catalogo.add(new PokemonPlanta("Bulbasaur", 100, 12, 10, 8));
        catalogo.add(new PokemonPlanta("Oddish", 90, 13, 8, 7)); 
        catalogo.add(new PokemonPlanta("Venusaur", 125, 15, 14, 9));
        
        catalogo.add(new PokemonElectrico("Pikachu", 80, 14, 7, 15)); 
        catalogo.add(new PokemonElectrico("Voltorb", 75, 12, 9, 16)); 
        catalogo.add(new PokemonElectrico("Jolteon", 95, 16, 11, 18));
        
        return catalogo;
    }   
}
