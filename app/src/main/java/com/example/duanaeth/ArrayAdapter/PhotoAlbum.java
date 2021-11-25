package com.example.duanaeth.ArrayAdapter;

public class PhotoAlbum {
    String id, linkanh;
    public PhotoAlbum() {

    }

    public PhotoAlbum(String id, String linkanh) {
        this.id = id;
        this.linkanh = linkanh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkanh() {
        return linkanh;
    }

    public void setLinkanh(String linkanh) {
        this.linkanh = linkanh;
    }
}
