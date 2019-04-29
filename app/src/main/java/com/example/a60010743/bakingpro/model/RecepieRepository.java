package com.example.a60010743.bakingpro.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecepieRepository  {

    private RecepieDao mRecepieDao;
    private LiveData<List<RecepieDetails>> mAllRecepies;
    private LiveData<List<String>> mAllRecepieItems;

    public RecepieRepository(Context application) {
        RecepieDetailsDatabase db = RecepieDetailsDatabase.getDatabase(application);
        mRecepieDao = db.recepieDao();
        mAllRecepies = mRecepieDao.getAllRecepieDetails();
    }

    LiveData<List<RecepieDetails>> getAllRecepies() {
        return mAllRecepies;
    }

    LiveData<List<String>> getAllRecepieItems() {
        mAllRecepieItems = mRecepieDao.getAllRecepieItems();
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

    public LiveData<List<String>> getFavIngredients() {
        LiveData<List<String>> favIngredients = mRecepieDao.getFavIngredients();
        return favIngredients;
    }

    public void updateFavouriteRecItem(String recItem, boolean fav) {
        UpdateDetails updateDb = new UpdateDetails(recItem, fav);
        new updateAsyncTask(mRecepieDao).execute(updateDb);
    }

    public String getRecepieItem(String recItem) {
        String recepieItem = mRecepieDao.getRecepieItem(recItem);
        return recepieItem;
    }


    public static class insertAsyncTask extends AsyncTask<RecepieDetails, Void, Void>{

        private RecepieDao mRecepieDao;
        insertAsyncTask(RecepieDao recepieDao) {
            mRecepieDao = recepieDao;
        }

        @Override
        protected Void doInBackground(RecepieDetails... recepieDetails) {
            if(mRecepieDao.getRecepieItem(recepieDetails[0].getRecepieItem()) == null) {
                mRecepieDao.insertRecepieDetails(recepieDetails[0]);
            }
            return null;
        }
    }


    public static class updateAsyncTask extends AsyncTask<UpdateDetails, Void, Void>{

        private RecepieDao mRecepieDao;
        updateAsyncTask(RecepieDao recepieDao) {
            mRecepieDao = recepieDao;
        }

        @Override
        protected Void doInBackground(UpdateDetails... updateDetails) {
            mRecepieDao.updateFavourite(updateDetails[0].recepieItem, updateDetails[0].fav);
            return null;
        }
    }

    public class UpdateDetails {

        String recepieItem;
        boolean fav;

        UpdateDetails(String recepieItem, boolean fav) {
            this.recepieItem = recepieItem;
            this.fav = fav;
        }
    }

}
