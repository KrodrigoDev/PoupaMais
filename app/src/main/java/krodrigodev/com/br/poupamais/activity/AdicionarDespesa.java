package krodrigodev.com.br.poupamais.activity;

import static krodrigodev.com.br.poupamais.helper.DataAtual.dataFormatada;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;

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

        // inicialização api do google (caso o usuário faça login com o google)
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this, gso);

        account = GoogleSignIn.getLastSignedInAccount(this);


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

            // verificação do usuário local ou google
            if (account != null) {
                movimentacao.setEmail_Usuario(account.getEmail());
            } else {
                movimentacao.setEmail_Usuario(UsuarioLogado.getEmail());
            }


            // realizando a soma das despesas antes de salvar
            double despesaAtualizada = despesaDigitada + despesaTotal;

            // atualizando a despesa total da minha tabela usuário
            atualizandoDespesa(despesaAtualizada);

            // salvando
            movimentacaoDao.salvarMovimentacao(movimentacao);

            // limpando os campos após salvar
            limpaCampo();

            // mensagem de confirmação para o usuário
            Toast.makeText(this, R.string.despesa_adicionada, Toast.LENGTH_SHORT).show();

            // voltando para janela principal (Talvez sejá necessário alterar depois)
            finish();
        }

    }

    // recuperando a despesa do usuário para realizar uma soma com a nova despesa adicionada
    public void recuperandoDespesa() {
        if (account != null) {
            despesaTotal = usuarioDao.recuperarTotal(account.getEmail(), NOMECOLUNA);
        } else {
            despesaTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), NOMECOLUNA);
        }
    }

    // método para atualizar a despesa total na conta do usuário
    public void atualizandoDespesa(double despesa) {
        if (account != null) {
            usuarioDao.alterarDespesaTotal(account.getEmail(), despesa, NOMECOLUNA);
        } else {
            usuarioDao.alterarDespesaTotal(UsuarioLogado.getEmail(), despesa, NOMECOLUNA);
        }
    }

    // método para finalizar a activity e voltar para a principal
    public void voltarD(View view) {
        finish();
    }

    // método para limpar os campos
    public void limpaCampo() {
        campoData.setText("");
        campoCategoria.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
    }

}