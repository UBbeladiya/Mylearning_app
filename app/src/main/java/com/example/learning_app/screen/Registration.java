package com.example.learning_app.screen;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learning_app.Home_Page;
import com.example.learning_app.MainActivity;
import com.example.learning_app.NavigationMainActivity;
import com.example.learning_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    TextInputLayout pass, email, name;
    TextInputEditText etRegPassword, etRegEmail, etnameText;
    TextView tvLoginHere;
    Button btnRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri filePath;
     FirebaseStorage storage;
     StorageReference storageRef;
    Uri imageUri;
    ImageView imageViewPreview;
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    ProgressDialog progressDialog;

    // instance for firebase storage and StorageReference
    StorageReference storageReference;

    private static final String NAME_PATTERN = "^[a-zA-Z\\s]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        etRegEmail = findViewById(R.id.EmailText);
        etRegPassword = findViewById(R.id.password);
        etnameText = findViewById(R.id.nameText);
        imageViewPreview = findViewById(R.id.imageView);
        pass = (TextInputLayout) findViewById(R.id.pass);
        email = (TextInputLayout) findViewById(R.id.email);
        name = (TextInputLayout) findViewById(R.id.name);

        etRegPassword.addTextChangedListener(new Registration.ValidationTextWatcher(etRegPassword));
        etRegEmail.addTextChangedListener(new Registration.ValidationTextWatcher(etRegEmail));
        etRegEmail.addTextChangedListener(new Registration.ValidationTextWatcher(etRegEmail));

        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.r_sing_in);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(Registration.this, MainActivity.class));
            finishAffinity();

        });

    }


    private void createUser() {
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String getname = etnameText.getText().toString();
        Log.e("My Name", getname.toString());
        Log.e("My email", password.toString());
        Log.e("My Name", email.toString());
        if (!isValidName()) {
            return;
        } else if (!validateEmail()) {
            return;
        } else if (!validatePassword()) {
            return;
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog = new ProgressDialog(Registration.this);
                    progressDialog.setTitle("Upload Data");
                    progressDialog.setMessage("Loading...");

                    // Set the progress dialog as cancelable
                    progressDialog.setCancelable(false);

                    // Show the progress dialog
                    progressDialog.show();
                    try {
                        StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".jpg");
                        imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    Log.e("Image getDownloadUrl",imageUrl);
                                    if (task.isSuccessful()) {
                                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        Map<String, Object> Data = new HashMap<>();
                                        Data.put("name", getname);
                                        Data.put("email", email);
                                        Data.put("password", password);
                                        Data.put("imageUrl", imageUrl);

                                        Log.e("My Data", Data.toString());

                                        db.collection("Student").document(currentFirebaseUser.getUid())
                                                .set(Data)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                                        Toast.makeText(getApplicationContext(), "successfully", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        finishAffinity();
                                                        startActivity(new Intent(Registration.this, MainActivity.class));

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "Error writing document", e);
                                                    }
                                                });

                                        //  Toast.makeText(Registration.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Registration.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Image fail",e.toString());
                            }
                        });
                    }catch (Exception e){
                        progressDialog.dismiss();

                        Log.e("Image fail",e.toString());
                    }


                }
            });
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (etRegPassword.getText().toString().trim().isEmpty()) {
            pass.setError("Password is required");
            requestFocus(etRegPassword);
            return false;
        } else if (etRegPassword.getText().toString().length() < 6) {
            pass.setError("Password can't be less than 6 digit");
            requestFocus(etRegPassword);
            return false;
        } else {
            pass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        String emailId = etRegEmail.getText().toString();
        Boolean isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
        if (etRegEmail.getText().toString().trim().isEmpty()) {
            email.setError("Email is required");
            requestFocus(email);
            return false;
        } else if (!isValid) {
            email.setError("Invalid Email address, ex: abc@example.com");
            requestFocus(etRegEmail);
            return false;
        } else {
            email.setErrorEnabled(false);
        }

        return true;
    }

    public boolean isValidName() {
        String getName = etnameText.getText().toString();

        Boolean isValid = Pattern.compile(NAME_PATTERN).matcher(getName).matches();
        if (etnameText.getText().toString().trim().isEmpty()) {
            name.setError("Name is required");
            requestFocus(email);
            return false;
        } else if (!isValid) {
            name.setError("Invalid Name, ex: XYZ ABC");
            requestFocus(etRegEmail);
            return false;
        } else {
            name.setErrorEnabled(false);
        }
        return true;
    }

    public void MyImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Start the activity to select the image
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image Uri
             imageUri = data.getData();
            // Get the image bitmap
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }




            // Set the image bitmap to the image view
            imageViewPreview.setImageBitmap(bitmap);
        }
    }


}