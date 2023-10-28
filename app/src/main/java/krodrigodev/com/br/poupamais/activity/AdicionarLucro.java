package krodrigodev.com.br.poupamais.activity;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.controller.BaseMovimentacao;
import krodrigodev.com.br.poupamais.helper.Alertas;

/**
 * @author Kauã Rodrigo
 * @since 02/10/2023
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class AdicionarLucro extends BaseMovimentacao {

    private EditText campoData, campoDescricao, campoCategoria, campoValor;
    private final String colunaLucro = "totallucro";
    private Alertas alertas;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lucro);

        inicializar();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarLucro(View view) {

        String data = campoData.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String valor = campoValor.getText().toString();

        if (data.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || valor.isEmpty()) {
            alertas.mensagemLonga(R.string.validar_campos);
            return;
        }

        try {

            String tipoMovimentacao = "lucro";

            salvarMovimentacao(data, descricao, categoria, valor, colunaLucro, tipoMovimentacao);

            alertas.mensagemLonga(R.string.movimentacao_salva);

            limparCampos(campoData, campoCategoria, campoDescricao, campoValor);

            finish();

        } catch (DataFormatException erro) {
            alertas.mensagemLonga(R.string.data_invalida);
        }

    }


    private void inicializar() {

        setMovimentacaoDao(this);
        setUsuarioDao(this);

        alertas = new Alertas(this);

        campoData = findViewById(R.id.campoDataLucro);
        campoDescricao = findViewById(R.id.campoDescricaoLucro);
        campoCategoria = findViewById(R.id.campoCategoriaLucro);
        campoValor = findViewById(R.id.campoValorLucro);
        ImageView iconVoltar = findViewById(R.id.iconVoltarLucro);

        // mudar isso após o banho
        LocalDate dataAtual = LocalDate.now();
        campoData.setText(dataAtual.format(getFormatoData()));

        voltar(iconVoltar);

        try {
            recuperandoValor(colunaLucro);
        } catch (Exception erro) {
            alertas.erroInterno();
        }

    }

}
