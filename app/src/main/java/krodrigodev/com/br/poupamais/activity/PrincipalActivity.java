package krodrigodev.com.br.poupamais.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.AdpterMovimentacoes;
import krodrigodev.com.br.poupamais.helper.Alertas;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.model.Movimentacao;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 02/10/2023
 */
public class PrincipalActivity extends AppCompatActivity {

    // Atributos
    private MaterialCalendarView calendario;
    private TextView saudacaoUsuario, saldoTotal;
    private UsuarioDao usuarioDao;
    private MovimentacaoDao movimentacaoDao;
    private RecyclerView recyclerMovimento;
    private List<Movimentacao> listaMovimentacoes;
    private String mesAnoSelecionado;
    private AdpterMovimentacoes adpterMovimentacoes;
    private RecyclerView.LayoutManager layoutManager;
    private Alertas alertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializar();

        confiCalendario();

        configRecyclerMovimento();
    }

    //(preciso melhorar isso obs: parte da vida últil da activity)
    @Override
    protected void onResume() {
        super.onResume();

        recuperarDados();
        recuperarMovimentacoes();
    }

    public void recuperarMovimentacoes() {
        try {
            listaMovimentacoes = movimentacaoDao.recuperarMovimentacaoMes(mesAnoSelecionado, UsuarioLogado.getEmail());
            adpterMovimentacoes.atualizarMovimentacoes(listaMovimentacoes);
        } catch (Exception erro) {
            alertas.erroInterno();
        }
    }

    private void configRecyclerMovimento() {
        recyclerMovimento.setLayoutManager(layoutManager);
        recyclerMovimento.setHasFixedSize(true);
        recyclerMovimento.setAdapter(adpterMovimentacoes);
    }

    private void confiCalendario() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM");

        calendario.setTitleMonths(getResources().getStringArray(R.array.meses));

        calendario.state().edit()
                .setMinimumDate(CalendarDay.from(2023, 10, 30))
                .setMaximumDate(CalendarDay.from(2026, 12, 31))
                .commit();


        // Mês e ano selecionados ao entrar no app
        CalendarDay dataAtual = calendario.getCurrentDate();
        mesAnoSelecionado = formatoData.format(dataAtual.getDate());

        // Ouvinte
        calendario.setOnMonthChangedListener((widget, date) -> {

            mesAnoSelecionado = formatoData.format(date.getDate());

            recuperarMovimentacoes();

        });

    }

    @SuppressLint("SetTextI18n")
    private void recuperarDados() {

        String COLUNALUCRO = "totallucro";
        String COLUNADESPESA = "totaldespesa";
        String nomeUsuario;
        double lucroTotal;
        double despesaTotal;

        try {
            nomeUsuario = UsuarioLogado.getNomeUsuarioLocal();
            lucroTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), COLUNALUCRO);
            despesaTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), COLUNADESPESA);

            double totalUsuario = lucroTotal - despesaTotal;

            DecimalFormat formato = new DecimalFormat("0.##");

            saldoTotal.setText(getString(R.string.moeda) + " " + formato.format(totalUsuario));

            saudacaoUsuario.setText(getString(R.string.saudacao) + " " + nomeUsuario);

        } catch (Exception erro) {
            alertas.erroInterno();
        }

    }


    private void inicializar() {

        usuarioDao = new UsuarioDao(this);
        movimentacaoDao = new MovimentacaoDao(this);
        alertas = new Alertas(this);

        listaMovimentacoes = new ArrayList<>();
        adpterMovimentacoes = new AdpterMovimentacoes(listaMovimentacoes, this);
        layoutManager = new LinearLayoutManager(this);

        calendario = findViewById(R.id.calendario);
        saudacaoUsuario = findViewById(R.id.textSaudacaoUsuario);
        saldoTotal = findViewById(R.id.textValorTotal);
        recyclerMovimento = findViewById(R.id.listaMovimentos);
    }

    // Métodos de navegação

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void abrirJanelaLucro(View view) {
        Intent intent = new Intent(PrincipalActivity.this, AdicionarLucro.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void abrirJanelaDespesa(View view) {
        Intent intent = new Intent(PrincipalActivity.this, AdicionarDespesa.class);
        startActivity(intent);
    }

    public void abrirMenu(View view) {
        Intent intent = new Intent(PrincipalActivity.this, Menu.class);
        startActivity(intent);
    }

}
