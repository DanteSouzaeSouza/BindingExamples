package br.com.theoldpinkeye.bindingexamples;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import br.com.theoldpinkeye.bindingexamples.models.UserInfo;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

  // Criando uma list de UserInfo pra receber os dados do intent
  List<UserInfo> users;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    // criando intent pra receber os dados da outra activity
    Intent intent = getIntent();
    // atribuindo o "Extra" com o ArrayList à List criada aqui
    users = intent.getParcelableArrayListExtra("Users");

    MeuAdapter adapter = new MeuAdapter(this, users);
    ListView lista = findViewById(R.id.usersListView);
    lista.setAdapter(adapter);
    // Fazendo o BINDING da toolbar do layout com o código java
    Toolbar toolbar = findViewById(R.id.list_toolbar);

    // configurando a minha toolbar como uma SupportActionBar
    setSupportActionBar(toolbar);

    // atrelando à nossa nova ToolBar uma ActionBar
    ActionBar actionBar = getSupportActionBar();

    // ativando a actionbar com um botão HOME na forma de um botão voltar
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setTitle("Lista de usuários");

    // somente listando os valores que vieram
    for (UserInfo u: users){
      Log.d("Dado", u.toString());
    }

    // TODO: Criar a visualização desses dados usando uma ListView com um layout próprio para os itens da listview (criado)
  }
}
