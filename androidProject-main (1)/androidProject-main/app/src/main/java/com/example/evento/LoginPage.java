package com.example.evento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LoginPage extends AppCompatActivity {
    private ImageButton googleSign;
    private ImageButton faceSign;
    private ImageButton phoneSign;

    private FirebaseAuth mAuth;

    private GoogleSignInOptions gso;
    private GoogleSignInClient client;


    // facebook part

//    private CallbackManager callbackManager;

    // phone part
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneCallback;
    private String phoneNumber = "8448228525";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        googleSign = findViewById(R.id.imageButton);
        faceSign = findViewById(R.id.imageButton2);
        phoneSign = findViewById(R.id.imageButton3);

        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        client = GoogleSignIn.getClient(this, gso);

        googleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        // facebook sdk initialize omitted because not mentioned in docs
//        callbackManager = CallbackManager.Factory.create();
//        faceSign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                Log.d("FaceBookSuccess", "Sucess"+loginResult);
//                                handleFacebookToken(loginResult.getAccessToken());
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                // App code
//
//                                Log.d("LoginManager", "Cancelled");
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                // App code
//                                Log.d("LoginManager", "Errored");
//                            }
//                        }
//                );
//            }
//        });

        phoneSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePhoneAuth();
            }
        });
    }

    private void handlePhoneAuth() {
        phoneCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("PhoneVerified", "onVerificationCompleted:" + credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("FailedPhoneAuth", "onVerificationFailed", e);
                Toast.makeText(LoginPage.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("SentOTP", "onCodeSent:" + verificationId);
                Intent i = new Intent(LoginPage.this, CheckOtp.class);
                i.putExtra("otp", verificationId);
                startActivity(i);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(phoneCallback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void handleFacebookToken(AccessToken t) {
        AuthCredential c = FacebookAuthProvider.getCredential(t.getToken());
        mAuth.signInWithCredential(c).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signIn();
                        }else {
                            Toast.makeText(LoginPage.this, "FaceBook login credentials failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent intent = client.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                switchToMain();
            } catch (ApiException e) {
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        }
    }

    private void switchToMain() {
        finish();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
