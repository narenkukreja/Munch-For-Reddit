package com.example.naren.mango.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naren.mango.R;
import com.example.naren.mango.activities.DetailedPostActivity;
import com.example.naren.mango.activities.DetailedSelfPostActivity;
import com.example.naren.mango.activities.ExpandedImageView;
import com.example.naren.mango.activities.GifActivity;
import com.example.naren.mango.activities.ImageGalleryActivity;
import com.example.naren.mango.activities.WebActivity;
import com.example.naren.mango.activities.YoutubeActivity;
import com.example.naren.mango.model.RedditPost;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<RedditPost> posts;
    private View view;
    private CardView mCardView;

    public RecyclerViewAdapter(Context context, ArrayList<RedditPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.single_row, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {

        final RedditPost redditPost = posts.get(position);

        final String url = redditPost.getUrl();
        final String domainUrl = redditPost.getDomain();
        final String title = redditPost.getTitle();
        final String author = redditPost.getAuthor();
        final String subreddit = redditPost.getSubreddit();
        final String domain = redditPost.getDomain();
        final String selftext_html = redditPost.getSelftext_html();
        final String permalink = redditPost.getPermalink();
        final int postScore = redditPost.getScore();
        final int comments = redditPost.getComments();

        final long time = redditPost.getTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(time * 1000);

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        holder.mTime.setText(hour + " hrs ago");

        Glide.with(context).load(url).asBitmap().into(holder.mPostImage);

        holder.mPostImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ExpandedImageView.class);

                Bundle bundle = new Bundle();

                bundle.putString("image", url);

                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        holder.mMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Layout Clicked", Toast.LENGTH_SHORT).show();

                if (domain.contains("self")) {

                    Intent intent = new Intent(context, DetailedSelfPostActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("selftext_html", selftext_html);
                    bundle.putString("title", title);
                    bundle.putString("author", author);
                    bundle.putString("subreddit", subreddit);
                    bundle.putString("domain", domain);
                    bundle.putInt("post_score", postScore);
                    bundle.putInt("comments", comments);
                    bundle.putString("permalink", permalink);
                    bundle.putInt("time", hour);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                } else {

                    Intent intent = new Intent(context, DetailedPostActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("url", url);
                    bundle.putString("title", title);
                    bundle.putString("author", author);
                    bundle.putString("subreddit", subreddit);
                    bundle.putString("domain", domain);
                    bundle.putInt("post_score", postScore);
                    bundle.putInt("comments", comments);
                    bundle.putString("permalink", permalink);
                    bundle.putInt("time", hour);


                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            }
        });

        if (url.contains("youtube") || url.contains("youtu.be")) {

            holder.mPlaceholder.setVisibility(View.VISIBLE);

            holder.mLinkDomain.setText("[Youtube] " + domainUrl);

            holder.mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, YoutubeActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("youtube_link", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

        } else if (url.contains("gallery") && url.contains("imgur") && domainUrl.contains("imgur")) {

            holder.mPlaceholder.setVisibility(View.VISIBLE);

            holder.mLinkDomain.setText("[Album] " + domainUrl);

            holder.mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ImageGalleryActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("imgur_album", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

        } else if (url.contains("imgur.com/a")) {

            holder.mPlaceholder.setVisibility(View.VISIBLE);

            holder.mLinkDomain.setText("[Album] " + domainUrl);

            holder.mLinkDomain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, WebActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("web_link", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });


        } else if (url.contains(".gif") || url.contains(".gifv") || url.contains("gfy") ||
                url.contains("gfycat")) {

            holder.mPlaceholder.setVisibility(View.VISIBLE);

            holder.mLinkDomain.setText("[Gif] " + domainUrl);

            holder.mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, GifActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("gif", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

        } else if (!url.contains("imgur") && !url.contains("gallery") &&
                !url.contains("gif") &&
                !url.contains("gifv") &&
                !url.contains("gfy") &&
                !url.contains("gfycat") &&
                !url.contains("gallery") &&
                !url.contains("youtube") &&
                !url.contains("youtu.be") &&
                !domainUrl.contains("imgur") &&
                !domainUrl.contains("youtube") &&
                !domainUrl.contains("youtu.be") &&
                !domainUrl.contains("gfycat")) {

            holder.mPlaceholder.setVisibility(View.VISIBLE);

            holder.mLinkDomain.setText("[Link] " + domainUrl);

            holder.mLinkDomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, WebActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("web_link", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        } else {

            holder.mPlaceholder.setVisibility(View.GONE);
        }

        holder.mPostTitle.setText(redditPost.getTitle());
        holder.mAuthor.setText(redditPost.getAuthor());
        holder.mSubreddit.setText(redditPost.getSubreddit());
        holder.mDomain.setText(redditPost.getDomain());
        holder.mPostScore.setText(redditPost.getScore() + " points");
        holder.mComments.setText(redditPost.getComments() + " comments");

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mPostTitle, mPostScore, mComments, mSubreddit, mAuthor,
                mDomain, mTime, mLinkDomain;
        private ImageView mPostImage;
        private LinearLayout mPlaceholder, mMainContent;

        public MyViewHolder(final View itemView) {
            super(itemView);

            mPostTitle = (TextView) itemView.findViewById(R.id.post_title_textView);
            mPostImage = (ImageView) itemView.findViewById(R.id.post_image_imageView);
            mPlaceholder = (LinearLayout) itemView.findViewById(R.id.post_link_placeholder);
            mPostScore = (TextView) itemView.findViewById(R.id.post_score);
            mComments = (TextView) itemView.findViewById(R.id.post_comments);
            mSubreddit = (TextView) itemView.findViewById(R.id.post_subreddit);
            mDomain = (TextView) itemView.findViewById(R.id.post_domain);
            mTime = (TextView) itemView.findViewById(R.id.post_time);
            mAuthor = (TextView) itemView.findViewById(R.id.post_author);
            mLinkDomain = (TextView) itemView.findViewById(R.id.link_domain);
            mMainContent = (LinearLayout) itemView.findViewById(R.id.main_content_linear);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mCardView.setPreventCornerOverlap(false);

        }

    }
}
