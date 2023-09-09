package krodrigodev.com.br.poupamais.model;

import java.io.Serializable;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class Usuario implements Serializable {

    // atributos
    private int id;
    private String nome;
    private String email;

    private String senha;

    // gets e sets

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() { // gambiarra
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
