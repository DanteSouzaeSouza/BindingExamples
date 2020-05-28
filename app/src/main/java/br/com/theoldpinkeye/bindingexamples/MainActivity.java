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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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

    // Checando se o arquivo existe antes de salvarmos o conteúdo
    public boolean isFilePresent(Context context, String filename) {
        // para checar a existência do arquivo, precisamos do caminho onde ele possívelmente estaria
        // Esse caminho é dado pelo Context da aplicação
        String path = context.getFilesDir().getAbsolutePath() + "/" + filename;
        // achando ou não, jogue numa variável File
        File file = new File(path);

        // Existindo arquivo retorna true, mas se File estiver vazia, retorna false;
        return file.exists();
    }

    // Criando método para salvamento/criação do arquivo no armazenamento
    private boolean createFile(Context context, String fileName, String jsonString) {
        try {
            // criando a stream de saída do arquivo
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            // se a string do Json gerado não estiver vazia, gravar os Bytes na FileOutputStream
            if (jsonString != null) {
                // adicionando os dados à FileOutputStream
                fos.write(jsonString.getBytes());
            }
            // fechando a FileOutputStream
            fos.close();
            // Se deu tudo certo, retorna true
            return true;

        } catch (Exception ex) {
            // tratando a exceção
            // mandando imprimir a exceção no Logcat
            ex.printStackTrace();
            // retornando false caso haja erro
            return false;
        }
    }

    // Criando método para leitura do arquivo com os dados de usuários
    private String readFile(Context context, String fileName){
        try {
            // Chamando e abrindo o arquivo
            FileInputStream fileInputStream = context.openFileInput(fileName);
            // lendo conteúdo do arquivo
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            // processar o conteúdo do arquivo
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            // criando um StringBuilder para receber os dados lidos e formar a string completa
            StringBuilder stringBuilder = new StringBuilder();
            // criando variável temporária para receber as linhas do arquivo
            String line;
            // enquanto houver linhas no arquivo, adicione-as ao StringBuilder
            while ((line = bufferedReader.readLine()) != null){
                // adicionando cada linha ao StringBuilder
                stringBuilder.append(line);
            }
            // retornando a string gerada pelo StringBuilder
            return stringBuilder.toString();

        } catch (Exception ex){
            // mostrando o conteúdo da exception
            ex.printStackTrace();
            // retornando null caso não consiga ler o arquivo
            return null;
        }

    }

}
