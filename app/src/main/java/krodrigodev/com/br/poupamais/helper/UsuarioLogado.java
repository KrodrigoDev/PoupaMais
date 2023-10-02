package krodrigodev.com.br.poupamais.helper;

/**
 * @author Kauã Rodrigo
 * @since 01/10/2023
 * <p>
 * Essa classe foi feita para quando o meu usuário decidir fazer um login
 * com uma conta local
 */
public class UsuarioLogado {

    // atributo
    private static String nomeUsuarioLogado;
    private static String email;

    // gets e sets
    public static String getNomeUsuarioLogado() {
        return nomeUsuarioLogado;
    }

    public static void setNomeUsuarioLogado(String nomeUsuario) {
        nomeUsuarioLogado = nomeUsuario;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UsuarioLogado.email = email;
    }
}
