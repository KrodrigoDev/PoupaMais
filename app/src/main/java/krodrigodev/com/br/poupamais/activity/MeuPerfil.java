package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.ValidarEmail;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 08/10/2023
 */
public class MeuPerfil extends AppCompatActivity {

    // atributos
    private TextInputEditText campoNome, campoEmail;
    private GoogleSignInAccount account;
    private UsuarioDao usuarioDao;
    private String emailAntigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        // Verificando se o usuário logou com o google
        account = GoogleSignIn.getLastSignedInAccount(this);

        // Inicializa os campos
        inicializar();

        // Preenchendo os campos com nome e email quando o usuário entra na tela
        recuperarInfo();

    }

    // método para atualzar o email e nome (depois coloco a senha)
    public void atualizarDados(View view) {

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        if (nome.isEmpty() || email.isEmpty()) {

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else if (!ValidarEmail.emailValido(email)) {

            Toast.makeText(this, R.string.email_invalido, Toast.LENGTH_SHORT).show();

        } else {

            desejaAlterar(nome, email, emailAntigo);

        }
    }

    // Método para voltar ao menu
    public void voltarMenu(View view) {
        finish();
    }

    // método que vai exibir um alerta e o usuário vai decidir o que quer
    public void desejaAlterar(String novoNome, String novoEmail, String emailAntigo) {

        // Instanciar AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Configurar título, ícone e mensagem
        builder.setTitle(R.string.titulo_alerta);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.mensagem_alerta);

        // Opção "Sim"
        builder.setPositiveButton(R.string.opcao_sim_alerta, (dialog, which) -> {

            // Realizar a atualização dos dados
            usuarioDao.alterarDados(novoNome, novoEmail, emailAntigo);

            UsuarioLogado.setNomeUsuarioLocal(novoNome);
            UsuarioLogado.setEmail(novoEmail);

            finish();
        });

        // Opção "Não"
        builder.setNegativeButton(R.string.opcao_nao_alerta, (dialog, which) -> Toast.makeText(MeuPerfil.this, R.string.atualizacao_cancelada, Toast.LENGTH_SHORT).show());

        // Impedir que o usuário clique fora do diálogo para cancelar
        builder.setCancelable(false);

        // Mostrar o diálogo
        builder.create().show();
    }

    public void recuperarInfo() {

        if (account != null) {

            // Usuário logado com o Google
            campoNome.setText(account.getDisplayName());
            campoEmail.setText(account.getEmail());
            emailAntigo = account.getEmail();

        } else {

            // Usuário logado localmente
            campoNome.setText(UsuarioLogado.getNomeUsuarioLocal());
            campoEmail.setText(UsuarioLogado.getEmail());
            emailAntigo = UsuarioLogado.getEmail();

        }

    }


    // Método para inicializar os campos
    public void inicializar() {
        campoNome = findViewById(R.id.campoNomeMeuPerfil);
        campoEmail = findViewById(R.id.campoEmailMeuPerfil);

        usuarioDao = new UsuarioDao(this);
    }
}
