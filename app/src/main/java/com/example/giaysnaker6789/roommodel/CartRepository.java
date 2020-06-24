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
        new InsertNoteAsyncTask(cartDao).execute(cart);
    }

    public void update(Cart cart) {
      new UpdateCartAsynctask(cartDao).execute(cart);

    }

    public void delete(Cart cart) {
        new DeleteCartAsynctask(cartDao).execute(cart);
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





    private static class InsertNoteAsyncTask extends AsyncTask<Cart, Void, Void> {
        private CartDao cartDao;

        private InsertNoteAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... notes) {
            cartDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateCartAsynctask extends AsyncTask<Cart,Void,Void>{
        private CartDao cartDao;

        public UpdateCartAsynctask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.update(carts[0]);
            return null;
        }
    }

    private static class DeleteCartAsynctask extends AsyncTask<Cart,Void,Void>{
        private CartDao cartDao;

        public DeleteCartAsynctask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.delete(carts[0]);
            return null;
        }
    }


}