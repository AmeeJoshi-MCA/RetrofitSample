package retrofit.com.retrofitsample;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.com.retrofitsample.Service.Api;
import retrofit.com.retrofitsample.Service.RerofitHelper;
import retrofit.com.retrofitsample.application.AppControl;
import retrofit.com.retrofitsample.helpers.FirebaseDatabaseHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog alertDialogRequestPermission;
    private FrameLoaderDialog frameLoaderDialog;
    private EditText editTextUserId, editTextPassword;
    private TextView textViewButtonSignIn;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupObjects();
        setupUpdateUI();
        setupComponents();
        performOperations();
    }

    private synchronized void setupObjects() {
        api = RerofitHelper.getAPIService();
        frameLoaderDialog = new FrameLoaderDialog(this);

        editTextUserId = (EditText) findViewById(R.id.activity_login_edittext_user_id);
        editTextPassword = (EditText) findViewById(R.id.activity_login_edittext_password);

        textViewButtonSignIn = (TextView) findViewById(R.id.activity_login_text_button_login);
    }

    private synchronized void setupUpdateUI() {

    }

    private synchronized void setupComponents() {

        textViewButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (isValidFields()) {
                        String userId = editTextUserId.getText().toString().trim();
                        String password = editTextPassword.getText().toString().trim();
                        AppControl.getPreferenceHelper().setUserName(userId);
                        AppControl.getPreferenceHelper().setUserPass(password);

                       // userLogin(userId, password);

                        listUser("2");
                    }

            }
        });

    }

    private synchronized void performOperations() {

    }

    private boolean isValidFields() {


        return true;
    }


    private void userLogin(String username, String password) {
        showProgress();

        api.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            hideProgress();
                            String response = responseBody.string();
                            Log.d("response", "response: " + response);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    public void listUser(String pageNo){

        api.listUser(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            hideProgress();
                            String response = responseBody.string();
                            Log.d("response", "response: " + response);
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLoaderDialog.show();
            }
        });
    }

    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLoaderDialog.dismiss();
            }
        });
    }

    private void updateFirebaseInstallData() {

        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(this, getString(R.string.app_name));

        final String deviceUniqueId = firebaseDatabaseHelper.getDeviceUniqueId(this);

        final DatabaseReference databaseReferenceInstall = firebaseDatabaseHelper
                .getSingleInstallsDatabaseReference(deviceUniqueId);

        HashMap<String, String> values = new HashMap<>();

        values.put(FirebaseDatabaseHelper.FIELD_SERIAL_NUMBER, deviceUniqueId);
        values.put(FirebaseDatabaseHelper.FIELD_DEFAULT_EMAIL_ID, "");
        values.put(FirebaseDatabaseHelper.FIELD_FCM_TOKEN, AppControl.getFcmToken());

        values.put(FirebaseDatabaseHelper.FIELD_DATA_CREATED_TIME, AppControl.getPreferenceHelper().getAppCreatedTime());
        values.put(FirebaseDatabaseHelper.FIELD_DATA_UPDATED_TIME, String.valueOf(System.currentTimeMillis()));

        values.put(FirebaseDatabaseHelper.FIELD_DEVICE_BRAND_NAME, firebaseDatabaseHelper.getDeviceBrand());
        values.put(FirebaseDatabaseHelper.FIELD_DEVICE_MODEL_NAME, firebaseDatabaseHelper.getDeviceModelName());

        values.put(FirebaseDatabaseHelper.FIELD_SCREEN_RESOLUTION, firebaseDatabaseHelper.getScreenResolution());
        values.put(FirebaseDatabaseHelper.FIELD_SCREEN_DPI, firebaseDatabaseHelper.getScreenDPI());

        values.put(FirebaseDatabaseHelper.FIELD_APP_VERSION_CODE, firebaseDatabaseHelper.getAppVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_APP_VERSION_NAME, firebaseDatabaseHelper.getAppVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_APP_PREVIOUS_VERSION_CODE, firebaseDatabaseHelper.getAppVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_APP_PREVIOUS_VERSION_NAME, firebaseDatabaseHelper.getAppVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_OS_VERSION_CODE, firebaseDatabaseHelper.getOsVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_OS_VERSION_NAME, firebaseDatabaseHelper.getOsVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_OS_PREVIOUS_VERSION_CODE, firebaseDatabaseHelper.getOsVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_OS_PREVIOUS_VERSION_NAME, firebaseDatabaseHelper.getOsVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_OTHER_DATA, "");

        databaseReferenceInstall.setValue(values)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("FIREBASE", "INSTALL DATA UPDATE FAILED");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("FIREBASE", "INSTALL DATA UPDATE SUCCESS");
                    }
                });
    }

    private void updateFirebaseLoginData(String userId, String userName, String userEmail) {
        FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper(this, getString(R.string.app_name));

        final String deviceUniqueId = firebaseDatabaseHelper.getDeviceUniqueId(this);

        final String userUniqueId = firebaseDatabaseHelper.getUserUniqueId(deviceUniqueId, userId);

        Log.i("UNIQUE_ID", "USER UNIQUE ID : " + userUniqueId);

        final DatabaseReference databaseReferenceLogin = firebaseDatabaseHelper
                .getSingleLoginDatabaseReference(deviceUniqueId, userId);

        HashMap<String, String> values = new HashMap<>();

        values.put(FirebaseDatabaseHelper.FIELD_USER_ID, userId);
        values.put(FirebaseDatabaseHelper.FIELD_SERIAL_NUMBER, deviceUniqueId);
        values.put(FirebaseDatabaseHelper.FIELD_FCM_TOKEN, AppControl.getFcmToken());

        values.put(FirebaseDatabaseHelper.FIELD_USER_DATA_CREATED_TIME, String.valueOf(System.currentTimeMillis()));
        values.put(FirebaseDatabaseHelper.FIELD_USER_DATA_UPDATED_TIME, String.valueOf(System.currentTimeMillis()));
        values.put(FirebaseDatabaseHelper.FIELD_USER_LAST_SEEN_TIME, String.valueOf(System.currentTimeMillis()));

        values.put(FirebaseDatabaseHelper.FIELD_USER_NAME, userName);
        values.put(FirebaseDatabaseHelper.FIELD_USER_EMAIL_ID, userEmail);
        values.put(FirebaseDatabaseHelper.FIELD_USER_PROFILE_IMAGE_URL, "");

        values.put(FirebaseDatabaseHelper.FIELD_APP_VERSION_CODE, firebaseDatabaseHelper.getAppVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_APP_VERSION_NAME, firebaseDatabaseHelper.getAppVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_APP_PREVIOUS_VERSION_CODE, firebaseDatabaseHelper.getAppVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_APP_PREVIOUS_VERSION_NAME, firebaseDatabaseHelper.getAppVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_OS_VERSION_CODE, firebaseDatabaseHelper.getOsVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_OS_VERSION_NAME, firebaseDatabaseHelper.getOsVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_OS_PREVIOUS_VERSION_CODE, firebaseDatabaseHelper.getOsVersionCode());
        values.put(FirebaseDatabaseHelper.FIELD_OS_PREVIOUS_VERSION_NAME, firebaseDatabaseHelper.getOsVersionName());

        values.put(FirebaseDatabaseHelper.FIELD_OTHER_DATA, "");

        databaseReferenceLogin.setValue(values)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("FIREBASE", "INSTALL DATA UPDATE FAILED");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("FIREBASE", "INSTALL DATA UPDATE SUCCESS");
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        checkPermissions();
        Log.i("RequestPermission", "ON REQUEST PERMISSION RESULT CALL");
    }

    private void checkPermissions() {

        boolean isAnyPermissionDenied = false;

        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "WRITE_EXTERNAL_STORAGE");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "READ_EXTERNAL_STORAGE");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "READ_PHONE_STATE");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "ACCESS_NETWORK_STATE");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "CALL_PHONE");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "CAMERA");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "ACCESS_COARSE_LOCATION");
            isAnyPermissionDenied = true;
        } else if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("DENIED", "ACCESS_FINE_LOCATION");
            isAnyPermissionDenied = true;
        }

        if (isAnyPermissionDenied) {
            if (alertDialogRequestPermission != null) {
                if (!alertDialogRequestPermission.isShowing()) {
                    alertDialogRequestPermission.show();
                }
            } else {
                alertDialogRequestPermission = new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Notice")
                        .setMessage("This application requires some device permissions. or goto app settings to enable required permissions")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] permissionStack = {
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_NETWORK_STATE,
                                        Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.CALL_PHONE,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                };

                                int permissionRequireCount = 0;
                                for (String permission : permissionStack) {
                                    if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
                                        permissionRequireCount++;
                                    }
                                }
                                if (permissionRequireCount != 0) {
                                    ActivityCompat.requestPermissions(LoginActivity.this, permissionStack, 1);
                                }
                            }
                        })
                        .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNeutralButton("SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        } else {
            //updateFirebaseInstallData();
        }
    }

}
