package org.terna.noteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {


    EditText Username;
    EditText Password;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=findViewById(R.id.loginButton);
        Username = findViewById(R.id.usernameEditText);
        Password = findViewById(R.id.passwordEditText);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.e("MainActivity","login pressed");
       String username_input=Username.getText().toString().trim();
        Log.e("MainActivity","Username "+username_input);
       String password_input=Password.getText().toString().trim();
        Log.e("MainActivity","Password "+password_input);
       if(username_input.compareTo("Prafulla")==0 && password_input.compareTo("123")==0)
       {
           Log.e("MainActivity","passwords matched");
           AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
           builder.setTitle("Success");
           builder.setMessage("Logged In Successfully!!");
           builder.setIcon(R.mipmap.tick);
           AlertDialog alertDialog = builder.create();
           alertDialog.show();
           alertDialog.setOnDismissListener(this);

       }
       else
       {
           AlertDialog.Builder b1 = new AlertDialog.Builder(MainActivity.this);
           b1.setIcon(R.mipmap.wrong);
           b1.setTitle("Failed");
           b1.setMessage("Login Failed!");
           AlertDialog alertDialog = b1.create();
           alertDialog.show();
       }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }
}
