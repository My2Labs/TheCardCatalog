package com.hemenway.thecardcatalog.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "authors")
public class Author {

    @PrimaryKey(autoGenerate = true)
    private int authorId;

    private String authorFirstName;
    private String authorLastName;

    @Ignore
    public Author() {}



    public Author(int authorId, String authorFirstName, String authorLastName) {
        this.authorId = authorId;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

    public int getAuthorId() { return authorId; }

    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getAuthorFirstName() { return authorFirstName; }

    public void setAuthorFirstName(String authorFirstName) { this.authorFirstName = authorFirstName; }

    public String getAuthorLastName() { return authorLastName; }

    public void setAuthorLastName(String authorLastName) { this.authorLastName = authorLastName; }
}