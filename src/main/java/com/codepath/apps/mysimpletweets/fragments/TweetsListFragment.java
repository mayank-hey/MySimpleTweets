package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mchacko on 2/10/15.
 */
public class TweetsListFragment extends Fragment{
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    public ListView lvTweets;

    void customLoadMoreDataFromApi() {

    };

    // inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra("screen_name", tweets.get(position).getUser().getScreenName());
                startActivity(i);
            }
        });

        lvTweets.setOnScrollListener( new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi();
            }
        });

        return v;
    }



    // creation lifecycle event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public long getOldestTweetUid() {
        return tweets.get(tweets.size()-1).getUid();
    }

    public void clearAdapter() {
        aTweets.clear();
    }

    public void addAllToTweets(int index, ArrayList<Tweet> arrayListOfTweets){
        tweets.addAll(index, arrayListOfTweets);
    }

    public void notifyDataSetChanged() {
        aTweets.notifyDataSetChanged();
    }

    public long getLatestTweetId() {
        return tweets.get(0).getUid();
    }
}
