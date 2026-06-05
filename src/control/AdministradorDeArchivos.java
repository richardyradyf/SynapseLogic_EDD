/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import estructuras.RedConectiva;
import estructuras.RepoQuimico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import modelos.BioMensajero;
import modelos.NodoNeural;
import modelos.Sinapsis;

/**
 *
 * @author Richard Yrady, Andres Arrieta, Leonardo Signorino
 */
public class AdministradorDeArchivos {
    
    private static final String SEPARADOR         = ",";
    private static final int    COLUMNAS_RED       = 5;
    private static final int    COLUMNAS_DICC      = 5;

    /**
     * Resultado de la carga de un archivo CSV.
     */
    public static class ResultadoCarga {
        /** Registros cargados exitosamente. */
        public int registrosCargados;
        /** Líneas con errores de formato. */
        public int lineasConError;
        /** Mensaje descriptivo del resultado. */
        public String mensaje;
        /** {@code true} si la carga fue exitosa. */
        public boolean exitosa;

        /**
         * Constructor del ResultadoCarga.
         * @param cargados  Registros cargados.
         * @param errores   Líneas con error.
         * @param mensaje   Mensaje descriptivo.
         * @param exitosa   {@code true} si fue exitosa.
         */
        public ResultadoCarga(int cargados, int errores, String mensaje, boolean exitosa) {
            this.registrosCargados = cargados;
            this.lineasConError    = errores;
            this.mensaje           = mensaje;
            this.exitosa           = exitosa;
        }
    }

    /**
     * Carga la red neuronal desde un CSV.
     * Formato: {@code origen,destino,distancia,ID_BioMensajero,coheficiente}
     *
     * @param archivo     Archivo CSV.
     * @param red         RedConectiva destino.
     * @param repoQuimico RepoQuimico para validación.
     * @return ResultadoCarga con estadísticas.
     */
    public ResultadoCarga cargarRedNeuronal(File archivo, RedConectiva red,
                                             RepoQuimico repoQuimico) {
        if (!validarArchivo(archivo))
            return new ResultadoCarga(0, 0, "Archivo inválido: " + archivo.getName(), false);

        int cargados = 0, errores = 0, linea = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String s;
            while ((s = br.readLine()) != null) {
                linea++;
                s = s.trim();
                if (s.isEmpty()) continue;
                if (linea == 1 && s.toLowerCase().startsWith("origen")) continue;

                String[] c = s.split(SEPARADOR, -1);
                if (c.length < COLUMNAS_RED) { errores++; continue; }

                try {
                    String idO = c[0].trim(), idD = c[1].trim(), idBM = c[3].trim();
                    double dist = Double.parseDouble(c[2].trim());
                    double k    = Double.parseDouble(c[4].trim());

                    if (idO.isEmpty() || idD.isEmpty() || idBM.isEmpty()) { errores++; continue; }

                    if (!red.existeNodo(idO))  red.agregarNodo(new NodoNeural(idO));
                    if (!red.existeNodo(idD))  red.agregarNodo(new NodoNeural(idD));
                    red.agregarSinapsis(new Sinapsis(idO, idD, dist, idBM, k));
                    cargados++;
                } catch (NumberFormatException e) { errores++; }
            }
        } catch (IOException e) {
            return new ResultadoCarga(cargados, errores, "Error: " + e.getMessage(), false);
        }

        String msg = "Red cargada: " + cargados + " sinapsis"
                + (errores > 0 ? " (" + errores + " errores)" : "");
        return new ResultadoCarga(cargados, errores, msg, cargados > 0);
    }

    /**
     * Carga el diccionario de BioMensajeros desde un CSV.
     * Formato: {@code id,nombre,efecto,velocidad,descripcion}
     *
     * @param archivo     Archivo CSV del diccionario.
     * @param repoQuimico RepoQuimico destino.
     * @return ResultadoCarga con estadísticas.
     */
    public ResultadoCarga cargarDiccionario(File archivo, RepoQuimico repoQuimico) {
        if (!validarArchivo(archivo))
            return new ResultadoCarga(0, 0, "Archivo inválido: " + archivo.getName(), false);

        int cargados = 0, errores = 0, linea = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String s;
            while ((s = br.readLine()) != null) {
                linea++;
                s = s.trim();
                if (s.isEmpty()) continue;
                if (linea == 1 && s.toLowerCase().startsWith("id")) continue;

                String[] c = parsearLineaCSV(s);
                if (c.length < COLUMNAS_DICC || c[0] == null) { errores++; continue; }

                try {
                    String id     = c[0].trim();
                    String nombre = c[1].trim();
                    String efecto = c[2].trim();
                    double vel    = Double.parseDouble(c[3].trim());
                    String desc   = c[4] == null ? "" : c[4].trim().replace("\"", "");

                    if (id.isEmpty()) { errores++; continue; }
                    repoQuimico.insertar(id, new BioMensajero(id, nombre, efecto, vel, desc));
                    cargados++;
                } catch (NumberFormatException e) { errores++; }
            }
        } catch (IOException e) {
            return new ResultadoCarga(cargados, errores, "Error: " + e.getMessage(), false);
        }

        String msg = "Diccionario cargado: " + cargados + " BioMensajeros"
                + (errores > 0 ? " (" + errores + " errores)" : "");
        return new ResultadoCarga(cargados, errores, msg, cargados > 0);
    }

    /**
     * Valida que el archivo exista, sea legible y tenga extensión .csv o .txt.
     *
     * @param archivo Archivo a validar.
     * @return {@code true} si es válido.
     */
    public boolean validarArchivo(File archivo) {
        if (archivo == null || !archivo.exists() || !archivo.isFile()) return false;
        if (!archivo.canRead()) return false;
        String n = archivo.getName().toLowerCase();
        return n.endsWith(".csv") || n.endsWith(".txt");
    }

    /**
     * Parsea una línea CSV respetando campos entre comillas dobles.
     *
     * @param linea Línea a parsear.
     * @return Arreglo de campos.
     */
    private String[] parsearLineaCSV(String linea) {
        String[] resultado = new String[COLUMNAS_DICC];
        int campo = 0;
        StringBuilder sb = new StringBuilder();
        boolean enComillas = false;

        for (int i = 0; i < linea.length() && campo < COLUMNAS_DICC - 1; i++) {
            char ch = linea.charAt(i);
            if (ch == '"') { enComillas = !enComillas; }
            else if (ch == ',' && !enComillas) { resultado[campo++] = sb.toString(); sb.setLength(0); }
            else { sb.append(ch); }
        }

        // Buscar inicio del último campo
        int consumido = 0, contados = 0;
        boolean enQ = false;
        for (int i = 0; i < linea.length(); i++) {
            char ch = linea.charAt(i);
            if (ch == '"') enQ = !enQ;
            else if (ch == ',' && !enQ) {
                contados++;
                if (contados == COLUMNAS_DICC - 1) { consumido = i + 1; break; }
            }
        }
        resultado[COLUMNAS_DICC - 1] = (consumido > 0 && consumido < linea.length())
                ? linea.substring(consumido) : sb.toString();
        return resultado;
    }
    
}
