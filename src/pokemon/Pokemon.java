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
public abstract class Pokemon implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    protected String nombre;
    protected TipoPokemon tipo;
    protected int nivel;
    protected int psMaximos;
    protected int psActuales;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected int usosHabilidadEspecial; 
    protected int usosRecuperar;
    
    public Pokemon(String nombre, TipoPokemon tipo, int psMaximos, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = 5;
        this.psMaximos = psMaximos;
        this.psActuales = psMaximos;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.usosHabilidadEspecial = 3; 
        this.usosRecuperar = 2; 
    }
    public boolean estaDebilitado() {
        return this.psActuales <= 0;
    }

    public void recibirDano(int cantidad) {
        this.psActuales = Math.max(0, this.psActuales - cantidad);
    }

    public boolean recuperarPS() {
        if (usosRecuperar > 0 && psActuales < psMaximos && !estaDebilitado()) {
            int curacion = (int) (psMaximos * 0.25); 
            this.psActuales = Math.min(psMaximos, psActuales + curacion);
            usosRecuperar--;
            return true;
        }
        return false;
    }

    public int calcularDano(Pokemon objetivo, int potencia) {
        double mult = tipo.getEfectividadContra(objetivo.getTipo());
        double calculo = (ataque + nivel + potencia - (objetivo.getDefensa() / 2.0)) * mult;
        return Math.max(1, (int) Math.round(calculo));
    }

    public void curar(int cantidad) {
        this.psActuales = Math.min(psMaximos, psActuales + cantidad);
    }

    public abstract String ejecutarHabilidadEspecial(Pokemon objetivo);

    @Override
    public Pokemon clone() {
        try {
            return (Pokemon) super.clone(); 
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getNombre() { return nombre; }
    public TipoPokemon getTipo() { return tipo; }
    public int getNivel() { return nivel; }
    public int getPsMaximos() { return psMaximos; }
    public int getPsActuales() { return psActuales; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public int getUsosHabilidadEspecial() { return usosHabilidadEspecial; }
    public int getUsosRecuperar() { return usosRecuperar; }
    public void consumirUsoHabilidad() { if (usosHabilidadEspecial > 0) usosHabilidadEspecial--; }
}
