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
public interface Movimiento extends Serializable {
    String getNombre();
    int ejecutar(Pokemon atacante, Pokemon defensor);
    String getMensaje();
}
