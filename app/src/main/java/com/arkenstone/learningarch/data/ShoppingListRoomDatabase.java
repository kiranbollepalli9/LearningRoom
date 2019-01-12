package com.arkenstone.learningarch.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {ShoppingItem.class}, version = 1)
public abstract class ShoppingListRoomDatabase extends RoomDatabase {

    private static volatile ShoppingListRoomDatabase INSTANCE;
    private static final String DATABASE_NAME = "shoppinglist_database";

    public abstract ShoppingListDAO getShoppingListDAO();

    public static ShoppingListRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            synchronized (ShoppingListRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ShoppingListRoomDatabase.class, DATABASE_NAME)
                           // .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ShoppingListDAO mDao;

        PopulateDbAsync(ShoppingListRoomDatabase db) {
            mDao = db.getShoppingListDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            mDao.insert(new ShoppingItem("First shopping item"));
            mDao.insert(new ShoppingItem("Second shopping item"));

            return null;
        }
    }

}
