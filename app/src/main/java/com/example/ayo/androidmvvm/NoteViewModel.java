package com.example.ayo.androidmvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import Model.Note;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
//since we dont have oncreate in this class we initialise our variables inside our constructor
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();

    }


    public void insert (Note note){
        repository.insert(note);
    }

    public void update (Note note){
        repository.update(note);
    }

    public void delete (Note note){
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
//this method returns the LiveData member variable we initialised in our constructor
    public LiveData<List<Note>> getAllNotes (){
        return allNotes;
    }
}
