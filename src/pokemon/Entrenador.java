/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Entrenador implements Serializable{
    private String nombre;
    private List<Pokemon> equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>();
    }
    public String getNombre() { return nombre; }
    public List<Pokemon> getEquipo() { return equipo; }
    
    public boolean agregarPokemon(Pokemon p) {
        if (equipo.size() < 6) {
            equipo.add(p);
            return true;
        }
        return false;
    }

    public boolean quitarPokemon(int index) {
        if (index >= 0 && index < equipo.size()) {
            equipo.remove(index);
            return true;
        }
        return false;
    }
}
