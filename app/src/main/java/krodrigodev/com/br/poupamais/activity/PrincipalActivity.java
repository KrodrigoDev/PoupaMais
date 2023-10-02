package krodrigodev.com.br.poupamais.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
 * @version 0.1
 * @since 30/09/2023
 */
public class PrincipalActivity extends AppCompatActivity {

    // atributos
    private MaterialCalendarView calendario;
    private TextView saudacaoUsuario, saldoTotal;
    private UsuarioDao usuarioDao;
    private MovimentacaoDao movimentacaoDao;
    private RecyclerView listaMovimento;
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private String mesAnoSelecionado;
    private AdpterMovimentacoes adpterMovimentacoes;
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        // inicializando o usuarioDAO
        usuarioDao = new UsuarioDao(this);
        movimentacaoDao = new MovimentacaoDao(this);

        // inicializando
        calendario = findViewById(R.id.calendario);
        saudacaoUsuario = findViewById(R.id.textSaudacaoUsuario);
        saldoTotal = findViewById(R.id.textValorTotal);
        listaMovimento = findViewById(R.id.listaMovimentos);

        // inicialização api do google (caso o usuário faça login com o google)
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this, gso);

        account = GoogleSignIn.getLastSignedInAccount(this);

        // configurações do adpter
        adpterMovimentacoes = new AdpterMovimentacoes(movimentacoes, this);

        // configuraçõs do recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaMovimento.setLayoutManager(layoutManager);
        listaMovimento.setHasFixedSize(true);
        listaMovimento.setAdapter(adpterMovimentacoes);

        // inicializando o método ao entrar na tela
        configuracaoCalendario();

    }

    // método para atualizar os valores quando eu voltar para activity (precriso melhorar isso)
    @Override
    protected void onResume() {
        super.onResume();
        recuperarDados();
        recuperarMovimentacoes();
    }

    // método para recuperar as movimentações
    public void recuperarMovimentacoes() {

        String emailUsario;

        if (account != null) {
            emailUsario = account.getEmail();
        } else {
            emailUsario = UsuarioLogado.getEmail();
        }

        // Recupera as movimentações com base no e-mail do usuário
        movimentacoes = movimentacaoDao.recuperarMovimentacaoMes(mesAnoSelecionado, emailUsario);

        // Atualiza o adaptador com as movimentações recuperadas
        adpterMovimentacoes.atualizarMovimentacoes(movimentacoes);
    }


    // método para configurar o calendário
    public void configuracaoCalendario() {

        // ajustando o título dos meses
        calendario.setTitleMonths(getResources().getStringArray(R.array.meses)); // passando o array de meses

        // ajustando o limite e o máximo
        calendario.state().edit()
                .setMinimumDate(CalendarDay.from(2023, 9, 30))
                .setMaximumDate(CalendarDay.from(2026, 12, 31))
                .commit();


        // formato que vai ser usado para filtrar no banco de dados
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        // mês ano selecionados ao entrar no app
        CalendarDay dataAtual = calendario.getCurrentDate();
        mesAnoSelecionado = sdf.format(dataAtual.getDate());

        // ouvinte
        calendario.setOnMonthChangedListener((widget, date) -> {

            mesAnoSelecionado = sdf.format(date.getDate());

            // recuperando as movimentações novamente quando o usuário clicar no calendário
            recuperarMovimentacoes();

        });

    }

    // método para recuperar o saldo total e o nome do usuário
    @SuppressLint("SetTextI18n")
    public void recuperarDados() {

        // Atributos locais
        String COLUNALUCRO = "totallucro";
        String COLUNADESPESA = "totaldespesa";
        String nomeUsuario;
        double lucroTotal;
        double despesaTotal;

        // verificicando se é um usuário local ou com a conta do google
        if (account != null) {
            nomeUsuario = account.getDisplayName();
            lucroTotal = usuarioDao.recuperarTotal(account.getEmail(), COLUNALUCRO);
            despesaTotal = usuarioDao.recuperarTotal(account.getEmail(), COLUNADESPESA);
        } else {
            nomeUsuario = UsuarioLogado.getNomeUsuarioLogado();
            lucroTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), COLUNALUCRO);
            despesaTotal = usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), COLUNADESPESA);
        }

        // aplicando alterações na view com os dados obtidos
        double totalUsuario = lucroTotal - despesaTotal;

        DecimalFormat formato = new DecimalFormat("0.##");

        saldoTotal.setText("R$ " + formato.format(totalUsuario));

        saudacaoUsuario.setText("Olá, " + nomeUsuario);
    }

    // métodos de navegação

    public void abrirJanelaLucro(View view) {
        startActivity(new Intent(this, AdicionarLucro.class));
    }

    public void abrirJanelaDespesa(View view) {
        startActivity(new Intent(this, AdicionarDespesa.class));
    }
}