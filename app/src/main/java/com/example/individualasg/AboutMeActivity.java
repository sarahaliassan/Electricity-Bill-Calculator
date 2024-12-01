package com.example.individualasg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Find the "Back to Calculator" button
        Button btnBack = findViewById(R.id.btnBack);

        // Set an OnClickListener on the button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to MainActivity
                Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Closes AboutMeActivity
            }
        });

        Button btnVisitGithub = findViewById(R.id.btnVisitGithub);
        btnVisitGithub.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sarahaliassan"));
            startActivity(intent);
        });

    }
}
