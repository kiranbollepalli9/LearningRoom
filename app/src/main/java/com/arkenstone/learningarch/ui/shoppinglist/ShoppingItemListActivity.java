package com.arkenstone.learningarch.ui.shoppinglist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arkenstone.learningarch.R;
import com.arkenstone.learningarch.data.ShoppingItem;
import com.arkenstone.learningarch.ui.additem.AddShoppingItemActivity;
import com.arkenstone.learningarch.viewmodel.ShoppingListViewModel;

import java.util.List;

public class ShoppingItemListActivity extends AppCompatActivity {

    private ShoppingListViewModel mViewModel;

    private ShoppingItemsListAdapter mAdapter;
    public static final int ADD_ITEM_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.shopping_list_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingItemListActivity.this, AddShoppingItemActivity.class);
                startActivityForResult(intent, ADD_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });
        mAdapter = new ShoppingItemsListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);

        mViewModel.getShoppingItemList().observe(this, new Observer<List<ShoppingItem>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingItem> shoppingItems) {
                if (shoppingItems!=null)
                Log.i("ShoppingItemList", "onChanged - " + shoppingItems.size());
                mAdapter.setData(shoppingItems);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            ShoppingItem shoppingItem = new ShoppingItem(data.getStringExtra(AddShoppingItemActivity.EXTRA_REPLY));
            mViewModel.insertShoppingItem(shoppingItem);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}
