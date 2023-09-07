package krodrigodev.com.br.poupamais;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Kauã Rodrigo
 * @since 07/09/2023
 * @version 0.1
 */
public class InformativoConta extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infomativo_conta, container, false);
    }
}