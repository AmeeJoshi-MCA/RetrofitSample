package retrofit.com.retrofitsample.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import retrofit.com.retrofitsample.BuildConfig;
import retrofit.com.retrofitsample.R;
import retrofit.com.retrofitsample.helpers.DatabaseHelper;
import retrofit.com.retrofitsample.helpers.FirebaseDatabaseHelper;
import retrofit.com.retrofitsample.helpers.PreferenceHelper;


import java.io.IOException;

public class AppControl extends Application {

    private static String deviceName;
    private static String fcmToken;
    private static PreferenceHelper preferenceHelper;
    private static DatabaseHelper databaseHelper;

    private Context globalContext;

    public static Typeface fontRobotoBold, fontRobotoLight, fontRobotoMedium, fontRobotoRegular, fontRobotoThin;

    public AppControl() {

    }

    public AppControl(Context context) {
        this.globalContext = context;
    }

    private void initFonts() {
        fontRobotoBold = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");
        fontRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf");
        fontRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/roboto_medium.ttf");
        fontRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        fontRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;

        preferenceHelper = new PreferenceHelper(this);
        databaseHelper = new DatabaseHelper(this);
        //initFonts();

        if (preferenceHelper.getAppCreatedTime() == null) {
            preferenceHelper.setAppCreatedTime(String.valueOf(System.currentTimeMillis()));
        }

        String preservedAppVersion = preferenceHelper.getPreservedAppVersion();

        //do stuffs if application version change
        String currentAppVersion = BuildConfig.VERSION_NAME;

        if (preservedAppVersion == null) {
            // application is first time installed and launched
            onApplicationFirstInstall();
            preferenceHelper.setPreservedAppVersion(currentAppVersion);
            Log.i(this.getClass().getName(), "Application First Installation CALL");
        } else if (!preservedAppVersion.equals(currentAppVersion)) {
            // application is updated
            onApplicationUpdate();
            preferenceHelper.setPreservedAppVersion(currentAppVersion);
            Log.i(this.getClass().getName(), "Application Upgrade CALL : Previous = "
                    + preservedAppVersion + " , Current " + currentAppVersion);
        } else {
            // application is running with same stable version without any version changes
            Log.i(this.getClass().getName(), "Version Check : Preserved = "
                    + preservedAppVersion + " , Current " + currentAppVersion);
        }

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(this, getString(R.string.app_name));

        String firebaseCurrentAppVersionCode = firebaseDatabaseHelper.getAppVersionCode();
        String firebaseCurrentAppVersionName = firebaseDatabaseHelper.getAppVersionName();
        String firebaseCurrentOsVersionCode = firebaseDatabaseHelper.getOsVersionCode();
        String firebaseCurrentOsVersionName = firebaseDatabaseHelper.getOsVersionName();

        String firebasePreviousAppVersionCode = preferenceHelper.getFirebasePreviousAppVersionCode();
        String firebasePreviousAppVersionName = preferenceHelper.getFirebasePreviousAppVersionName();
        String firebasePreviousOsVersionCode = preferenceHelper.getFirebasePreviousOsVersionCode();
        String firebasePreviousOsVersionName = preferenceHelper.getFirebasePreviousOsVersionName();

        if (firebasePreviousAppVersionCode == null) {
            preferenceHelper.setFirebasePreviousAppVersionCode("");
            preferenceHelper.setFirebasePreviousAppVersionName("");
        } else if (!firebasePreviousAppVersionCode.equalsIgnoreCase(firebaseCurrentAppVersionCode)) {
            preferenceHelper.setFirebasePreviousAppVersionCode(firebaseCurrentAppVersionCode);
            preferenceHelper.setFirebasePreviousAppVersionName(firebaseCurrentAppVersionName);
        }

        if (firebasePreviousOsVersionCode == null) {
            preferenceHelper.setFirebasePreviousOsVersionCode("");
            preferenceHelper.setFirebasePreviousOsVersionName("");
        } else if (!firebasePreviousOsVersionCode.equalsIgnoreCase(firebaseCurrentOsVersionCode)) {
            preferenceHelper.setFirebasePreviousOsVersionCode(firebaseCurrentAppVersionCode);
            preferenceHelper.setFirebasePreviousOsVersionName(firebaseCurrentAppVersionName);
        }

        deviceName = "test";
        Log.i("DEVICE_NAME", deviceName);



    }

    /**
     * @return device original name
     */
    public static String getDeviceName() {
        return deviceName;
    }

    /**
     * @return firebase device token
     */
    public static String getFcmToken() {
        return fcmToken;
    }

    /**
     * use this method to for first installation of application
     * this method will call on first installation of application
     */
    private void onApplicationFirstInstall() {

    }

    /**
     * use this method for application update
     * this method will call when application version will change and application update
     */
    private void onApplicationUpdate() {

    }

    /**
     * this method is used to access Global values
     *
     * @return GlobalValues
     */

    /**
     * this method returns Retrofit Service handler to handle web service calls
     *
     * @return RetrofitServiceHandler use get getApiInterface(); method to implement APIs
     */


    /**
     * this method returns Preference helper class to handle all kind of preferences in app
     *
     * @return PreferenceHelper
     */
    public static PreferenceHelper getPreferenceHelper() {
        return preferenceHelper;
    }

    /**
     * this method returns Database helper class to handle all kind of database operations in app
     *
     * @return DatabaseHelper
     */
    public static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    /**
     * this method returns Toast manager to show and dismiss all toast messages in app
     *
     * @return ToastManager
     */

    /**
     * this method returns drawable from drawable resource id
     *
     * @param id drawable resource id
     * @return Drawable
     */
    public Drawable getDrawableByID(int id) {
        return ContextCompat.getDrawable(globalContext, id);
    }

    /**
     * this method returns drawable with color by given color
     *
     * @param drawableID drawable resource id
     * @param colorID    color resource id
     * @return Drawable
     */
    public Drawable getDrawableWithColor(int drawableID, int colorID) {
        Drawable drawable = getDrawableByID(drawableID);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable = new BitmapDrawable(globalContext.getResources(), bitmap);
        drawable.setColorFilter(new PorterDuffColorFilter(getColorByID(colorID),
                PorterDuff.Mode.SRC_IN));
        return drawable;
    }

    /**
     * this method returns drawable with color by given color
     *
     * @param drawableID drawable resource id
     * @param color      integer color
     * @return Drawable
     */
    public Drawable getDrawableWithExistingColor(int drawableID, int color) {
        Drawable drawable = getDrawableByID(drawableID);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawable = new BitmapDrawable(globalContext.getResources(), bitmap);
        drawable.setColorFilter(new PorterDuffColorFilter(color,
                PorterDuff.Mode.SRC_IN));
        return drawable;
    }

    /**
     * this method returns integer color from given color resource id
     *
     * @param id color resource id
     * @return integer color
     */
    public int getColorByID(int id) {
        return ContextCompat.getColor(globalContext, id);
    }

    /**
     * this method is used to convert integer color to hex color code with #
     *
     * @param color integer color
     * @return hex color
     */
    public static String getIntColorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFF & color);
    }


    /**
     * this method is used to convert given to DP value to Pixel value
     *
     * @param dp      in float
     * @param metrics Display Metrics
     * @return pixel
     */
    public static int convertDpToPixel(float dp, DisplayMetrics metrics) {
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * this method  is used to hide keyboard if keyboard is visible on activity
     *
     * @param activity for context
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * this method is used to show keyboard input on specific input edittext
     *
     * @param editText input field
     * @param activity provide activity for context
     */
    public static void showKeyboardOnInput(EditText editText, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public Context getGlobalContext() {
        return globalContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}