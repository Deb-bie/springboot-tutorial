package com.debbie.tutorial.model;


import jakarta.persistence.*;


//it also means that the class is an entity or something that corresponds to a database table.
@Entity //this annotation indicates that the class is a persistent Java class. This means that the class is an entity that is mapped to a database table
@Table(name = "tutorials") // this annotation specifies the name of the database table to be used for the mapping
public class Tutorial {
    @Id //giving "id" an annotation of id so that when we create the table in the database, the primary key for the table will be the id
    @GeneratedValue(strategy = GenerationType.AUTO) //we will be auto generating the id. The GenerationType.AUTO means an auto increment field. This means that the id is to increase by 1
    private long id; // we are using long because it gives a higher range of storage as compared to int. Long is within the range of (-2^63 to 2^63 -1) while int is ()

    @Column(name = "title") // we are setting the column name for this field to be title. But it is not important or needed to do this.
    private String title;

    @Column(name = "description") // we should set the column names if we want a different name to appear in our database
    private String description;

    @Column(name = "isPublished")
    private boolean isPublished;

    // we could have used the "NoArgsConstructor" annotation if we had lombok as a dependency
    public Tutorial (){}

    // we could have also used the "RequiredArgs" annotation from lombok for this also
    public Tutorial(String title, String description, boolean isPublished) {
        this.title = title;
        this.description = description;
        this.isPublished = isPublished;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    @Override
    public String toString() {
        return "Tutorial [" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPublished=" + isPublished +
                ']';
    }
}
