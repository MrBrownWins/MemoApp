package me.karimoff.memochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    ////the app uses firebase authentication for registration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this); //Binding Butterknife. without this butterknife does not work

        mAuth = FirebaseAuth.getInstance(); //the app uses firebase authentication for registration
        //listener for new user creation
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            //This method gets invoked in the UI thread on changes in the authentication state
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //getting registered user

                if (user != null) {
                    // User is already signed in
                    //if user is already signed in user automatically goes to MainActivity without registration and login
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener); //listeners call back in the UI thread
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener); //unregistering listener
        }
    }
    ////when sign in buttons are clicked go to the proper Activity
    @OnClick({R.id.button_email_signin, R.id.button_phone_number_signin})
    public void onClick(Button button) {
        Intent intent = new Intent();

        if (button.getId() == R.id.button_phone_number_signin) {
            intent = new Intent(AuthActivity.this, PhoneNumberActivity.class); // if sign in with phone go to phoneActivity
        } else if (button.getId() == R.id.button_email_signin) {
            intent = new Intent(AuthActivity.this, EmailPasswordActivity.class); // if sign in with email go to EmailPasswordActivity
        }
        startActivity(intent);

    }

}
