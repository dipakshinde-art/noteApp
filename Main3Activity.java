package org.terna.noteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {
    Button saveNote;
    EditText note_title, note_description;
    notedb ndb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        saveNote = findViewById(R.id.saveNote);
        note_title = findViewById(R.id.addNoteTitle);
        note_description = findViewById(R.id.addNoteDescription);
        saveNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String title = note_title.getText().toString().trim();
        String description = note_description.getText().toString().trim();
        ndb = new notedb(this,"notedb.db",null,1);
        boolean status =ndb.insertIntoNote(title,description);
        Log.e("Main3Activity","Created Note"+title);
        Log.e("Main3Activity","notes created "+ndb.getAllNotes());
        if(status==true)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.note_created);
            builder.setTitle("Success!");
            builder.setMessage(" Note Successfully Created!");
            builder.create();
            AlertDialog alertDialog = builder.show();
            alertDialog.setOnDismissListener(this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Intent i = new Intent(Main3Activity.this,Main2Activity.class);
        startActivity(i);
    }
}