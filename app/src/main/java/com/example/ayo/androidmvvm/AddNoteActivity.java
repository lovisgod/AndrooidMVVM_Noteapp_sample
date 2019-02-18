package com.example.ayo.androidmvvm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.ayo.androidmvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.ayo.androidmvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.ayo.androidmvvm.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.example.ayo.androidmvvm.EXTRA_ID";

    private EditText title, description;
    private NumberPicker picker;
    Intent i;
    private static final String TAG = "AddNoteActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.edit_text_title);
        description = findViewById(R.id.edit_text_description);
        picker = findViewById(R.id.priority_picker);

        picker.setMinValue(1);
        picker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        i = getIntent();

        if(i.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            Log.d(TAG, "note id is : " + i.getIntExtra(EXTRA_ID,-1));
            title.setText(i.getStringExtra(EXTRA_TITLE));
            description.setText(i.getStringExtra(EXTRA_DESCRIPTION));
            picker.setValue(i.getIntExtra(EXTRA_PRIORITY, 1));
        }else{
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.note_activity_menu:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveNote() {

        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();
        int notePriority = picker.getValue();

        if (noteTitle.trim().isEmpty() || noteDescription.trim().isEmpty()){
            Toast.makeText(this, "TITLE OR DESCRIPTION SHOULD NOT BE EMPTY",
                    Toast.LENGTH_SHORT).show();
            return;
        }else {
            updateNote();
        }
        finish();
    }

    private void updateNote(){
        Intent updateIntent = new Intent();

        updateIntent.putExtra(EXTRA_TITLE,title.getText().toString());
        updateIntent.putExtra(EXTRA_DESCRIPTION,description.getText().toString());
        updateIntent.putExtra(EXTRA_PRIORITY,picker.getValue());
        Log.d("TAG", "Note has been updated : " + title.getText().toString());
        int id = i.getIntExtra(EXTRA_ID,-1);
        Log.d(TAG, "note id int is : "+ updateIntent.getIntExtra(EXTRA_ID, -1));
        if (id != -1){
            updateIntent.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,updateIntent);
    }
}
