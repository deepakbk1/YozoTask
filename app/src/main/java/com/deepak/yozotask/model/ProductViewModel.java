package com.deepak.yozotask.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.deepak.yozotask.db.AppDatabase;
import com.deepak.yozotask.db.ProductDao;

import java.util.List;

/**
 * Created by deepakpurohit on 25,August,2019
 */
public class ProductViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private ProductDao noteDao;
    private AppDatabase noteDB;
    private LiveData<List<Products>> mAllNotes;

    public ProductViewModel(Application application) {
        super(application);

        noteDB = AppDatabase.getDatabase(application);
        noteDao = noteDB.productDao();
        mAllNotes = noteDao.getAllProducts();
    }

    public void insert(Products note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    public LiveData<List<Products>> getAllNotes() {
        return mAllNotes;
    }

    public void update(Products products) {
        new UpdateAsyncTask(noteDao).execute(products);
    }

    public void delete(Products note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class OperationsAsyncTask extends AsyncTask<Products, Void, Void> {

        ProductDao mAsyncTaskDao;

        OperationsAsyncTask(ProductDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Products... notes) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(ProductDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Products... notes) {
            mAsyncTaskDao.insertProduct(notes[0]);
            return null;
        }
    }



    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(ProductDao productDao) {
            super(productDao);
        }

        @Override
        protected Void doInBackground(Products... products) {
            mAsyncTaskDao.update(products[0]);
            return null;
        }
    }
    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(ProductDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Products... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }
}
