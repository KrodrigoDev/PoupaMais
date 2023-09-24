package krodrigodev.com.br.poupamais.modeldao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import krodrigodev.com.br.poupamais.conexao.BancoDados;
import krodrigodev.com.br.poupamais.controller.EncriptaMD5;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.model.Usuario;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class UsuarioDao {

    // atributos
    private BancoDados conexao;
    private SQLiteDatabase banco;

    // método para realizar conexão
    public UsuarioDao(Context context) {
        conexao = new BancoDados(context);
        banco = conexao.getWritableDatabase();
    }

    // Método para inserir um usuário no banco de dados
    public void inserirUsuario(Usuario usuario) {

        // Obtém a senha do usuário e a criptografa
        String senhaCriptografada = EncriptaMD5.encriptaSenha(usuario.getSenha());

        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("senha", senhaCriptografada);
        valores.put("totallucro", usuario.getTotalLucro());
        valores.put("totaldespesa", usuario.getTotalDespesa());


        banco.insert("usuario", null, valores);
    }


    // método para realizar login
    public boolean validaLogin(String email, String senha) {

        try (Cursor cursor = banco.rawQuery("select id, nome from usuario where email = ? and senha = ?", new String[]{email, senha})) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                int idIndex = cursor.getColumnIndex("id");
                int nomeIndex = cursor.getColumnIndex("nome");

                if (idIndex != -1 || nomeIndex != -1) { // Verifica se a coluna "id" foi encontrada

                    int idUsuario = cursor.getInt(idIndex);
                    String nomeUsuario = cursor.getString(nomeIndex);

                    Log.d("Nome usuário","Recuperado" + nomeUsuario);

                    // pegando o id e o nome do usuário após o login
                    UsuarioLogado.setNomeUsuarioLogado(nomeUsuario);
                    UsuarioLogado.setIdUsuarioLogado(idUsuario);
                    return true;

                }

            }

        }

        return false;
    }

    // método para verificar se o e-mail já está presente na basse de dados
    public boolean validaEmailExitentes(String email) {
        try (Cursor cursor = banco.rawQuery("select email from usuario where email = ?", new String[]{email})) {
            return cursor.getCount() > 0;
        }
    }

    // método para me retornar a despesa total e o lucro total salvos na conta do usuário
    public double recuperarTotal(int id, String coluna) {

        double total = 0.0;

        Cursor cursor = banco.rawQuery("SELECT " + coluna + " FROM usuario WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(coluna);

            if (columnIndex != -1) {
                total = cursor.getDouble(columnIndex);
            }
        }

        cursor.close();

        return total;
    }

    // método para atualizar a despesa total do meu usuário
    public void alterarDespesaTotal(int id, double valorAtualizado, String coluna) {
        ContentValues valores = new ContentValues();
        valores.put(coluna, valorAtualizado);

        banco.update("usuario", valores, "id = ?", new String[]{String.valueOf(id)});

    }


}
