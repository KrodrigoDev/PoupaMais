package krodrigodev.com.br.poupamais.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.model.Movimentacao;

/**
 * @author Kauã Rodrigo
 * @since 30/09/2023
 */
public class AdpterMovimentacoes extends RecyclerView.Adapter<AdpterMovimentacoes.Reciclador> {

    // atributos
    private List<Movimentacao> movimentacoes;
    Context context;

    // construtor
    public AdpterMovimentacoes(List<Movimentacao> movimentacao, Context context) {
        this.movimentacoes = movimentacao;
        this.context = context;
    }

    // método para atualizar as movimentações
    @SuppressLint("NotifyDataSetChanged")
    public void atualizarMovimentacoes(List<Movimentacao> novasMovimentacoes) {
        movimentacoes.clear();
        movimentacoes.addAll(novasMovimentacoes);
        notifyDataSetChanged(); // Notificar o adaptador de que os dados foram alterados
    }

    // método que vai fazer as reciclagens dos meus componentes de tela
    @NonNull
    @Override
    public Reciclador onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // convertendo meu xml em view
        View itemMovimentacao = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_movimentacao, parent, false);

        // passando o itemMovimentacao para o construtor da classe interna
        return new Reciclador(itemMovimentacao);
    }

    // método que vai fazer com que os objetos sejam visíveis
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Reciclador holder, int position) {
        Movimentacao movimentacao = movimentacoes.get(position);

        holder.categoria.setText(movimentacao.getCategoria());
        holder.descricao.setText(movimentacao.getDescricao());
        holder.valor.setText(String.valueOf(movimentacao.getValor()));

        if (movimentacao.getTipo().equals("despesa")) {
            holder.valor.setTextColor(ContextCompat.getColor(context, R.color.vermelhoDespesa));
            holder.valor.setText("-" + movimentacao.getValor());
        } else {
            holder.valor.setTextColor(ContextCompat.getColor(context, R.color.verdeLucro));
        }

    }


    // método que vai definir a quantidade  de itens mostrados (base no tamanho da lista)
    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    // classe interna (Inner class)
    public static class Reciclador extends RecyclerView.ViewHolder {

        // atributos
        TextView descricao, valor, categoria;

        // construtor
        public Reciclador(@NonNull View itemView) {
            super(itemView);

            // inicialização
            descricao = itemView.findViewById(R.id.textDescricaoAdpter);
            categoria = itemView.findViewById(R.id.textCategoriaAdpter);
            valor = itemView.findViewById(R.id.textValorAdpter);
        }

    }
}
