package com.deepak.yozotask.ui.edit;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.deepak.yozotask.db.AppDatabase;
import com.deepak.yozotask.db.ProductDao;
import com.deepak.yozotask.model.Products;

/**
 * Created by deepakpurohit on 25,August,2019
 */
public class EditProductViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private ProductDao productDao;
    private AppDatabase db;

    public EditProductViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = AppDatabase.getDatabase(application);
        productDao = db.productDao();
    }

    public LiveData<Products> getProduct(String Id) {
        return productDao.getProduct(Id);
    }
}