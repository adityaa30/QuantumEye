package com.example.aditya.quantumeye.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aditya.quantumeye.R;
import com.example.aditya.quantumeye.api.ApiManager;
import com.example.aditya.quantumeye.api.LoginCredentials;
import com.example.aditya.quantumeye.api.ServerResponse;
import com.example.aditya.quantumeye.camera.CameraActivity;
import com.example.aditya.quantumeye.cloud_messaging.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mUsernameTextInputEditText;
    private TextInputEditText mPasswordTextInputEditText;
    private MaterialButton mLoginButton;
    private ProgressBar mProgressBar;

    private ApiManager mApiManager;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        mUsernameTextInputEditText = (TextInputEditText) findViewById(R.id.login_username_textInputEditText);
        mPasswordTextInputEditText = (TextInputEditText) findViewById(R.id.login_password_textInputEditText);
        mLoginButton = (MaterialButton) findViewById(R.id.login_login_materialButton);
        mProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);

        mApiManager = ApiManager.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameTextInputEditText.getEditableText().toString();
                final String password = mPasswordTextInputEditText.getEditableText().toString();
                if (username.isEmpty() || password.isEmpty() || password.length() < 6 || username.length() < 6){
                    //Toast.makeText(LoginActivity.this, "Wrong Credentials, Try Again", Toast.LENGTH_SHORT).show();
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (username.isEmpty()){
                            mUsernameTextInputEditText.setError("Username field empty", getDrawable(R.drawable.ic_error_outline_red_24dp));
                        } else if (username.length() < 6){
                            mUsernameTextInputEditText.setError("Atleast 6 character required", getDrawable(R.drawable.ic_error_outline_red_24dp));
                        } else if (password.isEmpty()){
                            mPasswordTextInputEditText.setError("Password field empty", getDrawable(R.drawable.ic_error_outline_red_24dp));
                        } else {
                            mPasswordTextInputEditText.setError("Atleast 6 characters required", getDrawable(R.drawable.ic_error_outline_red_24dp));
                        }
                    } else {

                        if (username.isEmpty()){
                            mUsernameTextInputEditText.setError("Username field empty");
                        } else if (username.length() < 6){
                            mUsernameTextInputEditText.setError("Atleast 6 character required");
                        } else if (password.isEmpty()){
                            mPasswordTextInputEditText.setError("Password field empty");
                        } else {
                            mPasswordTextInputEditText.setError("Atleast 6 characters required");
                        }
                    }
                } else {
                    /*LoginCredentials loginCredentials = new LoginCredentials();
                    loginCredentials.setUsername(username);
                    loginCredentials.setPassword(password);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mApiManager.checkLoginCredentials(loginCredentials, new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            mProgressBar.setVisibility(View.GONE);
                            ServerResponse serverResponse = response.body();
                            if (response.isSuccessful() && serverResponse != null){
                                if (serverResponse.isSuccess() && serverResponse.getMessage().toLowerCase().equals("success")){
                                    mEditor = mPreferences.edit();
                                    mEditor.putString("password", password);
                                    mEditor.apply();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (serverResponse.getMessage().toLowerCase().equals("wrong")){
                                    mEditor = mPreferences.edit();
                                    mEditor.putString("password", "");
                                    mEditor.apply();
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials, Try Again", Toast.LENGTH_SHORT).show();
                                } else {
                                    mEditor = mPreferences.edit();
                                    mEditor.putString("password", "");
                                    mEditor.apply();
                                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Response is : " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "onFailure " + t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }); */
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
