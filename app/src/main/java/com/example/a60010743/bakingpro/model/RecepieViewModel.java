package com.example.a60010743.bakingpro.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class RecepieViewModel extends AndroidViewModel {

    public RecepieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecepieRepository(application);
        mAllRecepies = mRepository.getAllRecepies();
    }

    private RecepieRepository mRepository;
    private LiveData<List<RecepieDetails>> mAllRecepies;

    public void insert(RecepieDetails recepieDetails) {
        mRepository.insert(recepieDetails);
    }

    public LiveData<List<RecepieDetails>> getAllRecepies(){
        return mAllRecepies;
    }
}
