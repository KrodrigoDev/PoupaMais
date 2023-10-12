package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;

/**
 * @author Kauã Rodrigo
 * @since 08/10/2023
 */
public class Menu extends AppCompatActivity {

    // Atributos
    private ImageView iconFoto;
    private TextView nomeUsuario, emailUsuario;
    private GoogleSignInAccount account;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Verificando se o usuário logou com o google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        // Chamando o método de inicialização
        inicializar();

        // Recuperando informações básicas
        recuperarInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarInfo();
    }

    // Método que vai recuperar email e nome
    public void recuperarInfo() {
        nomeUsuario.setText(UsuarioLogado.getNomeUsuarioLocal());
        emailUsuario.setText(UsuarioLogado.getEmail());
    }

    // Método que vai inicializar meus atributos
    public void inicializar() {
        iconFoto = findViewById(R.id.fotoUsuario);
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsuario);
    }

    // Métodos de navegação (Todos as opções do menu)

    public void voltarPrincipal(View view) {
        finish();
    }

    public void desconectarUsuario(View view) {
        if (account != null) {
            gsc.signOut();
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        // Definir as flags da Intent para iniciar como uma nova tarefa e limpar a pilha de atividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        finish();
        startActivity(intent);
    }

    public void meuPerfil(View view) {
        Intent intent = new Intent(Menu.this, MeuPerfil.class);
        startActivity(intent);
    }

}
