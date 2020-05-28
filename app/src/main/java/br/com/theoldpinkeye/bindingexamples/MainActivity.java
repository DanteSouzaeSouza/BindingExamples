package br.com.theoldpinkeye.bindingexamples;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.theoldpinkeye.bindingexamples.models.UserInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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

    public void sendMessage(View view){
        Intent intent = new Intent(this, ListActivity.class);
        intent.putParcelableArrayListExtra("Users", (ArrayList<? extends Parcelable>) users);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.v("Lista a salvar", criaJson(users));

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("users", criaJson(users));
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        restauraJson(this.getPreferences(Context.MODE_PRIVATE));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.v("Lista a salvar", criaJson(users));

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("users", criaJson(users));
        editor.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        restauraJson(this.getPreferences(Context.MODE_PRIVATE));


    }

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
    public String criaJson(List<UserInfo> users){
        Gson gson = new Gson();
        Log.d("Lista a salvar", gson.toJson(users));
        return gson.toJson(users);
    }

    public void restauraJson(SharedPreferences data){

        String json = data.getString("users", "Sem dados");

        if (!data.getString("users", "Nulo").equals("Nulo")){
            Log.e("dados retornados", json);
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<UserInfo>>() {
            }.getType();
            users  = gson.fromJson(json, collectionType);
            Log.d("Dados colocados no array users", users.toString());
        }
    }
}
