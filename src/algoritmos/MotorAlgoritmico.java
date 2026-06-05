/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import estructuras.Cola;
import estructuras.NodoAdyacencia;
import estructuras.RedConectiva;
import estructuras.RepoQuimico;
import modelos.NodoNeural;

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
}
