package krodrigodev.com.br.poupamais.helper;

/**
 * @author Kauã Rodrigo
 * @since 01/10/2023
 */
public class UsuarioLogado {

    // atributo para os usuários logados localmente
    private static String nomeUsuarioLocal;
    private static String email;

    // método para "sair" da conta
    public static void sair(){
        setEmail(null);
        setNomeUsuarioLocal(null);
    }

    // gets e sets do usuário local
    public static String getNomeUsuarioLocal() {
        return nomeUsuarioLocal;
    }

    public static void setNomeUsuarioLocal(String nomeUsuario) {
        nomeUsuarioLocal = nomeUsuario;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UsuarioLogado.email = email;
    }
}
