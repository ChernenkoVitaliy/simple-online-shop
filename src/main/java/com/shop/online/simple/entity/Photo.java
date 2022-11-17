package com.shop.online.simple.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Photo {
    private String photoPath;
    private LocalDate creationDate;

    public Photo(String photoPath, LocalDate creationDate) {
        this.photoPath = photoPath;
        this.creationDate = creationDate;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return photoPath.equals(photo.photoPath) && creationDate.equals(photo.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoPath, creationDate);
    }
}
