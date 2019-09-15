package com.deepak.yozotask.ui.home;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deepak.yozotask.db.AppDatabase;
import com.deepak.yozotask.db.ProductDao;
import com.deepak.yozotask.model.Products;
import com.deepak.yozotask.utils.ApiResponse;
import com.deepak.yozotask.utils.Repository;
import com.deepak.yozotask.utils.ViewModelFactory;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    @Inject
    ViewModelFactory viewModelFactory;
    private Repository repository;
    private ProductDao ProductDao;
    private AppDatabase productsDB;

    public HomeViewModel(Repository repository) {
        this.repository = repository;
        Application application = new Application();
        productsDB = AppDatabase.getDatabase(application);
        ProductDao = productsDB.productDao();
    }

    public MutableLiveData<ApiResponse> productsresponse() {
        return responseLiveData;
    }

    public void hitgetProducts() {


        disposables.add(repository.executeProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));

    }

    public void insert(Products Products) {
        new InsertAsyncTask(ProductDao).execute(Products);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(ProductDao).execute();
    }

    public MutableLiveData<ApiResponse> getAllProducts() {
        return responseLiveData;
    }

    public void update(Products Products) {
        new UpdateAsyncTask(ProductDao).execute(Products);
    }

    public void delete(Products Products) {
        new DeleteAsyncTask(ProductDao).execute(Products);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("Tag", "ViewModel Destroyed");
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

        InsertAsyncTask(ProductDao productDao) {
            super(productDao);
        }

        @Override
        protected Void doInBackground(Products... products) {
            mAsyncTaskDao.insertProduct(products);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(ProductDao ProductDao) {
            super(ProductDao);
        }

        @Override
        protected Void doInBackground(Products... products) {
            mAsyncTaskDao.update(products[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(ProductDao ProductDao) {
            super(ProductDao);
        }

        @Override
        protected Void doInBackground(Products... products) {
            mAsyncTaskDao.delete(products[0]);
            return null;
        }
    }

    private class DeleteAllAsyncTask extends OperationsAsyncTask {

        public DeleteAllAsyncTask(ProductDao ProductDao) {
            super(ProductDao);
        }

        @Override
        protected Void doInBackground(Products... products) {
            mAsyncTaskDao.nukeTable();
            return null;
        }
    }


}