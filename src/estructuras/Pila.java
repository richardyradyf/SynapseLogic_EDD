/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class Pila<T> {
    private static class NodoPila<T> {
        T dato;
        NodoPila<T> siguiente;
        NodoPila(T dato) { this.dato = dato; this.siguiente = null; }
    }

    private NodoPila<T> tope;
    private int tamano;

    /** Constructor de Pila vacía. */
    public Pila() { tope = null; tamano = 0; }

    /**
     * Apila un elemento en el tope.
     * @param dato Elemento a apilar.
     */
    public void apilar(T dato) {
        NodoPila<T> nuevo = new NodoPila<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    /**
     * Extrae y retorna el elemento del tope.
     * @return Elemento del tope o {@code null} si vacía.
     */
    public T desapilar() {
        if (tope == null) return null;
        T dato = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return dato;
    }

    /**
     * Retorna el tope sin extraerlo.
     * @return Elemento del tope o {@code null}.
     */
    public T verTope() { return tope == null ? null : tope.dato; }

    /** @return {@code true} si no tiene elementos. */
    public boolean estaVacia() { return tamano == 0; }

    /** @return Número de elementos. */
    public int getTamano() { return tamano; }

    /** Vacía la pila. */
    public void limpiar() { tope = null; tamano = 0; }
}
