package com.maxx.oschinanews.model;

import java.util.Objects;

public class Blog {

    public int id;
    public String author;
    public String pubDate;
    public String title;
    public int authorId;
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
}
