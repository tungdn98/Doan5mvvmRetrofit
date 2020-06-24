package com.example.giaysnaker6789.roommodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private CartRepository repository;
    private LiveData<List<Cart>> allNotes;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allNotes = repository.getAllNotes();
    }

    public LiveData<Integer> getcount() {
        LiveData<Integer> data=repository.getCount();
        return data;
    }

    public void insert(Cart cart) {
       repository.insert(cart);
    }

    public void update(Cart cart) {
      repository.update(cart);
    }

    public void delete(Cart cart) {
       repository.delete(cart);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Cart>> getAllNotes() {
        return allNotes;
    }
}