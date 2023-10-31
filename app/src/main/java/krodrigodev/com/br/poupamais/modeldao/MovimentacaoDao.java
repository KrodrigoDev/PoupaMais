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
        valores.put("email_Usuario", movimentacao.getEmail_Usuario());

        banco.insert("movimentacao", null, valores);

    }

    // método para recuperar a movimentação com base no mês
    public List<Movimentacao> recuperarMovimentacaoMes(String dataCalendario, String emailUsuario) {

        try (Cursor cursor = banco.rawQuery(
                "SELECT categoria, descricao, valor, tipo, id FROM movimentacao WHERE strftime('%Y-%m', data) = ? AND email_Usuario = ?",
                new String[]{dataCalendario, emailUsuario})) {

            List<Movimentacao> movimentacoes = new ArrayList<>();

            while (cursor.moveToNext()) {
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setCategoria(cursor.getString(0));
                movimentacao.setDescricao(cursor.getString(1));
                movimentacao.setValor(cursor.getDouble(2));
                movimentacao.setTipo(cursor.getString(3));
                movimentacao.setId(cursor.getInt(4));
                movimentacoes.add(movimentacao);
            }

            return movimentacoes;

        } catch (SQLiteException erro) {

            Log.d("Erro Listagem", "Erro : " + erro);

        }

        return null;
    }

    public List<Movimentacao> recuperarTodasMovimentacoes(String emailUsuario) {

        try (Cursor cursor = banco.rawQuery(
                "SELECT categoria, descricao, valor, tipo, id FROM movimentacao WHERE email_Usuario = ?",
                new String[]{emailUsuario})) {

            List<Movimentacao> movimentacoes = new ArrayList<>();

            while (cursor.moveToNext()) {
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setCategoria(cursor.getString(0));
                movimentacao.setDescricao(cursor.getString(1));
                movimentacao.setValor(cursor.getDouble(2));
                movimentacao.setTipo(cursor.getString(3));
                movimentacao.setId(cursor.getInt(4));
                movimentacoes.add(movimentacao);
            }

            return movimentacoes;

        } catch (SQLiteException erro) {

            Log.d("Erro Listagem", "Erro : " + erro);

        }

        return null;
    }

    public void excluirMovimentacao(int idMovimentacao) {
        try {

            String whereClause = "id = ?";

            String[] whereArgs = {String.valueOf(idMovimentacao)};

            banco.delete("movimentacao", whereClause, whereArgs);

        } catch (SQLiteException erro) {
            Log.d("Erro Exclusão", "Erro : " + erro);
        }
    }


}
