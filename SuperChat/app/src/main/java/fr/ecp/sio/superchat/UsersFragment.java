package fr.ecp.sio.superchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fr.ecp.sio.superchat.loaders.UsersLoader;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 05/12/2014.
 */
public class UsersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final int LOADER_USERS = 1000;

    //private ListView mListView;
    private UsersAdapter mListAdapter;
    private boolean mIsMasterDetailsMode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        listView.setLayoutParams(params);
        return listView;
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsMasterDetailsMode = getActivity().findViewById(R.id.tweets_content) != null;
        //mListView = (ListView) view.findViewById(android.R.id.list);
        mListAdapter = new UsersAdapter();
        setListAdapter(mListAdapter);
        //if (mIsMasterDetailsMode) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //}
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new UsersLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        mListAdapter.setUsers(users);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) { }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        User user = mListAdapter.getItem(position);
        if (mIsMasterDetailsMode) {
            Intent intent = new Intent(getActivity(), TweetsActivity.class);
            intent.putExtras(TweetsFragment.newArguments(user));
            startActivity(intent);
        } else {
            Fragment tweetsFragment = new TweetsFragment();
            tweetsFragment.setArguments(TweetsFragment.newArguments(user));
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tweets_content, tweetsFragment)
                    .commit();
        }
    }
}