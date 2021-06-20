package com.rseu.final_qualifying_work.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rseu.final_qualifying_work.BuildConfig;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmApp;


import java.util.logging.LogRecord;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.AuthenticationListener;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.auth.GoogleAuthType;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private int RC_SIGN_IN = 9002;

    private ProgressBar progressBar;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI(account);

        setContentView(R.layout.activity_start);

        progressBar = findViewById(R.id.progressBar);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);


        Realm.init(this);
        String appID = "fqw-mgjuw";



        app = new App(new AppConfiguration.Builder(appID)
                .defaultSyncErrorHandler((session, error) -> Log.e(TAG(), "Sync error: ${error.errorMessage}"))
                .build());



    findViewById(R.id.sign_in_button).

    setOnClickListener(this);

    GoogleSignInOptions gso = new GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.client_id))
            //.requestServerAuthCode(getString(R.string.client_id))
            .build();

    mGoogleSignInClient =GoogleSignIn.getClient(this,gso);
    }


    private String TAG() {
        return "TAG";
    }
    @Override
    public void onStart() {
        super.onStart();

        account = GoogleSignIn.getLastSignedInAccount(this);
         //Signed in successfully, show authenticated UI.

        updateUI(account);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {

            signIn();
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Toast.makeText(this,"RESULT",Toast.LENGTH_SHORT).show();
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String authorizationCode = account != null ? account.getIdToken() : null;

            Credentials googleCredentials = Credentials.google(authorizationCode, GoogleAuthType.ID_TOKEN);
            progressBar.setVisibility(View.VISIBLE);
            app.loginAsync(googleCredentials, result -> {
                if (result.isSuccess()) {


                  ;
                    updateUI(account);

                    progressBar.setVisibility(View.GONE);
                    Log.v("AUTH", "Successfully logged in to MongoDB Realm using Google OAuth.");
                }
                else {
                    updateUI(null);


                    Toast.makeText(this,"TOASTER",Toast.LENGTH_SHORT).show();
                    Log.e("AUTH", "Failed to log in to MongoDB Realm", result.getError());
                }
            });

        } catch (ApiException apiException) {
            apiException.printStackTrace();
            //updateUI(account);
        }


    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {

            Intent intent = new Intent(this, MainActivity.class);
            //Handler handler = new Handler();
            startActivity(intent);
            finish();

//            final Runnable r = new Runnable() {
//                public void run() {
//
//                   handler.postDelayed(this, 2000);
//
//                }
//            };

        }

    }



}