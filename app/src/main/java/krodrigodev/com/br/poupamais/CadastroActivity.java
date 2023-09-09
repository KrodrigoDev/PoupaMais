package krodrigodev.com.br.poupamais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import krodrigodev.com.br.poupamais.model.Usuario;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;


/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class CadastroActivity extends AppCompatActivity {

    // atributos

    private EditText nome;
    private EditText email;
    private EditText senha;


    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.campoNomeCadastro);
        email = findViewById(R.id.campoEmailCadastro);
        senha = findViewById(R.id.campoSenhaCadastro);

        usuarioDao = new UsuarioDao(this);

    }

    // método para salvar conta
    public void salvarConta(View view) {

        String nomeText = nome.getText().toString();
        String emailText = email.getText().toString();
        String senhaText = senha.getText().toString();

        if (nomeText.isEmpty() || emailText.isEmpty() || senhaText.isEmpty()) {

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            Usuario usuario = new Usuario();
            usuario.setNome(nomeText);
            usuario.setEmail(emailText);
            usuario.setSenha(senhaText);

            usuarioDao.inserirUsuario(usuario);

            limpaCampo();

            Toast.makeText(this, R.string.confirmar_criacao_usuario, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }

    public void limpaCampo() {
        nome.setText("");
        email.setText("");
        senha.setText("");
    }


}