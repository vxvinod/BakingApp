package com.example.a60010743.bakingpro.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RecepieRepository  {

    private RecepieDao mRecepieDao;
    private LiveData<List<RecepieDetails>> mAllRecepies;
    private LiveData<List<String>> mAllRecepieItems;

    RecepieRepository(Application application) {
        RecepieDetailsDatabase db = RecepieDetailsDatabase.getDatabase(application);
        mRecepieDao = db.recepieDao();
        mAllRecepies = mRecepieDao.getAllRecepieDetails();

    }

    LiveData<List<RecepieDetails>> getAllRecepies() {
        return mAllRecepies;
    }

    LiveData<List<String>> getAllRecepieItems() {
        mAllRecepieItems = mRecepieDao.getAllRecepieItems();
        Log.d("RecepieRepository", String.valueOf(mAllRecepieItems));
        return mAllRecepieItems;
    }

    public void insert(RecepieDetails recepieDetails) {
        new insertAsyncTask(mRecepieDao).execute(recepieDetails);
    }

    public LiveData<String> getRecepieIng(String recepieItem) {
        LiveData<String> recepieItems =  mRecepieDao.getIngredients(recepieItem);
        return recepieItems;
    }

    public LiveData<String> getRecepieSteps(String recepieItem) {
        LiveData<String> recepieSteps = mRecepieDao.getRecSteps(recepieItem);
        return recepieSteps;
    }

    public static class insertAsyncTask extends AsyncTask<RecepieDetails, Void, Void>{

        private RecepieDao mRecepieDao;

        insertAsyncTask(RecepieDao recepieDao) {
            mRecepieDao = recepieDao;
        }

        @Override
        protected Void doInBackground(RecepieDetails... recepieDetails) {
            mRecepieDao.insertRecepieDetails(recepieDetails[0]);
            return null;
        }
    }

}
