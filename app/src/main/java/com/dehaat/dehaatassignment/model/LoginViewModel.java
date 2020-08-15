package com.dehaat.dehaatassignment.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dehaat.dehaatassignment.rest.AppRestClient;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private AppRestClient appRestClient;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application;

    }

    private MutableLiveData<LoginResponse> userResponse = new MutableLiveData<>();

    public void onLogin(User user) {
        appRestClient.getInstance(context).getAppRestClientService().login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body()!=null){

                    userResponse.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }

    public MutableLiveData<LoginResponse> getUserResponse() {
        return userResponse;
    }
}
