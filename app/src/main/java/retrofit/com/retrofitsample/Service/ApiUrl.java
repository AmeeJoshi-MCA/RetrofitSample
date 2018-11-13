package retrofit.com.retrofitsample.Service;

public class ApiUrl {

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    public static final String HOST_0 = "reqres.in/api/";

    public static String CURRENT_HOST =  HTTPS + HOST_0; //host switch

    public static void setCurrentHost(String hostURL) {
        CURRENT_HOST = hostURL;
    }

    public static String getCurrentHost() {
        return CURRENT_HOST;
    }

    public static String API_Login;

    public static void initHost() {
        API_Login = HTTPS + CURRENT_HOST + "users";
    }
}