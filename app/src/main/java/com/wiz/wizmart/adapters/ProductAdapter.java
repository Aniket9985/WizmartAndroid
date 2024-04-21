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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.ProductUtilits;
import com.wiz.wizmart.pages.ProductDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductUtilits> productUtilitsList=new ArrayList<> ();
    private String[] array;
    private ArrayAdapter<String> adapter;
    private String s;
    private int count=0;

    public ProductAdapter(Context context, List<ProductUtilits> productUtilitsList) {
        this.context=context;
        this.productUtilitsList=productUtilitsList;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.product_row, parent, false );
        return new ProductViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductUtilits productUtilits=productUtilitsList.get ( position );
        holder.name.setText ( productUtilits.getItem () );
        Picasso.get ().load ( productUtilits.getImg () ).into ( holder.imageView );
        holder.price.setText ( "Rs. " + String.valueOf ( productUtilits.getPrice () ) + "/" + productUtilits.getUom () );

        holder.quickView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( context, ProductDetailsActivity.class );
                intent.putExtra ( "id", productUtilits.getId () );
                context.startActivity ( intent );
            }
        } );
        holder.itemView.setOnClickListener ( new View.OnClickListener () {
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, quickView;
        TextView name, pin_code, price;
        AppCompatButton shop;

        public ProductViewHolder(@NonNull View itemView) {
            super ( itemView );
            imageView=itemView.findViewById ( R.id.product_image );
            name=itemView.findViewById ( R.id.product_name );
            price=itemView.findViewById ( R.id.product_price );
            quickView=itemView.findViewById ( R.id.btnQuickView );
        }
    }
}
