package com.example.learning_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learning_app.screen.Registration;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    TextInputLayout pass, email;
    TextInputEditText password, editText;
    Button sing_in, googlesignInButton;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    TextView account;

    BeginSignInRequest signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId("436360299463-2dolh05aqmrfged7o2llm951kbvhi3p6.apps.googleusercontent.com")
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        sing_in = findViewById(R.id.sing_in);
        googlesignInButton = findViewById(R.id.googlesignInButton);
        pass = (TextInputLayout) findViewById(R.id.pass);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        editText = (TextInputEditText) findViewById(R.id.editText);
        password.addTextChangedListener(new ValidationTextWatcher(password));
        editText.addTextChangedListener(new ValidationTextWatcher(editText));
        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });

        sing_in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (!validateEmail()) {
                    return;
                } else if (!validatePassword()) {
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(editText.getText().toString(), password.getText().toString())
                            .addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        // Handle successful sign-in here
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        //GoogleSignInOptions gso;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("436360299463-2dolh05aqmrfged7o2llm951kbvhi3p6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googlesignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e("firebaseAuthWithGoogle", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("failed", "Google sign in failed", e);
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("signInWithsuccess", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String name = user.getDisplayName();
                            String contact = user.getPhoneNumber();
                            String email = user.getEmail();
                            String photourl = String.valueOf(user.getPhotoUrl());
                            Intent intent = new Intent(MainActivity.this, NavigationMainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("contact", contact);
                            intent.putExtra("email", email);
                            intent.putExtra("photourl", photourl);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("failure", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            pass.setError("Password is required");
            requestFocus(password);
            return false;
        } else if (password.getText().toString().length() < 6) {
            pass.setError("Password can't be less than 6 digit");
            requestFocus(password);
            return false;
        } else {
            pass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        String emailId = editText.getText().toString();
        Boolean isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
        if (editText.getText().toString().trim().isEmpty()) {
            email.setError("Email is required");
            requestFocus(email);
            return false;
        } else if (!isValid) {
            email.setError("Invalid Email address, ex: abc@example.com");
            requestFocus(editText);
            return false;
        } else {
            email.setErrorEnabled(false);
        }

        return true;
    }


    private class ValidationTextWatcher implements TextWatcher {
        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            if (view.getId() == R.id.editText) {
                validateEmail();
            }
            if (view.getId() == R.id.password) {
                validatePassword();
            }

        }
    }


}

