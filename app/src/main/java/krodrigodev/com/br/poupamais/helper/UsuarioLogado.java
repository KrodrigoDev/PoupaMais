package krodrigodev.com.br.poupamais.helper;

public class UsuarioLogado {

    // atributo
    private static int idUsuarioLogado;
    private static String nomeUsuarioLogado;

    // gets e sets
    public static int getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public static void setIdUsuarioLogado(int idUsuario) {
        idUsuarioLogado = idUsuario;
    }

    public static String getNomeUsuarioLogado() {
        return nomeUsuarioLogado;
    }

    public static void setNomeUsuarioLogado(String nomeUsuario) {
        nomeUsuarioLogado = nomeUsuario;
    }
}
