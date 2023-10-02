package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;

import krodrigodev.com.br.poupamais.R;

/**
 * @author Kauã Rodrigo
 * @since 01/10/2023
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // atributos
        TabLayout tabLayout = findViewById(R.id.tabelaApresentacao);
        ViewPager viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        VpaAdpter vpaAdpter = new VpaAdpter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpaAdpter.addFragments(new Informativo1(), "●");
        vpaAdpter.addFragments(new Informativo2(), "●");
        vpaAdpter.addFragments(new Informativo3(), "●");
        vpaAdpter.addFragments(new InformativoConta(), "●");
        viewPager.setAdapter(vpaAdpter);

        // inicialização api do google (caso o usuário faça login com o google)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Verifica se o usuário está logado com a conta do Google e prossegue para a tela principal
        if (account != null) {
            abrirTelaPrincipal();
        }

    }

    // métodos para abrir activitys presentes em fragmentos

    public void bntEntrarConta(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void bntCriarConta(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    // método para navegar direto para janela principal caso o usuário tenha feito login com o google
    private void abrirTelaPrincipal() {
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

}