package com.example.ayo.androidmvvm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Update;
import android.os.AsyncTask;

import java.util.List;

import Database.NoteDao;
import Database.NoteDatabase;
import Model.Note;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteRepository (Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();

        allNotes = noteDao.getAllNotes();

    }

    public void insert (Note note){

        new InsertAssyncTask(noteDao).execute(note);

    }

    public void delete (Note note){

        new DeleteAssyncTask(noteDao).execute(note);

    }

    public void update (Note note){

        new UpdateAssyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes(){

        new DeleteAllNotesAssyncTask(noteDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertAssyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public InsertAssyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteAssyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public DeleteAssyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateAssyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public UpdateAssyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAssyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;

        public DeleteAllNotesAssyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.deleteAllNotes();
            return null;
        }
    }
}
