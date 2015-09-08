package com.example.naren.mango.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naren.mango.R;
import com.example.naren.mango.model.Comment;
import com.example.naren.mango.model.RedditPost;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by naren on 2015-09-01.
 */
public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<Comment> comments;
    private View view;
    private CardView mCardView;

    public CommentRecyclerViewAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        view = LayoutInflater.from(context).inflate(R.layout.comment_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Comment comment = comments.get(position);

        holder.comment_author.setText(comment.getComment_author());
        holder.comment_score.setText(comment.getComment_score() + "points");
        holder.comment_body.setText(comment.getComment_body());

//        holder.comment_indicator_space.setPadding(0,0,0,0);
//        holder.comment_indicator_color.setBackgroundColor(R.color.green_800);


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout comment_indicator_space, comment_indicator_color;
        private TextView comment_author, comment_score, comment_time, comment_body;

        public MyViewHolder(View itemView) {
            super(itemView);

//            comment_indicator_space = (LinearLayout) itemView.findViewById(R.id.comment_indicator_space);
//            comment_indicator_color = (LinearLayout) itemView.findViewById(R.id.comment_indicator_color);
//            comment_author = (TextView) itemView.findViewById(R.id.comment_author);
            comment_score = (TextView) itemView.findViewById(R.id.comment_score);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            comment_body = (TextView) itemView.findViewById(R.id.comment_body);

        }
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        private CardView mCardView;

        private TextView mPostTitle, mPostScore, mComments, mSubreddit, mAuthor,
                mDomain, mLinkDomain;
        private ImageView mPostImage;
        private LinearLayout mPlaceholder, mMainContent;
        private PhotoViewAttacher mPhotoViewAttacher;
        private ViewPager mViewPager;

        public MyViewHolder2(View itemView) {
            super(itemView);

            mPostTitle = (TextView) view.findViewById(R.id.post_title_textView);
            mPostImage = (ImageView) view.findViewById(R.id.post_image_imageView);
            mPlaceholder = (LinearLayout) view.findViewById(R.id.post_link_placeholder);
            mPostScore = (TextView) view.findViewById(R.id.post_score);
            mComments = (TextView) view.findViewById(R.id.post_comments);
            mSubreddit = (TextView) view.findViewById(R.id.post_subreddit);
            mDomain = (TextView) view.findViewById(R.id.post_domain);
            mAuthor = (TextView) view.findViewById(R.id.post_author);

            mLinkDomain = (TextView) view.findViewById(R.id.link_domain);

            mCardView = (CardView) view.findViewById(R.id.card_view);
            mCardView.setPreventCornerOverlap(false);

        }
    }

}
