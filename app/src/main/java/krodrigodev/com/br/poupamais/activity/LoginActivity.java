package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.EncriptaMD5;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class LoginActivity extends AppCompatActivity {

    private EditText email, senha;
    private UsuarioDao usuarioDao;

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

    }

    // método para efetuar o login
    public void fazerLogin(String email, String senha) {
        try {

            String senhaEncriptada = EncriptaMD5.encriptaSenha(senha);
            boolean checarLogin = usuarioDao.validaLogin(email, senhaEncriptada);

            if (checarLogin) {

                Toast.makeText(this, R.string.sucesso_login, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);

                // Definir as flags da Intent para iniciar como uma nova tarefa e limpar a pilha de atividades
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();


            } else {

                Toast.makeText(this, R.string.senha_email_invalidos, Toast.LENGTH_SHORT).show();

            }

        } catch (Exception erro) {

            Log.e("Erro", "Não foi possível recuperar a senha: " + erro);

        }

    }

}
