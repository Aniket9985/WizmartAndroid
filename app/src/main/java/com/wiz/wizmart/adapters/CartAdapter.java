package com.wiz.wizmart.adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.wiz.wizmart.pages.LoginActivity.KEY_VUserName;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CartModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartModel> cartModelList;
    SharedPreferences sharedPreferences;
    int cartid;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context=context;
        this.cartModelList=cartModelList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.cart_item_single_row, parent, false );
        return new CartViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartModel=cartModelList.get ( position );
        Picasso.get ().load ( cartModel.getImg () ).into ( holder.imgCart );

        sharedPreferences=context.getSharedPreferences ( SHARED_NAME, MODE_PRIVATE );
        SharedPreferences.Editor editor=sharedPreferences.edit ();
        editor.putString ( KEY_VUserName, cartModel.getVusername () );
        editor.commit ();
        cartid=cartModel.getMaintotal ();


    }

    public int grandTotal() {
        int total=20;
        for (CartModel model : cartModelList) {
            total+=model.getSubtotal ();
        }
        return total;
    }

    public int subTotal() {
        int subTotal=0;
        for (CartModel model : cartModelList) {
            subTotal+=model.getSubtotal ();
        }
        return subTotal;
    }


    @Override
    public int getItemCount() {
        return cartModelList.size ();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCart;

        public CartViewHolder(@NonNull View itemView) {
            super ( itemView );
            imgCart=itemView.findViewById ( R.id.cart_img1 );
        }
    }
}
