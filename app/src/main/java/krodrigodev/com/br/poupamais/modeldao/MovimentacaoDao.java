package krodrigodev.com.br.poupamais.modeldao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import krodrigodev.com.br.poupamais.conexao.BancoDados;
import krodrigodev.com.br.poupamais.model.Movimentacao;

public class MovimentacaoDao {

    // atributos
    private BancoDados conexao;
    private SQLiteDatabase banco;

    // método para realizar conexão
    public MovimentacaoDao(Context context) {
        conexao = new BancoDados(context);
        banco = conexao.getWritableDatabase();
    }

    // método para adicionar uma movimentação
    public void salvarMovimentacao(Movimentacao movimentacao) {

        ContentValues valores = new ContentValues();
        valores.put("data", movimentacao.getData().toString());
        valores.put("descricao", movimentacao.getDescricao());
        valores.put("categoria", movimentacao.getCategoria());
        valores.put("valor", movimentacao.getValor());
        valores.put("tipo", movimentacao.getTipo());
        valores.put("id_usuario", movimentacao.getId_usuario());

        banco.insert("movimentacao", null, valores);

    }

    // método para recuperar a movimentação com base no mês
    public List<Movimentacao> recuperarMovimentacaoMes(String dataCalendario,int idUsuario) {

        try (Cursor cursor = banco.rawQuery("SELECT categoria, descricao, valor, tipo FROM movimentacao WHERE strftime('%Y-%m', data) = ? AND id_usuario = ?", new String[]{dataCalendario, String.valueOf(idUsuario)})) {

            List<Movimentacao> movimentacoes = new ArrayList<>();

            while (cursor.moveToNext()) {
                Movimentacao movimentacao = new Movimentacao();

                movimentacao.setCategoria(cursor.getString(0));
                movimentacao.setDescricao(cursor.getString(1));
                movimentacao.setValor(cursor.getDouble(2));
                movimentacao.setTipo(cursor.getString(3));
                movimentacoes.add(movimentacao);
            }

            return movimentacoes;

        } catch (SQLiteException erro) {

            Log.d("Erro Listagem", "Erro : " + erro);

        }

        return null;
    }



}
