package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.ValidarEmail;
import krodrigodev.com.br.poupamais.helper.Alertas;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 08/10/2023
 */
public class MeuPerfil extends AppCompatActivity {

    // atributos
    private EditText campoNome, campoEmail;
    private UsuarioDao usuarioDao;
    private String emailAntigo;
    private Alertas alertas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        inicializar();
    }


    public void inicializar() {
        campoNome = findViewById(R.id.campoNomeMeuPerfil);
        campoEmail = findViewById(R.id.campoEmailMeuPerfil);

        usuarioDao = new UsuarioDao(this);
        alertas = new Alertas(this);

        campoNome.setText(UsuarioLogado.getNomeUsuarioLocal());
        campoEmail.setText(UsuarioLogado.getEmail());
        emailAntigo = UsuarioLogado.getEmail();
    }

    public void atualizarDados(View view) {

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();

        if (nome.isEmpty() || email.isEmpty()) {

            alertas.mensagemLonga(R.string.validar_campos);

        } else if (ValidarEmail.emailValido(email)) {

            alertas.mensagemLonga(R.string.email_invalido);

        } else {
            confirmarAlteracao(nome, email, emailAntigo);
        }
    }

    public void confirmarAlteracao(String novoNome, String novoEmail, String emailAntigo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.titulo_alerta);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.mensagem_alerta);

        // Opção "Sim"
        builder.setPositiveButton(R.string.opcao_sim_alerta, (dialog, which) -> {

            usuarioDao.alterarDados(novoNome, novoEmail, emailAntigo);

            UsuarioLogado.setNomeUsuarioLocal(novoNome);
            UsuarioLogado.setEmail(novoEmail);

            alertas.mensagemLonga(R.string.sucesso_ao_atualizar);

            finish();
        });

        // Opção "Não"
        builder.setNegativeButton(R.string.opcao_nao_alerta, (dialog, which) ->
                alertas.mensagemLonga(R.string.atualizacao_cancelada));

        builder.setCancelable(false);

        builder.create().show();
    }

    public void voltarMenu(View view) {
        finish();
    }

}
