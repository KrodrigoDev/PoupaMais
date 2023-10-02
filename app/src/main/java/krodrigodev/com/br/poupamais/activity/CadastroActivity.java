package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.ValidarEmail;
import krodrigodev.com.br.poupamais.model.Usuario;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 08/09/2023
 */
public class CadastroActivity extends AppCompatActivity {

    // Atributos
    private EditText nome, email, senha;
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

    // Método para salvar uma conta de usuário
    public void salvarConta(View view) {

        String nomeDigitado = nome.getText().toString();
        String emailDigitado = email.getText().toString();
        String senhaDigitada = senha.getText().toString();

        // Validar se os campos foram preenchidos
        if (nomeDigitado.isEmpty() || emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            // Validar se o formato do e-mail é válido
            if (!ValidarEmail.emailValido(emailDigitado)) {

                Toast.makeText(this, R.string.email_invalido, Toast.LENGTH_SHORT).show();

            } else {

                Usuario usuario = new Usuario();
                usuario.setNome(nomeDigitado);
                usuario.setEmail(emailDigitado);
                usuario.setSenha(senhaDigitada);

                // Validação para verificar se o e-mail já existe no banco
                if (usuarioDao.validaEmailExitentes(usuario.getEmail())) {

                    Toast.makeText(this, R.string.email_duplicado, Toast.LENGTH_SHORT).show();

                } else {

                    usuarioDao.inserirUsuario(usuario); // Se todas as validações estiverem ok, cria uma conta
                    limpaCampos();

                    Toast.makeText(this, R.string.confirmar_criacao_usuario, Toast.LENGTH_SHORT).show();

                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
            }
        }
    }

    // Método para limpar os campos de entrada de dados
    public void limpaCampos() {
        nome.setText("");
        email.setText("");
        senha.setText("");
    }
}
