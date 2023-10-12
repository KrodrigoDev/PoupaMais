package krodrigodev.com.br.poupamais.modeldao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

        try (Cursor cursor = banco.rawQuery("select email, nome from usuario where email = ? and senha = ?", new String[]{email, senha})) {

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                int emailIndex = cursor.getColumnIndex("email");
                int nomeIndex = cursor.getColumnIndex("nome");

                if (emailIndex != -1) { // Verifica se a coluna "email" foi encontrada

                    String emailUsuario = cursor.getString(emailIndex);
                    String nomeUsuario = cursor.getString(nomeIndex);

                    // pegando o email do usuário após o login
                    UsuarioLogado.setEmail(emailUsuario);
                    UsuarioLogado.setNomeUsuarioLocal(nomeUsuario);

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
    public double recuperarTotal(String email, String coluna) {

        double total = 0.0;

        Cursor cursor = banco.rawQuery("SELECT " + coluna + " FROM usuario WHERE email = ?", new String[]{email});

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
    public void alterarDespesaTotal(String email, double valorAtualizado, String coluna) {

        ContentValues valores = new ContentValues();

        valores.put(coluna, valorAtualizado);

        banco.update("usuario", valores, "email = ?", new String[]{email});

    }

    // método para inserir um usuário que fez login com o google
    public void inserirUsuarioGoogle(String nome, String email) {

        ContentValues valores = new ContentValues();

        UsuarioLogado.setNomeUsuarioLocal(nome);
        UsuarioLogado.setEmail(email);

        valores.put("nome", nome);
        valores.put("email", email);

        banco.insert("usuario", null, valores);

    }

    // método para atualizar os dados do meu usuário
    public void alterarDados(String novoNome, String novoEmail, String emailAntigo) {

        ContentValues valores = new ContentValues();

        valores.put("nome", novoNome);
        valores.put("email", novoEmail);

        // Atualiza os dados onde o email antigo corresponde ao email na tabela
        banco.update("usuario", valores, "email = ?", new String[]{emailAntigo});

    }


}
