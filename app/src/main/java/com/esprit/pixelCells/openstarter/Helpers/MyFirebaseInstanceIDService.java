package com.esprit.pixelCells.openstarter.Helpers;

/**
 * Created by Mohamed on 12/11/2017.
 */


import android.util.Log;

import com.esprit.pixelCells.openstarter.Data.DataSuppliers.UserServer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

        UserServer userDs = new UserServer();
        userDs.updateToken(FirebaseAuth.getInstance().getCurrentUser().getEmail(), token, new UserServer.CallbackUpdate() {
            @Override
            public void onSuccess() {
                Log.d("userr","token updated");
            }

            @Override
            public void onError() {
                Log.d("userr","token failed to update");
            }
        });
    }
}