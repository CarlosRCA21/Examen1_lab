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
    private static final long serialVersionUID = 1L;
    private String nombre;
    private List<Pokemon> equipo;

    public Entrenador(String nombre) {
        String limpio = "";
        if (nombre != null) {
            limpio = nombre.trim();
        }
        if (limpio.length() == 0) {
            throw new IllegalArgumentException("El nombre no puede estar vacío ni tener solo espacios.");
        }
        if (limpio.length() < 3 || limpio.length() > 10) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 10 caracteres.");
        }
        if (!limpio.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El nombre solo puede tener letras, números y espacios.");
        }
        this.nombre = limpio;
        this.equipo = new ArrayList<>();
    }
    public String getNombre() { return nombre; }
    public List<Pokemon> getEquipo() { return equipo; }
    
    public int contarCopias(String nombrePokemon) {
        int copias = 0;
        for (int i = 0; i < equipo.size(); i++) {
            if (equipo.get(i).getNombre().equals(nombrePokemon)) {
                copias++;
            }
        }
        return copias;
    }

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
