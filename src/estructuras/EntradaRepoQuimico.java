/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class EntradaRepoQuimico<V> {
     /** Clave de la entrada (ID del BioMensajero). */
    private String clave;

    /** Valor asociado a la clave. */
    private V valor;

    /** Siguiente nodo en la misma cubeta (resolución de colisiones). */
    EntradaRepoQuimico<V> siguiente;

    /**
     * Constructor de EntradaRepoQuimico.
     *
     * @param clave Clave String de la entrada.
     * @param valor Valor a almacenar.
     */
    public EntradaRepoQuimico(String clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }

    /** @return Clave de la entrada. */
    public String getClave() { return clave; }

    /** @param clave Nueva clave. */
    public void setClave(String clave) { this.clave = clave; }

    /** @return Valor almacenado. */
    public V getValor() { return valor; }

    /** @param valor Nuevo valor. */
    public void setValor(V valor) { this.valor = valor; }
}
