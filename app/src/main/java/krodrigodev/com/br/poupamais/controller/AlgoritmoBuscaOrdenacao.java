package krodrigodev.com.br.poupamais.controller;

import java.util.Collections;
import java.util.List;

import krodrigodev.com.br.poupamais.model.Movimentacao;

/**
 * @author Kauã Rodrigo
 * @since 25/10/2023
 */
public class AlgoritmoBuscaOrdenacao {


    public List<Movimentacao> ordenar(List<Movimentacao> movimentacoes) {

        for (int i = 0; i < movimentacoes.size() - 1; i++) {

            int menorValor = i;

            for (int j = i + 1; j < movimentacoes.size(); j++) {

                if (movimentacoes.get(j).getValor() < movimentacoes.get(menorValor).getValor()) {
                    menorValor = j;
                }

            }

            if (menorValor != i) {
                Movimentacao auxiliar = movimentacoes.get(i);
                movimentacoes.set(i, movimentacoes.get(menorValor));
                movimentacoes.set(menorValor, auxiliar);
            }

        }

        return movimentacoes;
    }

    public List<Movimentacao> buscaBinaria(List<Movimentacao> movimentacoes, double valor) {

        movimentacoes = ordenar(movimentacoes);

        int inicio = 0;
        int fim = movimentacoes.size() - 1;

        while (inicio <= fim) {

            int indiceMeio = (inicio + fim) / 2;

            double valorMeio = movimentacoes.get(indiceMeio).getValor();

            if (valorMeio == valor) {
                return Collections.singletonList(movimentacoes.get(indiceMeio));
            } else if (valorMeio < valor) {
                inicio = indiceMeio + 1;
            } else {
                fim = indiceMeio - 1;
            }

        }
        return Collections.emptyList();
    }

}
