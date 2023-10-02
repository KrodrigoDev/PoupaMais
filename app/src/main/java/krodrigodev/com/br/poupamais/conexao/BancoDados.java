package krodrigodev.com.br.poupamais.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author Kauã Rodrigo
 * @version 0.1
 * @since 01/10/2023
 */
public class BancoDados extends SQLiteOpenHelper {

    // dados do meu banco
    public static final String bd = "banco.bd";
    public static final int versao = 7;

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
                + "senha char(32)," // retirei o not null para realizar um teste
                + "totallucro Real default 0.00,"
                + "totaldespesa Real default 0.00)");

        db.execSQL("create table movimentacao("
                + "id Integer primary key autoincrement,"
                + "data date not null,"
                + "descricao varchar(20) not null,"
                + "categoria varchar(20) not null,"
                + "valor REAL not null,"
                + "tipo varchar(7) not null check(tipo in ('lucro','despesa')),"
                + "email_Usuario varchar(50),"
                + "foreign key(email_Usuario) references usuario(email))");
    }

    // método para atualizar os registritos do banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS movimentacao");
        onCreate(db);
    }

}
