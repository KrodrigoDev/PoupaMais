package krodrigodev.com.br.poupamais.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.DataFormatException;

import krodrigodev.com.br.poupamais.helper.UsuarioLogado;
import krodrigodev.com.br.poupamais.model.Movimentacao;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;
import krodrigodev.com.br.poupamais.modeldao.UsuarioDao;

/**
 * @author Kauã Rodrigo
 * @since 02/10/2023
 * <p>
 * Essa classe serve como base para as atividades de adição de despesas e lucros,
 * fornecendo métodos e atributos comuns que podem ser herdados por essas atividades.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class BaseMovimentacao extends Activity {

    // atributos
    private MovimentacaoDao movimentacaoDao;
    private double valorTotal;
    private UsuarioDao usuarioDao;
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarMovimentacao(String data, String descricao, String categoria,
                                   String valor, String nomeColuna, String tipoMovimento)
            throws DataFormatException {

        LocalDate dataAtual = LocalDate.parse(data, this.FORMATO_DATA);

        double valorDigitado = Double.parseDouble(valor);

        Movimentacao movimentacao = new Movimentacao(dataAtual, descricao, categoria, tipoMovimento, UsuarioLogado.getEmail(), valorDigitado);
        this.movimentacaoDao.salvarMovimentacao(movimentacao);

        double valorAtualizado = valorDigitado + valorTotal;
        atualizarValorColuna(valorAtualizado, nomeColuna);

    }

    public void recuperandoValor(String nomeColuna) throws Exception {
        valorTotal = this.usuarioDao.recuperarTotal(UsuarioLogado.getEmail(), nomeColuna);
    }

    private void atualizarValorColuna(double valor, String nomeColuna) {
        this.usuarioDao.alterarDespesaTotal(UsuarioLogado.getEmail(), valor, nomeColuna);
    }

    public void limparCampos(EditText data, EditText categoria, EditText descricao, EditText valor) {
        data.setText("");
        categoria.setText("");
        descricao.setText("");
        valor.setText("");
    }

    public void voltar(ImageView iconVoltar) {
        iconVoltar.setOnClickListener(v -> finish());
    }

    public DateTimeFormatter getFormatoData() {
        return FORMATO_DATA;
    }

    public void setUsuarioDao(Context context) {
        this.usuarioDao = new UsuarioDao(context);
    }

    public void setMovimentacaoDao(Context context) {
        this.movimentacaoDao = new MovimentacaoDao(context);
    }

}
