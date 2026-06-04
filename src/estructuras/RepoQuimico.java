/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import modelos.BioMensajero;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class RepoQuimico {
    /** Capacidad por defecto (número primo para reducir colisiones). */
    private static final int CAPACIDAD_DEFAULT = 101;

    /** Arreglo de cubetas; cada una es cabeza de una lista enlazada. */
    private EntradaRepoQuimico<BioMensajero>[] tabla;

    /** Número de BioMensajeros almacenados. */
    private int tamano;

    /** Capacidad total de cubetas. */
    private int capacidad;

    /**
     * Constructor con capacidad personalizada.
     * @param capacidad Número de cubetas (primo recomendado).
     */
    @SuppressWarnings("unchecked")
    public RepoQuimico(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new EntradaRepoQuimico[capacidad];
        this.tamano = 0;
    }

    /** Constructor con capacidad por defecto (101 cubetas). */
    public RepoQuimico() {
        this(CAPACIDAD_DEFAULT);
    }

    /**
     * Función de dispersión polinomial.
     * {@code h = (h * 31 + char[i]) % capacidad}
     *
     * @param clave Clave String a dispersar.
     * @return Índice de cubeta (0 a capacidad-1).
     */
    private int hash(String clave) {
        if (clave == null) return 0;
        int h = 0;
        for (int i = 0; i < clave.length(); i++) {
            h = (h * 31 + clave.charAt(i)) % capacidad;
        }
        return Math.abs(h);
    }

    /**
     * Inserta o actualiza un BioMensajero. Complejidad O(1) amortizado.
     *
     * @param clave        ID del BioMensajero.
     * @param bioMensajero BioMensajero a almacenar.
     */
    public void insertar(String clave, BioMensajero bioMensajero) {
        int indice = hash(clave);
        EntradaRepoQuimico<BioMensajero> actual = tabla[indice];
        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                actual.setValor(bioMensajero);
                return;
            }
            actual = actual.siguiente;
        }
        EntradaRepoQuimico<BioMensajero> nueva =
                new EntradaRepoQuimico<>(clave, bioMensajero);
        nueva.siguiente = tabla[indice];
        tabla[indice] = nueva;
        tamano++;
    }

    /**
     * Busca y retorna el BioMensajero por clave. Complejidad O(1) amortizado.
     *
     * @param clave ID a buscar.
     * @return BioMensajero o {@code null} si no existe.
     */
    public BioMensajero buscar(String clave) {
        if (clave == null) return null;
        int indice = hash(clave);
        EntradaRepoQuimico<BioMensajero> actual = tabla[indice];
        while (actual != null) {
            if (actual.getClave().equals(clave)) return actual.getValor();
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Verifica si existe un BioMensajero con la clave dada. O(1) amortizado.
     * @param clave ID a verificar.
     * @return {@code true} si existe.
     */
    public boolean contiene(String clave) {
        return buscar(clave) != null;
    }

    /**
     * Elimina el BioMensajero por clave. Complejidad O(1) amortizado.
     *
     * @param clave ID a eliminar.
     * @return {@code true} si fue eliminado.
     */
    public boolean eliminar(String clave) {
        if (clave == null) return false;
        int indice = hash(clave);
        EntradaRepoQuimico<BioMensajero> actual = tabla[indice];
        EntradaRepoQuimico<BioMensajero> anterior = null;
        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                if (anterior == null) tabla[indice] = actual.siguiente;
                else                  anterior.siguiente = actual.siguiente;
                tamano--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Retorna todos los BioMensajeros almacenados.
     * @return Arreglo de BioMensajeros.
     */
    public BioMensajero[] getTodos() {
        BioMensajero[] resultado = new BioMensajero[tamano];
        int idx = 0;
        for (int i = 0; i < capacidad; i++) {
            EntradaRepoQuimico<BioMensajero> actual = tabla[i];
            while (actual != null) {
                resultado[idx++] = actual.getValor();
                actual = actual.siguiente;
            }
        }
        return resultado;
    }

    /**
     * Retorna todas las claves almacenadas.
     * @return Arreglo de IDs de BioMensajero.
     */
    public String[] getClaves() {
        String[] claves = new String[tamano];
        int idx = 0;
        for (int i = 0; i < capacidad; i++) {
            EntradaRepoQuimico<BioMensajero> actual = tabla[i];
            while (actual != null) {
                claves[idx++] = actual.getClave();
                actual = actual.siguiente;
            }
        }
        return claves;
    }

    /** Vacía completamente el repositorio. */
    @SuppressWarnings("unchecked")
    public void limpiar() {
        tabla = new EntradaRepoQuimico[capacidad];
        tamano = 0;
    }

    /** @return Número de BioMensajeros almacenados. */
    public int getTamano() { return tamano; }

    /** @return {@code true} si no contiene entradas. */
    public boolean estaVacio() { return tamano == 0; }

    /** @return Representación textual (depuración). */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                "RepoQuimico[" + tamano + " BioMensajeros]:\n");
        for (int i = 0; i < capacidad; i++) {
            if (tabla[i] != null) {
                sb.append("  [").append(i).append("]: ");
                EntradaRepoQuimico<BioMensajero> actual = tabla[i];
                while (actual != null) {
                    sb.append(actual.getClave());
                    if (actual.siguiente != null) sb.append(" -> ");
                    actual = actual.siguiente;
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
