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
public class AdicionarLucro extends AppCompatActivity {

    // atributos
    private TextInputEditText campoData, campoDescricao, campoCategoria;
    private EditText campoValor;
    private MovimentacaoDao movimentacaoDao;
    private double lucroTotal;
    private UsuarioDao usuarioDao;
    private final String TIPOMOVIMENTO = "lucro";
    private final String NOMECOLUNA = "totallucro";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lucro);

        // inicializando o movimentoDAO e usuarioDao
        movimentacaoDao = new MovimentacaoDao(this);
        usuarioDao = new UsuarioDao(this);

        // fazendo referências
        campoData = findViewById(R.id.campoDataLucro);
        campoDescricao = findViewById(R.id.campoDescricaoLucro);
        campoCategoria = findViewById(R.id.campoCategoriaLucro);
        campoValor = findViewById(R.id.campoValorLucro);


        //fazendo a modificação no texto da data
        campoData.setText(DataAtual.getDataFormatada());

        // fazendo o lucro ser recuperado antes que o usuário digite um novo valor
        recuperandoLucro();

    }

    // método para salvar o lucro
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarLucro(View view) {

        String data = campoData.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String valor = campoValor.getText().toString();

        // validando de todos os campos foram preenchidos
        if(data.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || valor.isEmpty()){

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            Movimentacao movimentacao = new Movimentacao();

            double lucroDigitado = Double.parseDouble(valor);

            movimentacao.setData(LocalDate.parse(data,dataFormatada)); // melhorar isso depois
            movimentacao.setDescricao(descricao);
            movimentacao.setCategoria(categoria);
            movimentacao.setValor(lucroDigitado);
            movimentacao.setTipo(TIPOMOVIMENTO);
            movimentacao.setId_usuario(UsuarioLogado.getIdUsuarioLogado());

            // realizando a soma das despesas antes de salvar
            double lucroAtualizado = lucroDigitado + lucroTotal;

            // atualizando o lucro total da minha tabela usuário
            atualizandoLucro(lucroAtualizado);

            // salvando
            movimentacaoDao.salvarMovimentacao(movimentacao);

            // limpando os campos após os dados serem salvoss
            limpaCampo();

            // mensagem de confirmação para o usuário
            Toast.makeText(this, R.string.lucro_adicionado, Toast.LENGTH_SHORT).show();

            // voltando para janela principal (Talvez sejá necessário alterar depois)
            finish();
        }
    }

    // recuperando o lucro do usuário para realizar uma soma quando um novo lucro for adicionado
    public void recuperandoLucro(){
        lucroTotal = usuarioDao.recuperarTotal(UsuarioLogado.getIdUsuarioLogado(),NOMECOLUNA);
    }

    // método para atualizar a despesa total na conta do usuário
    public void atualizandoLucro(double lucro){
        usuarioDao.alterarDespesaTotal(UsuarioLogado.getIdUsuarioLogado(), lucro,NOMECOLUNA);
    }

    // método para finalizar a activity e voltar para a principal
    public void voltarL(View view){
        finish();
    }

    // método para limpar os campos
    public void limpaCampo(){
        campoData.setText("");
        campoCategoria.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
    }

}