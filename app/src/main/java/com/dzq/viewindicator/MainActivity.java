package com.dzq.viewindicator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dzq.widget.ViewIndicator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewIndicator viewIndicator = (ViewIndicator) findViewById(R.id.lay_indicator1);
        viewIndicator.init(5);
        viewIndicator.switchIndicator(1);
        ViewIndicator viewIndicator2 = (ViewIndicator) findViewById(R.id.lay_indicator2);
        viewIndicator2.init(5);
        viewIndicator2.switchIndicator(2);
        ViewIndicator viewIndicator3 = (ViewIndicator) findViewById(R.id.lay_indicator3);
        viewIndicator3.init(5);
        viewIndicator3.switchIndicator(3);
        ViewIndicator viewIndicator4 = (ViewIndicator) findViewById(R.id.lay_indicator4);
        viewIndicator4.init(5);
        viewIndicator4.switchIndicator(4);
        viewIndicator4.setOnItemClickListener(new ViewIndicator.OnItemClickListener() {
            @Override
            public void onnItemClick(int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
