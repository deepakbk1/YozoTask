package com.deepak.yozotask.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.yozotask.R;
import com.deepak.yozotask.model.Products;
import com.deepak.yozotask.ui.edit.EditProductActivity;
import com.deepak.yozotask.ui.edit.EditProductViewModel;
import com.deepak.yozotask.ui.home.HomeActivity;
import com.deepak.yozotask.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by deepakpurohit on 25,August,2019
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {


    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Products> mProducts;


    public ProductListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_row, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        if (mProducts != null) {
            Products products1 = mProducts.get(position);
            holder.setData(products1.getProduct_name(), products1.getPrice(), products1.getCompany(), products1.getImage(), position);
            holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
            // holder.noteItemView.setText(R.string.no_note);
        }
    }

    @Override
    public int getItemCount() {
        if (mProducts != null)
            return mProducts.size();
        else return 0;
    }

    public void setNotes(List<Products> products) {
        mProducts = products;
        notifyDataSetChanged();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
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
        private int mPosition;


        public ProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(String product, String pricetxt, String companytxt, String imgURL, int position) {
            productName.setText(product);
            price.setText(pricetxt);
            company.setText(companytxt);
            Picasso.get().load(imgURL).into(profilePic);
            mPosition = position;
        }

        public void setListeners() {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, EditProductActivity.class);
                intent.putExtra("Id", mProducts.get(mPosition).getId());
                ((Activity) mContext).startActivityForResult(intent, HomeFragment.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
            });
        }
    }
}
