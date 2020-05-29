package br.com.theoldpinkeye.bindingexamples;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import br.com.theoldpinkeye.bindingexamples.models.UserInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  // Fazendo o Binding dos componentes via Butterknife
  @BindView(R.id.editTextPassword)
  EditText editTextPassword;
  @BindView(R.id.editTextPassRepeat)
  EditText editTextPassRepeat;
  @BindView(R.id.editTextEmail)
  EditText editTextEmail;
  @BindView(R.id.checkBoxAgree)
  CheckBox checkBoxAgree;
  @BindView(R.id.buttonSignUp)
  Button buttonSignUp;
  @BindView(R.id.editTextNome)
  EditText editTextNome;

  // Criação da List de UserInfo
  List<UserInfo> users;
  List<UserInfo> arquivoImportado;

  // determinando o nome padrão do arquivo a ser salvo
  String FILENAME = "users.json";


  // Transferindo dados entre telas usando Intents
  public void sendMessage(View view) {
    // Criando e instanciando o intent
    Intent intent = new Intent(this, ListActivity.class);
    // adicionando os dados ao intent
    intent.putParcelableArrayListExtra("Users", (ArrayList<? extends Parcelable>) users);
    // iniciando a nova activity
    startActivity(intent);
  }

  @Override
  protected void onPause() {
    super.onPause();
    // mandando criar arquivo json no armazenamento
    criaArquivo(this, criaJson(users));
    // mandando salvar SharedPreferences
    salvarPreferences();
  }

  @Override
  protected void onResume() {
    super.onResume();

    restauraJson(this.getPreferences(Context.MODE_PRIVATE));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    // mandando criar arquivo json no armazenamento
    criaArquivo(this, criaJson(users));
    // mandando salvar SharedPreferences
    salvarPreferences();
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
      for (UserInfo u : users) { // enhanced for
        // passará por cada registro dentro do ArrayList e jogará no Logcat
        Log.i("Usuário", u.toString());
      }
    });

    // testando a questão da leitura do arquivo
    arquivoImportado = new ArrayList<>();

    // checando se o arquivo existe
    if (arquivoExiste(this, FILENAME)) {
      // se existir, restaure o JSON a partir do arquivo lido
      restauraJson(lerArquivo(this, FILENAME));
      // conferindo pelo Logcat se os dados estão OK
      for (UserInfo u : arquivoImportado) {
        Log.i("Arquivo importado", u.toString());
      }
    }


  }

  // Criando método que faz a conversão do ArrayList em JSON
  public String criaJson(List<UserInfo> users) {

    // usando a biblioteca Gson pra conversão
    // criando um objeto Gson
    Gson gson = new Gson();

    // mostrando dados convertidos no Logcat somente para conferência
    Log.d("Lista a salvar", gson.toJson(users));

    // retornando o valor em String em formato Json
    return gson.toJson(users);
  }

  // restaura os dados de dentro do JSON armazenado nas SharedPreferences
  public void restauraJson(SharedPreferences data) {
    // criando e instanciando variável que extrai a string de dentro das Shared Preferences
    String json = data.getString("users", "Sem dados");

    // se os dados existirem...
    if (!data.getString("users", "Nulo").equals("Nulo")) {

      // fazer:

      Log.e("dados retornados", json);

      // instanciando um objeto Gson
      Gson gson = new Gson();

      // criando um Type baseado no tipo de dados que queremos obter do JSON
      Type collectionType = new TypeToken<List<UserInfo>>() {
      }.getType();

      // atribuir o JSON processado pelo Gson à minha lista de UserInfo:
      users = gson.fromJson(json, collectionType);
      Log.d("Dados colocados no array users", users.toString());
    }
  }

  // método que restaura dados do arquivo json salvo no armazenamento
  public void restauraJson(String conteudoArquivo) {

    if (conteudoArquivo != null) {
      // fazer:

      Log.e("dados retornados", conteudoArquivo);

      // instanciando um objeto Gson
      Gson gson = new Gson();

      // criando um Type baseado no tipo de dados que queremos obter do JSON
      Type collectionType = new TypeToken<List<UserInfo>>() {
      }.getType();

      // atribuir o JSON processado pelo Gson à minha lista de UserInfo:
      arquivoImportado = gson.fromJson(conteudoArquivo, collectionType);
      Log.d("Dados colocados no array arquivoImportado", arquivoImportado.toString());
    }
  }

  // criando método que salva as SharedPreferences
  public void salvarPreferences() {
    Log.v("Lista a salvar", criaJson(users));

    // criando uma variável para receber as preferências atuais do App
    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

    // Editando as preferências pra colocar os dados a serem salvos
    SharedPreferences.Editor editor = sharedPref.edit();
    // colocando uma String dentro do SharedPreferences
    editor.putString("users", criaJson(users));

    // validando a inserção dos valores
    editor.apply();
  }

  // Criando a estrutura de criação de um arquivo no armazenamento do App
  public void criaArquivo(Context context, String json) {

    try {
      // criando a estrutura vazia de um arquivo no armazenamento:
      FileOutputStream arquivoSaida = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
      // checando se o JSON tem dados para prosseguir com a gravação:
      if (json != null) {
        // jogando dentro do arquivo users.json o conteúdo da String json:
        arquivoSaida.write(json.getBytes());
      }
      // fechando o arquivo de saída:
      arquivoSaida.close();
      // faz aparecer um popup informativo na tela
      Toast
          .makeText(getApplicationContext(), "O arquivo foi salvo com sucesso!", Toast.LENGTH_SHORT)
          .show();
      // return true significa que tudo deu certo
      Log.e("OK", "Arquivo salvo");

      // tratando as Exceptions
    } catch (FileNotFoundException e) {
      // jogando a Exception no Logcat
      e.printStackTrace();
      // faz aparecer um popup informativo na tela
      Toast.makeText(getApplicationContext(), "O arquivo não foi salvo!", Toast.LENGTH_SHORT)
          .show();
      Log.e("ERRO", "Arquivo não encontrado");

    } catch (IOException ioe) {
      // jogando a Exception no Logcat
      ioe.printStackTrace();
      // faz aparecer um popup informativo na tela
      Toast.makeText(getApplicationContext(), "Falha de Entrada/Saída!", Toast.LENGTH_SHORT).show();
      Log.e("ERRO", "Erro de entrada e Saída");

    }
  }

  // criando um  método que checa a existência do arquivo no armazenamento
  public boolean arquivoExiste(Context context, String filename) {
    // capturando o caminho do arquivo
    String caminho = context.getFilesDir().getAbsolutePath() + "/" + filename;
    // criando um objeto do tipo file pra receber a informação de que o arquivo existe
    File arquivo = new File(caminho);
    // se arquivo solicitado existir, retorna true, senão false:
    return arquivo.exists();
  }

  public String lerArquivo(Context context, String filename) {
    // criando elementos necessários para leitura do conteúdo do arquivo
    try {
      // criando objeto para receber o conteúdo em bytes do arquivo
      FileInputStream arquivoEntrada = context.openFileInput(filename);
      // criando objeto para ler o conteúdo recebido no arquivo
      InputStreamReader leitorDeDados = new InputStreamReader(arquivoEntrada);
      // criando o objeto que processa os dados linha a linha
      BufferedReader processador = new BufferedReader(leitorDeDados);
      // criando um construtor de Strings
      StringBuilder construtor = new StringBuilder();
      // Criando a string para receber linhas
      String linha;

      // enquanto houver linhas no arquivo, adicione elas ao construtor
      while ((linha = processador.readLine()) != null) {
        // adicionando as linhas ao construtor
        construtor.append(linha);
      }
      // retornando texto recuperado do arquivo json
      return construtor.toString();

    } catch (FileNotFoundException e) {
      // jogando a Exception no Logcat
      e.printStackTrace();
      // faz aparecer um popup informativo na tela
      Toast.makeText(getApplicationContext(), "O arquivo não encontrado!", Toast.LENGTH_SHORT)
          .show();
      Log.e("ERRO", "Arquivo não encontrado");
      return null;

    } catch (IOException ioe) {
      // jogando a Exception no Logcat
      ioe.printStackTrace();
      // faz aparecer um popup informativo na tela
      Toast.makeText(getApplicationContext(), "Falha de Entrada/Saída!", Toast.LENGTH_SHORT).show();
      Log.e("ERRO", "Erro de entrada e Saída");
      return null;
    }
  }


}
