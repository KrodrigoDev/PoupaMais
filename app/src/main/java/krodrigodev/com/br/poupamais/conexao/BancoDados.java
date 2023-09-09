package krodrigodev.com.br.poupamais.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 08/09/2023
 */
public class BancoDados extends SQLiteOpenHelper {

    // dados do meu banco
    public static final String bd = "banco.bd";
    public static final int versao = 1;

    // construtor do banco
    public BancoDados(@Nullable Context context) {
        super(context, bd, null, versao);
    }


    // método para criar tabelas do banco
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(" +
                "id Integer primary key autoincrement,"
                + "nome varchar(30) not null,"
                + "email varchar(50) unique not null,"
                + "senha char(32) not null)");
    }

    // método para atualizar os registritos do banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}