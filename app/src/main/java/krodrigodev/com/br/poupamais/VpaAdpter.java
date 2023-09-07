package krodrigodev.com.br.poupamais;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VpaAdpter extends FragmentPagerAdapter {

    // Listas para armazenar os fragmentos e títulos
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitulo = new ArrayList<>();

    // Construtor
    public VpaAdpter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    // Retorna o fragmento na posição especificada
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    // Retorna o número total de fragmentos
    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    // Adiciona fragmentos à lista
    public void addFragments(Fragment fragment, String titulo) {
        fragmentArrayList.add(fragment);
        fragmentTitulo.add(titulo);
    }

    // Retorna o título da guia na posição especificada
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitulo.get(position);
    }
}
