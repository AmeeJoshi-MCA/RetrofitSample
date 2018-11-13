package retrofit.com.retrofitsample.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private DatabaseHandler databaseHandler;

    public DatabaseHelper(Context context) {
        databaseHandler = new DatabaseHandler(context, DatabaseHandler.DATABASE_MAIN);
    }

    public boolean setFavouriteCustomers(String userId, String data) {
        if (userId == null) userId = "";
        if (data == null) {
            data = "";
        } else {
            data = data.trim();
        }
        ContentValues contentValues = new ContentValues();
        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_FAVOURITE_CUSTOMERS, null,
                new String[]{DatabaseHandler.FIELD_USER_ID},
                new String[]{userId});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            String primaryId = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_PRIMARY_ID));
            contentValues.put(DatabaseHandler.FIELD_DATA, data);
            return databaseHandler.updateTableRow(DatabaseHandler.TABLE_FAVOURITE_CUSTOMERS, contentValues,
                    new String[]{DatabaseHandler.FIELD_PRIMARY_ID},
                    new String[]{primaryId}) > 0;
        } else {
            contentValues.put(DatabaseHandler.FIELD_USER_ID, userId);
            contentValues.put(DatabaseHandler.FIELD_DATA, data);
            return databaseHandler.insertDataIntoDatabase(DatabaseHandler.TABLE_FAVOURITE_CUSTOMERS, contentValues) > 0;
        }
    }

    public String getFavouriteCustomers(String userId) {
        if (userId == null) userId = "";
        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_FAVOURITE_CUSTOMERS, null,
                new String[]{DatabaseHandler.FIELD_USER_ID},
                new String[]{userId});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_DATA));
        }
        return null;
    }

    public boolean removeFavouriteCustomers(String userId) {
        return userId != null && databaseHandler.deleteTableRow(DatabaseHandler.TABLE_FAVOURITE_CUSTOMERS,
                new String[]{DatabaseHandler.FIELD_USER_ID},
                new String[]{userId});
    }

    public boolean setFavouriteMaterials(String userId, String customerId, String data) {
        if (userId == null) userId = "";
        if (customerId == null) customerId = "";
        data = (data == null ? "" : data.trim());

        ContentValues contentValues = new ContentValues();
        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_FAVOURITE_MATERIALS, null,
                new String[]{DatabaseHandler.FIELD_USER_ID, DatabaseHandler.FIELD_CUSTOMER_ID},
                new String[]{userId, customerId});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            String primaryId = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_PRIMARY_ID));
            contentValues.put(DatabaseHandler.FIELD_DATA, data);
            return databaseHandler.updateTableRow(DatabaseHandler.TABLE_FAVOURITE_MATERIALS, contentValues,
                    new String[]{DatabaseHandler.FIELD_PRIMARY_ID},
                    new String[]{primaryId}) > 0;
        } else {
            contentValues.put(DatabaseHandler.FIELD_USER_ID, userId);
            contentValues.put(DatabaseHandler.FIELD_CUSTOMER_ID, customerId);
            contentValues.put(DatabaseHandler.FIELD_DATA, data);
            return databaseHandler.insertDataIntoDatabase(DatabaseHandler.TABLE_FAVOURITE_MATERIALS, contentValues) > 0;
        }
    }

    public String getFavouriteMaterials(String userId, String customerId) {
        if (userId == null) userId = "";
        if (customerId == null) customerId = "";
        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_FAVOURITE_MATERIALS, null,
                new String[]{DatabaseHandler.FIELD_USER_ID, DatabaseHandler.FIELD_CUSTOMER_ID},
                new String[]{userId, customerId});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_DATA));
        }
        return null;
    }

    public boolean removeFavouriteMaterials(String userId, String customerId) {
        if (userId != null && customerId != null) {
            return databaseHandler.deleteTableRow(DatabaseHandler.TABLE_FAVOURITE_MATERIALS,
                    new String[]{DatabaseHandler.FIELD_USER_ID, DatabaseHandler.FIELD_CUSTOMER_ID},
                    new String[]{userId, customerId});
        } else {
            return false;
        }
    }

    public boolean removeAllFavouriteMaterials(String userId) {
        return userId != null && databaseHandler.deleteTableRow(DatabaseHandler.TABLE_FAVOURITE_MATERIALS,
                new String[]{DatabaseHandler.FIELD_USER_ID},
                new String[]{userId});
    }

    public boolean setSubmitLaterOrders(String userId, String customerId, String data) {
        if (userId == null) userId = "";
        if (customerId == null) customerId = "";
        data = (data == null ? "" : data.trim());

        ContentValues contentValues = new ContentValues();
        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_SUBMIT_LATER_ORDER, null,
                new String[]{DatabaseHandler.FIELD_USER_ID, DatabaseHandler.FIELD_CUSTOMER_ID},
                new String[]{userId, customerId});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            String primaryId = cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_PRIMARY_ID));
            contentValues.put(DatabaseHandler.FIELD_DATA, data);
            return databaseHandler.updateTableRow(DatabaseHandler.TABLE_SUBMIT_LATER_ORDER, contentValues,
                    new String[]{DatabaseHandler.FIELD_PRIMARY_ID},
                    new String[]{primaryId}) > 0;
        } else {
            contentValues.put(DatabaseHandler.FIELD_USER_ID, userId);
            contentValues.put(DatabaseHandler.FIELD_CUSTOMER_ID, customerId);
            contentValues.put(DatabaseHandler.FIELD_DATA, data);
            return databaseHandler.insertDataIntoDatabase(DatabaseHandler.TABLE_SUBMIT_LATER_ORDER, contentValues) > 0;
        }
    }

    public String getSubmitLaterOrders(String userId, String customerId) {
        if (userId == null) userId = "";
        if (customerId == null) customerId = "";
        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_SUBMIT_LATER_ORDER, null,
                new String[]{DatabaseHandler.FIELD_USER_ID, DatabaseHandler.FIELD_CUSTOMER_ID},
                new String[]{userId, customerId});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_DATA));
        }
        return null;
    }

    public boolean removeSubmitLaterOrders(String userId, String customerId) {
        if (userId != null && customerId != null) {
            return databaseHandler.deleteTableRow(DatabaseHandler.TABLE_SUBMIT_LATER_ORDER,
                    new String[]{DatabaseHandler.FIELD_USER_ID, DatabaseHandler.FIELD_CUSTOMER_ID},
                    new String[]{userId, customerId});
        } else {
            return false;
        }
    }

    public boolean removeAllSubmitLaterOrders(String userId) {
        return userId != null && databaseHandler.deleteTableRow(DatabaseHandler.TABLE_SUBMIT_LATER_ORDER,
                new String[]{DatabaseHandler.FIELD_USER_ID},
                new String[]{userId});
    }

