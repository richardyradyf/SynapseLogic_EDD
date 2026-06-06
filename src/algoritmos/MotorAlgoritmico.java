/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import estructuras.Cola;
import estructuras.NodoAdyacencia;
import estructuras.Pila;
import estructuras.RedConectiva;
import estructuras.RepoQuimico;
import modelos.BioMensajero;
import modelos.NodoNeural;
import modelos.Sinapsis;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class MotorAlgoritmico {
    /** Red neuronal sobre la que operan los algoritmos. */
    private RedConectiva red;

    /** Repositorio de BioMensajeros para consulta de velocidad V. */
    private RepoQuimico repoQuimico;

    /**
     * Constructor del MotorAlgoritmico.
     *
     * @param red         RedConectiva que modela la red neuronal.
     * @param repoQuimico RepoQuimico con el diccionario de BioMensajeros.
     */
    public MotorAlgoritmico(RedConectiva red, RepoQuimico repoQuimico) {
        this.red = red;
        this.repoQuimico = repoQuimico;
    }


    /**
     * Recorrido en Amplitud (BFS) desde un nodo fuente.
     *
     * @param idFuente ID del NodoNeural de partida.
     * @return Arreglo de IDs de nodos alcanzados.
     */
    public String[] bfs(String idFuente) {
        red.reiniciarVisitas();
        red.reiniciarEstadosConectividad();
        if (!red.existeNodo(idFuente)) return new String[0];

        Cola<String> cola = new Cola<>();
        cola.encolar(idFuente);
        red.getNodo(idFuente).setVisitada(true);

        String[] alcanzados = new String[red.getNumNodos()];
        int count = 0;

        while (!cola.estaVacia()) {
            String idActual = cola.desencolar();
            alcanzados[count++] = idActual;
            NodoAdyacencia vecino = red.getListaAdyacencia(idActual);
            while (vecino != null) {
                NodoNeural nodoVecino = red.getNodo(vecino.getIdDestino());
                if (nodoVecino != null && !nodoVecino.isVisitada()) {
                    nodoVecino.setVisitada(true);
                    cola.encolar(vecino.getIdDestino());
                }
                vecino = vecino.siguiente;
            }
        }

        for (String id : red.getIdsNodos()) {
            NodoNeural n = red.getNodo(id);
            if (n != null && !n.isVisitada()) n.setActiva(false);
        }

        String[] resultado = new String[count];
        for (int i = 0; i < count; i++) resultado[i] = alcanzados[i];
        return resultado;
    }

    /**
     * Recorrido en Profundidad (DFS) iterativo desde un nodo fuente.
     *
     * @param idFuente ID del NodoNeural de partida.
     * @return Arreglo de IDs de nodos alcanzados en orden DFS.
     */
    // DFS
    public String[] dfs(String idFuente) {
        red.reiniciarVisitas();
        red.reiniciarEstadosConectividad();
        if (!red.existeNodo(idFuente)) return new String[0];

        Pila<String> pila = new Pila<>();
        pila.apilar(idFuente);

        String[] alcanzados = new String[red.getNumNodos()];
        int count = 0;

        while (!pila.estaVacia()) {
            String idActual = pila.desapilar();
            NodoNeural nodoActual = red.getNodo(idActual);
            if (nodoActual == null || nodoActual.isVisitada()) continue;
            nodoActual.setVisitada(true);
            alcanzados[count++] = idActual;
            NodoAdyacencia vecino = red.getListaAdyacencia(idActual);
            while (vecino != null) {
                NodoNeural nv = red.getNodo(vecino.getIdDestino());
                if (nv != null && !nv.isVisitada()) pila.apilar(vecino.getIdDestino());
                vecino = vecino.siguiente;
            }
        }

        for (String id : red.getIdsNodos()) {
            NodoNeural n = red.getNodo(id);
            if (n != null && !n.isVisitada()) n.setActiva(false);
        }

        String[] resultado = new String[count];
        for (int i = 0; i < count; i++) resultado[i] = alcanzados[i];
        return resultado;
    }

    /**
     * Retorna los IDs de todos los NodoNeural aislados tras el último BFS/DFS.
     * @return Arreglo de IDs con {@code activa = false}.
     */
    public String[] getZonasAisladas() {
        String[] todos = red.getIdsNodos();
        String[] aislados = new String[todos.length];
        int count = 0;
        for (String id : todos) {
            NodoNeural n = red.getNodo(id);
            if (n != null && !n.isActiva()) aislados[count++] = id;
        }
        String[] resultado = new String[count];
        for (int i = 0; i < count; i++) resultado[i] = aislados[i];
        return resultado;
    }

    /**
     * Determina si la red es fuertemente conexa (BFS desde cada nodo).
     * @return {@code true} si desde cualquier nodo se alcanza todos los demás.
     */
    public boolean esFuertementeConexo() {
        String[] ids = red.getIdsNodos();
        if (ids.length == 0) return true;
        for (String id : ids) {
            if (bfs(id).length < red.getNumNodos()) return false;
        }
        return true;
    }

    // ====================================================================
    // OPTIMIZACIÓN DIJKSTRA CON MIN-HEAP (HECHO A MANO)
    // ====================================================================

    /**
     * Encapsula el resultado del algoritmo de Dijkstra.
     */
    public static class ResultadoDijkstra {
        public double distanciaMinima;
        public String[] ruta;
        public boolean existeCamino;

        public ResultadoDijkstra(double distanciaMinima, String[] ruta, boolean existeCamino) {
            this.distanciaMinima = distanciaMinima;
            this.ruta = ruta;
            this.existeCamino = existeCamino;
        }
    }

    /**
     * Elemento interno para manejar el Min-Heap.
     */
    private static class ElementoHeap {
        int indiceNodo;
        double distancia;

        ElementoHeap(int idx, double dist) {
            this.indiceNodo = idx;
            this.distancia = dist;
        }
    }

    /**
     * Cola de Prioridad manual basada en arreglos.
     */
    private static class MinHeapCustom {
        private ElementoHeap[] heap;
        private int tamano;

        public MinHeapCustom(int capacidadInicial) {
            heap = new ElementoHeap[capacidadInicial];
            tamano = 0;
        }

        public boolean estaVacia() {
            return tamano == 0;
        }

        public void insertar(int idx, double dist) {
            // Si el arreglo se llena, duplicamos su tamaño (muy útil para grafos densos)
            if (tamano == heap.length) {
                ElementoHeap[] nuevoHeap = new ElementoHeap[heap.length * 2];
                System.arraycopy(heap, 0, nuevoHeap, 0, heap.length);
                heap = nuevoHeap;
            }
            heap[tamano] = new ElementoHeap(idx, dist);
            subir(tamano);
            tamano++;
        }

        public ElementoHeap extraerMinimo() {
            if (tamano == 0) return null;
            ElementoHeap minimo = heap[0];
            heap[0] = heap[tamano - 1];
            tamano--;
            if (tamano > 0) bajar(0);
            return minimo;
        }

        private void subir(int i) {
            while (i > 0) {
                int padre = (i - 1) / 2;
                if (heap[i].distancia >= heap[padre].distancia) break;
                swap(i, padre);
                i = padre;
            }
        }

        private void bajar(int i) {
            while (2 * i + 1 < tamano) {
                int izq = 2 * i + 1;
                int der = 2 * i + 2;
                int menor = izq;
                if (der < tamano && heap[der].distancia < heap[izq].distancia) menor = der;
                if (heap[i].distancia <= heap[menor].distancia) break;
                swap(i, menor);
                i = menor;
            }
        }

        private void swap(int i, int j) {
            ElementoHeap temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    /**
     * Algoritmo de Dijkstra O(E log V) — Ruta de Mayor Activación.
     */
    public ResultadoDijkstra dijkstra(String idOrigen, String idDestino) {
        if (!red.existeNodo(idOrigen) || !red.existeNodo(idDestino))
            return new ResultadoDijkstra(Double.MAX_VALUE, new String[0], false);

        String[] ids = red.getIdsNodos();
        int n = ids.length;

        HashIndice mapaIndice = new HashIndice(n * 2 + 1);
        for (int i = 0; i < n; i++) mapaIndice.put(ids[i], i);

        int src = mapaIndice.get(idOrigen);
        int dst = mapaIndice.get(idDestino);

        double[] dist   = new double[n];
        int[] previo = new int[n];

        for (int i = 0; i < n; i++) { 
            dist[i] = Double.MAX_VALUE; 
            previo[i] = -1; 
        }
        dist[src] = 0.0;

        // Instanciamos nuestro MinHeap en lugar del arreglo de visitados
        MinHeapCustom heap = new MinHeapCustom(n);
        heap.insertar(src, 0.0);

        while (!heap.estaVacia()) {
            ElementoHeap actual = heap.extraerMinimo();
            int u = actual.indiceNodo;

            // Optimización: Si llegamos al destino, cortamos
            if (u == dst) break;

            // Descartamos distancias viejas que se quedaron en la cola
            if (actual.distancia > dist[u]) continue;

            NodoAdyacencia vecino = red.getListaAdyacencia(ids[u]);
            while (vecino != null) {
                double peso = calcularPesoSinapsis(vecino.getSinapsis());
                int v = mapaIndice.get(vecino.getIdDestino());
                
                // Relajación de la arista
                if (v >= 0 && dist[u] + peso < dist[v]) {
                    dist[v] = dist[u] + peso;
                    previo[v] = u;
                    heap.insertar(v, dist[v]); // Insertamos al Heap en lugar de buscar a pie luego
                }
                vecino = vecino.siguiente;
            }
        }

        if (dist[dst] == Double.MAX_VALUE)
            return new ResultadoDijkstra(Double.MAX_VALUE, new String[0], false);

        return new ResultadoDijkstra(dist[dst], reconstruirRuta(previo, ids, src, dst), true);
    }

    /**
     * Calcula el peso real de una Sinapsis consultando el RepoQuimico.
     */
    private double calcularPesoSinapsis(Sinapsis s) {
        BioMensajero bm = repoQuimico.buscar(s.getIdBioMensajero());
        return bm == null ? Double.MAX_VALUE : s.calcularPeso(bm.getVelocidad());
    }

    /**
     * Reconstruye la ruta desde el arreglo de predecesores.
     */
    private String[] reconstruirRuta(int[] previo, String[] ids, int src, int dst) {
        int longitud = 0, actual = dst;
        while (actual != -1) { longitud++; if (actual == src) break; actual = previo[actual]; }
        String[] ruta = new String[longitud];
        actual = dst;
        for (int i = longitud - 1; i >= 0; i--) { ruta[i] = ids[actual]; actual = previo[actual]; }
        return ruta;
    }

    /**
     * Hash auxiliar interno para mapeo ID → índice en Dijkstra.
     */
    private static class HashIndice {
        private static class Entrada {
            String clave; int valor; Entrada siguiente;
            Entrada(String c, int v) { clave = c; valor = v; }
        }
        private Entrada[] tabla;
        private int cap;
        HashIndice(int cap) { this.cap = cap; this.tabla = new Entrada[cap]; }
        private int hash(String s) {
            int h = 0;
            for (int i = 0; i < s.length(); i++) h = (h * 31 + s.charAt(i)) % cap;
            return Math.abs(h);
        }
        void put(String clave, int valor) {
            int i = hash(clave);
            for (Entrada e = tabla[i]; e != null; e = e.siguiente)
                if (e.clave.equals(clave)) { e.valor = valor; return; }
            Entrada n = new Entrada(clave, valor); n.siguiente = tabla[i]; tabla[i] = n;
        }
        int get(String clave) {
            for (Entrada e = tabla[hash(clave)]; e != null; e = e.siguiente)
                if (e.clave.equals(clave)) return e.valor;
            return -1;
        }
    }

    public void setRed(RedConectiva red) { this.red = red; }
    public void setRepoQuimico(RepoQuimico repoQuimico) { this.repoQuimico = repoQuimico; }
}

