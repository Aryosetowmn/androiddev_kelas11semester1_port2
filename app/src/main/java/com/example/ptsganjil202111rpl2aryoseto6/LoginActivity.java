package com.example.ptsganjil202111rpl2aryoseto6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txtuser, txtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtuser = (EditText) findViewById(R.id.txtusername);
        txtpass = (EditText) findViewById(R.id.txtpassword);
        Button tombol = (Button) findViewById(R.id.tombollogin);

        tombol.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        if (txtuser.getText().toString().equals("admin") && txtpass.getText().toString().equals("admin")) {
            Toast.makeText(LoginActivity.this, "Login Sukses!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else Toast.makeText(LoginActivity.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
    }
}
