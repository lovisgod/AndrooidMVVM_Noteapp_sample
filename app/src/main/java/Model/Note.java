package Model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private  String Description;

    private int priority;


//
//  this is contructor for the Note object we did not add the id because we are not the one generating the ID
   @Ignore
    public Note(String title, String description, int priority) {
        this.title = title;
        this.Description = description;
        this.priority = priority;
    }
    public Note(String title, String description, int priority, int id) {
        this.title = title;
        this.Description = description;
        this.priority = priority;
        this.id = id;
    }

    public Note(){

    }

    //we create setter for Id so to make Room set it for us
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }

    public int getPriority() {
        return priority;
    }



}
