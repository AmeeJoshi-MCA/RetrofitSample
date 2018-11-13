package retrofit.com.retrofitsample.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DatabaseHandler extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private Context context;

    public static final String DATABASE_MAIN = "main_app_db.db";
    private static final int DATABASE_VERSION = 10;

    public static final String FIELD_PRIMARY_ID = "_id";
    public static final String FIELD_DATA = "data";

    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_TIME_STAMP = "timeStamp";
    public static final String FIELD_STATE = "state";

    public static final String TABLE_FAVOURITE_CUSTOMERS = "FavouriteCustomers";
    public static final String TABLE_FAVOURITE_MATERIALS = "FavouriteMaterials";
    public static final String TABLE_SUBMIT_LATER_ORDER = "SubmitLaterOrders";
    public static final String TABLE_OFFLINE_ORDERS = "OfflineOrders";

    public DatabaseHandler(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
        this.context = context;

        //creating tables
        createTableFavouriteCustomers();
        createTableFavouriteMaterials();
        createTableSubmitLaterOrders();
        createTableOfflineOrders();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(DatabaseHandler.class.getName(), "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.i(DatabaseHandler.class.getName(), "onUpgrade");
    }

    public synchronized void createTableFavouriteCustomers() {
        openDB();
        try {
            database.execSQL(QueryBuilder.CREATE_TABLE + TABLE_FAVOURITE_CUSTOMERS + QueryBuilder.PS
                    + FIELD_PRIMARY_ID + QueryBuilder.INTEGER_PRIMARY_AUTOINCREMENT
                    + FIELD_USER_ID + QueryBuilder.TEXT
                    + FIELD_DATA + QueryBuilder.END_TEXT
                    + QueryBuilder.PE);
            Log.i(DatabaseHandler.class.getName(), "createTableFavouriteCustomers() if not exists");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    public synchronized void createTableFavouriteMaterials() {
        openDB();
        try {
            database.execSQL(QueryBuilder.CREATE_TABLE + TABLE_FAVOURITE_MATERIALS + QueryBuilder.PS
                    + FIELD_PRIMARY_ID + QueryBuilder.INTEGER_PRIMARY_AUTOINCREMENT
                    + FIELD_USER_ID + QueryBuilder.TEXT
                    + FIELD_CUSTOMER_ID + QueryBuilder.TEXT
                    + FIELD_DATA + QueryBuilder.END_TEXT
                    + QueryBuilder.PE);
            Log.i(DatabaseHandler.class.getName(), "createTableFavouriteMaterials() if not exists");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    public synchronized void createTableSubmitLaterOrders() {
        openDB();
        try {
            database.execSQL(QueryBuilder.CREATE_TABLE + TABLE_SUBMIT_LATER_ORDER + QueryBuilder.PS
                    + FIELD_PRIMARY_ID + QueryBuilder.INTEGER_PRIMARY_AUTOINCREMENT
                    + FIELD_USER_ID + QueryBuilder.TEXT
                    + FIELD_CUSTOMER_ID + QueryBuilder.TEXT
                    + FIELD_DATA + QueryBuilder.END_TEXT
                    + QueryBuilder.PE);
            Log.i(DatabaseHandler.class.getName(), "createTableSubmitLaterOrders() if not exists");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    public synchronized void createTableOfflineOrders() {
        openDB();
        try {
            database.execSQL(QueryBuilder.CREATE_TABLE + TABLE_OFFLINE_ORDERS + QueryBuilder.PS
                    + FIELD_PRIMARY_ID + QueryBuilder.INTEGER_PRIMARY_AUTOINCREMENT
                    + FIELD_USER_ID + QueryBuilder.TEXT
                    + FIELD_CUSTOMER_ID + QueryBuilder.TEXT
                    + FIELD_TIME_STAMP + QueryBuilder.TEXT
                    + FIELD_STATE + QueryBuilder.TEXT
                    + FIELD_DATA + QueryBuilder.END_TEXT
                    + QueryBuilder.PE);
            Log.i(DatabaseHandler.class.getName(), "createTableOfflineOrders() if not exists");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    private synchronized void openDB() {
        Log.i(DatabaseHandler.class.getName(), "openDB");
        try {
            database = this.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void closeDB() {
        Log.i(DatabaseHandler.class.getName(), "closeDB");
        try {
            if (database.isOpen()) {
                database.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void addFieldInTable(String tableName, String fieldName, String fieldType) {
        openDB();
        try {
            String query = QueryBuilder.ALTER_TABLE + tableName
                    + QueryBuilder.ADD_COLUMN + fieldName + fieldType;
            database.execSQL(query);
            Log.i(DatabaseHandler.class.getName(), "addFieldInTable() : " + query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    public synchronized void executeCustomQuery(String query) {
        openDB();
        try {
            database.execSQL(query);
            Log.i(DatabaseHandler.class.getName(), "executeCustomQuery()");
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    public synchronized long truncateTable(String table) {
        long resultValue;
        openDB();
        try {
            resultValue = database.delete(table, "1", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultValue = 0;
        }
        closeDB();
        return resultValue;
    }

    public synchronized void dropTable(String table) {
        openDB();
        try {
            database.execSQL("DROP TABLE IF EXISTS " + table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeDB();
    }

    public synchronized long insertDataIntoDatabase(String table, ContentValues values) {

        long resultValue;
        openDB();

        try {
            resultValue = database.insert(table, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            resultValue = 0;
        }

        closeDB();
        return resultValue;
    }

    public synchronized long updateTableRow(String table, ContentValues values,
                                            String[] whereFieldNames, String[] whereFieldValues) {
        long resultValue;
        StringBuilder whereClause = new StringBuilder();

        if (whereFieldNames != null) {
            if (whereFieldNames.length > 0) {
                for (int i = 0; i < whereFieldNames.length; i++) {
                    if (i < 1)
                        whereClause.append(whereFieldNames[i]).append("=? ");
                    else
                        whereClause.append("and ").append(whereFieldNames[i]).append("=? ");
                }
            }
        }
        openDB();
        try {
            resultValue = database.update(table, values, whereClause.toString(), whereFieldValues);
        } catch (Exception e) {
            e.printStackTrace();
            resultValue = 0;
        }
        closeDB();
        return resultValue;
    }

    public synchronized boolean deleteTableRow(String table, String[] whereFieldNames,
                                               String[] whereFieldValues) {
        boolean resultValue;
        StringBuilder whereClause = new StringBuilder();

        if (whereFieldNames != null) {
            if (whereFieldNames.length > 0) {
                for (int i = 0; i < whereFieldNames.length; i++) {
                    if (i < 1)
                        whereClause.append(whereFieldNames[i]).append("=? ");
                    else
                        whereClause.append("and ").append(whereFieldNames[i]).append("=? ");
                }
            }
        }

        openDB();
        try {
            resultValue = database.delete(table, whereClause.toString(), whereFieldValues) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            resultValue = false;
        }
        closeDB();

        return resultValue;
    }

    public synchronized Cursor selectAllFromTable(String table) {
        openDB();
        Cursor cursor = database.rawQuery("SELECT * FROM " + table, null);
        cursor.getCount();
        closeDB();
        return cursor;
    }

    public synchronized Cursor selectCustomFromTable(String table, String[] fieldSelection,
                                                     String[] whereFieldNames, String[] whereFieldValues) {

        StringBuilder select = new StringBuilder("select ");
        String whereClause = "";

        if (fieldSelection != null) {
            if (fieldSelection.length > 0) {
                for (int i = 0; i < fieldSelection.length; i++) {
                    if (i < 1)
                        select.append(fieldSelection[i]);
                    else
                        select.append(",").append(fieldSelection[i]);
                }
            }
        } else {
            select.append("*");
        }

        select.append(" from ").append(table);

        if (whereFieldNames != null) {
            if (whereFieldNames.length > 0) {
                select.append(" where ");
                for (int i = 0; i < whereFieldNames.length; i++) {
                    if (i < 1)
                        whereClause += whereFieldNames[i] + "=? ";
                    else
                        whereClause += "and " + whereFieldNames[i] + "=? ";
                }
            }
        }

        String query = select + whereClause;

        openDB();
        Cursor cursor = database.rawQuery(query, whereFieldValues);
        cursor.getCount();
        closeDB();

        return cursor;
    }

    public synchronized Cursor selectCustomFromTableWithDate(String table, String[] fieldSelection,
                                                             String[] whereFieldNames, String[] whereFieldValues,
                                                             String dateField, String fromDate, String toDate) {

        StringBuilder select = new StringBuilder("select ");
        StringBuilder whereClause = new StringBuilder();

        if (fieldSelection != null) {
            if (fieldSelection.length > 0) {
                for (int i = 0; i < fieldSelection.length; i++) {
                    if (i < 1)
                        select.append(fieldSelection[i]);
                    else
                        select.append(",").append(fieldSelection[i]);
                }
            }
        } else {
            select.append("*");
        }

        select.append(" from ").append(table);

        if (whereFieldNames != null) {
            if (whereFieldNames.length > 0) {
                select.append(" where ");
                for (int i = 0; i < whereFieldNames.length; i++) {
                    if (i < 1)
                        whereClause.append(whereFieldNames[i]).append("=? ");
                    else
                        whereClause.append("and ").append(whereFieldNames[i]).append("=? ");
                }
                whereClause.append(" and ");
            }
        } else {
            whereClause.append(" where ");
        }

        whereClause.append(dateField).append(" between '").append(fromDate).append("' and '").append(toDate).append("'");

        String query = select + whereClause.toString();

        openDB();
        Cursor cursor = database.rawQuery(query, whereFieldValues);
        cursor.getCount();
        closeDB();

        return cursor;
    }

    public synchronized boolean removeDatabase() {
        return context.deleteDatabase(DATABASE_MAIN);
    }

    public synchronized String getDBFileName() {
        return DATABASE_MAIN.trim();
    }

    public synchronized File getApplicationDataBaseFile() {
        File file = context.getDatabasePath(DATABASE_MAIN);
        if (file.isFile()) {
            return file;
        }
        return null;
    }

    public interface QueryBuilder {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
        String ALTER_TABLE = "ALTER TABLE ";
        String ADD_COLUMN = " ADD COLUMN ";
        String PS = " ( ";
        String PE = " ) ";
        String INTEGER_PRIMARY = " INTEGER PRIMARY KEY, ";
        String INTEGER_PRIMARY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        String INTEGER = " INTEGER, ";
        String TEXT = " TEXT, ";
        String END_TEXT = " TEXT";
        String END_INTEGER = " INTEGER";
        String END_BLOB = " BLOB";
    }
}