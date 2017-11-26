package com.yhb.myyouhui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.views.ResizableImageView;

import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

/**
 * Created by Administrator on 2017/11/25.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<String> data;

    public DetailAdapter(LayoutInflater inflater, List<String> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.detail_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(inflater.getContext()).asBitmap()

                 .transition(withCrossFade(500))
                .load(data.get(position))
                .into(holder.iv_detail);
        Log.d("test", "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ResizableImageView iv_detail;
        public ViewHolder(View itemView) {
            super(itemView);
           iv_detail= (ResizableImageView) itemView.findViewById(R.id.iv_detail);
        }
    }
}
