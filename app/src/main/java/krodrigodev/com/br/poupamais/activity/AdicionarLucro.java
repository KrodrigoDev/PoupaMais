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
public class AdicionarLucro extends BaseMovimentacao {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lucro);

        // chamando o inicizalizador dos meus elementos de interface
        inicializar();

        // Inicialização API do Google (caso o usuário faça login com o Google)
        account = GoogleSignIn.getLastSignedInAccount(this);

        // Modificando o texto da data para a data atual
        campoData.setText(DataAtual.getDataFormatada());

        // Configurando o tipo de movimentação e o nome da coluna
        TIPOMOVIMENTO = "lucro";
        NOMECOLUNA = "totallucro";

        // Fazendo o lucro ser recuperado antes que o usuário digite um novo valor
        recuperandoValor();
    }

    // Método para salvar o lucro
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarLucro(View view) {
        salvarMovimentacao(view);
    }

    // Método para finalizar a activity e voltar para a principal
    public void voltarL(View view) {
        voltar(view);
    }

    // método que vai inicializar os meus atributos de lucro
    public void inicializar() {
        movimentacaoDao = new MovimentacaoDao(this);
        usuarioDao = new UsuarioDao(this);

        campoData = findViewById(R.id.campoDataLucro);
        campoDescricao = findViewById(R.id.campoDescricaoLucro);
        campoCategoria = findViewById(R.id.campoCategoriaLucro);
        campoValor = findViewById(R.id.campoValorLucro);
    }

}
