//package com.example.naren.munch.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.naren.munch.R;
//import com.example.naren.munch.activities.ExpandedImageView;
//import com.example.naren.munch.model.ImgurAlbum;
//
//import java.util.ArrayList;
//
///**
// * Created by naren on 2015-08-27.
// */
//public class ImgurGalleryAdapter extends RecyclerView.Adapter<ImgurGalleryAdapter.MyViewHolder> {
//
//
//    private Context context;
//    private ArrayList<ImgurAlbum> imgurGalleries;
//    private View view;
//    private CardView mCardView;
//
//    public ImgurGalleryAdapter(Context context, ArrayList<ImgurAlbum> imgurGalleries) {
//        this.context = context;
//        this.imgurGalleries = imgurGalleries;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        view = LayoutInflater.from(context).inflate(R.layout.image_gallery_row, parent, false);
//
//        MyViewHolder viewHolder = new MyViewHolder(view);
//
//        return viewHolder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//        final ImgurAlbum imgurAlbum = imgurGalleries.get(position);
//
//        final String imageUrl = ImgurAlbum.IMGUR_ALBUM_URL + imgurAlbum.getHash() + imgurAlbum.getExt();
//
//        Glide.with(context).load(imageUrl).into(holder.mGalleryImage);
//
//        holder.mGalleryImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context, ExpandedImageView.class);
//
//                Bundle bundle = new Bundle();
//
//                bundle.putString("image", imageUrl);
//
//                intent.putExtras(bundle);
//
//                context.startActivity(intent);
//
//            }
//        });
//
//
//        holder.mGalleryImageDescription.setText(imgurAlbum.getDescription());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return imgurGalleries.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView mGalleryImage;
//        private TextView mGalleryImageDescription;
//
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//
//            mGalleryImage = (ImageView) itemView.findViewById(R.id.gallery_imageView);
//
//            mGalleryImageDescription = (TextView) itemView.findViewById(R.id.gallery_image_description);
//
//            mCardView = (CardView) itemView.findViewById(R.id.card_view);
//            mCardView.setPreventCornerOverlap(false);
//
//        }
//    }
//
//}
