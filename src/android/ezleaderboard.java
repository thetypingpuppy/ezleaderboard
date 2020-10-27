package com.orbost.plugins;

import android.content.Intent;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.orbost.bananareach.R;

public class ezleaderboard extends CordovaPlugin {
    private static final String ACTION_SUBMIT_TO_LEADERBOARD = "submitToLeaderboard";
    public int score;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_SUBMIT_TO_LEADERBOARD.equals(action)) {

                // score = args.getInt(0);

                JSONObject jsonObj = args.getJSONObject(0);
                JSONArray keys = jsonObj.names ();

                for (int i = 0; i < keys.length(); i++) {
                    String key = keys.getString(i);
                    if (key.equals("score")){
                        score = Integer.parseInt(jsonObj.getString(key));
                    }
                }

                Context context = this.cordova.getActivity().getApplicationContext();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                this.cordova.setActivityResultCallback (this);
                this.cordova.getActivity().startActivityForResult(signInIntent, 1);

            }
            else {

                callbackContext.error("ezleaderboard." + action + " is not a supported function. Did you mean '" + ACTION_SUBMIT_TO_LEADERBOARD + "'?");
                return false;

            }
        } catch (Exception e) {

            callbackContext.error(e.getMessage());
            return false;

        }

        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            this.handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount account = null;//GoogleSignIn.getLastSignedInAccount(this);
        try {
            account = completedTask.getResult(ApiException.class);

        } catch (ApiException e) {

            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            return;
            //updateUI(null);
        }

        assert account != null;
        Games.getLeaderboardsClient(this.cordova.getActivity(), account).submitScore(this.cordova.getActivity().getString(R.string.ezleaderboard_leaderboard_id), score);

        Games.getLeaderboardsClient(this.cordova.getActivity(), GoogleSignIn.getLastSignedInAccount(this.cordova.getActivity().getApplicationContext()))
                .getLeaderboardIntent(this.cordova.getActivity().getString(R.string.ezleaderboard_leaderboard_id))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        cordova.getActivity().startActivityForResult(intent, 2);
                    }
                });
    }
}