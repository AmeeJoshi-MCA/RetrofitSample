package retrofit.com.retrofitsample.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import retrofit.com.retrofitsample.BuildConfig;

/**
 * Created by lithe on 5/29/2018.
 */

public class FirebaseDatabaseHelper {

    private Context context;
    private String childAppName;

    public static final String ROOT_NAME = "Android Applications";
    public static final String CHILD_KEY_LOGIN = "Login";
    public static final String CHILD_KEY_INSTALLS = "Installs";

    public static final String FIELD_DATA_CREATED_TIME = "dataCreatedTime";
    public static final String FIELD_DATA_UPDATED_TIME = "dataUpdatedTime";

    public static final String FIELD_USER_DATA_CREATED_TIME = "userDataCreatedTime";
    public static final String FIELD_USER_DATA_UPDATED_TIME = "userDataUpdatedTime";

    public static final String FIELD_SERIAL_NUMBER = "serialNumber";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_FCM_TOKEN = "fcmToken";

    public static final String FIELD_USER_LAST_SEEN_TIME = "userLastSeenTime";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_USER_EMAIL_ID = "userEmailId";
    public static final String FIELD_USER_PROFILE_IMAGE_URL = "userProfileImageURL";

    public static final String FIELD_SCREEN_RESOLUTION = "screenResolution";
    public static final String FIELD_SCREEN_DPI = "screenDPI";
    public static final String FIELD_DEFAULT_EMAIL_ID = "defaultEmailId";
    public static final String FIELD_DEVICE_BRAND_NAME = "deviceBrandName";
    public static final String FIELD_DEVICE_MODEL_NAME = "deviceModelName";

    public static final String FIELD_APP_VERSION_CODE = "appVersionCode";
    public static final String FIELD_APP_VERSION_NAME = "appVersionName";
    public static final String FIELD_APP_PREVIOUS_VERSION_CODE = "appPreviousVersionCode";
    public static final String FIELD_APP_PREVIOUS_VERSION_NAME = "appPreviousVersionName";

    public static final String FIELD_OS_VERSION_CODE = "osVersionCode";
    public static final String FIELD_OS_VERSION_NAME = "osVersionName";
    public static final String FIELD_OS_PREVIOUS_VERSION_CODE = "osPreviousVersionCode";
    public static final String FIELD_OS_PREVIOUS_VERSION_NAME = "osPreviousVersionName";

    public static final String FIELD_OTHER_DATA = "otherData";

    public FirebaseDatabaseHelper(Context context, String appName) {
        this.context = context;
        this.childAppName = appName;
    }

    public DatabaseReference getInstallsDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference()
                .child(ROOT_NAME)
                .child(childAppName)
                .child(CHILD_KEY_INSTALLS);
    }

    public DatabaseReference getLoginDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference()
                .child(ROOT_NAME)
                .child(childAppName)
                .child(CHILD_KEY_LOGIN);
    }

    public DatabaseReference getSingleInstallsDatabaseReference(final String deviceUniqueId) {
        return FirebaseDatabase.getInstance().getReference()
                .child(ROOT_NAME)
                .child(childAppName)
                .child(CHILD_KEY_INSTALLS)
                .child(deviceUniqueId);
    }

    public DatabaseReference getSingleLoginDatabaseReference(final String deviceUniqueId, final String userId) {
        final String userUniqueId = deviceUniqueId + "_" + userId;
        return FirebaseDatabase.getInstance().getReference()
                .child(ROOT_NAME)
                .child(childAppName)
                .child(CHILD_KEY_LOGIN)
                .child(userUniqueId);
    }

    public String getUserUniqueId(final String deviceUniqueId, final String userId) {
        return deviceUniqueId + "_" + userId;
    }

    public String getScreenResolution() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point localPoint = new Point();
        display.getRealSize(localPoint);
        int height = localPoint.y;
        int width = localPoint.x;
        return height + "x" + width;
    }

    public String getScreenDPI() {
        String dpi = "";
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;

        switch (densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                dpi = "LDPI";
                break;

            case DisplayMetrics.DENSITY_MEDIUM:
                // MDPI
                dpi = "MDPI";
                break;

            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
                // HDPI
                dpi = "HDPI";
                break;

            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_280:
                // XHDPI
                dpi = "XHDPI";
                break;

            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
                // XXHDPI
                dpi = "XXHDPI";
                break;

            case DisplayMetrics.DENSITY_XXXHIGH:
            case DisplayMetrics.DENSITY_560:
                // XXXHDPI
                dpi = "XXXHDPI";
                break;
        }
        return dpi;
    }

    public String getDeviceBrand() {
        return "" + Build.BRAND;
    }

    public String getDeviceModelName() {
        return "" + Build.MODEL;
    }

    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getAppVersionName() {
        return "" + String.valueOf(BuildConfig.VERSION_NAME);
    }

    public String getOsVersionCode() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public String getOsVersionName() {
        return "" + Build.VERSION.RELEASE;
    }

    public String getDeviceUniqueId(Context context) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return "" + Build.getSerial();
            } else {
                String serialNumber = Build.SERIAL;
                return "" + serialNumber;
            }
        }
        return "";
    }

}
