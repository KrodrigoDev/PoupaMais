package krodrigodev.com.br.poupamais.controller;

public class ValidarEmail {

    public static boolean emailValido(String email) {

        int meioEmail = email.indexOf('@');

        if (meioEmail > 0) {

            String parteDoisEmail = email.substring(meioEmail + 1, email.length() - 1);

            return parteDoisEmail.endsWith(".com");
        }

        return false;
    }


}
