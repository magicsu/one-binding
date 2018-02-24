package me.magicsu.onebinding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import me.magicsu.annotation.BindView;
import me.magicsu.annotation.OnClick;
import me.magicsu.api.OneBinding;

/**
 * Created by sushun on 2018/2/23.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView mTextView;
    @BindView(R.id.btn)
    Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneBinding.bind(this);

        mTextView.setText("hello one binding!");
    }

    @OnClick({R.id.btn})
    public void onBtnClick() {
        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
    }
}