//    public boolean insertOfflineOrder(DBGeneralData dbGeneralData) {
//        String userId = dbGeneralData.getUserId();
//        String customerId = dbGeneralData.getCustomerId();
//        String timeStamp = dbGeneralData.getTimeStamp();
//        String state = dbGeneralData.getState();
//        String data = dbGeneralData.getData();
//        if (userId == null) userId = "";
//        if (customerId == null) customerId = "";
//        if (timeStamp == null) timeStamp = "";
//        if (state == null) state = "";
//        data = (data == null ? "" : data.trim());
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHandler.FIELD_USER_ID, userId);
//        contentValues.put(DatabaseHandler.FIELD_CUSTOMER_ID, customerId);
//        contentValues.put(DatabaseHandler.FIELD_TIME_STAMP, timeStamp);
//        contentValues.put(DatabaseHandler.FIELD_STATE, state);
//        contentValues.put(DatabaseHandler.FIELD_DATA, data);
//        return databaseHandler.insertDataIntoDatabase(DatabaseHandler.TABLE_OFFLINE_ORDERS, contentValues) > 0;
//    }
//
//    public boolean updateOfflineOrder(DBGeneralData dbGeneralData) {
//        String id = dbGeneralData.getId();
//        String userId = dbGeneralData.getUserId();
//        String customerId = dbGeneralData.getCustomerId();
//        String timeStamp = dbGeneralData.getTimeStamp();
//        String state = dbGeneralData.getState();
//        String data = dbGeneralData.getData();
//        if (id == null) {
//            return false;
//        }
//        if (userId == null) userId = "";
//        if (customerId == null) customerId = "";
//        if (timeStamp == null) timeStamp = "";
//        if (state == null) state = "";
//        data = (data == null ? "" : data.trim());
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHandler.FIELD_USER_ID, userId);
//        contentValues.put(DatabaseHandler.FIELD_CUSTOMER_ID, customerId);
//        contentValues.put(DatabaseHandler.FIELD_TIME_STAMP, timeStamp);
//        contentValues.put(DatabaseHandler.FIELD_STATE, state);
//        contentValues.put(DatabaseHandler.FIELD_DATA, data);
//        long result = databaseHandler.updateTableRow(DatabaseHandler.TABLE_OFFLINE_ORDERS, contentValues,
//                new String[]{DatabaseHandler.FIELD_PRIMARY_ID},
//                new String[]{id});
//        return result > 0;
//    }
//
//    public List<DBGeneralData> getOfflineOrderList(String userId) {
//        List<DBGeneralData> list = new ArrayList<>();
//        Cursor cursor = databaseHandler.selectCustomFromTable(DatabaseHandler.TABLE_OFFLINE_ORDERS, null,
//                new String[]{DatabaseHandler.FIELD_USER_ID},
//                new String[]{userId});
//        if (cursor != null) {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                DBGeneralData dbGeneralData = new DBGeneralData();
//                dbGeneralData.setId(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_PRIMARY_ID)));
//                dbGeneralData.setUserId(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_USER_ID)));
//                dbGeneralData.setCustomerId(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_CUSTOMER_ID)));
//                dbGeneralData.setTimeStamp(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_TIME_STAMP)));
//                dbGeneralData.setState(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_STATE)));
//                dbGeneralData.setData(cursor.getString(cursor.getColumnIndex(DatabaseHandler.FIELD_DATA)));
//                list.add(dbGeneralData);
//                cursor.moveToNext();
//            }
//            cursor.close();
//        }
//        return list;
//    }

    public boolean removeAllOfflineOrders(String userId) {
        return userId != null && databaseHandler.deleteTableRow(DatabaseHandler.TABLE_OFFLINE_ORDERS,
                new String[]{DatabaseHandler.FIELD_USER_ID},
                new String[]{userId});
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    private void saveDBFile() {
        try {
            FileInputStream inputStream = new FileInputStream(databaseHandler.getApplicationDataBaseFile());
            FileOutputStream outputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory()
                    + "/" + DatabaseHandler.DATABASE_MAIN));

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}