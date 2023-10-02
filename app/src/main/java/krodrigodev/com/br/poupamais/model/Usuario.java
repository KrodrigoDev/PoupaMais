package krodrigodev.com.br.poupamais.model;

import java.io.Serializable;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 16/09/2023
 */
public class Usuario implements Serializable {

    // atributos
    private int id;
    private String nome;
    private String email;
    private String senha;
    private double totalLucro = 0.00; //inicializando uma conta com saldo 0.00
    private double totalDespesa = 0.00;

    // construtor
    public Usuario(){

    }

    // gets e sets

    public int getId() {
        return id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalLucro() {
        return totalLucro;
    }

    public void setTotalLucro(double totalLucro) {
        this.totalLucro = totalLucro;
    }

    public double getTotalDespesa() {
        return totalDespesa;
    }

    public void setTotalDespesa(double totalDespesa) {
        this.totalDespesa = totalDespesa;
    }

}
