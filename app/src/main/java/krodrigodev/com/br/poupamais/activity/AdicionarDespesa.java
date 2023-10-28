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
public class AdicionarDespesa extends BaseMovimentacao {

    private EditText campoData, campoDescricao, campoCategoria, campoValor;
    private final String colunaDespesa = "totaldespesa";
    private Alertas alertas;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesa);

        inicializar();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarDespesa(View view) {

        String data = campoData.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String valor = campoValor.getText().toString();

        if (data.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || valor.isEmpty()) {
            alertas.mensagemLonga(R.string.validar_campos);
            return;
        }

        try {

            String tipoMovimentacao = "despesa";

            salvarMovimentacao(data, descricao, categoria, valor, colunaDespesa, tipoMovimentacao);

            alertas.mensagemLonga(R.string.movimentacao_salva);

            limparCampos(campoData, campoCategoria, campoDescricao, campoValor);

            finish();

        } catch (DataFormatException erro) {
            alertas.mensagemLonga(R.string.data_invalida);
        }

    }


    public void inicializar() {

        setMovimentacaoDao(this);
        setUsuarioDao(this);

        alertas = new Alertas(this);

        campoData = findViewById(R.id.campoDataDespesa);
        campoDescricao = findViewById(R.id.campoDescricaoDespesa);
        campoCategoria = findViewById(R.id.campoCategoriaDespesa);
        campoValor = findViewById(R.id.campoValorDespesa);
        ImageView iconVoltar = findViewById(R.id.iconVoltarDespesa);

        // mudar isso após o banho
        LocalDate dataAtual = LocalDate.now();
        campoData.setText(dataAtual.format(getFormatoData()));

        voltar(iconVoltar);

        try {
            recuperandoValor(colunaDespesa);
        } catch (Exception erro) {
            alertas.erroInterno();
        }

    }

}
