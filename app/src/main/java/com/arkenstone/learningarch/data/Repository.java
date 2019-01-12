package com.arkenstone.learningarch.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {

    private ShoppingListDAO mDAO;

    private LiveData<List<ShoppingItem>> mShoppingItemList;


    public Repository(Application application) {
        mDAO = ShoppingListRoomDatabase.getDatabase(application).getShoppingListDAO();
        mShoppingItemList = mDAO.getAllItems();
    }

    public LiveData<List<ShoppingItem>> getAllShoppingListItems() {
        return mShoppingItemList;
    }

    public void insertShoppingItem(ShoppingItem item) {
        mDAO.insert(item);
    }

    public void insertShoppingItemAsync(ShoppingItem item) {
        new insertAsyncTask(mDAO).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<ShoppingItem, Void, Void> {

        ShoppingListDAO mAsyncTaskDao;

        insertAsyncTask(ShoppingListDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            mAsyncTaskDao.insert(shoppingItems[0]);
            return null;
        }
    }
}
