package com.orbost.plugins;

import android.content.Intent;
import android.view.WindowManager;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
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
  private static final String ACTION_ALLOW_SLEEP_AGAIN = "allowSleepAgain";

  @Override
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    try {
      if (ACTION_SUBMIT_TO_LEADERBOARD.equals(action)) {

        Context context = this.cordova.getActivity().getApplicationContext();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        this.cordova.getActivity().startActivityForResult(signInIntent, 1);

        //return true;


        // cordova.getActivity().runOnUiThread(
        //     new Runnable() {
        //       public void run() {
        //         cordova.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //         callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
        //       }
        //     });


      } else if (ACTION_ALLOW_SLEEP_AGAIN.equals(action)) {
        cordova.getActivity().runOnUiThread(
                new Runnable() {
                  public void run() {
                    cordova.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
                  }
                });
        return true;

      } else {
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

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == 1) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      handleSignInResult(task);
    }

    if (requestCode == 2) {

    }
  }


  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    GoogleSignInAccount account = null;//GoogleSignIn.getLastSignedInAccount(this);
    try {
      account = completedTask.getResult(ApiException.class);
      //account.



      // Signed in successfully, show authenticated UI.
      //updateUI(account);
    } catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.

      return;
      //Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
      //updateUI(null);
    }

    assert account != null;
    Games.getLeaderboardsClient(this.cordova.getActivity(), account).submitScore(this.cordova.getActivity().getString(R.string.ezleaderboard_leaderboard_id), 1400);

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