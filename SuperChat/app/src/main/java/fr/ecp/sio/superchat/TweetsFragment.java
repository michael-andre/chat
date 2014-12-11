package fr.ecp.sio.superchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.List;

import fr.ecp.sio.superchat.loaders.TweetsLoader;
import fr.ecp.sio.superchat.model.Tweet;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 05/12/2014.
 */
public class TweetsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Tweet>> {

    private static final int LOADER_TWEETS = 1000;

    private static final String ARG_USER_ID = "userId";

    private TweetsAdapter mListAdapter;

    public static Bundle newArguments(User user) {
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, user.getId());
        return args;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new TweetsAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_TWEETS, null, this);
    }

    @Override
    public Loader<List<Tweet>> onCreateLoader(int id, Bundle args) {
        String userId = getArguments().getString(ARG_USER_ID);
        return new TweetsLoader(getActivity(), userId);
    }

    @Override
    public void onLoadFinished(Loader<List<Tweet>> loader, List<Tweet> tweets) {
        mListAdapter.setTweets(tweets);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Tweet>> loader) { }

}