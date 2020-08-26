package org.terna.noteapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
 Button addNote;
 RecyclerView recyclerView;
 ArrayList<noteModel> notes;
 notedb ndb;
 ImageView deleteall;
 SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button addNote = findViewById(R.id.addNoteButton);
        addNote.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ndb = new notedb(this,"notedb.db",null,1);
        database=ndb.getReadableDatabase();
        notes = ndb.getAllNotes();
        deleteall = findViewById(R.id.deleteAllimage);
        deleteall.setImageResource(R.mipmap.delete_all);
        deleteall.setOnClickListener(this);
        recyclerView.setAdapter(new ListOfNotesAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        notedb ndb = new notedb(this,"notedb.db",null,1);
        notes = ndb.getAllNotes();
        recyclerView.setAdapter(new ListOfNotesAdapter());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addNoteButton) {
            Intent intent_addnote = new Intent(Main2Activity.this, Main3Activity.class);
            startActivity(intent_addnote);
        }
        else if(view.getId()==R.id.deleteAllimage)
        {
            ndb.emptyNotes();
            notes = ndb.getAllNotes();
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Cleared Notes");
            builder.setMessage("Successfully Cleared All Notes!");
            builder.setIcon(R.mipmap.tick);
            AlertDialog alertDialog = builder.create();
            builder.show();
            recyclerView.setAdapter(new ListOfNotesAdapter());
        }
    }


    public class ListOfNotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




        public class Note extends RecyclerView.ViewHolder {
          TextView note_title,note_description;
          ImageView delete_note;
            public Note(@NonNull View itemView) {
                super(itemView);
                note_title=itemView.findViewById(R.id.noteTitleView);
                note_description=itemView.findViewById(R.id.noteDescriptionView);
                delete_note=itemView.findViewById(R.id.deleteNoteImage);
                delete_note.setImageResource(R.mipmap.delete);
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noteview,parent,false);

            return new Note(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final noteModel note = notes.get(position);
            ((Note)holder).note_description.setText(note.description);
            ((Note)holder).note_title.setText(note.title);
            ((Note)holder).delete_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { ndb.DeleteNote(note.id);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Success");
                builder.setIcon(R.mipmap.tick);
                builder.setMessage("Note Successfully Deleted!");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                notes = ndb.getAllNotes();
                recyclerView.setAdapter(new ListOfNotesAdapter());

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Main2Activity.this,Main4Activity.class);
                    i.putExtra("noteid",note.id);
                    i.putExtra("NoteTitle",note.title);
                    i.putExtra("NoteDescription",note.description);
                    startActivity(i);

                }
            });
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
    }
}
