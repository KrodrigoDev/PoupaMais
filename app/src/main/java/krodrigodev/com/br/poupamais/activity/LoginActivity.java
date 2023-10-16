package krodrigodev.com.br.poupamais.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.EncriptaMD5;
import krodrigodev.com.br.poupamais.model.Usuario;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

public class LoginActivity extends AppCompatActivity {

    // Atributos
    private EditText email, senha;
    private UsuarioDao usuarioDao;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Implementando o login com o Google (requerindo o email e o ID do Google)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        // chamando o método que inicaliza os meus atributos
        inicializar();

    }

    // método que realiza o acesso
    public void acessar(View view) {

        String emailDigitado = email.getText().toString();
        String senhaDigitada = senha.getText().toString();

        // Validar se os campos estão vazios
        if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            fazerLogin(emailDigitado, senhaDigitada);

        }

    }

    // Método para abrir uma activity com as contas do Google disponíveis
    public void fazerLoginGoogle(View view) {
        Intent entrarComGoogle = gsc.getSignInIntent();
        startActivityForResult(entrarComGoogle, 1000); // Este código é o que eu espero caso tenha sucesso depois
    }

    // Método para lidar com o resultado do login com o Google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null) {

                    boolean validacaoEmail = usuarioDao.validaEmailExitentes(account.getEmail());

                    if (!validacaoEmail) {
                        Usuario usuario = new Usuario(account.getDisplayName(), account.getEmail(), account.getId());
                        usuarioDao.inserirUsuario(usuario);
                    } else {
                        // usuários que fazem login com o google fornecem o id como senha
                        usuarioDao.validaLogin(account.getEmail(), account.getId());
                    }

                    loginSucesso();

                }

            } catch (ApiException erro) {

                if (erro.getStatusCode() == 7) {   // Lidar com o erro de NETWORK_ERROR aqui

                    Toast.makeText(this, R.string.sem_conexao, Toast.LENGTH_SHORT).show();

                } else {  // Outros erros

                    Log.e("Erro", "Erro ao fazer login com o Google. Código de erro: " + erro.getStatusCode());

                    Toast.makeText(this, R.string.erro_login_google, Toast.LENGTH_SHORT).show();

                }

            }
        }
    }

    // Método para efetuar o login com email e senha (Usuário local)
    public void fazerLogin(String email, String senha) {

        try {

            String senhaEncriptada = EncriptaMD5.encriptaSenha(senha);
            boolean checarLogin = usuarioDao.validaLogin(email, senhaEncriptada);

            if (checarLogin) {

                Toast.makeText(this, R.string.sucesso_login, Toast.LENGTH_SHORT).show();
                loginSucesso();

            } else {

                Toast.makeText(this, R.string.senha_email_invalidos, Toast.LENGTH_SHORT).show();

            }

        } catch (Exception erro) {

            Log.e("Erro", "Não foi possível recuperar a senha: " + erro);

        }
    }

    // Método para navegar para tela principal caso tenha sucesso no login
    public void loginSucesso() {
        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);

        // Definir as flags da Intent para iniciar como uma nova tarefa e limpar a pilha de atividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        finish();
        startActivity(intent);
    }

    // método que inicailizar os meus atributos
    public void inicializar() {
        usuarioDao = new UsuarioDao(this);

        email = findViewById(R.id.campoEmail);
        senha = findViewById(R.id.campoSenha);
    }


}
