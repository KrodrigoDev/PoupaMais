package krodrigodev.com.br.poupamais.activity;

import static krodrigodev.com.br.poupamais.helper.DataAtual.dataFormatada;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.DataAtual;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.model.Movimentacao;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 17/09/2023
 */
public class AdicionarDespesa extends AppCompatActivity {

    // atributos
    private TextInputEditText campoData, campoDescricao, campoCategoria;
    private EditText campoValor;
    private MovimentacaoDao movimentacaoDao;
    private double despesaTotal;
    private UsuarioDao usuarioDao;
    private final String TIPOMOVIMENTO = "despesa";
    private final String NOMECOLUNA = "totaldespesa";


    @RequiresApi(api = Build.VERSION_CODES.O) //talvez procurar outra solução (DEPOIS)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesa);

        // inicializando o movimentoDAO e usuarioDao
        movimentacaoDao = new MovimentacaoDao(this);
        usuarioDao = new UsuarioDao(this);

        // fazendo referências
        campoData = findViewById(R.id.campoDataDespesa);
        campoDescricao = findViewById(R.id.campoDescricaoDespesa);
        campoCategoria = findViewById(R.id.campoCategoriaDespesa);
        campoValor = findViewById(R.id.campoValorDespesa);


        //fazendo a modificação no texto da data para ao entrar puxar a data atual
        campoData.setText(DataAtual.getDataFormatada());

        // fazendo a despesa ser recuperada antes que o usuário digite um novo valor
        recuperandoDespesa();
    }

    // método para salvar uma despesa
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarDespesa(View view) {

        String data = campoData.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String valor = campoValor.getText().toString();

        // validando de todos os campos foram preenchidos
        if (data.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || valor.isEmpty()) {

            Toast.makeText(getApplicationContext(), R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            Movimentacao movimentacao = new Movimentacao();

            double despesaDigitada = Double.parseDouble(valor);

            movimentacao.setData(LocalDate.parse(data, dataFormatada)); // melhorar isso depois
            movimentacao.setDescricao(descricao);
            movimentacao.setCategoria(categoria);
            movimentacao.setValor(despesaDigitada);
            movimentacao.setTipo(TIPOMOVIMENTO);
            movimentacao.setId_usuario(UsuarioLogado.getIdUsuarioLogado());

            // realizando a soma das despesas antes de salvar
            double despesaAtualizada = despesaDigitada + despesaTotal;

            // atualizando a despesa total da minha tabela usuário
            atualizandoDespesa(despesaAtualizada);

            // salvando
            movimentacaoDao.salvarMovimentacao(movimentacao);

            // limpando os campos após salvar
            limpaCampo();

            // mensagem de confirmação para o usuário
            Toast.makeText(getApplicationContext(), R.string.despesa_adicionada, Toast.LENGTH_SHORT).show();

            // voltando para janela principal (Talvez sejá necessário alterar depois)
            finish();
        }

    }

    // recuperando a despesa do usuário para realizar uma soma com a nova despesa adicionada
    public void recuperandoDespesa() {
        despesaTotal = usuarioDao.recuperarTotal(UsuarioLogado.getIdUsuarioLogado(), NOMECOLUNA);
    }

    // método para atualizar a despesa total na conta do usuário
    public void atualizandoDespesa(double despesa) {
        usuarioDao.alterarDespesaTotal(UsuarioLogado.getIdUsuarioLogado(), despesa, NOMECOLUNA);
    }

    // método para limpar os campos
    public void limpaCampo() {
        campoData.setText("");
        campoCategoria.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
    }

}