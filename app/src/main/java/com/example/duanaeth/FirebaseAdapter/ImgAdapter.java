package com.example.duanaeth.FirebaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanaeth.ArrayAdapter.NhaTro;
import com.example.duanaeth.ArrayAdapter.PhotoAlbum;
import com.example.duanaeth.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.PhotoViewHolder>{
    private Context mContext;
    private List<PhotoAlbum> mListPhoto;
    private int layout;

    public ImgAdapter() {

    }

    public ImgAdapter(Context context, int layout, List<PhotoAlbum> list) {
        this.mContext = context;
        this.mListPhoto = list;
        this.layout=layout;

    }
    public void setDataPhoto(List<PhotoAlbum> list) {
        this.mListPhoto = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        final PhotoAlbum photoAlbum = mListPhoto.get(position);
        if (photoAlbum == null) {
            return;
        }
        String linkHanhDB = photoAlbum.getLinkanh();
        Picasso.get().load(linkHanhDB).into(holder.imgPhoto);



    }

    @Override
    public int getItemCount() {
        if (mListPhoto != null) {
           return mListPhoto.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private TextView deleteimg;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            deleteimg = itemView.findViewById(R.id.delete);
        }
    }
}
