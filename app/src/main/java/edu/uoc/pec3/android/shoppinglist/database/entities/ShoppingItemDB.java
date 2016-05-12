package edu.uoc.pec3.android.shoppinglist.database.entities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;

import java.util.ArrayList;

import edu.uoc.pec3.android.shoppinglist.database.helper.ShoppingElementHelper;
import edu.uoc.pec3.android.shoppinglist.database.model.ShoppingItem;

/**
 * Created by Marc on 18/09/2015.
 */
public class ShoppingItemDB {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private ShoppingElementHelper dbHelper;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ShoppingItemDB(Context context) {
        // Create new helper
        dbHelper = new ShoppingElementHelper(context);
    }

    /* Inner class that defines the table contents */
    public static abstract class ShoppingElementEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_NAME_TITLE + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    /**
     * Method to create new element in the database
     *
     * @param productName
     */
    public void insertElement(String productName) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO " + ShoppingElementEntry.TABLE_NAME + " (" + ShoppingElementEntry.COLUMN_NAME_TITLE + ") VALUES ('" + productName + "')";
        db.execSQL(sql);
        db.close();
    }

    /**
     * Method to get all the shopping elements of the database
     *
     * @return ShoppingItem array
     */
    public ArrayList<ShoppingItem> getAllItems() {
        // Init target array
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();

        // Get database for read data
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Query
        String sql = "SELECT * FROM " + ShoppingElementEntry.TABLE_NAME;
        // Init cursor
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            ShoppingItem item;
            do{
                item = new ShoppingItem(Long.parseLong(cursor.getString(0)), cursor.getString(1));
                shoppingItems.add(item);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return shoppingItems;
    }

    /**
     * Method to clear all the elements
     */
    public void clearAllItems() {
        // Init database for write
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Query
        String sql = "DELETE FROM " + ShoppingElementEntry.TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }

    /**
     * Method to update a database item
     *
     * @param shoppingItem
     */
    public void updateItem(ShoppingItem shoppingItem) {
        // Init database for write
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Query
        String sql = "UPDATE " + ShoppingElementEntry.TABLE_NAME
                + " SET " + ShoppingElementEntry.COLUMN_NAME_TITLE + " = '" + shoppingItem.getName() + "'"
                + " WHERE " + ShoppingElementEntry._ID + " = " + shoppingItem.getId();
        db.execSQL(sql);
        db.close();
    }

    /**
     * Method to delete one item
     *
     * @param shoppingItem
     */
    public void deleteItem(ShoppingItem shoppingItem) {
        // Init database for write
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Query
        String sql = "DELETE FROM " + ShoppingElementEntry.TABLE_NAME + " WHERE " + ShoppingElementEntry._ID + " = " + shoppingItem.getId();
        db.execSQL(sql);
        db.close();
    }
}
