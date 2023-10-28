package krodrigodev.com.br.poupamais.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.ValidarEmail;
import krodrigodev.com.br.poupamais.helper.Alertas;
import krodrigodev.com.br.poupamais.model.Usuario;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 08/09/2023
 */
public class CadastroActivity extends AppCompatActivity {

    private EditText nome, email, senha;
    private UsuarioDao usuarioDao;
    private GoogleSignInClient gsc;
    private Alertas alertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializar();
    }

    public void salvarConta(View view) {

        String nomeDigitado = nome.getText().toString();
        String emailDigitado = email.getText().toString();
        String senhaDigitada = senha.getText().toString();


        if (nomeDigitado.isEmpty() || emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            alertas.mensagemLonga(R.string.validar_campos);
            return;
        } else if (ValidarEmail.emailValido(emailDigitado)) {
            alertas.mensagemLonga(R.string.email_invalido);
            return;
        }

        Usuario usuario = new Usuario(nomeDigitado, emailDigitado, senhaDigitada);

        if (usuarioDao.validaEmailExitentes(usuario.getEmail())) {
            alertas.mensagemLonga(R.string.email_duplicado);
        } else {

            usuarioDao.inserirUsuario(usuario);

            limpaCampos();

            alertas.mensagemLonga(R.string.confirmar_criacao_usuario);

            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        }

    }

    public void fazerCadastroGoogle(View view) {
        Intent cadastrarComGoogle = gsc.getSignInIntent();
        startActivityForResult(cadastrarComGoogle, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null) {
                    nome.setText(account.getDisplayName());
                    email.setText(account.getEmail());

                    alertas.mensagemAlerta(R.string.titulo_alerta_criar_conta, R.string.mensagem_alerta_criar_conta);
                }

            } catch (ApiException erro) {

                if (erro.getStatusCode() == 7) {   // Lidar com o erro de NETWORK_ERROR aqui
                    alertas.mensagemLonga(R.string.sem_conexao);
                } else {
                    alertas.mensagemLonga(R.string.erro_login_google);
                }

            }
        }

    }

    public void inicializar() {
        nome = findViewById(R.id.campoNomeCadastro);
        email = findViewById(R.id.campoEmailCadastro);
        senha = findViewById(R.id.campoSenhaCadastro);

        usuarioDao = new UsuarioDao(this);
        alertas = new Alertas(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
    }


    public void limpaCampos() {
        nome.setText("");
        email.setText("");
        senha.setText("");
    }

}
