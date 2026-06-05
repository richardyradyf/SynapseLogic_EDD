package estructuras;

import modelos.Sinapsis;

public class NodoAdyacencia {

    private String idDestino;

    private Sinapsis sinapsis;

    public NodoAdyacencia siguiente;

    /**
     * Constructor de NodoAdyacencia.
     *
     * @param idDestino ID del NodoNeural destino.
     * @param sinapsis  Sinapsis asociada a esta arista dirigida.
     */
    public NodoAdyacencia(String idDestino, Sinapsis sinapsis) {
        this.idDestino = idDestino;
        this.sinapsis = sinapsis;
        this.siguiente = null;
    }

    /** @return ID del NodoNeural destino. */
    public String getIdDestino() { return idDestino; }

    /** @param idDestino Nuevo ID destino. */
    public void setIdDestino(String idDestino) { this.idDestino = idDestino; }

    /** @return Sinapsis de esta arista. */
    public Sinapsis getSinapsis() { return sinapsis; }
    
    /**
     * Establece o actualiza la sinapsis asociada a este nodo de adyacencia.
     * @param sinapsis 
     */
    public void setSinapsis(Sinapsis sinapsis) {
    this.sinapsis = sinapsis;
    }
}