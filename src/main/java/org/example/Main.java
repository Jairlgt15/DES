package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean salir = false;
        TripleDes des = new TripleDes("9mng65v8jf4lxn93nabf981m", "a76nb5h9");
        int opcion; //Guardaremos la opcion del usuario
        do {
            System.out.println("3DES");
            System.out.println("1. Opcion 1");
            System.out.println("2. Opcion 2");
            System.out.println("3. Salir 3");
            System.out.println("Seleccione una de las opciones");
            opcion = Integer.parseInt(br.readLine());
            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionado la opcion 1");
                    System.out.println("Ingrese la fórmula secreta que será cifrada");
                    String nombre = br.readLine();
                    Archivos archivos = new Archivos();
                    byte[] encode = archivos.CrearArchivo(nombre);
                    String nombreCifrado = des.cifrarByte(encode);
                    System.out.println(nombreCifrado);

                    //TODO aqui va la parte del documento a editar
                    break;
                case 2:
                    System.out.println("Has seleccionado la opcion 2");
                    System.out.println("La formula secreta es: ");
                    break;
                //TODO Aqui se muestra el mensaje cifrado con la opcionde poder descifrar o no
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Solo números entre 1 y 3");
            }
        }
        while (!salir);
    }

}