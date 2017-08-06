package me.karimoff.memochat.memo;

import android.graphics.drawable.Drawable;

import java.util.Date;

/**
 * Created by karimoff on 8/2/17.
 */

public class Memo {
    private Drawable image;
    private String title;
    private Date created;

    public Memo(Drawable image, String title, Date created) {
        this.image = image;
        this.title = title;
        this.created = created;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "image=" + image +
                ", title='" + title + '\'' +
                ", created=" + created +
                '}';
    }
}
