package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TripleDes {
    private static final String ALGORITMO = "TripleDES";
    //La informacion que genera, generalmente es en bytes
    // si quiero almacenar en bd debo crear el campo con tipo byte
    // en este ejemplo vamos a transformar a string para entenderlo

    private byte[] secretKey;
    private byte[] iv;

    public TripleDes(String secretKey, String iv) {
        this.secretKey = secretKey.getBytes();
        this.iv= iv.getBytes();
    }

    //UN ALGORITMO
    //generador de claves
    private Key generateKey() {
        Key key = new SecretKeySpec(secretKey, ALGORITMO);
        return key;
    }

    private IvParameterSpec generateVector() {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        return ivSpec;
    }

    // cifrado

    public String cifrar(String mensaje) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key key = generateKey();
        IvParameterSpec ivSpec = generateVector();
        Cipher cipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        //cipher es un byte
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encodeValue = cipher.doFinal(mensaje.getBytes());
        String codeValue = Base64.getEncoder().encodeToString(encodeValue);
        return codeValue;
    }

    //descifrado
    public String descifrar(String mensajeCifrado) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key key = generateKey();
        IvParameterSpec ivSpec = generateVector();
        Cipher cipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        //cipher es un byte
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decodeValue =Base64.getDecoder().decode(mensajeCifrado);
        byte[] valorDesc = cipher.doFinal(decodeValue);
        String mensaje = new String(valorDesc);
        return mensaje;
    }
}
