package krodrigodev.com.br.poupamais.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.EncriptaMD5;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class LoginActivity extends AppCompatActivity {

    // atributos
    private EditText email, senha;
    private UsuarioDao usuarioDao;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialize o objeto usuarioDao
        usuarioDao = new UsuarioDao(this);

        email = findViewById(R.id.campoEmail);
        senha = findViewById(R.id.campoSenha);
        Button botaoAcessar = findViewById(R.id.bntAcessar);

        // Adicionando uma ação para o botão
        botaoAcessar.setOnClickListener(v -> {

            String emailDigitado = email.getText().toString();
            String senhaDigitada = senha.getText().toString();

            // validação para verificar se os campos estão vazios
            if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {

                Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

            } else {

                fazerLogin(emailDigitado, senhaDigitada);

            }

        });

        // inicialização da imagem google
        ImageView googleEntrar = findViewById(R.id.iconEntrarGoogle);

        // implementando o login com o google (requerindo o email e o id do google)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        gsc = GoogleSignIn.getClient(this, gso);

        // ouvinte da imagem do google
        googleEntrar.setOnClickListener(v -> fazerLoginGoogle());

    }

    // método para abrir uma activity com as contas do google disponíveis
    public void fazerLoginGoogle() {
        Intent entrarComGoogle = gsc.getSignInIntent();
        startActivityForResult(entrarComGoogle, 1000); //esse code é o que eu espero caso tenha sucesso depois
    }

    // método para lidar com o resultado do login com o google
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
                        usuarioDao.inserirUsuarioComIdGoogle(account.getDisplayName(), account.getEmail());
                    }

                    loginSucesso();
                }

            } catch (ApiException erro) {
                int errorCode = erro.getStatusCode();
                String errorMessage = erro.getMessage();

                Log.e("Erro", "Erro ao fazer login com o Google. Código de erro: " + errorCode + ", Mensagem: " + errorMessage);

                // Exiba uma mensagem de erro genérica ao usuário
                Toast.makeText(this, "Erro ao fazer login com o Google. Por favor, tente novamente.", Toast.LENGTH_SHORT).show();

            }

        }

    }


    // método para efetuar o login com email e senha
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

    // método para navegar para tela principal caso tenha sucesso no lofin
    public void loginSucesso() {
        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);

        // Definir as flags da Intent para iniciar como uma nova tarefa e limpar a pilha de atividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

}
