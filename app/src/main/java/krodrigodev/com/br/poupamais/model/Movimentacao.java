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
    private int id, id_usuario;
    private LocalDate data;
    private String descricao, categoria, tipo;
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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
