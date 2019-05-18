package zangdol.careme.discoverRecord;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import zangdol.careme.R;

public class DiscoverRecordActivity extends AppCompatActivity implements DiscoverRecordContract.View {

    private DiscoverRecordContract.Presenter presenter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_record);
        presenter = new DiscoverRecordPresenter(this);
        setElements();
        presenter.getDiscoverRecord();
    }

    public void setElements() {
        listView = (ListView) findViewById(R.id.discover_record_listview);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setAdapter(final DiscoverRecordAdapter adapter) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
            }
        });
    }
}
