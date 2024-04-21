package com.wiz.wizmart.adapters;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CartModel;
import com.wiz.wizmart.Utilities.DeleteCartResponce;
import com.wiz.wizmart.Utilities.UpdateCartModel;
import com.wiz.wizmart.retrofit.RetrofitIntance;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductSowAdapter extends RecyclerView.Adapter<CartProductSowAdapter.MyViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;
    private int count;
    private String username;
    private CartModel cartModel;
    int qty;
    SharedPreferences sharedPreferences;
    Dialog dialog;

    public CartProductSowAdapter(Context context, List<CartModel> cartModelList) {
        this.context=context;
        this.cartModelList=cartModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.cart_item_new, parent, false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         cartModel=cartModelList.get ( position );
        Picasso.get ().load ( cartModel.getImg () ).into ( holder.cart_img );

        sharedPreferences=context.getSharedPreferences ( SHARED_NAME, Context.MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME, "" );


        holder.cart_name.setText ( cartModel.getDisplayname () );
        holder.cart_qty.setText ( String.valueOf ( cartModel.getQty () ) );
        holder.cart_prise.setText ( String.valueOf ( cartModel.getPackPrice () ) + "/" + cartModel.getCartpsize () + cartModel.getUom () );
        holder.cart_total.setText ( "Rs. " + String.valueOf ( cartModel.getSubtotal () ) );

        holder.delete.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String id=cartModelList.get ( position ).getId ();
                dialog=new Dialog ( context );
                dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
                dialog.setContentView ( R.layout.progress_bar );
                dialog.setCanceledOnTouchOutside ( false );
                dialog.show ();
                deleteCard ( position, id );
                cartModelList.remove ( position );
                notifyDataSetChanged ();
            }
        } );

        holder.qtyAdd.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String ids=cartModelList.get ( position ).getId ();
                count=Integer.parseInt ( holder.cart_qty.getText ().toString () );
                count++;
                int total=cartModelList.get ( holder.getAdapterPosition () ).getPackPrice () *  count;
                holder.cart_qty.setText ( String.valueOf ( count ) );
                holder.cart_total.setText ( "Rs. " + String.valueOf ( total ) );
                qty=Integer.parseInt ( holder.cart_qty.getText ().toString () );
                getData ( qty, ids );
            }
        } );
        holder.minus.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String cid=cartModelList.get ( position ).getId ();
                int c=Integer.parseInt ( holder.cart_qty.getText ().toString () );
                c--;
                int total=cartModelList.get ( holder.getAdapterPosition () ).getPackPrice ();
                holder.cart_qty.setText ( String.valueOf ( c ) );
                total=total * c ;
                holder.cart_total.setText ( "Rs. " + String.valueOf ( total ) );
                if (c==1)
                {
                    c=1;
                    holder.cart_qty.setText ( String.valueOf ( c ) );
                    total=cartModel.getPackPrice ();
                    holder.cart_total.setText ( "Rs. " + String.valueOf ( total ) );
                    holder.minus.setEnabled ( false );

                }
                getData ( c, cid );
            }
        } );

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private void getData(int q, String id) {
        Call<UpdateCartModel> call=RetrofitIntance.getService ().cartUpdate ( username, q, id );
        call.enqueue ( new Callback<UpdateCartModel> () {
            @Override
            public void onResponse(Call<UpdateCartModel> call, Response<UpdateCartModel> response) {
                if (response.isSuccessful ()) {
                    UpdateCartModel updateCartModel=response.body ();
                } else {
                    Toast.makeText ( context, "An error occurred please try again......", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<UpdateCartModel> call, Throwable t) {
                Toast.makeText ( context, "An error occurred please try again......", Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void deleteCard(int position, String id) {
        Call<DeleteCartResponce> deleteCartResponceCall=RetrofitIntance.getService ().cartDelete ( username, id );
        deleteCartResponceCall.enqueue ( new Callback<DeleteCartResponce> () {
            @Override
            public void onResponse(Call<DeleteCartResponce> call, Response<DeleteCartResponce> response) {
                if (response.isSuccessful ()) {
                    DeleteCartResponce res=response.body ();
                    notifyItemRemoved ( position );
                    dialog.dismiss ();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponce> call, Throwable t) {
                Toast.makeText ( context, "An error occurred please try again......", Toast.LENGTH_SHORT ).show ();
                dialog.dismiss ();
            }
        } );

    }

    public int size() {
        notifyDataSetChanged ();
        return cartModelList.size ();
    }

    @Override
    public int getItemCount() {
        return cartModelList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_img;
        TextView cart_name, cart_prise, cart_total;
        EditText cart_qty;
        ImageButton qtyAdd, delete, minus;

        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            cart_img=itemView.findViewById ( R.id.cart_img );
            cart_name=itemView.findViewById ( R.id.cart_name );
            cart_prise=itemView.findViewById ( R.id.cart_prise );
            cart_total=itemView.findViewById ( R.id.cart_total );
            cart_qty=itemView.findViewById ( R.id.cart_qty );
            qtyAdd=itemView.findViewById ( R.id.qtyAdd );
            delete=itemView.findViewById ( R.id.delete );
            minus=itemView.findViewById ( R.id.minus );

        }
    }
}
