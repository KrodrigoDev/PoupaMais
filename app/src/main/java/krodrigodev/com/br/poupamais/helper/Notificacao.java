package krodrigodev.com.br.poupamais.helper;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Random;

import krodrigodev.com.br.poupamais.R;

public class Notificacao extends ContextWrapper {

    // Atributos
    private final String canalId = "1"; // Identificador do canal de notificaçã

    // Construtor
    public Notificacao(Context base) {
        super(base);
        canal(); // Chama o método para criar o canal de notificação
    }

    // Método para criar o canal de notificação
    public void canal() {

        String nomeCanal = "Notificação Poupa+";
        String desricaoCanal = "Alertas importantes";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {   // versão do Android >= 26 (Oreo)

            NotificationChannel canal = new NotificationChannel(canalId, nomeCanal, NotificationManager.IMPORTANCE_HIGH);
            canal.setDescription(desricaoCanal); // Define a descrição do canal

            // Obtém o gerenciador de notificações
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            // Cria o canal de notificação usando o gerenciador
            notificationManager.createNotificationChannel(canal);
        }
    }

    // Método para criar uma notificação
    @SuppressLint("MissingPermission")
    public void notificacao(String titulo, String descricao) {

        // Usando o canal previamente definido
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canalId)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(titulo)
                .setContentText(descricao)
                .setPriority(NotificationCompat.PRIORITY_HIGH); // Prioridade da notificação (alta)

        // Obtém o gerenciador de notificações
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Notifica o gerenciador para exibir a notificação com o ID gerado aleatoriamente
        int idNotificacao = new Random().nextInt(11);
        notificationManager.notify(idNotificacao, builder.build());
    }

}
