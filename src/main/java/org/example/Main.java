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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String jdbcURL = "jdbc:h2:./user_db";
    static final String USER = "sa";
    static final String PASS = "";
    public static void main(String[] args) throws Exception {

        try {
            Connection connection = DriverManager.getConnection(jdbcURL);
            System.out.println("TA BIEN");
            System.out.println("Connected to H2 in-memory database.");

            String sql = "Create table students (ID int primary key, name varchar(50))";

            Statement statement = connection.createStatement();

            statement.execute(sql);

            System.out.println("Created table students.");

            sql = "Insert into students (ID, name) values (1, 'Nam Ha Minh')";

            int rows = statement.executeUpdate(sql);

            if (rows > 0) {
                System.out.println("Inserted a new row.");
            }

            connection.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                    System.out.println("Ingrese la fórmula secreta que será cifrada");
                    String nombre = br.readLine();
                    System.out.println("Ingrese la clave para salvaguardar la fórmula");
                    System.out.println("OJO- Debe ser de 24 bytes|");
                    String claveS = br.readLine();
                    TripleDes des = new TripleDes(claveS, "a76nb5h9");
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