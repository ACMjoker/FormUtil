package com.example.formapp.utils.form.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formapp.R;
import com.example.formapp.utils.GlideUtils;
import com.example.formapp.utils.form.bean.FormImageBean;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {
    private List<FormImageBean.ImageData> imageList;
    private Context mContext;

    public ImageListAdapter(List<FormImageBean.ImageData> imageList, Context mContext) {
        this.imageList = imageList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_form_image, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        FormImageBean.ImageData imageItemBean = imageList.get(position);
        ImageView itemImage = viewHolder.itemImage;
        GlideUtils.showImage(mContext, imageItemBean.getImageUrl(),itemImage);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
        }
    }
}
