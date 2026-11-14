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

public class MotorBatalla {
    private Entrenador jugador;
    private Entrenador rivalCPU;
    private Pokemon activoJugador;
    private Pokemon activoRival;
    private int numeroRonda;
    private EstadoBatalla estado;
    private static final int LIMITE_RONDAS = 50;
    
    public MotorBatalla(Entrenador jugador, List<Pokemon> catalogoGlobal) {
        this.jugador = jugador;
        this.numeroRonda = 1;
        this.estado = EstadoBatalla.PREPARACION;
        generarRival(catalogoGlobal);
    }
    private void generarRival(List<Pokemon> catalogo) {
        this.rivalCPU = new Entrenador("Rival CPU");
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            int indexAleatorio = (int) (Math.random() * catalogo.size());
            rivalCPU.agregarPokemon(catalogo.get(indexAleatorio).clone());
        }
        this.activoJugador = jugador.getEquipo().get(0);
        this.activoRival = rivalCPU.getEquipo().get(0);
    }

   
    public int calcularDano(Pokemon atacante, Pokemon defensor, int potencia) {
        return atacante.calcularDano(defensor, potencia);
    }

 
   
    public int calcularPSRestantesRecursivo(List<Pokemon> equipo, int indice) {
        if (indice >= equipo.size()) {
            return 0; 
        }
        return equipo.get(indice).getPsActuales() + calcularPSRestantesRecursivo(equipo, indice + 1);
    }

    public Pokemon buscarSiguienteDisponibleRecursivo(List<Pokemon> equipo, int indice) {
        if (indice >= equipo.size()) {
            return null; 
        }
        if (!equipo.get(indice).estaDebilitado()) {
            return equipo.get(indice);
        }
        return buscarSiguienteDisponibleRecursivo(equipo, indice + 1);
    }


    public EstadoBatalla getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoBatalla estado) {
        this.estado = estado;
    }

    public Entrenador getJugador() { return jugador; }
    public Entrenador getRival() { return rivalCPU; }
    public Pokemon getActivoJugador() { return activoJugador; }
    public Pokemon getActivoRival() { return activoRival; }
    public int getNumeroRonda() { return numeroRonda; }

    private boolean puedeUsar(Pokemon p, AccionBatalla accion) {
        boolean valida = false;
        switch (accion) {
            case ATACAR:
                valida = true;
                break;
            case HABILIDAD_ESPECIAL:
                valida = p.getUsosHabilidadEspecial() > 0;
                break;
            case RECUPERAR:
                valida = p.getUsosRecuperar() > 0 && p.getPsActuales() < p.getPsMaximos();
                break;
            default:
                valida = false;
                break;
        }
        return valida;
    }

    private boolean hayOtroDisponible(Entrenador entrenador, Pokemon activo) {
        for (int i = 0; i < entrenador.getEquipo().size(); i++) {
            Pokemon p = entrenador.getEquipo().get(i);
            if (p != activo && !p.estaDebilitado()) {
                return true;
            }
        }
        return false;
    }

    public boolean esAccionValida(AccionBatalla accion) {
        boolean valida = false;
        if (estado == EstadoBatalla.ESPERANDO_ACCION && !activoJugador.estaDebilitado()) {
            if (accion == AccionBatalla.CAMBIAR_POKEMON) {
                valida = hayOtroDisponible(jugador, activoJugador);
            } else {
                valida = puedeUsar(activoJugador, accion);
            }
        }
        return valida;
    }

    private void validarCambio(int indice) {
        if (indice < 0 || indice >= jugador.getEquipo().size()) {
            throw new IllegalArgumentException("Selecciona un Pokémon de tu equipo.");
        }
        Pokemon elegido = jugador.getEquipo().get(indice);
        if (elegido.estaDebilitado()) {
            throw new IllegalArgumentException(elegido.getNombre() + " está debilitado y no puede combatir.");
        }
        if (elegido == activoJugador) {
            throw new IllegalArgumentException(elegido.getNombre() + " ya está en combate.");
        }
    }

    private AccionBatalla elegirAccionRival() {
        List<AccionBatalla> validas = new ArrayList<>();
        validas.add(AccionBatalla.ATACAR);
        if (puedeUsar(activoRival, AccionBatalla.HABILIDAD_ESPECIAL)) {
            validas.add(AccionBatalla.HABILIDAD_ESPECIAL);
        }
        if (puedeUsar(activoRival, AccionBatalla.RECUPERAR)) {
            validas.add(AccionBatalla.RECUPERAR);
        }
        int posicion = (int) (Math.random() * validas.size());
        return validas.get(posicion);
    }

    private String textoEfectividad(Pokemon atacante, Pokemon defensor) {
        double mult = atacante.getTipo().getEfectividadContra(defensor.getTipo());
        String texto = "";
        if (mult > 1) {
            texto = " ¡Es muy eficaz!";
        } else if (mult < 1) {
            texto = " No es muy eficaz…";
        }
        return texto;
    }

    private Movimiento crearMovimiento(AccionBatalla accion) {
        Movimiento movimiento = null;
        switch (accion) {
            case ATACAR:
                movimiento = new AtaqueBasico();
                break;
            case HABILIDAD_ESPECIAL:
                movimiento = new HabilidadEspecial();
                break;
            case RECUPERAR:
                movimiento = new Recuperacion();
                break;
            default:
                movimiento = null;
                break;
        }
        return movimiento;
    }

    private void ejecutarAccion(String quien, Pokemon yo, Pokemon otro, AccionBatalla accion, List<String> mensajes) {
        Movimiento movimiento = crearMovimiento(accion);
        if (movimiento == null) {
            return;
        }
        movimiento.ejecutar(yo, otro);
        String texto = quien + ": " + movimiento.getMensaje();
        if (accion != AccionBatalla.RECUPERAR) {
            texto = texto + textoEfectividad(yo, otro);
        }
        mensajes.add(texto);
        if (accion != AccionBatalla.RECUPERAR && otro.estaDebilitado()) {
            mensajes.add(otro.getNombre() + " fue debilitado.");
        }
    }

    public List<String> jugarRonda(AccionBatalla accion, int indiceEquipo) {
        if (estado != EstadoBatalla.ESPERANDO_ACCION) {
            throw new IllegalStateException("La batalla no está esperando una acción.");
        }
        if (accion == AccionBatalla.CAMBIAR_POKEMON) {
            validarCambio(indiceEquipo);
        } else if (!esAccionValida(accion)) {
            throw new IllegalArgumentException("Esa acción no está disponible ahora (sin usos o PS completos).");
        }

        List<String> mensajes = new ArrayList<>();
        estado = EstadoBatalla.RESOLVIENDO_RONDA;
        if (accion == AccionBatalla.CAMBIAR_POKEMON) {
            activoJugador = jugador.getEquipo().get(indiceEquipo);
            mensajes.add(jugador.getNombre() + " cambia a " + activoJugador.getNombre() + ".");
        }

        AccionBatalla accionRival = elegirAccionRival();
        boolean jugadorPrimero = true;
        if (accion != AccionBatalla.CAMBIAR_POKEMON && activoRival.getVelocidad() > activoJugador.getVelocidad()) {
            jugadorPrimero = false;
        }

        if (jugadorPrimero) {
            if (accion != AccionBatalla.CAMBIAR_POKEMON) {
                ejecutarAccion(jugador.getNombre(), activoJugador, activoRival, accion, mensajes);
            }
            if (!activoJugador.estaDebilitado() && !activoRival.estaDebilitado()) {
                ejecutarAccion(rivalCPU.getNombre(), activoRival, activoJugador, accionRival, mensajes);
            }
        } else {
            ejecutarAccion(rivalCPU.getNombre(), activoRival, activoJugador, accionRival, mensajes);
            if (!activoJugador.estaDebilitado() && !activoRival.estaDebilitado()) {
                ejecutarAccion(jugador.getNombre(), activoJugador, activoRival, accion, mensajes);
            }
        }
        cerrarRonda(mensajes);
        return mensajes;
    }

    private void cerrarRonda(List<String> mensajes) {
        if (activoRival.estaDebilitado()) {
            Pokemon siguiente = buscarSiguienteDisponibleRecursivo(rivalCPU.getEquipo(), 0);
            if (siguiente != null) {
                activoRival = siguiente;
                mensajes.add("El rival envía a " + activoRival.getNombre() + ".");
            }
        }
        if (buscarSiguienteDisponibleRecursivo(rivalCPU.getEquipo(), 0) == null) {
            estado = EstadoBatalla.VICTORIA;
            mensajes.add("¡Todos los Pokémon rivales fueron debilitados!");
            return;
        }
        if (buscarSiguienteDisponibleRecursivo(jugador.getEquipo(), 0) == null) {
            estado = EstadoBatalla.DERROTA;
            mensajes.add("Todos tus Pokémon fueron debilitados.");
            return;
        }
        if (numeroRonda >= LIMITE_RONDAS) {
            int psJugador = calcularPSRestantesRecursivo(jugador.getEquipo(), 0);
            int psRival = calcularPSRestantesRecursivo(rivalCPU.getEquipo(), 0);
            mensajes.add("Se alcanzó el límite de rondas. PS restantes: " + psJugador + " contra " + psRival + ".");
            if (psJugador > psRival) {
                estado = EstadoBatalla.VICTORIA;
            } else if (psRival > psJugador) {
                estado = EstadoBatalla.DERROTA;
            } else {
                estado = EstadoBatalla.EMPATE;
            }
            return;
        }
        if (activoJugador.estaDebilitado()) {
            estado = EstadoBatalla.ELEGIR_REEMPLAZO;
            mensajes.add("Tu Pokémon fue debilitado: elige un reemplazo.");
            return;
        }
        numeroRonda++;
        estado = EstadoBatalla.ESPERANDO_ACCION;
    }

    public List<String> elegirReemplazo(int indiceEquipo) {
        if (estado != EstadoBatalla.ELEGIR_REEMPLAZO) {
            throw new IllegalStateException("No hace falta elegir un reemplazo ahora.");
        }
        validarCambio(indiceEquipo);
        activoJugador = jugador.getEquipo().get(indiceEquipo);
        numeroRonda++;
        estado = EstadoBatalla.ESPERANDO_ACCION;
        List<String> mensajes = new ArrayList<>();
        mensajes.add(jugador.getNombre() + " envía a " + activoJugador.getNombre() + ".");
        return mensajes;
    }

    
}
