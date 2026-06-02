/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class NodoNeural {
    private String id;
    private boolean activa;
    private boolean visitada;

    /**
     * Constructor de NodoNeural.
     *
     * @param id Identificador único de la neurona.
     */
    public NodoNeural(String id) {
        this.id = id;
        this.activa = true;
        this.visitada = false;
    }

    /**
     * Retorna el identificador único de la neurona.
     * @return ID de la neurona.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único de la neurona.
     * @param id Nuevo ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Indica si la neurona está activa (alcanzable en el último análisis).
     * @return {@code true} si está activa, {@code false} si está aislada.
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Establece el estado de conectividad del nodo.
     * @param activa {@code true} para marcarla como activa; {@code false} como aislada.
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * Indica si la neurona fue visitada durante el último recorrido BFS/DFS.
     * @return {@code true} si fue visitada.
     */
    public boolean isVisitada() {
        return visitada;
    }

    /**
     * Establece la marca de visita del nodo para recorridos.
     * @param visitada {@code true} para marcarla como visitada.
     */
    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    /**
     * Dos NodoNeural son iguales si tienen el mismo ID.
     * @param obj Objeto a comparar.
     * @return {@code true} si los IDs son iguales.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NodoNeural other = (NodoNeural) obj;
        return id != null && id.equals(other.id);
    }

    /** @return Hash code basado en el ID. */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    /** @return Representación textual del nodo. */
    @Override
    public String toString() {
        return "NodoNeural(" + id + ")[" + (activa ? "activa" : "aislada") + "]";
    }
    
}
