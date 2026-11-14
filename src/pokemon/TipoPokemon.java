/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
public enum TipoPokemon {
    FUEGO, AGUA, PLANTA, ELECTRICO;
    
    public double getEfectividadContra(TipoPokemon defensor){
        switch(this){
           case FUEGO:
                if (defensor == FUEGO || defensor == AGUA) return 0.5; 
                if (defensor == PLANTA) return 2.0; 
                break;
            case AGUA:
                if (defensor == FUEGO) return 2.0;
                if (defensor == AGUA || defensor == PLANTA) return 0.5; 
                break;
            case PLANTA:
                if (defensor == FUEGO || defensor == PLANTA) return 0.5; 
                if (defensor == AGUA) return 2.0; 
                break;
            case ELECTRICO:
                if (defensor == AGUA) return 2.0; 
                if (defensor == PLANTA || defensor == ELECTRICO) return 0.5; 
                break; 
        }
        return 1.0;
    }
}
