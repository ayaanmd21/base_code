package com.dehaat.dehaatassignment.model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dehaat.dehaatassignment.rest.AppRestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Author>> authorsResponseMutableLiveData;
    private AppRestClient appRestClient;
    private Context context;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        this.context=application;
        authorsResponseMutableLiveData=new MutableLiveData<>();
    }

    public void fetchAAuthors(){
        appRestClient.getInstance(context).getAppRestClientService().getListOfAuthors().enqueue(new Callback<AuthorsResponse>() {
            @Override
            public void onResponse(Call<AuthorsResponse> call, Response<AuthorsResponse> response) {
                System.out.println("response........."+response.body().toString());
                authorsResponseMutableLiveData.postValue(response.body().getData());

            }

            @Override
            public void onFailure(Call<AuthorsResponse> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Author>> getAuthorsResponseMutableLiveData() {
        return authorsResponseMutableLiveData;
    }
}
