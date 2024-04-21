package com.wiz.wizmart.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.ProductUtilits;
import com.wiz.wizmart.pages.ProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShopMoreAdapter extends RecyclerView.Adapter<ShopMoreAdapter.ShopMoreViewHolder> {
    private Context context;
    private List<ProductUtilits> productUtilitsList=new ArrayList<> ();
    private String[] array;
    private ArrayAdapter<String> adapter;
    private String s;
    private int count=0;

    public ShopMoreAdapter(Context context, List<ProductUtilits> productUtilitsList) {
        this.context=context;
        this.productUtilitsList=productUtilitsList;
    }

    @NonNull
    @Override
    public ShopMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.shop_more_single_page, parent, false );
        return new ShopMoreViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ShopMoreViewHolder holder, int position) {
        ProductUtilits productUtilits=productUtilitsList.get ( position );
        holder.shop_name.setText ( productUtilits.getItem () );
        Picasso.get ().load ( productUtilits.getImg () ).into ( holder.shop_image );
        holder.shop_price.setText ( "RS. " + String.valueOf ( productUtilits.getPrice () ) );
        holder.btnShopQuickView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( context, ProductDetailsActivity.class );
                intent.putExtra ( "id", productUtilits.getId () );
                context.startActivity ( intent );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return productUtilitsList.size ();
    }

    class ShopMoreViewHolder extends RecyclerView.ViewHolder {
        ImageView shop_image;
        TextView shop_name, shop_price;
        ImageView btnShopQuickView;

        public ShopMoreViewHolder(@NonNull View itemView) {
            super ( itemView );
            shop_image=itemView.findViewById ( R.id.shop_image );
            shop_name=itemView.findViewById ( R.id.shop_name );
            shop_price=itemView.findViewById ( R.id.shop_price );
            btnShopQuickView=itemView.findViewById ( R.id.btnView );
        }
    }
}
