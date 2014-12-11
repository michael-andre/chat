package fr.ecp.sio.superchat.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import fr.ecp.sio.superchat.model.Tweet;

/**
 * Created by Michaël on 05/12/2014.
 */
public class TweetsLoader extends AsyncTaskLoader<List<Tweet>> {

    private String mUserId;
    private List<Tweet> mResult;

    public TweetsLoader(Context context, String userId) {
        super(context);
        mUserId = userId;
    }

    @Override
    public List<Tweet> loadInBackground() {
        try {
            Log.i(TweetsLoader.class.getName(), "Loading tweets");
            InputStream stream = new URL("http://www.wapplix.com/ecp/" + mUserId + ".json").openStream();
            String response = IOUtils.toString(stream);
            return Arrays.asList(new Gson().fromJson(response, Tweet[].class));
        } catch (Exception e) {
            Log.e(TweetsLoader.class.getName(), "Failed to download tweets", e);
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null){
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<Tweet> data) {
        Log.i(TweetsLoader.class.getName(), "Returned data: " + data);
        mResult = data;
        super.deliverResult(data);
    }

}