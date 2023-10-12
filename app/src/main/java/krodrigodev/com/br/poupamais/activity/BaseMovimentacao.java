package krodrigodev.com.br.poupamais.activity;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.model.Movimentacao;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 02/10/2023
 * <p>
 * Essa classe serve como base para as atividades de adição de despesas e lucros,
 * fornecendo métodos e atributos comuns que podem ser herdados por essas atividades.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class BaseMovimentacao extends Activity {

    // atributos
    protected TextInputEditText campoData, campoDescricao, campoCategoria;
    protected EditText campoValor;
    protected MovimentacaoDao movimentacaoDao;
    protected double valorTotal;
    protected UsuarioDao usuarioDao;
    protected GoogleSignInAccount account;
    protected String NOMECOLUNA, TIPOMOVIMENTO;
    protected DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected LocalDate dataAtual = LocalDate.now();

    //Método para salvar uma movimentação financeira (despesa ou lucro).
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void salvarMovimentacao(View view) {

        String data = campoData.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String valor = campoValor.getText().toString();

        if (data.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || valor.isEmpty()) {

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else if (data.length() != 10) {

            Toast.makeText(this, R.string.data_invalida, Toast.LENGTH_SHORT).show();

        } else {

            try {

                dataAtual = LocalDate.parse(data, formatoData);
                double valorDigitado = Double.parseDouble(valor);

                // Criando a movimentação
                Movimentacao movimentacao = new Movimentacao(dataAtual, descricao, categoria, TIPOMOVIMENTO, UsuarioLogado.getEmail(), valorDigitado);

                // Realizando a soma das despesas antes de salvar
                double valorAtualizado = valorDigitado + valorTotal;

                // Atualizando o total de despesas ou lucros na tabela do usuário
                atualizandoValor(valorAtualizado);

                // Salvando a movimentação
                movimentacaoDao.salvarMovimentacao(movimentacao);

                // Limpando os campos após os dados serem salvos
                limpaCampo();

                // Exibindo mensagem de confirmação para o usuário
                Toast.makeText(this, R.string.movimentacao_salva, Toast.LENGTH_SHORT).show();

                // Finalizando a atividade e voltando para a janela principal
                finish();

            } catch (Exception erro) {
                Toast.makeText(this, R.string.data_invalida, Toast.LENGTH_SHORT).show();
            }

        }

    }


    // Método para recuperar o valor total das movimentações (despesas ou lucros) do usuário.
    protected void recuperandoValor() {
        valorTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), NOMECOLUNA);
    }


    // Método para atualizar o valor total das movimentações (despesas ou lucros) do usuário.
    protected void atualizandoValor(double valor) {
        usuarioDao.alterarDespesaTotal(UsuarioLogado.getEmail(), valor, NOMECOLUNA);
    }


    // Método para finalizar a atividade e voltar para a janela principal.
    protected void voltar(View view) {
        finish();
    }


    // Método para limpar os campos de entrada de dados.
    protected void limpaCampo() {
        campoData.setText("");
        campoCategoria.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
    }

}
