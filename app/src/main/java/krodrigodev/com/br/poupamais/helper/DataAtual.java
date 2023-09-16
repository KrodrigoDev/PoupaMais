package krodrigodev.com.br.poupamais.helper;


import android.os.Build;

import androidx.annotation.RequiresApi;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 16/09/2023
 */
@RequiresApi(api = Build.VERSION_CODES.O) // isso é por conta do localDate
public class DataAtual {

    public static DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Método para obter a data atual formatada
    public static String getDataFormatada() {
        LocalDate dataAtual = LocalDate.now(); // Obtém a data atual
        return dataAtual.format(dataFormatada);
    }

}
