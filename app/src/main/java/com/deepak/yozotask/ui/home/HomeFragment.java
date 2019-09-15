package com.deepak.yozotask.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.yozotask.MyApplication;
import com.deepak.yozotask.R;
import com.deepak.yozotask.adapter.HomeAdapter;
import com.deepak.yozotask.adapter.ProductListAdapter;
import com.deepak.yozotask.db.AppDatabase;
import com.deepak.yozotask.db.ProductDao;
import com.deepak.yozotask.model.ProductViewModel;
import com.deepak.yozotask.model.Products;
import com.deepak.yozotask.ui.edit.EditProductActivity;
import com.deepak.yozotask.utils.ApiResponse;
import com.deepak.yozotask.utils.SharedPreferenceHelper;
import com.deepak.yozotask.utils.ViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 1;

    @Inject
    ViewModelFactory viewModelFactory;
    Unbinder unbinder;
    @BindView(R.id.rv_main_home)
    RecyclerView rvMainHome;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.progressBar_home)
    LinearLayout progressBarHome;
    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private ArrayList<Products> productsArrayList = new ArrayList<>();
    private ProductDao productDao;
    private ProductListAdapter productListAdapter;
    private AppDatabase appDatabase;
    private ProductViewModel productViewModel;
    boolean isFirstTime = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        appDatabase = AppDatabase.getDatabase(getActivity());
        isFirstTime = SharedPreferenceHelper.getBoolean("isFirstTime",true);
        if (isFirstTime) {
            ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);
            homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
            homeViewModel.productsresponse().observe(this, this::consumeResponse);
            homeViewModel.hitgetProducts();
        } else {
            setDataOffline();
        }

        return view;
    }

    private void setDataOffline() {
        progressBarHome.setVisibility(View.GONE);
        rvMainHome.setVisibility(View.VISIBLE);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllNotes().observe(this, products -> productListAdapter.setNotes(products));
        productListAdapter = new ProductListAdapter(getActivity());
        rvMainHome.setAdapter(productListAdapter);
        rvMainHome.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                progressBarHome.setVisibility(View.VISIBLE);
                rvMainHome.setVisibility(View.GONE);
                break;
            case SUCCESS:
                progressBarHome.setVisibility(View.GONE);
                rvMainHome.setVisibility(View.VISIBLE);
                renderSuccessResponse(apiResponse.data);
                break;
            case ERROR:
                Toast.makeText(getActivity(), apiResponse.error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        try {
            if (response != null) {

                JsonArray jsonarry = response.getAsJsonArray();
                Products[] array = new Gson().fromJson(jsonarry.toString(), Products[].class);
                productsArrayList.clear();
                productsArrayList = new ArrayList<>(Arrays.asList(array));
                homeViewModel.deleteAll();
                for (int i = 0; i < productsArrayList.size(); i++) {
                    saveInDB(productsArrayList.get(i));
                }

                setDataOffline();
                SharedPreferenceHelper.put("isFirstTime",false);
            }
        } catch (Exception e) {
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void saveInDB(Products products) {

        Products products1 = new Products(0, products.getProduct_name(), products.getPrice(), products.getCompany(), products.getImage());
        homeViewModel.insert(products1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to update the note
            Products products = new Products(
                    data.getIntExtra(EditProductActivity.PRODUCT_ID, 0),
                    data.getStringExtra(EditProductActivity.UPDATED_PRODUCT_NAME),
                    data.getStringExtra(EditProductActivity.UPDATED_PRODUCT_PRICE),
                    data.getStringExtra(EditProductActivity.UPDATED_PRODUCT_COMPANY)
                    , data.getStringExtra(EditProductActivity.UPDATED_PRODUCT_IMAGE));

            productViewModel.update(products);


        } else {
            Toast.makeText(
                    getActivity(),
                    R.string.update_error,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDetach() {
        unbinder.unbind();
        super.onDetach();
    }
}
