package com.dehaat.dehaatassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dehaat.dehaatassignment.R;
import com.dehaat.dehaatassignment.databinding.ActivityLoginBinding;
import com.dehaat.dehaatassignment.model.LoginResponse;
import com.dehaat.dehaatassignment.model.LoginViewModel;
import com.dehaat.dehaatassignment.model.User;
import com.dehaat.dehaatassignment.utils.SharedPrefUtils;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private LoginViewModel loginViewModel;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);
        viewModelFactory=new ViewModelProvider.AndroidViewModelFactory(getApplication());
        loginViewModel= new ViewModelProvider(this,viewModelFactory).get(LoginViewModel.class);


        loginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(loginBinding.etEmail.getText().toString().trim(), loginBinding.etPassword.getText().toString().trim());
                if(TextUtils.isEmpty(user.getEmail())){
                    loginBinding.tilEmail.setError(getString(R.string.error_required_email));
                    loginBinding.tilEmail.requestFocus();
                }else if(!user.isEmailValid()){
                    loginBinding.tilEmail.setError(getString(R.string.error_invalid_email));
                    loginBinding.tilEmail.requestFocus();
                }else if(TextUtils.isEmpty(user.getPassword())){
                    loginBinding.tilPassword.setError(getString(R.string.error_required_password));
                    loginBinding.tilPassword.requestFocus();
                }else {
                    loginBinding.progressCircular.setVisibility(View.VISIBLE);
                    loginBinding.btnLogin.setEnabled(false);
                    loginViewModel.onLogin(user);
                    loginViewModel.getUserResponse().observe(LoginActivity.this, new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            if(!TextUtils.isEmpty(loginResponse.getStatus()) && loginResponse.getStatus().equalsIgnoreCase("success")){
                                SharedPrefUtils.saveData(getApplicationContext(),SharedPrefUtils.TOKEN,loginResponse.getToken().toString());
                                Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                Intent mIntent=new Intent(getApplicationContext(),MainActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(mIntent);
                                finish();
                            }
                        }
                    });
                }

            }
        });


    }
}
