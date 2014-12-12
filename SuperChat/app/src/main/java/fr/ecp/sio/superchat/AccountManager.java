package fr.ecp.sio.superchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Michaël on 12/12/2014.
 */
public class AccountManager {

    private static final String PREF_API_TOKEN = "apiToken";

    public static boolean isConnected(Context context) {
        return getUserToken(context) != null;
    }

    public static String getUserToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_TOKEN, null);
    }

    public static void saveUserToken(Context context, String token) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .putString(PREF_API_TOKEN, token)
                .apply();
    }

    public static void clearUserToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .remove(PREF_API_TOKEN)
                .apply();
    }

}
