package krodrigodev.com.br.poupamais.model;

import java.io.Serializable;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 16/09/2023
 */
public class Usuario implements Serializable {

    // atributos
    private String nome;
    private String email;
    private String senha;
    private double totalLucro;
    private double totalDespesa;

    // construtor
    public Usuario(String nome, String email,String senha){
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setTotalDespesa(0.00);
        setTotalLucro(0.00);
    }

    // gets e sets

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

    public void setTotalLucro(double totalLucro) {
        this.totalLucro = totalLucro;
    }

    public void setTotalDespesa(double totalDespesa) {
        this.totalDespesa = totalDespesa;
    }

    public double getTotalLucro() {
        return totalLucro;
    }

    public double getTotalDespesa() {
        return totalDespesa;
    }

}
