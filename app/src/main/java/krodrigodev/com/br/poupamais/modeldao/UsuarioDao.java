package krodrigodev.com.br.poupamais.modeldao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import krodrigodev.com.br.poupamais.conexao.BancoDados;
import krodrigodev.com.br.poupamais.controller.EncriptaMD5;
import krodrigodev.com.br.poupamais.helper.IdUsuarioLogado;
import krodrigodev.com.br.poupamais.model.Usuario;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class UsuarioDao {

    // atributos
    private final BancoDados conexao;  // verificar depois se vai ser final ou não
    private final SQLiteDatabase banco;

    // método para realizar conexão
    public UsuarioDao(Context context) {
        conexao = new BancoDados(context);
        banco = conexao.getWritableDatabase();
    }

    // Método para inserir um usuário no banco de dados
    public long inserirUsuario(Usuario usuario) {

        // Obtém a senha do usuário e a criptografa
        String senhaCriptografada = EncriptaMD5.encriptaSenha(usuario.getSenha());

        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("senha", senhaCriptografada);
        valores.put("totallucro", usuario.getTotalLucro());
        valores.put("totaldespesa", usuario.getTotalDespesa());


        return banco.insert("usuario", null, valores);
    }


    // método para realizar login
    public boolean validaLogin(String email, String senha) {
        Cursor cursor = banco.rawQuery("select * from usuario where email = ? and senha = ?", new String[]{email, senha});

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("id");

            if (columnIndex != -1) {

                int idUsuario = cursor.getInt(columnIndex);
                IdUsuarioLogado.setIdUsuarioLogado(idUsuario);
                return true;

            }

        }

        return false;
    }

    // método para verificar se o e-mail já está presente na basse de dados
    public boolean validaEmailExitentes(String email) {
        Cursor cursor = banco.rawQuery("select email from usuario where email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

}
