package krodrigodev.com.br.poupamais.helper;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.activity.LoginActivity;

public class Alertas extends Activity {

    // atributo
    private Context context;

    // construtor
    public Alertas(Context context) {
        this.context = context;
    }

    public void mensagemLonga(int idMensagem) {
        Toast.makeText(context, idMensagem, Toast.LENGTH_SHORT).show();
    }

    public void mensagemAlerta(int idMensagem, int idTitulo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(idMensagem);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(idTitulo);

        builder.setPositiveButton(R.string.alerta_visto, (dialog, which) -> {
            // pensar em algo
        });

        builder.setCancelable(false);

        builder.create().show();
    }

    public void erroInterno() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.titulo_erro);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.mensagem_erro);

        builder.setPositiveButton(R.string.alerta_visto, (dialog, which) -> {

            // implementar um envio descritivo sobre o erro para o meu e-mail ou arquivo de log

            // fechando as atividades atuais e voltando para tela de login
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).finish();
            }

        });

        builder.setCancelable(false);

        builder.create().show();
    }


}
