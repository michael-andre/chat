package fr.ecp.sio.superchat;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import fr.ecp.sio.superchat.model.Tweet;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 12/12/2014.
 */
public class ApiClient {

    private static final String API_BASE = "http://hackndo.com:5667/mongo/";

    public String login(String handle, String password) throws IOException {
        String url = Uri.parse(API_BASE + "session").buildUpon()
                .appendQueryParameter("handle", handle)
                .appendQueryParameter("password", password)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Login: " + url);
        InputStream stream = new URL(url).openStream();
        return IOUtils.toString(stream);
    }

    public List<User> getUsers() throws IOException {
        InputStream stream = new URL(API_BASE + "users").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }

    public List<Tweet> getUserTweets(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/tweets").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, Tweet[].class));
    }

    public void postTweet(String handle, String content) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/tweets/post").buildUpon()
                .appendQueryParameter("content", content)
                .build().toString();
        new URL(url).openStream();
    }
}