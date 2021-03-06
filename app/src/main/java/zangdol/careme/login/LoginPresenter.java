package zangdol.careme.login;


import android.content.Intent;
import android.widget.Toast;

import zangdol.careme.login.register.RegisterActivity;
import zangdol.careme.model.User;
import zangdol.careme.util.SaveSharedPreference;

public class LoginPresenter implements LoginContract.Presenter, User.OnLoginListener {
    private User user;
    private LoginActivity loginActivity;

    public LoginPresenter(LoginActivity view) {
        loginActivity = view;
        user = new User();
        user.setLoginListener(this);
    }

    /*
     * 로그인 버튼 눌렸을 때 실행되는 과정
     * */
    @Override
    public void login(String id, String pw) {
        String token = SaveSharedPreference.getToken();
        user.login(id, pw,token);
    }

    @Override
    public void register() {
        loginActivity.startActivity(new Intent(loginActivity, RegisterActivity.class));
    }

    @Override
    public void onLogin(boolean isSuccess) {

        final String message;

        if (isSuccess)
            message = "로그인 성공";
        else
            message = "로그인 실패";

        loginActivity.runOnUiThread(new Runnable() { // Toast를 띄우기 위해서 runOnUiThead를 사용.
            @Override
            public void run() {
                Toast toast = Toast.makeText(loginActivity, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        if (isSuccess)
            loginActivity.moveMainActivity();

    }
}
