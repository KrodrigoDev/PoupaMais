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
import krodrigodev.com.br.poupamais.adpter.AdpterMovimentacoes;
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
    private RecyclerView listaMovimento;
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private String mesAnoSelecionado;
    private AdpterMovimentacoes adpterMovimentacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // método inicializador (para os atributos)
        inicializar();

        // Configurações do adaptador
        adpterMovimentacoes = new AdpterMovimentacoes(movimentacoes, this);

        // Configurações do RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaMovimento.setLayoutManager(layoutManager);
        listaMovimento.setHasFixedSize(true);
        listaMovimento.setAdapter(adpterMovimentacoes);

        // Inicializando o método ao entrar na tela
        configuracaoCalendario();
    }

    // Método para atualizar os valores quando eu voltar para activity (preciso melhorar isso)
    @Override
    protected void onResume() {
        super.onResume();
        recuperarDados();
        recuperarMovimentacoes();
    }

    // Método para recuperar as movimentações
    public void recuperarMovimentacoes() {

        // Recupera as movimentações com base no e-mail do usuário
        movimentacoes = movimentacaoDao.recuperarMovimentacaoMes(mesAnoSelecionado, UsuarioLogado.getEmail());

        // Atualiza o adaptador com as movimentações recuperadas
        adpterMovimentacoes.atualizarMovimentacoes(movimentacoes);
    }

    // Método para configurar o calendário
    public void configuracaoCalendario() {

        // Ajustando o título dos meses
        calendario.setTitleMonths(getResources().getStringArray(R.array.meses)); // Passando o array de meses

        // Ajustando o limite e o máximo
        calendario.state().edit()
                .setMinimumDate(CalendarDay.from(2023, 9, 30))
                .setMaximumDate(CalendarDay.from(2026, 12, 31))
                .commit();

        // Formato que vai ser usado para filtrar no banco de dados
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        // Mês e ano selecionados ao entrar no app
        CalendarDay dataAtual = calendario.getCurrentDate();
        mesAnoSelecionado = sdf.format(dataAtual.getDate());

        // Ouvinte
        calendario.setOnMonthChangedListener((widget, date) -> {

            mesAnoSelecionado = sdf.format(date.getDate());

            // Recuperando as movimentações novamente quando o usuário clicar no calendário
            recuperarMovimentacoes();

        });

    }

    // Método para recuperar o saldo total e o nome do usuário
    @SuppressLint("SetTextI18n")
    public void recuperarDados() {

        // Atributos locais
        String COLUNALUCRO = "totallucro";
        String COLUNADESPESA = "totaldespesa";
        String nomeUsuario;
        double lucroTotal;
        double despesaTotal;

        nomeUsuario = UsuarioLogado.getNomeUsuarioLocal();
        lucroTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), COLUNALUCRO);
        despesaTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), COLUNADESPESA);


        // Aplicando alterações na view com os dados obtidos
        double totalUsuario = lucroTotal - despesaTotal;

        DecimalFormat formato = new DecimalFormat("0.##");

        saldoTotal.setText(getString(R.string.moeda) + " " + formato.format(totalUsuario));

        saudacaoUsuario.setText(getString(R.string.saudacao) + " " + nomeUsuario);

    }

    // método para inicializar os meus atributos
    public void inicializar() {

        usuarioDao = new UsuarioDao(this);
        movimentacaoDao = new MovimentacaoDao(this);

        calendario = findViewById(R.id.calendario);
        saudacaoUsuario = findViewById(R.id.textSaudacaoUsuario);
        saldoTotal = findViewById(R.id.textValorTotal);
        listaMovimento = findViewById(R.id.listaMovimentos);
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
