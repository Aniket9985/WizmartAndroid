package com.wiz.wizmart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.smarteist.autoimageslider.SliderViewAdapter;
import com.wiz.wizmart.R;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.NewViewHolder> {
    int[] images;

    public SliderAdapter(int[] images) {
        this.images=images;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent) {
        View view=LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.slider_items, parent, false );
        return new NewViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(NewViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource ( images[position] );

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class NewViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;


        public NewViewHolder(View itemView) {
            super ( itemView );
            imageView=itemView.findViewById ( R.id.image_view );

        }
    }
}

