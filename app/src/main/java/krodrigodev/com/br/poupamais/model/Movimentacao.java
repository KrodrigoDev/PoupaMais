package krodrigodev.com.br.poupamais.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 16/09/2023
 */
public class Movimentacao implements Serializable {

    // atributos
    private int id;
    private LocalDate data;
    private String descricao, categoria, tipo, email_Usuario;
    private double valor;

    // construtor
    public Movimentacao(){

    }

    // gets e sets

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }


    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail_Usuario() {
        return email_Usuario;
    }

    public void setEmail_Usuario(String email_Usuario) {
        this.email_Usuario = email_Usuario;
    }
}
