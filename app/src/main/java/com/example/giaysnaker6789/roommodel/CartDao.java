package com.example.giaysnaker6789.roommodel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM cart_table")
    void deleteAllNotes();

    @Query("SELECT * FROM cart_table")
    LiveData<List<Cart>> getAllNotes();

    @Query("SELECT COUNT(id) FROM cart_table")
    LiveData<Integer> getcount();

}