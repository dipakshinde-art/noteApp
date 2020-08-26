package org.terna.noteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {
Button updateNote;
EditText title,description;
Bundle intent_extras;
int got_note_id;
String got_title;
String got_description;
notedb ndb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        updateNote = findViewById(R.id.updateNote);
        ndb = new notedb(this,"notedb.db",null,1);
        title = findViewById(R.id.updateNoteTitle);
        description = findViewById(R.id.updateNoteDescription);
        intent_extras = getIntent().getExtras();
        got_note_id=intent_extras.getInt("noteid");
        got_title=intent_extras.getString("NoteTitle");
        got_description = intent_extras.getString("NoteDescription");
        title.setText(got_title);
        description.setText(got_description);
        updateNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ndb.updateNote(got_note_id,title.getText().toString().trim(),description.getText().toString().trim());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.tick);
        builder.setTitle("Success");
        builder.setMessage("Note Successfully Updated!");
        AlertDialog alert = builder.create();
        alert.show();
        alert.setOnDismissListener(this);

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        Intent i= new Intent(Main4Activity.this,Main2Activity.class);
        startActivity(i);
    }
}
