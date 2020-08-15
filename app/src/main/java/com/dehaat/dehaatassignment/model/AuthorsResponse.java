package com.dehaat.dehaatassignment.model;

import java.util.List;

public class AuthorsResponse {

    private List<Author> data;

    public List<Author> getData() {
        return data;
    }

    public void setData(List<Author> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuthorsResponse{" +
                "data=" + data +
                '}';
    }
}
