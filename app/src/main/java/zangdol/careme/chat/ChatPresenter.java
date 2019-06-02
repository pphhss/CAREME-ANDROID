package zangdol.careme.chat;

import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.HashMap;

import zangdol.careme.util.SaveSharedPreference;

public class ChatPresenter implements ChatContract.Presenter{
    private ChatContract.View view;
    private ChatManager chatManager;
    private MessageListAdapter mMessageAdapter;

    private String shelterIdx;
    public ChatPresenter(ChatContract.View view) {
        this.view = view;
        chatManager = new ChatManager();
        shelterIdx = view.getActivity().getIntent().getStringExtra("shelter_idx");
    }

    @Override
    public void getData() {
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        mMessageAdapter = new MessageListAdapter(view.getActivity(), list);


        view.setAdapter(mMessageAdapter);
    }

    @Override
    public void connect() {
        if(!SaveSharedPreference.isLogin())
            return;
        chatManager.connect(SaveSharedPreference.getIdx());
    }

    @Override
    public void disconnect() {
        if(!SaveSharedPreference.isLogin())
            return;
        chatManager.disconnect();
    }

    @Override
    public void sendMessage(String message) {
        HashMap<String,String> data = new HashMap<>();

        data.put("message",message);
        data.put("sender",SaveSharedPreference.getIdx());
        data.put("receiver",shelterIdx); // 쉘터 idx 추가 필요.
        data.put("type","0");

        chatManager.sendMessage(data);
        data.put("time",new String(Calendar.getInstance().getTime().toString()).substring(10,16));
        mMessageAdapter.addItem(data);
    }
}
