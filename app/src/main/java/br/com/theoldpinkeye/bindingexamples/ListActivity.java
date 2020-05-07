package br.com.theoldpinkeye.bindingexamples;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.theoldpinkeye.bindingexamples.models.UserInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
  @BindView(R.id.peopleListView) ListView myListView;

  List<UserInfo> users = new ArrayList<>();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

  // liga os Bindings do butterknife a essa Activity
    ButterKnife.bind(this);






    for (UserInfo u: users){
      Log.i("Item", u.toString());
    }



    ArrayAdapter<UserInfo > arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);

    myListView.setAdapter(arrayAdapter);

    myListView.setOnItemClickListener(
        (parent, view, position, id) -> Log.i("Pessoa tocada: ", users.get(position).toString()));
    // Get the Intent that started this activity and extract the string
    Intent intent = getIntent();
    users = intent.getParcelableArrayListExtra(MainActivity.EXTRA_MESSAGE);

    // Capture the layout's TextView and set the string as its text
    //Toast.makeText(getApplicationContext(),message,LENGTH_LONG).show();




  }
}
