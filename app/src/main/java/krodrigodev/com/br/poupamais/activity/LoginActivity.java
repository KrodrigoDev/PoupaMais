package krodrigodev.com.br.poupamais.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;


import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.Alertas;
import krodrigodev.com.br.poupamais.helper.ReceptorModoAviao;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

public class LoginActivity extends AppCompatActivity {

    // Atributos
    private EditText email, senha;
    private UsuarioDao usuarioDao;
    private Alertas alertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializar();
    }

    public void acessar(View view) {

        String emailDigitado = email.getText().toString();
        String senhaDigitada = senha.getText().toString();

        if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            alertas.mensagemLonga(R.string.validar_campos);
        } else {
            fazerLogin(emailDigitado, senhaDigitada);
        }

    }

    public void fazerLogin(String email, String senha) {

        if (usuarioDao.validaLogin(email, senha)) {

            alertas.mensagemLonga(R.string.sucesso_login);

            Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            finish();
            startActivity(intent);

        } else {
            alertas.mensagemLonga(R.string.senha_email_invalidos);
        }

    }


    // método que inicailizar os meus atributos
    public void inicializar() {
        email = findViewById(R.id.campoEmail);
        senha = findViewById(R.id.campoSenha);

        usuarioDao = new UsuarioDao(this);
        alertas = new Alertas(this);



    }


}
