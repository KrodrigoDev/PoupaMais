package krodrigodev.com.br.poupamais.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.AdpterMovimentacoes;
import krodrigodev.com.br.poupamais.controller.AlgoritmoBuscaOrdenacao;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.model.Movimentacao;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;

public class MinhasMovimetacoes extends AppCompatActivity {

    // atributos
    private EditText campoValor;
    private TextView total;
    private RecyclerView listaFiltrado;
    private AdpterMovimentacoes adpterMovimentacoes;
    private MovimentacaoDao movimentacaoDao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_movimetacoes);

        inicializar();

        // Configurações do adaptador
        adpterMovimentacoes = new AdpterMovimentacoes(movimentacoes, this);

        // Configurações do RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaFiltrado.setLayoutManager(layoutManager);
        listaFiltrado.setHasFixedSize(true);
        listaFiltrado.setAdapter(adpterMovimentacoes);
    }


    public void recuperarMovimentacoes(View view) {

        movimentacaoDao = new MovimentacaoDao(this);
        movimentacoes = movimentacaoDao.recuperarTodasMovimentacoes(UsuarioLogado.getEmail());

        AlgoritmoBuscaOrdenacao algoritmoBuscaOrdenacao = new AlgoritmoBuscaOrdenacao();

        String valorDigitado = campoValor.getText().toString();

        try {

            if (!valorDigitado.isEmpty()) {
                double valor = Double.parseDouble(valorDigitado);
                movimentacoes = algoritmoBuscaOrdenacao.buscaBinaria(movimentacoes, valor);
            }

            adpterMovimentacoes.atualizarMovimentacoes(movimentacoes);
            total.setText(String.valueOf(movimentacoes.size()));

        } catch (Exception erro) {
            Toast.makeText(this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void inicializar() {
        campoValor = findViewById(R.id.campoBuscaValor);
        listaFiltrado = findViewById(R.id.listaFiltrada);
        total = findViewById(R.id.textTotalMovimentacoes);
    }

    // métodos de navegação

    public void voltarMenuMovimen(View view) {
        finish();
    }

}