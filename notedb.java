package org.terna.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class notedb extends SQLiteOpenHelper {

    private static String NOTE = "Note";
    public static String CREATE_NOTE = "CREATE TABLE "+NOTE+"(n_id INTEGER PRIMARY KEY AUTOINCREMENT,n_name TEXT,n_description TEXT);";
    public static String SELECT_NOTES = "SELECT * FROM "+NOTE;
    public static String EMPTY_NOTES = "DELETE FROM  "+NOTE;

    public notedb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     sqLiteDatabase.execSQL(CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean insertIntoNote(String n_name,String n_description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("n_name", n_name);
        contentValues.put("n_description", n_description);

        SQLiteDatabase database = getWritableDatabase();
        long noteId = database.insert(NOTE, null, contentValues);
        if (noteId > 0) {
            return true;
        } else
        {
            return false;
        }
    }

    public boolean updateNote(int n_id,String n_name,String n_description)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("n_name", n_name);
        contentValues.put("n_description", n_description);
        SQLiteDatabase database = getWritableDatabase();

        String whereClause = "n_id = ?";
        String Args[] = new String[]{String.valueOf(n_id)};

        long noteId = database.update(NOTE,contentValues,whereClause,Args);
        if(noteId>0)
        {
            return  true;
        }
        else
        {
            return  false;
        }
    }

    public boolean DeleteNote(int n_id)
    {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database = getWritableDatabase();
        String whereClause = "n_id = ?";
        String whereArgs[] = new String[]{String.valueOf(n_id)};
        long noteId = database.delete(NOTE,whereClause,whereArgs);
        if(noteId >0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void emptyNotes()
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(EMPTY_NOTES);

    }
    public ArrayList<noteModel> getAllNotes()
    {
        Log.e("notedb","inside get all notes");
        ArrayList<noteModel> notesd = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(SELECT_NOTES,null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                noteModel note = new noteModel();
                note.id = cursor.getInt(cursor.getColumnIndex("n_id"));
                Log.e("notedb"," "+note.id);
                note.title = cursor.getString(cursor.getColumnIndex("n_name"));
                Log.e("notedb"," "+note.title);
                note.description = cursor.getString(cursor.getColumnIndex("n_description"));
                notesd.add(note);
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();
        Log.e("notedb",""+notesd);
        return notesd;
    }
}
