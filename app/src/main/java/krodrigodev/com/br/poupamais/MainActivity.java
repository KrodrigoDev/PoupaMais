package krodrigodev.com.br.poupamais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    // atributos
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = findViewById(R.id.tabelaApresentacao);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        VpaAdpter vpaAdpter = new VpaAdpter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpaAdpter.addFragments(new Informativo1(), "●");
        vpaAdpter.addFragments(new Informativo2(), "●");
        vpaAdpter.addFragments(new Informativo3(), "●");
        vpaAdpter.addFragments(new InformativoConta(), "●");
        viewPager.setAdapter(vpaAdpter);

    }

    // métodos para abrir activitys

    public void bntEntrarConta(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void bntCriarConta(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

}