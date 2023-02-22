package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Archivos {

    public Archivos() {
    }



    public FileWriter writeString(String nombreArchivo, String content) throws Exception {
        FileWriter fichero = null;
        PrintWriter pw = null;
        String ruta = "C:/Users/Jair";
        try {
            fichero = new FileWriter(ruta + "\\" + nombreArchivo + ".txt");
            pw = new PrintWriter(fichero);
            pw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return fichero;
    }

    public String readString(String nombreArchivo) throws Exception {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String ruta = "C:/Users/Jair";

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(ruta + "\\" + nombreArchivo + ".txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null)
                return linea;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "no hay nada";
    }
}
