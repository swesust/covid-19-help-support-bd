package com.example.covid19shahajjo.services;

public interface ServiceCallback<TResult> {
    public void onResult(TResult result);
    public void onFailed(Exception exception);
}
