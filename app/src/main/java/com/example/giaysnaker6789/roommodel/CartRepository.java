package com.example.giaysnaker6789.roommodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

public class CartRepository {
    private CartDao cartDao;
    private LiveData<List<Cart>> allNotes;

    public CartRepository(Application application) {
        CartDatabase database = CartDatabase.getInstance(application);
        cartDao = database.noteDao();
        allNotes = cartDao.getAllNotes();
    }

    public void insert(Cart cart) {
         cartDao.insert(cart);
    }

    public void update(Cart cart) {
        cartDao.update(cart);

    }

    public void delete(Cart cart) {
        cartDao.delete(cart);
    }

    public void deleteAllNotes() {
        cartDao.deleteAllNotes();
    }

    public LiveData<Integer> getCount() {
        LiveData<Integer>  count;
        count =  cartDao.getcount();
        return count;
    }

    public LiveData<List<Cart>> getAllNotes() {
        return allNotes;
    }

}