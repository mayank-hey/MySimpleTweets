package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "gDd5iq3NipSxxaRAtmM2cqutX";       // Change this
	public static final String REST_CONSUMER_SECRET = "52IbdBmAfKJOpI35uvhwG4nfZ8xtlecEMQ1MFFWT9PD8Fm12Ew"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cbsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        getHomeTimelineTweets(handler, 1, -1);
    }

    public void getHomeTimelineTweets(AsyncHttpResponseHandler handler, long sinceId, long maxId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        // specify params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (sinceId != -1) {
            params.put("since_id", sinceId);
        }
        if (maxId != -1) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void composeTweet(AsyncHttpResponseHandler handler, String tweet) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        getClient().post(apiUrl, params, handler);
    }

    public void getThisUserProfileInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, handler);
    }

    public void getMentionsTimeline(AsyncHttpResponseHandler handler, long sinceId, long maxId) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        // specify params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (sinceId != -1) {
            params.put("since_id", sinceId);
        }
        if (maxId != -1) {
            params.put("max_id", maxId);
        }
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler, long sinceId, long maxId) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        // specify params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (sinceId != -1) {
            params.put("since_id", sinceId);
        }
        if (maxId != -1) {
            params.put("max_id", maxId);
        }
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        // specify params
        getClient().get(apiUrl, null, handler);
    }

    public void getUserInfoByScreenName(AsyncHttpResponseHandler handler, String screenName) {
        String apiUrl = getApiUrl("users/show.json");
        // specify params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}