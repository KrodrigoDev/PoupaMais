package krodrigodev.com.br.poupamais.controller;

public class ValidarEmail {

    // Função para validar o formato do e-mail com Patterns.EMAIL_ADDRESS
    public static boolean emailValido(CharSequence email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            // Verificar se o domínio é válido
            String[] partesEmail = email.toString().split("@"); // dividindo o email em duas partes

            if (partesEmail.length == 2) { // verifica se foi feita a divisão

                String dominio = partesEmail[1];

                return !dominio.contains(".") || dominio.length() <= 2; // Verificar se contém pelo menos um ponto e tem mais de 2 caracteres.

            }

        }
        return true;
    }

}
