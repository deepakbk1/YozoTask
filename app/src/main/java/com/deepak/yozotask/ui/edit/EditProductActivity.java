package com.deepak.yozotask.ui.edit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.deepak.yozotask.R;
import com.deepak.yozotask.model.Products;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProductActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "product_id";
    public static final String UPDATED_PRODUCT_NAME = "product_name";
    public static final String UPDATED_PRODUCT_PRICE = "product_price";
    public static final String UPDATED_PRODUCT_COMPANY = "product_company";
    public static final String UPDATED_PRODUCT_IMAGE = "product_image";

    String imgURL;
    @BindView(R.id.etProductName)
    AppCompatEditText etProductName;
    @BindView(R.id.etProductPrice)
    AppCompatEditText etProductPrice;
    @BindView(R.id.etProductCompany)
    AppCompatEditText etProductCompany;

    private Bundle bundle;
    private int productId;
    private LiveData<Products> product;

    EditProductViewModel editProductViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            productId = bundle.getInt("Id");
        }

        editProductViewModel = ViewModelProviders.of(this).get(EditProductViewModel.class);
        product = editProductViewModel.getProduct(productId + "");
        Log.d("Product", product.toString());
        product.observe(this, product -> etProductName.setText(product.getProduct_name()));
        product.observe(this, product -> etProductPrice.setText(product.getPrice()));
        product.observe(this, product -> etProductCompany.setText(product.getCompany()));

        product.observe(this, product -> imgURL = product.getImage());

    }

    public void updateProduct(View view) {
        String updatedProductName = etProductName.getText().toString();
        String updatedProductPrice = etProductPrice.getText().toString();
        String updatedProductCompany = etProductCompany.getText().toString();
        String updatedProductIMAGE = imgURL;
        Intent resultIntent = new Intent();
        resultIntent.putExtra(PRODUCT_ID, productId);
        resultIntent.putExtra(UPDATED_PRODUCT_NAME, updatedProductName);
        resultIntent.putExtra(UPDATED_PRODUCT_PRICE, updatedProductPrice);
        resultIntent.putExtra(UPDATED_PRODUCT_COMPANY, updatedProductCompany);
        resultIntent.putExtra(UPDATED_PRODUCT_IMAGE, updatedProductIMAGE);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}
