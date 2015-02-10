package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mchacko on 2/8/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // Apply View Holder adapter pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        Tweet tweet = getItem(position);
        // 2. Find or inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            holder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            holder.tvScreenname = (TextView) convertView.findViewById(R.id.tvScreenname);
            holder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            holder.tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);

            convertView.setTag(holder);
        }
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");

        // 3. Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvScreenname = (TextView) convertView.findViewById(R.id.tvScreenname);
        TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);

        tvUsername.setText(tweet.getUser().getName());
        tvScreenname.setText("@" + tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvRelativeTime.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt()));
        ivProfileImage.setImageResource(android.R.color.transparent); // clear the old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        tvUsername.setTypeface(boldFont);
        tvScreenname.setTypeface(font);
        tvBody.setTypeface(font);
        tvRelativeTime.setTypeface(font);
        return convertView;
    }

    final class ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenname;
        public TextView tvRelativeTime;
    }
}
