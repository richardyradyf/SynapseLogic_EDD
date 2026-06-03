/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class BioMensajero {
    
    /** Identificador único del neurotransmisor (ej: "GLU", "GABA", "DA"). */
    private String id;

    /** Nombre completo del neurotransmisor. */
    private String nombre;

    /**
     * Efecto del neurotransmisor sobre la neurona postsináptica.
     * Valores posibles: "Excitatorio", "Inhibitorio", "Modulador".
     */
    private String efecto;

    /**
     * Factor de velocidad de transmisión (V).
     * Usado en la fórmula: {@code peso = distancia / (V * k)}.
     */
    private double velocidad;

    /** Descripción breve del rol biológico del neurotransmisor. */
    private String descripcion;

    /**
     * Constructor completo de BioMensajero.
     *
     * @param id          Identificador único (clave en el RepoQuimico).
     * @param nombre      Nombre completo del neurotransmisor.
     * @param efecto      Tipo de efecto ("Excitatorio", "Inhibitorio", "Modulador").
     * @param velocidad   Factor de velocidad (V) de transmisión.
     * @param descripcion Descripción breve.
     */
    public BioMensajero(String id, String nombre, String efecto,
                        double velocidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.efecto = efecto;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
    }

    /** @return ID del neurotransmisor. */
    public String getId() { return id; }

    /** @param id Nuevo ID. */
    public void setId(String id) { this.id = id; }

    /** @return Nombre completo. */
    public String getNombre() { return nombre; }

    /** @param nombre Nuevo nombre. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return Efecto ("Excitatorio", "Inhibitorio" o "Modulador"). */
    public String getEfecto() { return efecto; }

    /** @param efecto Nuevo efecto. */
    public void setEfecto(String efecto) { this.efecto = efecto; }

    /** @return Factor de velocidad (V). */
    public double getVelocidad() { return velocidad; }

    /** @param velocidad Nueva velocidad. */
    public void setVelocidad(double velocidad) { this.velocidad = velocidad; }

    /** @return Descripción breve. */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion Nueva descripción. */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Indica si este BioMensajero es excitatorio.
     * @return {@code true} si el efecto es "Excitatorio".
     */
    public boolean esExcitatorio() {
        return "Excitatorio".equalsIgnoreCase(efecto);
    }

    /**
     * Indica si este BioMensajero es inhibitorio.
     * @return {@code true} si el efecto es "Inhibitorio".
     */
    public boolean esInhibitorio() {
        return "Inhibitorio".equalsIgnoreCase(efecto);
    }

    /** @return Representación textual del BioMensajero. */
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " | " + efecto
                + " | V=" + velocidad + " | " + descripcion;
    }
    
}
