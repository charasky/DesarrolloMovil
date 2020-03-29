package org.lapoderosa.app.normal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lapoderosa.app.R;

public class ContactoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        LinearLayout lFacebook = findViewById(R.id.contactoFacebook);
        LinearLayout lTwitter = findViewById(R.id.contactoTwitter);
        TextView tvWeb = findViewById(R.id.contactoWeb);

        lFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url1 = "fb://page/213440425391495";
                final String url2 = "https:www.facebook.com/213440425391495";

                goTo(url1, url2);
                finish();
            }
        });

        lTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url1 = "twitter://user?user_id=402559824";
                final String url2 = "https://twitter.com/gargantapodero";

                goTo(url1, url2);
                finish();
            }
        });

        tvWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lapoderosa.org.ar")));
                finish();
            }
        });
    }

    public void goTo(String string1, String string2) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(string1)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(string2)));
        }
    }
}
