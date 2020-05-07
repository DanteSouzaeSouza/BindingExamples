package br.com.theoldpinkeye.bindingexamples;

import androidx.appcompat.app.AppCompatActivity;
import br.com.theoldpinkeye.bindingexamples.models.UserInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // Fazendo o Binding dos componentes via Butterknife
    @BindView(R.id.editTextPassword) EditText editTextPassword;
    @BindView(R.id.editTextPassRepeat) EditText editTextPassRepeat;
    @BindView(R.id.editTextEmail) EditText editTextEmail;
    @BindView(R.id.checkBoxAgree) CheckBox checkBoxAgree;
    @BindView(R.id.buttonSignUp) Button buttonSignUp;
    @BindView(R.id.editTextNome) EditText editTextNome;

    // Criação da List de UserInfo
    List<UserInfo> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // liga os Bindings do butterknife a essa Activity
        ButterKnife.bind(this);

        // instanciando a nossa lista de UserInfo como um ArrayList
        users = new ArrayList<>();

        // adicionando o onClickListener ao objeto Button
        buttonSignUp.setOnClickListener(v -> {
            // adicionando o conteúdo preenchido dos campos a uma variável UserInfo
            UserInfo currentUser = new UserInfo(editTextNome.getText().toString(),
                    editTextPassword.getText().toString(),
                    editTextEmail.getText().toString(),
                    checkBoxAgree.isChecked());
            // testando jogar no LogCat o conteúdo da variável UserInfo
            Log.i("Usuário Atual", currentUser.toString());

            // adicionando registro ao ArrayList
            users.add(currentUser);

            // limpando a tela
            editTextNome.setText("");
            editTextPassword.setText("");
            editTextPassRepeat.setText("");
            editTextEmail.setText("");
            checkBoxAgree.setChecked(false);
            // colocando o foco de volta no controle do Nome
            editTextNome.isFocused();
            // imprimindo os usuários da lista no Logcat
            for (UserInfo u : users){ // enhanced for
                // passará por cada registro dentro do ArrayList e jogará no Logcat
                Log.i("Usuário", u.toString());
            }
        });
    }
}
