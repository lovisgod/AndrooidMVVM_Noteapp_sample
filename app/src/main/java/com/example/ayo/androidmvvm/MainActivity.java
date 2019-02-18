package com.example.ayo.androidmvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import Model.Note;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST_MAINACTIVITY = 1;
    public static final int EDIT_NOTE_REQUEST_MAINACTIVITY = 2;

    private NoteViewModel noteViewModel;
    private FloatingActionButton actionButton;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.note_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);


        actionButton = findViewById(R.id.add_note);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST_MAINACTIVITY);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //Implement Recycler View
                adapter.setNote(notes);
            }
        });
//this class object help us to implement swipe to delete on our recycler view Item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Note position = adapter.getNotesAt(viewHolder.getAdapterPosition()); //this gets an object type of Note at position swiped
                noteViewModel.delete(position);
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView); //this attach this class to the recyclerView

        adapter.setOnItemClickedListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onClick(Note note) {
                Intent edit = new Intent(MainActivity.this, AddNoteActivity.class);
                edit.putExtra(AddNoteActivity.EXTRA_ID, note.getId());
                edit.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
                edit.putExtra(AddNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                edit.putExtra(AddNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(edit, EDIT_NOTE_REQUEST_MAINACTIVITY);

                Log.d("TAG", "intectCheck :" + edit);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "Intent3: " + data);
        if (requestCode == ADD_NOTE_REQUEST_MAINACTIVITY && resultCode == RESULT_OK) {
            Log.d("TAG", "Intent : " + data.getStringExtra(AddNoteActivity.EXTRA_TITLE));
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST_MAINACTIVITY && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1);
            Log.d(TAG, "onActivityResult For Updated Note is : " + id);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            } else {

                String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

                Note note = new Note(title, description, priority, id);
//                note.setId(id);
                noteViewModel.update(note);

                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void deleteNote() {
        noteViewModel.deleteAllNotes();
        Toast.makeText(this, "All Note Deleted", Toast.LENGTH_SHORT).show();
    }
}
