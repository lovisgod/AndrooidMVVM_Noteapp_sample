package Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Model.Note;

@Dao
public interface NoteDao {

    @Insert
    void insert (Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update (Note note);

    @Delete
    void delete (Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM Note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes(); //livedata here allows us
    // to observe the object returned from the getallNote Operation


}
