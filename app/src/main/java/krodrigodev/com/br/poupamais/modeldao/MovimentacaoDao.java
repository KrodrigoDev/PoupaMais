package krodrigodev.com.br.poupamais.modeldao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import krodrigodev.com.br.poupamais.conexao.BancoDados;
import krodrigodev.com.br.poupamais.model.Movimentacao;

public class MovimentacaoDao {

    // atributos
    private final BancoDados conexao;  // verificar depois se vai ser final ou não
    private final SQLiteDatabase banco;

    // método para realizar conexão
    public MovimentacaoDao(Context context) {
        conexao = new BancoDados(context);
        banco = conexao.getWritableDatabase();
    }

    // método para adicionar uma movimentação
    public long salvarMovimentacao(Movimentacao movimentacao) {

        ContentValues valores = new ContentValues();
        valores.put("data", movimentacao.getData().toString());
        valores.put("descricao", movimentacao.getDescricao());
        valores.put("categoria", movimentacao.getCategoria());
        valores.put("valor", movimentacao.getValor());
        valores.put("tipo", movimentacao.getTipo());
        valores.put("id_usuario", movimentacao.getId_usuario());

        return banco.insert("movimentacao", null, valores);

    }


}
