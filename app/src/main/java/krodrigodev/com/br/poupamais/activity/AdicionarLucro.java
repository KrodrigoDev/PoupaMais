package krodrigodev.com.br.poupamais.activity;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

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

        // Inicializando o movimentoDAO e usuarioDao
        movimentacaoDao = new MovimentacaoDao(this);
        usuarioDao = new UsuarioDao(this);

        // Fazendo referências
        campoData = findViewById(R.id.campoDataLucro);
        campoDescricao = findViewById(R.id.campoDescricaoLucro);
        campoCategoria = findViewById(R.id.campoCategoriaLucro);
        campoValor = findViewById(R.id.campoValorLucro);

        // Inicialização API do Google (caso o usuário faça login com o Google)
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this, gso);

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

}
