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
        Archivos archivos = new Archivos();
        int opcion; //Guardaremos la opcion del usuario
        do {
            System.out.println("-----------------\t3DES\t--------------------------------");
            System.out.println("ESCRIBIR LA RECETA SECRETA. Opción 1");
            System.out.println("CONOCER LA RECETA SECRETA. Opción 2");
            System.out.println("SALIR. Opción 3");
            System.out.println("Seleccione una de las opciones");
            opcion = Integer.parseInt(br.readLine());
            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionado la opcion 1");
                    System.out.println("Ingrese la fórmula secreta que será cifrada");
                    String nombre = br.readLine();
                    String rutaNombre = des.cifrar(nombre);
                    archivos.writeString("prueba", rutaNombre);
                    break;
                case 2:
                    System.out.println("Has seleccionado la opcion 2");
                    System.out.println("La formula secreta es: ");
                    String cifrado = archivos.readString("prueba");
                    System.out.println(cifrado);
                    boolean salir1 = false;

                    System.out.println("¿Desea revelar la fórmula secreta");
                    System.out.println("Sí. Opcion 1");
                    System.out.println("Nó. Opcion 2");
                    System.out.println("Seleccione una de las opciones");
                    cifrado = archivos.readString("prueba");
                    opcion = Integer.parseInt(br.readLine());
                    switch (opcion) {
                        case 1:
                            System.out.println("Ingrese la clave para revelar");
                            String clave = br.readLine();
                            TripleDes desC = new TripleDes(clave, "a76nb5h9");
                            try {
                                String mensajeDescifrado = desC.descifrar(cifrado);
                                System.out.println(mensajeDescifrado);
                            }catch (Exception e){
                                System.out.println("INTRUSO-CLAVE INCORRECTA");
                            }


                            break;
                        case 2:
                            System.out.println("Hasta luego");
                            break;
                        default:
                            System.out.println("Solo números entre 1 y 2");
                    }
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