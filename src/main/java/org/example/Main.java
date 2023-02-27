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
import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String jdbcURL = "jdbc:h2:file:C:\\Program Files\\Java\\h2\\bin\\user_db";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) throws Exception {

        try {
            int rol = 0;
            PreparedStatement statementa;
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, USER, PASS);
            System.out.println("Connected to H2 in-memory database.");

            System.out.println("Por favor ingresa el nombre de usuario");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String nombreu = br.readLine();

            System.out.println("Por favor ingresa el password");
            String PW = br.readLine();
            statementa = connection.prepareStatement("select * from usuario where nombre=? and password=?");
            statementa.setString(1, nombreu);
            statementa.setString(2, PW);


            ResultSet rs = statementa.executeQuery();
            if (rs.next()) {
                nombreu = rs.getString(2);
                System.out.println("Bienvenido: " + nombreu);
                rol = rs.getInt(4);


                boolean salir = false;
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
                            if (rol == 0) {
                                System.out.println("Ingrese la fórmula secreta que será cifrada");
                                String nombre = br.readLine();
                                System.out.println("Ingrese la clave para salvaguardar la fórmula");
                                System.out.println("OJO- Debe ser de 24 bytes|");
                                String claveS = br.readLine();
                                TripleDes des = new TripleDes(claveS, "a76nb5h9");
                                String rutaNombre = des.cifrar(nombre);

                                statementa = connection.prepareStatement(" update receta set valor=? where id=1");
                                statementa.setString(1, rutaNombre);
                                int nuevo = statementa.executeUpdate();
                                if (nuevo>=1) {
                                    System.out.println("INGRESADO");
                                } else {
                                    System.out.println("ERROR");
                                }


                                archivos.writeString("prueba", rutaNombre);
                            } else {
                                System.out.println("No tiene los permisos necesarios");
                            }
                            break;
                        case 2:
                            System.out.println("Has seleccionado la opcion 2");
                            System.out.println("La formula secreta es: ");
                            statementa = connection.prepareStatement("select * from receta where id=1");
                            rs = statementa.executeQuery();
                            if (rs.next()) {
                                String cifrado = rs.getString(2);
                                System.out.print("Bienvenido: " + cifrado);


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
                                        } catch (Exception e) {
                                            System.out.println("INTRUSO-CLAVE INCORRECTA");
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Hasta luego");
                                        break;
                                    default:
                                        System.out.println("Solo números entre 1 y 2");
                                }
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

                connection.close();
            } else {
                System.out.print("Credenciales incorrectas");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}