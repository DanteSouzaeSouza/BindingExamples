package br.com.theoldpinkeye.bindingexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.editTextPassword) EditText editTextPassword;
    @BindView(R.id.editTextPassRepeat) EditText editTextPassRepeat;
    @BindView(R.id.editTextEmail) EditText editTextEmail;
    @BindView(R.id.checkBoxAgree) EditText checkBoxAgree;
    @BindView(R.id.buttonSignUp) Button buttonSignUp;

    List<UserInfo> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtNome = findViewById(R.id.editTextNome);


    }
}
