package krodrigodev.com.br.poupamais.activity;

import static krodrigodev.com.br.poupamais.helper.DataAtual.dataFormatada;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.DataAtual;
import krodrigodev.com.br.poupamais.helper.IdUsuarioLogado;
import krodrigodev.com.br.poupamais.model.Movimentacao;
import krodrigodev.com.br.poupamais.modeldao.MovimentacaoDao;

public class AdicionarDespesa extends AppCompatActivity {

    private TextInputEditText campoData, campoDescricao, campoCategoria;
    private EditText campoValor;
    private MovimentacaoDao movimentacaoDao;


    @RequiresApi(api = Build.VERSION_CODES.O) //talvez procurar outra solução (DEPOIS)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_despesa);

        // inicializando o mocimentoDAO
        movimentacaoDao = new MovimentacaoDao(this);

        // fazendo referencias
        campoData = findViewById(R.id.campoDataDespesa);
        campoDescricao = findViewById(R.id.campoDescricaoDespesa);
        campoCategoria = findViewById(R.id.campoCategoriaDespesa);
        campoValor = findViewById(R.id.campoValorDespesa);


        //fazendo a modificação no texto da data
        campoData.setText(DataAtual.getDataFormatada());
    }

    // método para salvar uma despesa
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvarDespesa(View view) {

        String data = campoData.getText().toString();
        String descricao = campoDescricao.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String valor = campoValor.getText().toString();

        // validando de todos os campos foram preenchidos
        if(data.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || valor.isEmpty()){

            Toast.makeText(this, R.string.validar_campos, Toast.LENGTH_SHORT).show();

        } else {

            Movimentacao movimentacao = new Movimentacao();

            movimentacao.setData(LocalDate.parse(data,dataFormatada)); // melhorar isso depois
            movimentacao.setDescricao(descricao);
            movimentacao.setCategoria(categoria);
            movimentacao.setValor(Double.parseDouble(valor));
            movimentacao.setTipo("despesa");
            movimentacao.setId_usuario(IdUsuarioLogado.getIdUsuarioLogado());

            movimentacaoDao.salvarMovimentacao(movimentacao);

            limpaCampo();

            Toast.makeText(this, R.string.despesa_adicionada, Toast.LENGTH_SHORT).show();
        }

    }

    // método para limpar os campos
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void limpaCampo(){
       campoData.setText(DataAtual.getDataFormatada());
       campoCategoria.setText("");
       campoDescricao.setText("");
       campoValor.setText("");
    }


}