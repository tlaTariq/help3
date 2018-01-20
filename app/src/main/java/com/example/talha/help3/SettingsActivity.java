package com.example.talha.help3;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView displayName;
    TextView About;
    static String textInDisplayName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        displayName = (TextView) findViewById(R.id.display_name);

        displayName.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.customdialog, null);
                final AlertDialog ad = dialogBuilder.create();
                ad.setView(dialogView);
                final EditText DisplayName =  (EditText) dialogView.findViewById(R.id.username);
                DisplayName.setText(textInDisplayName);
                Button setButton = (Button) dialogView.findViewById(R.id.setB);

                setButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        textInDisplayName = DisplayName.getText().toString();
                        DisplayName.setText(textInDisplayName);
                        ad.cancel();

                    }

                });

                ad.show();
            }
        });

        About = (TextView) findViewById(R.id.about);



    }
}
