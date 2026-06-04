/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import modelos.NodoNeural;
import modelos.Sinapsis;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class RedConectiva {
    
    /** Agrupa un NodoNeural con su lista de adyacencia. */
    private static class EntradaGrafo {
        NodoNeural nodo;
        NodoAdyacencia listaAdyacencia;
        EntradaGrafo(NodoNeural nodo) {
            this.nodo = nodo;
            this.listaAdyacencia = null;
        }
    }

    /** Mapeo del ID → EntradaGrafo para acceso O(1) a vértices. */
    private HashTable<EntradaGrafo> vertices;

    /** Número de Sinapsis en la red. */
    private int numAristas;

    /** Número de NodoNeural en la red. */
    private int numVertices;

    /** Constructor de RedConectiva vacía. */
    public RedConectiva() {
        vertices = new HashTable<>(211);
        numAristas = 0;
        numVertices = 0;
    }

    // VÉRTICES 

    /**
     * Agrega un NodoNeural si no existe ya.
     * @param nodo NodoNeural a agregar.
     * @return {@code true} si fue agregado.
     */
    public boolean agregarNodo(NodoNeural nodo) {
        if (nodo == null || vertices.contiene(nodo.getId())) return false;
        vertices.put(nodo.getId(), new EntradaGrafo(nodo));
        numVertices++;
        return true;
    }

    /**
     * Elimina un NodoNeural y todas sus Sinapsis incidentes.
     * @param id ID del NodoNeural a eliminar.
     * @return {@code true} si fue eliminado.
     */
    public boolean eliminarNodo(String id) {
        if (!vertices.contiene(id)) return false;
        EntradaGrafo entrada = vertices.get(id);
        NodoAdyacencia nodo = entrada.listaAdyacencia;
        while (nodo != null) { numAristas--; nodo = nodo.siguiente; }
        String[] ids = vertices.getClaves();
        for (String otraId : ids) {
            if (!otraId.equals(id)) eliminarSinapsis(otraId, id);
        }
        vertices.remove(id);
        numVertices--;
        return true;
    }

    /**
     * Indica si existe un NodoNeural con el ID dado.
     * @param id ID a verificar.
     * @return {@code true} si existe.
     */
    public boolean existeNodo(String id) { return vertices.contiene(id); }

    /**
     * Retorna el NodoNeural con el ID dado.
     * @param id ID del nodo.
     * @return NodoNeural o {@code null}.
     */
    public NodoNeural getNodo(String id) {
        EntradaGrafo e = vertices.get(id);
        return e == null ? null : e.nodo;
    }

    /**
     * Retorna todos los IDs de NodoNeural.
     * @return Arreglo de IDs.
     */
    public String[] getIdsNodos() { return vertices.getClaves(); }

    /**
     * Retorna todos los NodoNeural de la red.
     * @return Arreglo de NodoNeural.
     */
    public NodoNeural[] getTodosNodos() {
        String[] ids = vertices.getClaves();
        NodoNeural[] nodos = new NodoNeural[ids.length];
        for (int i = 0; i < ids.length; i++)
            nodos[i] = vertices.get(ids[i]).nodo;
        return nodos;
    }

    //  ARISTAS 

    /**
     * Agrega una Sinapsis dirigida. Crea nodos si no existen.
     * Actualiza si la arista ya existe.
     * @param sinapsis Sinapsis a agregar.
     */
    public void agregarSinapsis(Sinapsis sinapsis) {
        String origen  = sinapsis.getIdOrigen();
        String destino = sinapsis.getIdDestino();
        if (!vertices.contiene(origen))  agregarNodo(new NodoNeural(origen));
        if (!vertices.contiene(destino)) agregarNodo(new NodoNeural(destino));
        EntradaGrafo entradaOrigen = vertices.get(origen);
        NodoAdyacencia actual = entradaOrigen.listaAdyacencia;
        while (actual != null) {
            if (actual.getIdDestino().equals(destino)) { actual.setSinapsis(sinapsis); return; }
            actual = actual.siguiente;
        }
        NodoAdyacencia nuevo = new NodoAdyacencia(destino, sinapsis);
        nuevo.siguiente = entradaOrigen.listaAdyacencia;
        entradaOrigen.listaAdyacencia = nuevo;
        numAristas++;
    }

    /**
     * Elimina la Sinapsis entre origen y destino.
     * @param idOrigen  ID origen.
     * @param idDestino ID destino.
     * @return {@code true} si fue eliminada.
     */
    public boolean eliminarSinapsis(String idOrigen, String idDestino) {
        EntradaGrafo entrada = vertices.get(idOrigen);
        if (entrada == null) return false;
        NodoAdyacencia actual = entrada.listaAdyacencia, anterior = null;
        while (actual != null) {
            if (actual.getIdDestino().equals(idDestino)) {
                if (anterior == null) entrada.listaAdyacencia = actual.siguiente;
                else                  anterior.siguiente = actual.siguiente;
                numAristas--;
                return true;
            }
            anterior = actual; actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Verifica si existe una Sinapsis entre origen y destino.
     * @param idOrigen  ID origen.
     * @param idDestino ID destino.
     * @return {@code true} si existe.
     */
    public boolean existeSinapsis(String idOrigen, String idDestino) {
        EntradaGrafo entrada = vertices.get(idOrigen);
        if (entrada == null) return false;
        NodoAdyacencia actual = entrada.listaAdyacencia;
        while (actual != null) {
            if (actual.getIdDestino().equals(idDestino)) return true;
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Retorna la Sinapsis entre dos nodos.
     * @param idOrigen  ID origen.
     * @param idDestino ID destino.
     * @return Sinapsis o {@code null}.
     */
    public Sinapsis getSinapsis(String idOrigen, String idDestino) {
        EntradaGrafo entrada = vertices.get(idOrigen);
        if (entrada == null) return null;
        NodoAdyacencia actual = entrada.listaAdyacencia;
        while (actual != null) {
            if (actual.getIdDestino().equals(idDestino)) return actual.getSinapsis();
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Retorna el primer NodoAdyacencia del nodo origen.
     * @param idOrigen ID del nodo.
     * @return Primer NodoAdyacencia o {@code null}.
     */
    public NodoAdyacencia getListaAdyacencia(String idOrigen) {
        EntradaGrafo entrada = vertices.get(idOrigen);
        return entrada == null ? null : entrada.listaAdyacencia;
    }

    /**
     * Retorna todas las Sinapsis de la red.
     * @return Arreglo de Sinapsis.
     */
    public Sinapsis[] getTodasSinapsis() {
        Sinapsis[] resultado = new Sinapsis[numAristas];
        int idx = 0;
        for (String id : vertices.getClaves()) {
            NodoAdyacencia nodo = vertices.get(id).listaAdyacencia;
            while (nodo != null) { resultado[idx++] = nodo.getSinapsis(); nodo = nodo.siguiente; }
        }
        return resultado;
    }

    //  UTILIDADES

    /**
     * Reinicia la marca de visita de todos los NodoNeural.
     * Llamar antes de cada BFS/DFS.
     */
    public void reiniciarVisitas() {
        for (String id : vertices.getClaves()) {
            EntradaGrafo e = vertices.get(id);
            if (e != null) e.nodo.setVisitada(false);
        }
    }

    /**
     * Marca todos los NodoNeural como activos.
     * Llamar antes de cada análisis de conectividad.
     */
    public void reiniciarEstadosConectividad() {
        for (String id : vertices.getClaves()) {
            EntradaGrafo e = vertices.get(id);
            if (e != null) e.nodo.setActiva(true);
        }
    }

    /**
     * Aplica fatiga cognitiva global: multiplica k de cada Sinapsis por 1.2.
     * CRC: "Actualizar el factor K global".
     */
    public void aplicarFatigaGlobal() {
        for (String id : vertices.getClaves()) {
            NodoAdyacencia nodo = vertices.get(id).listaAdyacencia;
            while (nodo != null) { nodo.getSinapsis().aplicarFatiga(); nodo = nodo.siguiente; }
        }
    }

    /** Vacía completamente la red. */
    public void limpiar() { vertices.limpiar(); numAristas = 0; numVertices = 0; }

    /** @return Número de NodoNeural. */
    public int getNumNodos() { return numVertices; }

    /** @return Número de Sinapsis. */
    public int getNumSinapsis() { return numAristas; }

    /** @return Representación textual resumida. */
    @Override
    public String toString() {
        return "RedConectiva[nodos=" + numVertices + ", sinapsis=" + numAristas + "]";
    }
    
}
