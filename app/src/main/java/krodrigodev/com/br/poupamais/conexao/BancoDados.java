package krodrigodev.com.br.poupamais.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author Kauã Rodrigo
 * @since 01/10/2023
 */
public class BancoDados extends SQLiteOpenHelper {

    public static final String nomeBanco = "banco.bd";
    public static final int versao = 23;


    public BancoDados(@Nullable Context context) {
        super(context, nomeBanco, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tabela de usuários
        db.execSQL("create table usuario(" +
                "id Integer primary key autoincrement,"
                + "nome varchar(30) not null,"
                + "email varchar(50) unique not null,"
                + "senha char(32) not null,"
                + "totallucro Real default 0.00,"
                + "totaldespesa Real default 0.00)");

        // Tabela de movimentações
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Remove as tabelas antigas se elas existirem
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS movimentacao");
        // Cria as tabelas novamente
        onCreate(db);

    }

}
