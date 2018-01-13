package com.example.talha.help3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by talha on 1/8/2018.
 */

public class SecondActivity extends Activity  {

    private TextView msg_edittext;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_second);


        msg_edittext = (TextView) findViewById(R.id.ttt);

        btn = (Button) findViewById(R.id.btn);

        msg_edittext.setText("test test etst");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatDisplay.class);
                startActivity(intent);
            }
        });

        //Toast.makeText(DetailActivity.this, name, Toast.LENGTH_SHORT).show();

    }

}
