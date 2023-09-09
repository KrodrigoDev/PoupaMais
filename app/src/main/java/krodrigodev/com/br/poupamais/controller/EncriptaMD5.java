package krodrigodev.com.br.poupamais.controller;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptaMD5 {

    // Método para encriptar a senha do usuário usando o algoritmo MD5
    public static String encriptaSenha(String senha) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // Converte a senha em bytes
            byte[] senhaBytes = senha.getBytes("UTF-8");

            // Atualiza o MessageDigest com os dados (senha)
            md5.update(senhaBytes);

            // Calcula o hash MD5 da senha
            byte[] hash = md5.digest();

            // Converte o hash para uma representação hexadecimal
            StringBuilder senhaCriptografada = new StringBuilder();
            for (byte b : hash) {
                senhaCriptografada.append(String.format("%02x", b));
            }

            // Retorna a senha criptografada como string hexadecimal
            return senhaCriptografada.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException erro) {

            Log.e("Erro", "Não foi possível criptografar a senha: " + erro);
            return null;

        }
    }

}
