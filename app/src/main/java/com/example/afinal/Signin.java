package com.example.afinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.afinal.api.ApiClient;
import com.example.afinal.api.CallToken;
import com.example.afinal.model.TokenResponse;
import com.example.afinal.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin extends AppCompatActivity {
    ImageButton backHomeBtn, signInBtn;
    EditText username_editText, pwd_editText;
    Button btn_resetPwd;
    CheckBox cbRemember;
    SharedPreferences sharedPreferences;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sharedPreferences = getSharedPreferences("dataSignin",MODE_PRIVATE);
        backHomeBtn = findViewById(R.id.homeBtn);
        signInBtn = findViewById(R.id.sign_in_btn);
        username_editText = findViewById(R.id.edit_text_name);
        pwd_editText = findViewById(R.id.edit_text_password);
        cbRemember = findViewById(R.id.cbRemember);
        username_editText.setText(sharedPreferences.getString("username",""));
        pwd_editText.setText(sharedPreferences.getString("password",""));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked",false));


        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, Home.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canSignIn() == true) {
                    CallToken apiService= ApiClient.CallToken();
                    user.setUsername(username_editText.getText().toString());
                    user.setPassword(pwd_editText.getText().toString());
                    Call<TokenResponse> call = apiService.sendRequest(
                        "password",
                        "openremote",
                        user.getUsername(),
                        user.getPassword()
                    );
                    Log.d("lỗi" ,"b");

                    call.enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            String username=username_editText.getText().toString().trim();
                            String password=pwd_editText.getText().toString().trim();
                            if (response.body() != null) {
                                Log.d("lỗi" ,"a");

                                Intent intent = new Intent(Signin.this, Dashboard.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                                if (cbRemember.isChecked()) {
                                    Log.d("lỗi" ,"b");

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putBoolean("checked", true);
                                    editor.commit();
                                }
                            }
                            else {
                                showAlert("Invalid username or password");
                            }
                        }
                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            Log.d("response call", t.getMessage().toString());
                        }
                    });
                }
                else {
                    if(checkUser() == false) {
                        showAlert("Please fill username");
                    }
                    else if(checkPwd() == false) {
                        showAlert("Please fill password");
                    }
                }
            }
        });
    }

    private boolean checkUser() {
        if(username_editText.getText().toString().equals("")) return false;
        return true;
    }
    private boolean checkPwd() {
        if(pwd_editText.getText().toString().equals("")) return false;
        return true;
    }
    private boolean canSignIn() {
        if(checkUser() == true && checkPwd() == true) return true;
        return false;
    }

    private void showAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Signin.this);
        builder.setTitle("Error")
            .setMessage(text)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}