package retrofit.com.retrofitsample.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {

    private SharedPreferences sharedPreferences;

    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String IS_REGISTERED = "IS_REGISTERED";
    public static final String USER_ID = "USER_ID";
    public static final String USER_PASS = "USER_PASS";
    public static final String AUTHENTICATION_SECURITY_TYPE = "AUTHENTICATION_SECURITY_TYPE";
    public static final String REGISTERED_PIN = "REGISTERED_PIN";
    public static final String PRESERVED_APP_VERSION = "PRESERVED_APP_VERSION";
    public static final String XCSRFTOKEN = "XCSRFTOKEN";
    public static final String LOGIN_RESPONSE = "LOGIN_RESPONSE";
    public static final String REGISTER_RESPONSE = "REGISTER_RESPONSE";
    public static final String SELECTED_CUSTOMER_ID = "SELECTED_CUSTOMER_ID";
    public static final String APP_CREATED_TIME = "APP_CREATED_TIME";
    public static final String USER_CREATED_TIME = "USER_CREATED_TIME";

    public static final String FIREBASE_PREVIOUS_OS_VERSION_CODE = "FIREBASE_PREVIOUS_OS_VERSION_CODE";
    public static final String FIREBASE_PREVIOUS_APP_VERSION_CODE = "FIREBASE_PREVIOUS_APP_VERSION_CODE";

    public static final String FIREBASE_PREVIOUS_OS_VERSION_NAME = "FIREBASE_PREVIOUS_OS_VERSION_NAME";
    public static final String FIREBASE_PREVIOUS_APP_VERSION_NAME = "FIREBASE_PREVIOUS_APP_VERSION_NAME";

    public static final String VAL_AUTHENTICATION_PIN_SECURITY = "VAL_AUTHENTICATION_PIN_SECURITY";
    public static final String VAL_AUTHENTICATION_FINGERPRINT_SECURITY = "VAL_AUTHENTICATION_FINGERPRINT_SECURITY";

    public PreferenceHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setIsLogin(boolean value) {
        sharedPreferences.edit().putBoolean(IS_LOGIN, value).apply();
    }

    public boolean getIsLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void setIsRegistered(boolean value) {
        sharedPreferences.edit().putBoolean(IS_REGISTERED, value).apply();
    }

    public boolean getIsRegistered() {
        return sharedPreferences.getBoolean(IS_REGISTERED, false);
    }

    public void setUserName(String value) {
        sharedPreferences.edit().putString(USER_ID, value).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setUserPass(String value) {
        sharedPreferences.edit().putString(USER_PASS, value).apply();
    }

    public String getUserPass() {
        return sharedPreferences.getString(USER_PASS, null);
    }

    public void setAuthenticationSecurityType(String value) {
        sharedPreferences.edit().putString(AUTHENTICATION_SECURITY_TYPE, value).apply();
    }

    public String getAuthenticationSecurityType() {
        return sharedPreferences.getString(AUTHENTICATION_SECURITY_TYPE, null);
    }

    public void setRegisteredPin(String value) {
        sharedPreferences.edit().putString(REGISTERED_PIN, value).apply();
    }

    public String getRegisteredPin() {
        return sharedPreferences.getString(REGISTERED_PIN, null);
    }

    public void setPreservedAppVersion(String values) {
        sharedPreferences.edit().putString(PRESERVED_APP_VERSION, values).apply();
    }

    public String getPreservedAppVersion() {
        return sharedPreferences.getString(PRESERVED_APP_VERSION, null);
    }

    public void setXCSRFToken(String values) {
        sharedPreferences.edit().putString(XCSRFTOKEN, values).apply();
    }

    public String getXCSRFToken() {
        return sharedPreferences.getString(XCSRFTOKEN, null);
    }

    public void setLoginResponse(String values) {
        sharedPreferences.edit().putString(LOGIN_RESPONSE, values).apply();
    }

    public String getLoginResponse() {
        return sharedPreferences.getString(LOGIN_RESPONSE, null);
    }

    public void setRegisterResponse(String values) {
        sharedPreferences.edit().putString(REGISTER_RESPONSE, values).apply();
    }

    public String getRegisterResponse() {
        return sharedPreferences.getString(REGISTER_RESPONSE, null);
    }

    public void setSelectedCustomerId(String values) {
        sharedPreferences.edit().putString(SELECTED_CUSTOMER_ID, values).apply();
    }

    public String getSelectedCustomerId() {
        return sharedPreferences.getString(SELECTED_CUSTOMER_ID, null);
    }

    public void setAppCreatedTime(String values) {
        sharedPreferences.edit().putString(APP_CREATED_TIME, values).apply();
    }

    public String getAppCreatedTime() {
        return sharedPreferences.getString(APP_CREATED_TIME, null);
    }


    public void setUserCreatedTime(String values) {
        sharedPreferences.edit().putString(USER_CREATED_TIME, values).apply();
    }

    public String getUserCreatedTime() {
        return sharedPreferences.getString(USER_CREATED_TIME, null);
    }

    public void setFirebasePreviousOsVersionCode(String values) {
        sharedPreferences.edit().putString(FIREBASE_PREVIOUS_OS_VERSION_CODE, values).apply();
    }

    public String getFirebasePreviousOsVersionCode() {
        return sharedPreferences.getString(FIREBASE_PREVIOUS_OS_VERSION_CODE, null);
    }

    public void setFirebasePreviousAppVersionCode(String values) {
        sharedPreferences.edit().putString(FIREBASE_PREVIOUS_APP_VERSION_CODE, values).apply();
    }

    public String getFirebasePreviousAppVersionCode() {
        return sharedPreferences.getString(FIREBASE_PREVIOUS_APP_VERSION_CODE, null);
    }

    public void setFirebasePreviousOsVersionName(String values) {
        sharedPreferences.edit().putString(FIREBASE_PREVIOUS_OS_VERSION_NAME, values).apply();
    }

    public String getFirebasePreviousOsVersionName() {
        return sharedPreferences.getString(FIREBASE_PREVIOUS_OS_VERSION_NAME, null);
    }

    public void setFirebasePreviousAppVersionName(String values) {
        sharedPreferences.edit().putString(FIREBASE_PREVIOUS_APP_VERSION_NAME, values).apply();
    }

    public String getFirebasePreviousAppVersionName() {
        return sharedPreferences.getString(FIREBASE_PREVIOUS_APP_VERSION_NAME, null);
    }

}