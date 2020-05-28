package br.com.theoldpinkeye.bindingexamples;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.theoldpinkeye.bindingexamples.models.UserInfo;
import java.util.List;

public class MeuAdapter extends BaseAdapter {

  private Context context;
  private List<UserInfo> users;

  public MeuAdapter(Context context, List<UserInfo> users) {
    this.context = context;
    this.users = users;
  }


  @Override
  public int getCount() {
    return users.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    @SuppressLint("ViewHolder") View linha = LayoutInflater.from(context)
        .inflate(R.layout.activity_list_item, parent, false);

    TextView nomeTextView = linha.findViewById(R.id.nomeTextView);
    TextView emailTextView = linha.findViewById(R.id.emailTextView);

    nomeTextView.setText(users.get(position).getNome());
    emailTextView.setText(users.get(position).getEmail());
    return linha;
  }
}
