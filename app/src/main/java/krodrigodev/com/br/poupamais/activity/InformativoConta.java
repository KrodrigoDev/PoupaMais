package krodrigodev.com.br.poupamais.activity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import krodrigodev.com.br.poupamais.R;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 07/09/2023
 */
public class InformativoConta extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infomativo_conta, container, false);
    }

}