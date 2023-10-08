package krodrigodev.com.br.poupamais.activity;


import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.DataAtual;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 02/10/2023
 */
public class AdicionarDespesa extends BaseMovimentacao {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesa);

        // chamando o inicizalizador dos meus elementos de interface
        inicializar();

        // Inicialização API do Google (caso o usuário faça login com o Google)
        account = GoogleSignIn.getLastSignedInAccount(this);

        // Configurando o tipo de movimentação e o nome da coluna
        TIPOMOVIMENTO = "despesa";
        NOMECOLUNA = "totaldespesa";

        // Modificando o texto da data para a data atual
        campoData.setText(DataAtual.getDataFormatada());

        // Recuperando a despesa do usuário antes de adicionar uma nova
        recuperandoValor();
    }

    // Método para salvar uma despesa
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarDespesa(View view) {
        salvarMovimentacao(view);
    }

    // Método para finalizar a atividade e voltar para a janela principal
    public void voltarD(View view) {
        voltar(view);
    }

    // método que vai inicializar os meus atributos de despesa
    public void inicializar() {

        movimentacaoDao = new MovimentacaoDao(this);
        usuarioDao = new UsuarioDao(this);

        campoData = findViewById(R.id.campoDataDespesa);
        campoDescricao = findViewById(R.id.campoDescricaoDespesa);
        campoCategoria = findViewById(R.id.campoCategoriaDespesa);
        campoValor = findViewById(R.id.campoValorDespesa);

    }

}
