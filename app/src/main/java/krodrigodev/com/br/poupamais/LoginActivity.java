package krodrigodev.com.br.poupamais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import krodrigodev.com.br.poupamais.controller.EncriptaMD5;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class LoginActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialize o objeto usuarioDao
        usuarioDao = new UsuarioDao(this);

        // Acessando os campos presentes na tela
        EditText email = findViewById(R.id.campoEmail);
        EditText senha = findViewById(R.id.campoSenha);
        Button botao = findViewById(R.id.bntAcessar);

        // Adicionando uma ação para o botão
        botao.setOnClickListener(v -> {
            String emailDigitado = email.getText().toString();
            String senhaDigitada = senha.getText().toString();

            if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {

                Toast.makeText(LoginActivity.this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

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

                Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                startActivity(intent);

            } else {

                Toast.makeText(LoginActivity.this, R.string.senha_email_invalidos, Toast.LENGTH_SHORT).show();

            }

        } catch (Exception erro) {
            Log.e("Erro", "Não foi possível recuperar a senha: " + erro);
        }

    }

}
