package krodrigodev.com.br.poupamais.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DecimalFormat;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 19/09/2023
 */
public class PrincipalActivity extends AppCompatActivity {

    // atributos
    private MaterialCalendarView calendario;
    private TextView saudacaoUsuario, saldoTotal;
    private UsuarioDao usuarioDao;
    private RecyclerView listaMovimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        // inicializando o usuarioDAO
        usuarioDao = new UsuarioDao(this);

        // inicializando
        calendario = findViewById(R.id.calendario);
        saudacaoUsuario = findViewById(R.id.textSaudacaoUsuario);
        saldoTotal = findViewById(R.id.textValorTotal);
        listaMovimento = findViewById(R.id.listaMovimentos);


        // configurações do adpter


        // configuraçõs do recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaMovimento.setLayoutManager(layoutManager);
        listaMovimento.setHasFixedSize(true);
        //listaMovimento.setAdapter();

        configuracaoCalendario();

    }

    // método para atualizar os valores quando eu voltar para activity (precriso melhorar isso)
    @Override
    protected void onResume() {
        super.onResume();
        recuperarDados();
    }

    // método para configurar o calendário
    public void configuracaoCalendario() {

        // ajustando o título dos meses
        calendario.setTitleMonths(getResources().getStringArray(R.array.meses)); // passando o array de meses

        // ajustando o limite e o máximo
        calendario.state().edit()
                .setMinimumDate(CalendarDay.from(2023, 8, 17))
                .setMaximumDate(CalendarDay.from(2026, 12, 31))
                .commit();
    }

    // método para recuperar o saldo total e o nome do usuário
    @SuppressLint("SetTextI18n")
    public void recuperarDados() {

        // recuperando o saldo total do usuário
        String COLUNALUCRO = "totallucro";
        String COLUNADESPESA = "totaldespesa";

        double lucroTotal = usuarioDao.recuperarTotal(UsuarioLogado.getIdUsuarioLogado(), COLUNALUCRO);
        double despesaTotal = usuarioDao.recuperarTotal(UsuarioLogado.getIdUsuarioLogado(), COLUNADESPESA);
        double totalUsuario = lucroTotal - despesaTotal;

        DecimalFormat formato = new DecimalFormat("0.##");

        saldoTotal.setText("R$ " + formato.format(totalUsuario));

        // recuperando o nome do usuário

        saudacaoUsuario.setText("Olá, " + UsuarioLogado.getNomeUsuarioLogado());
    }

    // métodos de navegação

    public void abrirJanelaLucro(View view) {
        startActivity(new Intent(this, AdicionarLucro.class));
    }

    public void abrirJanelaDespesa(View view) {
        startActivity(new Intent(this, AdicionarDespesa.class));
    }
}