package com.example.a60010743.bakingpro.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RecepieRepository  {

    private RecepieDao mRecepieDao;
    private LiveData<List<RecepieDetails>> mAllRecepies;

    RecepieRepository(Application application) {
        RecepieDetailsDatabase db = RecepieDetailsDatabase.getDatabase(application);
        mRecepieDao = db.recepieDao();
        mAllRecepies = mRecepieDao.getAllRecepieDetails();
    }

    LiveData<List<RecepieDetails>> getAllRecepies() {
        return mAllRecepies;
    }

    public void insert(RecepieDetails recepieDetails) {
        new insertAsyncTask(mRecepieDao).execute(recepieDetails);
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
