package com.example.ayo.androidmvvm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> noteList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {

        Note currentNote = noteList.get(i);
        noteViewHolder.title.setText(currentNote.getTitle());
        Log.d("TAG", "TITLE " + currentNote.getTitle());
        noteViewHolder.description.setText(currentNote.getDescription());
        Log.d("TAG", "Desc " + currentNote.getDescription());
        noteViewHolder.priority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNote (List<Note> note){
        this.noteList = note;
        notifyDataSetChanged();
    }

    public Note getNotesAt(int position){
        return noteList.get(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView priority, title, description;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            priority = itemView.findViewById(R.id.priority);
            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onClick(noteList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClick (Note note);
    }

    public void setOnItemClickedListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
