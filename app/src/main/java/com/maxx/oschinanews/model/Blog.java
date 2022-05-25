package com.maxx.oschinanews.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/*public class Blog {

    public int id;
    public String author;
    public String pubDate;
    public String title;
    public long authorId;
    public int commentCount;
    public long type;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", commentCount=" + commentCount +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return id == blog.id && authorId == blog.authorId && commentCount == blog.commentCount && type == blog.type && author.equals(blog.author) && pubDate.equals(blog.pubDate) && title.equals(blog.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, pubDate, title, authorId, commentCount, type);
    }
}*/
@Entity(tableName = "blog")
public class Blog {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "no",typeAffinity = ColumnInfo.INTEGER)
    public int no;
    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    public int id;
    @ColumnInfo(name = "author",typeAffinity = ColumnInfo.TEXT)
    public String author;
    @ColumnInfo(name = "pubdate",typeAffinity = ColumnInfo.TEXT)
    public String pubDate;
    @ColumnInfo(name = "title",typeAffinity = ColumnInfo.TEXT)
    public String title;
    @ColumnInfo(name = "authorid",typeAffinity = ColumnInfo.INTEGER)
    public long authorId;
    @ColumnInfo(name = "commentcount",typeAffinity = ColumnInfo.INTEGER)
    public int commentCount;
    @ColumnInfo(name = "type",typeAffinity = ColumnInfo.INTEGER)
    public long type;

    @Override
    public String toString() {
        return "Blog{" +
                "no=" + no +
                ", id=" + id +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", commentCount=" + commentCount +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return no == blog.no && id == blog.id && authorId == blog.authorId && commentCount == blog.commentCount && type == blog.type && author.equals(blog.author) && pubDate.equals(blog.pubDate) && title.equals(blog.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, id, author, pubDate, title, authorId, commentCount, type);
    }
}
