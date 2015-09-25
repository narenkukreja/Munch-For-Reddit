package com.example.naren.munch.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naren.munch.R;
import com.example.naren.munch.model.Comment;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {

    ArrayList<Comment> comments = new ArrayList<>();

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.comments = comments;

    }

    @Override
    public int getCount() {
        return comments.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;

        Comment comment = getItem(position);

        //Figure out how to properly display post time

//        final long time = comment.getComment_time();
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        calendar.setTimeZone(TimeZone.getDefault());
//        calendar.setTimeInMillis(time * 1000);
//
//
//        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_row, parent, false);
            holder = new MyViewHolder();

            holder.comment_indicator_space = (LinearLayout) convertView.findViewById(R.id.comment_indicator_space);
            holder.comment_indicator_color = (LinearLayout) convertView.findViewById(R.id.comment_indicator_color);
            holder.comment_author = (TextView) convertView.findViewById(R.id.comment_author);
            holder.comment_score = (TextView) convertView.findViewById(R.id.comment_score);
//            holder.comment_time = (TextView) convertView.findViewById(R.id.comment_time);
            holder.comment_body = (TextView) convertView.findViewById(R.id.comment_body);

            convertView.setTag(holder);


        } else {

            holder = (MyViewHolder) convertView.getTag();
        }
        holder.comment_author.setText(comment.getComment_author());
        holder.comment_score.setText(comment.getComment_score() + "points");
        holder.comment_body.setText(Html.fromHtml(comment.getComment_body()));
        holder.comment_body.setMovementMethod(LinkMovementMethod.getInstance());
//        holder.comment_time.setText(hour + " hrs ago");


        //handle comment replies/children and set the appropriate indication colors
        if (comments.get(position).level == 0) {

            holder.comment_indicator_space.setPadding(0, 0, 0, 0);
            holder.comment_indicator_color.setVisibility(View.GONE);

        } else if (comments.get(position).level == 1) {

            holder.comment_indicator_space.setPadding(comments.get(position).level * 8, 0, 0, 0);
            holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue_800));
            holder.comment_indicator_color.setVisibility(View.VISIBLE);

        } else if (comments.get(position).level == 2) {

            holder.comment_indicator_space.setPadding(comments.get(position).level * 8, 0, 0, 0);
            holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_800));
            holder.comment_indicator_color.setVisibility(View.VISIBLE);

        } else if (comments.get(position).level == 3) {


            holder.comment_indicator_space.setPadding(comments.get(position).level * 8, 0, 0, 0);
            holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_800));
            holder.comment_indicator_color.setVisibility(View.VISIBLE);

        } else if (comments.get(position).level == 4) {

            holder.comment_indicator_space.setPadding(comments.get(position).level * 8, 0, 0, 0);
            holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange_800));
            holder.comment_indicator_color.setVisibility(View.VISIBLE);

        } else if (comments.get(position).level == 5) {

            holder.comment_indicator_space.setPadding(comments.get(position).level * 8, 0, 0, 0);
            holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_800));
            holder.comment_indicator_color.setVisibility(View.VISIBLE);

        } else if (comments.get(position).level == 6) {

            holder.comment_indicator_space.setPadding(comments.get(position).level * 8, 0, 0, 0);
            holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow_800));

            holder.comment_indicator_color.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class MyViewHolder {

        LinearLayout comment_indicator_space, comment_indicator_color, comment_container;
        TextView comment_author, comment_score, comment_time, comment_body;

    }

}

