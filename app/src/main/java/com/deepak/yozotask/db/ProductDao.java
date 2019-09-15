package com.deepak.yozotask.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.deepak.yozotask.model.Products;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Products... products);

    @Query("SELECT * FROM Products")
    LiveData<List<Products>> getAllProducts();

    @Query("SELECT * FROM products WHERE id=:id")
    LiveData<Products> getProduct(String id);

    @Update
    void update(Products... products);

    @Delete
    int delete(Products products);

    @Query("DELETE FROM products")
    public void nukeTable();

}