/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class Cola<T> {
    private static class NodoCola<T> {
        T dato;
        NodoCola<T> siguiente;
        NodoCola(T dato) { this.dato = dato; this.siguiente = null; }
    }

    private NodoCola<T> frente;
    private NodoCola<T> fin;
    private int tamano;

    /** Constructor de Cola vacía. */
    public Cola() { frente = null; fin = null; tamano = 0; }

    /**
     * Agrega un elemento al final de la cola.
     * @param dato Elemento a encolar.
     */
    public void encolar(T dato) {
        NodoCola<T> nuevo = new NodoCola<>(dato);
        if (fin != null) fin.siguiente = nuevo;
        fin = nuevo;
        if (frente == null) frente = nuevo;
        tamano++;
    }

    /**
     * Extrae y retorna el elemento del frente.
     * @return Elemento al frente o {@code null} si vacía.
     */
    public T desencolar() {
        if (frente == null) return null;
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        tamano--;
        return dato;
    }

    /**
     * Retorna el frente sin extraerlo.
     * @return Elemento al frente o {@code null}.
     */
    public T verFrente() { return frente == null ? null : frente.dato; }

    /** @return {@code true} si no tiene elementos. */
    public boolean estaVacia() { return tamano == 0; }

    /** @return Número de elementos. */
    public int getTamano() { return tamano; }

    /** Vacía la cola. */
    public void limpiar() { frente = null; fin = null; tamano = 0; }
}
