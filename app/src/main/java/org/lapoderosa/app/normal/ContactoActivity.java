package org.lapoderosa.app.normal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.lapoderosa.app.databinding.ActivityContactoBinding;

public class ContactoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContactoBinding binding = ActivityContactoBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.contactoFacebook.setOnClickListener(view -> {
            final String url1 = "fb://page/213440425391495";
            final String url2 = "https:www.facebook.com/213440425391495";

            goTo(url1, url2);
            finish();
        });

        binding.contactoTwitter.setOnClickListener(view -> {
            final String url1 = "twitter://user?user_id=402559824";
            final String url2 = "https://twitter.com/gargantapodero";

            goTo(url1, url2);
            finish();
        });

        binding.contactoWeb.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.lapoderosa.org.ar")));
            finish();
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
