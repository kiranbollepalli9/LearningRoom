package com.arkenstone.learningarch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.arkenstone.learningarch.data.Repository;
import com.arkenstone.learningarch.data.ShoppingItem;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<ShoppingItem>> mShoppingItemList;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mShoppingItemList = mRepository.getAllShoppingListItems();
    }

    public LiveData<List<ShoppingItem>> getShoppingItemList(){
        return mShoppingItemList;
    }

    public void insertShoppingItem(ShoppingItem item){
        mRepository.insertShoppingItemAsync(item);
    }
}
