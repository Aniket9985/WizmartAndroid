package com.wiz.wizmart.adapters;

import static com.wiz.wizmart.pages.LoginActivity.KEY_NAME;
import static com.wiz.wizmart.pages.LoginActivity.SHARED_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wiz.wizmart.R;
import com.wiz.wizmart.Utilities.CatModel;
import com.wiz.wizmart.pages.LoginActivity;
import com.wiz.wizmart.pages.ProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {
    private Context context;
    private List<CatModel> catModels;
    private SharedPreferences sharedPreferences;
    private String username;

    public CategoryAdapter(Context context, List<CatModel> catModels) {
        this.context=context;
        this.catModels=catModels;
    }

    public void filterList(List<CatModel> filterlist) {
        catModels=filterlist;
        notifyDataSetChanged ();
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from ( context ).inflate ( R.layout.category_items, parent, false );
        return new CatViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CatModel catModel=catModels.get ( position );
        Picasso.get ().load ( catModel.getImg () ).into ( holder.imageView );
        sharedPreferences=context.getSharedPreferences ( SHARED_NAME,Context.MODE_PRIVATE );
        username=sharedPreferences.getString ( KEY_NAME,"" );

        holder.itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (isLogedIn ())
                {
                    Toast.makeText ( context, "Please Login First...", Toast.LENGTH_SHORT ).show ();
                    Intent intent1=new Intent ( context, LoginActivity.class);
                    intent1.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity ( intent1 );
                }else {
                    Intent intent=new Intent ( context, ProductActivity.class );
                    intent.putExtra ( "name", catModel.getServiceName () );
                    intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity ( intent );
                }

            }
        } );

    }
    private  Boolean isLogedIn()
    {
        if (!username.equalsIgnoreCase ( "" ))
        {
            return false;
        }
        else {
            return true;
        }

    }

    @Override
    public int getItemCount() {
        return catModels.size ();
    }

    class CatViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ImageView imageView;

        public CatViewHolder(@NonNull View itemView) {
            super ( itemView );
            // catName=itemView.findViewById ( R.id.txtCatName );
            imageView=itemView.findViewById ( R.id.imgCat );
        }
    }
}
