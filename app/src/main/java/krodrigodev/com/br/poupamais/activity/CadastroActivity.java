package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.model.Usuario;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;


/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class CadastroActivity extends AppCompatActivity {

    // atributos

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

    // método para salvar conta
    public void salvarConta(View view) {

        String nomeDigitado = nome.getText().toString();
        String emailDigitado = email.getText().toString();
        String senhaDigitada = senha.getText().toString();

        // Validar se os campos foram preenchidos
        if (nomeDigitado.isEmpty() || emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            Usuario usuario = new Usuario();


            // Validar se o formato do e-mail é válido
            if (!emailValido(emailDigitado)) {

                Toast.makeText(this, R.string.email_invalido, Toast.LENGTH_SHORT).show();

            } else {

                usuario.setNome(nomeDigitado);
                usuario.setEmail(emailDigitado);
                usuario.setSenha(senhaDigitada);

                // Validação para verificar se o e-mail já existe no banco
                if (usuarioDao.validaEmail(usuario.getEmail())) {

                    Toast.makeText(this, R.string.email_duplicado, Toast.LENGTH_SHORT).show();

                } else {

                    usuarioDao.inserirUsuario(usuario); // se todos as validações estiverem ok, vai criar uma conta

                    limpaCampo();

                    Toast.makeText(this, R.string.confirmar_criacao_usuario, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        }

    }

    // Função para validar o formato do e-mail com Patterns.EMAIL_ADDRESS
    public static boolean emailValido(CharSequence email) {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            // Verificar se o domínio é válido
            String[] partesEmail = email.toString().split("@"); // dividindo o email em duas partes

            if (partesEmail.length == 2) { // verifica se foi feita a divisão

                String dominio = partesEmail[1];

                return dominio.contains(".") && dominio.length() > 2; // Verificar se contém pelo menos um ponto e tem mais de 2 caracteres.

            }

        }
        return false;
    }


    public void limpaCampo() {
        nome.setText("");
        email.setText("");
        senha.setText("");
    }


}