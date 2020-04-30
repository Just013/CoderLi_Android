package com.example.lysandroiddemo.model;

public interface Callback<T> {
    public void onSuccess(final T result);
    public void onFailure(final String message);
}

