/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class Sinapsis {
    private String idOrigen;
    private String idDestino;
    private double distancia;
    private String idBioMensajero;

    /**
     * Coeficiente de eficiencia sináptica (k).
     * Se multiplica por 1.2 al simular fatiga cognitiva.
     */
    private double coheficienteEficiencia;

    /**
     * Constructor completo de Sinapsis.
     *
     * @param idOrigen               ID del NodoNeural origen.
     * @param idDestino              ID del NodoNeural destino.
     * @param distancia              Distancia sináptica.
     * @param idBioMensajero         ID del BioMensajero usado.
     * @param coheficienteEficiencia Coeficiente de eficiencia sináptica (k).
     */
    public Sinapsis(String idOrigen, String idDestino, double distancia,
                    String idBioMensajero, double coheficienteEficiencia) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.distancia = distancia;
        this.idBioMensajero = idBioMensajero;
        this.coheficienteEficiencia = coheficienteEficiencia;
    }

    /**
     * Calcula el peso real de la sinapsis.
     * @param velocidadBioMensajero Factor V del BioMensajero.
     * @return Peso calculado.
     */
    public double calcularPeso(double velocidadBioMensajero) {
        double denominador = velocidadBioMensajero * coheficienteEficiencia;
        if (denominador <= 0) return Double.MAX_VALUE;
        return distancia / denominador;
    }

    /**
     * Aplica deterioro cognitivo por fatiga.
     * Multiplica k por 1.2.
     */
    public void aplicarFatiga() {
        this.coheficienteEficiencia *= 1.2;
    }

    /** @return ID del NodoNeural origen. */
    public String getIdOrigen() { return idOrigen; }

    /** @param idOrigen Nuevo ID de origen. */
    public void setIdOrigen(String idOrigen) { this.idOrigen = idOrigen; }

    /** @return ID del NodoNeural destino. */
    public String getIdDestino() { return idDestino; }

    /** @param idDestino Nuevo ID de destino. */
    public void setIdDestino(String idDestino) { this.idDestino = idDestino; }

    /** @return Distancia sináptica. */
    public double getDistancia() { return distancia; }

    /** @param distancia Nueva distancia. */
    public void setDistancia(double distancia) { this.distancia = distancia; }

    /** @return ID del BioMensajero asociado. */
    public String getIdBioMensajero() { return idBioMensajero; }

    /** @param idBioMensajero Nuevo ID del BioMensajero. */
    public void setIdBioMensajero(String idBioMensajero) { this.idBioMensajero = idBioMensajero; }

    /** @return Coeficiente de eficiencia sináptica (k). */
    public double getCoheficienteEficiencia() { return coheficienteEficiencia; }

    /** @param coheficienteEficiencia Nuevo valor de k. */
    public void setCoheficienteEficiencia(double coheficienteEficiencia) {
        this.coheficienteEficiencia = coheficienteEficiencia;
    }

    /** @return Representación textual de la sinapsis. */
    @Override
    public String toString() {
        return idOrigen + " -> " + idDestino
                + " | d=" + distancia
                + " | BM=" + idBioMensajero
                + " | k=" + String.format("%.4f", coheficienteEficiencia);
    }
}
