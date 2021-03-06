package zangdol.careme.restapi.core;

public class RestFactory {
    /*
     * RestUtil을 사용하는 Class들이 코드 중복이 되는 문제점을 피하기 위해 만듬.
     *
     * */
    private static RestFactory instance = null;

    private RestFactory() {

    }

    public static RestFactory getInstance() {
        if (instance == null)
            instance = new RestFactory();
        return instance;
    }


    public void request(final String URL, final RestUtil.OnRestApiListener listener, final Parameters param) {
        new RestUtil().request(URL, param.getParams(), listener);
    }

    public void uploadImage(final String URL,final RestUtil.OnRestApiListener listener,final ImageParameters imageParameters){
        new RestUtil().uploadImage(URL,imageParameters.getBuilder(),listener);
    }

    public void get(final String URL,final Headers headers, final Parameters params,final RestUtil.OnRestApiListener listener){
        new RestUtil().get(URL,headers.getHeaders(),params.getParams(),listener);
    }
}
