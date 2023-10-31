package krodrigodev.com.br.poupamais.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import java.util.concurrent.Executor;

import krodrigodev.com.br.poupamais.R;
import krodrigodev.com.br.poupamais.helper.Alertas;
import krodrigodev.com.br.poupamais.helper.UsuarioLogado;

/**
 * @author Kauã Rodrigo
 * @since 08/10/2023
 */
public class Menu extends AppCompatActivity {

    // Atributos
    private TextView nomeUsuario, emailUsuario;
    private GoogleSignInAccount account;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private Alertas alertas;
    private static final int REQUEST_CODE = 101010; // biométria

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        inicializar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarInfo();
    }

    public void recuperarInfo() {
        nomeUsuario.setText(UsuarioLogado.getNomeUsuarioLocal());
        emailUsuario.setText(UsuarioLogado.getEmail());
    }

    // Método que vai inicializar meus atributos
    public void inicializar() {
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsuario);

        alertas = new Alertas(this);

        // api da google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        recuperarInfo();
    }

    // Métodos de navegação (Todos as opções do menu)

    public void voltarPrincipal(View view) {
        finish();
    }

    public void desconectarUsuario(View view) {

        if (account != null) {
            gsc.signOut();
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        // Definir as flags da Intent para iniciar como uma nova tarefa e limpar a pilha de atividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // limpando as váriaveis globais do usuário
        UsuarioLogado.setEmail(null);
        UsuarioLogado.setNomeUsuarioLocal(null);

        finish();
        startActivity(intent);
    }

    public void meuPerfil(View view) {
        compatibilidadeDispotivo();
    }

    public void minhasReceitas(View view) {
        Intent intent = new Intent(Menu.this, MinhasMovimetacoes.class);
        startActivity(intent);
    }

    public void compatibilidadeDispotivo() {

        BiometricManager controlador = BiometricManager.from(this);

        switch (controlador.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.BIOMETRIC_STRONG)) {

            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("Poupa+ Biométria", "O usuário pode autenticar com êxito");
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.d("Poupa+ Biométria", "O usuário não pode autenticar porque não há hardware adequado");
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.d("Poupa+ Biométria", "O usuário não pode autenticar porque o hardware não está disponível");
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.d("Poupa+ Biométria", "O usuário não pode autenticar porque nenhuma credencial biométrica ou de dispositivo está registrada");

                final Intent adicionarBiometria = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                adicionarBiometria.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.BIOMETRIC_STRONG);
                startActivityForResult(adicionarBiometria, REQUEST_CODE);

                break;

            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                Log.d("Poupa+ Biométria", "O usuário não pode se autenticar porque uma vulnerabilidade de segurança foi descoberta com um ou mais sensores de hardware");
                break;

            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                Log.d("Poupa+ Biométria", "O usuário não pode autenticar porque as opções especificadas são incompatíveis com a versão atual do Android");
                break;

            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                Log.d("Poupa+ Biométria", "Não é possível determinar se o usuário pode autenticar");
                break;

        }


        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt((FragmentActivity) this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString); // erro de hardware
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result); // sucesso

                Intent intent = new Intent(Menu.this, MeuPerfil.class);
                startActivity(intent);
                alertas.mensagemLonga(R.string.sucesso_biometria);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed(); // rejeitado
                alertas.mensagemLonga(R.string.falha_biometria);
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_desc))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .setConfirmationRequired(false)
                .build();

        biometricPrompt.authenticate(promptInfo);

    }

}
