/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 * @param <V> Tipo del valor almacenado.
 */
public class HashTable<V> {
    
    private static final int CAPACIDAD_DEFAULT = 101;

    private static class Nodo<V> {
        String clave;
        V valor;
        Nodo<V> siguiente;
        Nodo(String clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private Nodo<V>[] tabla;
    private int tamano;
    private int capacidad;

    /**
     * Constructor con capacidad personalizada.
     * @param capacidad Número de cubetas (primo recomendado).
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new Nodo[capacidad];
        this.tamano = 0;
    }

    /** Constructor con capacidad por defecto. */
    public HashTable() { this(CAPACIDAD_DEFAULT); }

    private int hash(String clave) {
        if (clave == null) return 0;
        int h = 0;
        for (int i = 0; i < clave.length(); i++)
            h = (h * 31 + clave.charAt(i)) % capacidad;
        return Math.abs(h);
    }

    /**
     * Inserta o actualiza un par clave-valor. O(1) amortizado.
     * @param clave Clave String única.
     * @param valor Valor a almacenar.
     */
    public void put(String clave, V valor) {
        int indice = hash(clave);
        Nodo<V> actual = tabla[indice];
        while (actual != null) {
            if (actual.clave.equals(clave)) { actual.valor = valor; return; }
            actual = actual.siguiente;
        }
        Nodo<V> nuevo = new Nodo<>(clave, valor);
        nuevo.siguiente = tabla[indice];
        tabla[indice] = nuevo;
        tamano++;
    }

    /**
     * Retorna el valor por clave. O(1) amortizado.
     * @param clave Clave a buscar.
     * @return Valor, o {@code null} si no existe.
     */
    public V get(String clave) {
        if (clave == null) return null;
        int indice = hash(clave);
        Nodo<V> actual = tabla[indice];
        while (actual != null) {
            if (actual.clave.equals(clave)) return actual.valor;
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Verifica si la clave existe. O(1) amortizado.
     * @param clave Clave a verificar.
     * @return {@code true} si existe.
     */
    public boolean contiene(String clave) { return get(clave) != null; }

    /**
     * Elimina la entrada por clave. O(1) amortizado.
     * @param clave Clave a eliminar.
     * @return {@code true} si fue eliminada.
     */
    public boolean remove(String clave) {
        if (clave == null) return false;
        int indice = hash(clave);
        Nodo<V> actual = tabla[indice], anterior = null;
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                if (anterior == null) tabla[indice] = actual.siguiente;
                else                  anterior.siguiente = actual.siguiente;
                tamano--;
                return true;
            }
            anterior = actual;
            actual   = actual.siguiente;
        }
        return false;
    }

    /**
     * Retorna todas las claves almacenadas.
     * @return Arreglo de claves.
     */
    public String[] getClaves() {
        String[] claves = new String[tamano];
        int idx = 0;
        for (int i = 0; i < capacidad; i++) {
            Nodo<V> actual = tabla[i];
            while (actual != null) { claves[idx++] = actual.clave; actual = actual.siguiente; }
        }
        return claves;
    }

    /** Vacía la tabla. */
    @SuppressWarnings("unchecked")
    public void limpiar() { tabla = new Nodo[capacidad]; tamano = 0; }

    /** @return Número de entradas. */
    public int getTamano() { return tamano; }

    /** @return {@code true} si no hay entradas. */
    public boolean estaVacia() { return tamano == 0; }
}
    

