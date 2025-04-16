// package com.example.model;

// import java.sql.Date;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "Authors")
// public class Author {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private long authorid;

// @Column(nullable = false)
// private String authorname;
// private Date dateofbirth;

// public Author() {

// }

// public Author(String authorname, Date dateofbirth) {
// this.authorname = authorname;
// this.dateofbirth = dateofbirth;
// }

// public long getAuthorid() {
// return authorid;
// }

// public void setAuthorid(long authorid) {
// this.authorid = authorid;
// }

// public String getAuthorname() {
// return authorname;
// }

// public void setAuthorname(String authorname) {
// this.authorname = authorname;
// }

// public Date getDateofbirth() {
// return dateofbirth;
// }

// public void setDateofbirth(Date dateofbirth) {
// this.dateofbirth = dateofbirth;
// }

// // @Override
// // public String toString() {
// // return "Author{" +
// // "authorid=" + authorid +
// // ", authorname='" + authorname + '\'' +
// // ", dateofbirth=" + dateofbirth +
// // '}';
// // }
// }

package com.example.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authorId;

    @Column(nullable = false, length = 100)
    private String authorName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    public Author() {
    }

    public Author(String authorName, LocalDate dateOfBirth) {
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
