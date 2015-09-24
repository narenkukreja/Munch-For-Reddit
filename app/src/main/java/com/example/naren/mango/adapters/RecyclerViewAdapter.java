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
import com.squareup.picasso.Picasso;

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

        final String jpegImageUrl = url + ".jpg";

        final String title = redditPost.getTitle();
        final String author = redditPost.getAuthor();
        final String subreddit = redditPost.getSubreddit();
        final String domain = redditPost.getDomain();
        final String selftext_html = redditPost.getSelftext_html();
        final String permalink = redditPost.getPermalink();
        final String thumbnail = redditPost.getThumbnail();
        final String youtube_thumbnail = redditPost.getYoutubeThumbnail();
        final int postScore = redditPost.getScore();
        final int comments = redditPost.getComments();

        final long time = redditPost.getTime();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(time * 1000);

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

//        holder.mTime.setText(hour + " hrs ago");

        Glide.with(context).load(url).into(holder.mPostImage);


        if (domain.equals("imgur.com") || domain.equals("i.imgur.com") ||
                domain.equals("m.imgur.com")) {

            if (!url.equals(jpegImageUrl)) {

                Glide.with(context).load(jpegImageUrl).into(holder.mPostImage);
                holder.mPlaceholder.setVisibility(View.GONE);

                holder.mPostImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, ExpandedImageView.class);

                        Bundle bundle = new Bundle();

                        bundle.putString("image", jpegImageUrl);

                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                });

            } else {

                Glide.with(context).load(url).into(holder.mPostImage);
                holder.mPlaceholder.setVisibility(View.GONE);

                holder.mPostImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, ExpandedImageView.class);

                        Bundle bundle = new Bundle();

                        bundle.putString("image", jpegImageUrl);

                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                });

            }

        }

        if (url.contains(".gif") || url.contains("gfy")) {

            Glide.with(context).load(thumbnail).into(holder.mPostImage);

            holder.mPlaceholder.setVisibility(View.VISIBLE);
            holder.mLinkDomain.setText("[Gif] " + domain);

            holder.mPostImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, GifActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("gif", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });


            holder.mPlaceholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, GifActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("gif", url);

                    intent.putExtras(bundle);

                    context.startActivity(intent);

                }
            });


        } else if (domain.equals("youtube.com") || domain.equals("youtu.be")
                || domain.equals("m.youtube.com")) {

            Glide.with(context).load(youtube_thumbnail).into(holder.mPostImage);

            holder.mPlaceholder.setVisibility(View.VISIBLE);
            holder.mLinkDomain.setText("[Video] " + domain);

            holder.mPostImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, YoutubeActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("youtube_link", url);

                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });


            holder.mPlaceholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, YoutubeActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("youtube_link", url);

                    intent.putExtras(bundle);

                    context.startActivity(intent);

                }
            });

        }


        if (domain.contains("imgur") || domain.contains("youtube") ||
                domain.contains("youtu.be") || url.contains(".gif") || url.contains("gfy")
                || url.contains(".jpg") || url.equals(jpegImageUrl)) {

            if (url.contains("/gallery/") || url.contains("/a/")) {

                Glide.with(context).load(thumbnail).into(holder.mPostImage);

                holder.mPlaceholder.setVisibility(View.VISIBLE);
                holder.mLinkDomain.setText("[Album] " + domain);

                holder.mPostImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, WebActivity.class);

                        Bundle bundle = new Bundle();

                        bundle.putString("web_link", url);

                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                });

                holder.mPlaceholder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, WebActivity.class);

                        Bundle bundle = new Bundle();

                        bundle.putString("web_link", url);

                        intent.putExtras(bundle);

                        context.startActivity(intent);

                    }
                });

            }

        } else {

            holder.mPlaceholder.setVisibility(View.VISIBLE);
            holder.mLinkDomain.setText("[Link] " + domain);

            holder.mPlaceholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, WebActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("web_link", url);

                    intent.putExtras(bundle);

                    context.startActivity(intent);

                }
            });

        }


//        if (url.contains("/gallery/")) {
//
//            Glide.with(context).load(thumbnail).asBitmap().into(holder.mPostImage);
//
//            holder.mPostThumbnailLink.setVisibility(View.VISIBLE);
//            holder.mPostThumbnailLink.setText("[Album] " + domain);
//
//            holder.mPostThumbnailLink.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent(context, GifActivity.class);
//
//                    Bundle bundle = new Bundle();
//
//                    bundle.putString("gif", url);
//
//                    intent.putExtras(bundle);
//
//                    context.startActivity(intent);
//
//                }
//            });
//
//        }

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
                                                           bundle.putString("thumbnail", thumbnail);
                                                           bundle.putString("youtube_thumbnail", youtube_thumbnail);

                                                           intent.putExtras(bundle);
                                                           context.startActivity(intent);

                                                       }
                                                   }
                                               }

        );


        //        if (url.contains("//imgur.com/gallery/")){
//
//            Glide.with(context).load(jpegImageUrl).asBitmap().into(holder.mPostImage);
//
//            holder.mPostImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent(context, WebActivity.class);
//
//                    Bundle bundle = new Bundle();
//
//                    bundle.putString("web_link", url);
//
//                    intent.putExtras(bundle);
//
//                    context.startActivity(intent);
//
//                }
//            });
//
//        }

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
                mDomain, mTime, mLinkDomain, mPostThumbnailLink;
        private ImageView mPostImage;
        private LinearLayout mPlaceholder, mMainContent;

        public MyViewHolder(final View itemView) {
            super(itemView);

            mPostTitle = (TextView) itemView.findViewById(R.id.post_title_textView);
            mPostImage = (ImageView) itemView.findViewById(R.id.post_image_imageView);
            mPostThumbnailLink = (TextView) itemView.findViewById(R.id.post_image_thumbnail_link);
            mPlaceholder = (LinearLayout) itemView.findViewById(R.id.post_link_placeholder);
            mPostScore = (TextView) itemView.findViewById(R.id.post_score);
            mComments = (TextView) itemView.findViewById(R.id.post_comments);
            mSubreddit = (TextView) itemView.findViewById(R.id.post_subreddit);
            mDomain = (TextView) itemView.findViewById(R.id.post_domain);
//            mTime = (TextView) itemView.findViewById(R.id.post_time);
            mAuthor = (TextView) itemView.findViewById(R.id.post_author);
            mLinkDomain = (TextView) itemView.findViewById(R.id.link_domain);
            mMainContent = (LinearLayout) itemView.findViewById(R.id.main_content_linear);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mCardView.setPreventCornerOverlap(false);

        }

    }
}
