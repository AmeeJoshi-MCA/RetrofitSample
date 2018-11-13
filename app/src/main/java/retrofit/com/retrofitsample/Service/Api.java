package retrofit.com.retrofitsample.Service;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import rx.Observable;

/**
 * Created by root on 11/9/18.
 */

public interface Api {

    @POST("users")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("name") String username,
                                   @Field("job") String password);

    @GET("users?")
    Observable<ResponseBody> listUser(@Query("page=") String userId);
}
