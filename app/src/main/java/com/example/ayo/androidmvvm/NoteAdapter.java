package com.example.ayo.androidmvvm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {
//    private List<Note> noteList = new ArrayList<>();
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            if(oldItem.getId() == newItem.getId()){
                return true;
            }
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            if (oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority()== newItem.getPriority()){
                return true;
            }
            return false;
        }
    };



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {

        Note currentNote = getItem(i);
        noteViewHolder.title.setText(currentNote.getTitle());
        Log.d("TAG", "TITLE " + currentNote.getTitle());
        noteViewHolder.description.setText(currentNote.getDescription());
        Log.d("TAG", "Desc " + currentNote.getDescription());
        noteViewHolder.priority.setText(String.valueOf(currentNote.getPriority()));
    }




    public Note getNotesAt(int position){
        return getItem(position);
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
                        listener.onClick(getItem(position));
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
