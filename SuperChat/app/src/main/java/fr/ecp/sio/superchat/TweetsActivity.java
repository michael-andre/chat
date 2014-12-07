package fr.ecp.sio.superchat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Michaël on 05/12/2014.
 */
public class TweetsActivity extends ActionBarActivity {

    public static final String EXTRA_USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, new TweetsFragment())
                    .commit();
        }
    }

}
