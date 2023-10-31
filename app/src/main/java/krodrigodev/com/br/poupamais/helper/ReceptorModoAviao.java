package krodrigodev.com.br.poupamais.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import krodrigodev.com.br.poupamais.R;

public class ReceptorModoAviao extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Alertas alertas = new Alertas(context);

        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {

            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);

            if (isAirplaneModeOn) {
                alertas.mensagemLonga(R.string.modo_aviao_ON);
            } else {
                alertas.mensagemLonga(R.string.modo_aviao_OFF);
            }

        }
    }

}
