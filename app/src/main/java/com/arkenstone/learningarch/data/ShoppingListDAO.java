package com.arkenstone.learningarch.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ShoppingListDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingItem item);

    @Query("DELETE from shopping_list_table")
    void deleteAll();

    @Query("SELECT * FROM shopping_list_table ORDER BY name ASC")
    LiveData<List<ShoppingItem>> getAllItems();
}
