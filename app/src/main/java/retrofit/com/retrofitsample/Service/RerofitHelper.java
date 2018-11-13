package retrofit.com.retrofitsample.Service;


/**
 * Created by root on 11/9/18.
 */

public class RerofitHelper {

    public RerofitHelper() {}

    public static Api getAPIService() {
        return RetrofitClient.getRetrofit(ApiUrl.CURRENT_HOST).create(Api.class);
    }
}
