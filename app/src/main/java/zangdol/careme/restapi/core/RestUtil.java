package zangdol.careme.restapi.core;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RestUtil {


    public interface OnRestApiListener {
        void OnResult(JSONObject result);
    }

    /*
     * 서버에게 request를 보내는 함수.
     *
     * URL : postURL
     * param : 파라메터
     * listener : 끝나고 처리할 리스너.
     *
     * */
    public void request(final String URL, final List<NameValuePair> params, final OnRestApiListener listener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                httpPost.setHeader(HTTP.CONTENT_TYPE,
                        "application/x-www-form-urlencoded;charset=UTF-8");

                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                    HttpResponse response = httpClient.execute(httpPost);

                    JSONObject result = new JSONObject(EntityUtils.toString(response.getEntity())); // JSON 형태의 결과값.
                    listener.OnResult(result);

                } catch (Exception e) {
                    Log.d("LoginError", "LoginError");
                    e.printStackTrace();
                }
            }
        });
    }

    public void uploadImage(final String URL,final MultipartEntityBuilder builder) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);

                try {/* "http://192.168.35.19:3000/erp/animal/action/android"
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    builder.setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                    builder.addPart("test",new StringBody("test", ContentType.APPLICATION_JSON));

                    builder.addPart("animalImage", new FileBody(new File(path)));

                    httppost.setEntity(builder.build());*/

                    httppost.setEntity(builder.build());

                    HttpResponse response = httpclient.execute(httppost);

                    Log.e("test", "결과:" + EntityUtils.toString(response.getEntity()));



                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    // JSON object를 MAP 클래스로 변환시켜주는 클래스
    public static HashMap<String, String> json2map(JSONObject res) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();

            Iterator<String> iterator = res.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = res.getString(key);
                map.put(key, value);

            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
