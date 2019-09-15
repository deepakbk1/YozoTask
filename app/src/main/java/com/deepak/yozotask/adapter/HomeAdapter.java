package com.deepak.yozotask.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.yozotask.R;
import com.deepak.yozotask.model.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private Activity activity;
    private List<Products> productsList;

    public HomeAdapter(Activity activity, List<Products> productsList) {

        this.productsList = productsList;
        this.activity = activity;

    }

    public void setNotes(List<Products> products) {
        productsList = products;
        notifyDataSetChanged();
    }

    public HomeAdapter(FragmentActivity activity, LiveData<List<Products>> allProducts) {

        this.productsList = (List<Products>) allProducts;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products products = productsList.get(position);
        Picasso.get().load(products.getImage()).into(holder.profilePic);
        holder.company.setText(products.getCompany());
        holder.price.setText(products.getPrice());
        holder.productName.setText(products.getProduct_name());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(v);
    }

    /**
     * View holder class
     */

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_pic)
        ImageView profilePic;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.header)
        RelativeLayout header;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
