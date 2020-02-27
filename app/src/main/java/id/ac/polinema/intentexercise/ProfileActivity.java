package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUTYOU_KEY = "about";
    private TextView fullnameInput;
    private TextView emailInput;
    private TextView homePageInput;
    private TextView aboutInput;
    private Button visitButton;
    private ImageView imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullnameInput = findViewById(R.id.label_fullname);
        emailInput = findViewById(R.id.label_email);
        homePageInput = findViewById(R.id.label_homepage);
        aboutInput = findViewById(R.id.label_about);
        visitButton = findViewById(R.id.button_homepage);
        imageProfile = findViewById(R.id.image_profile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // TODO: display value here
            String fullname = bundle.getString(FULLNAME_KEY);
            String email = bundle.getString(EMAIL_KEY);
            String homepage = bundle.getString(HOMEPAGE_KEY);
            String about = bundle.getString(ABOUTYOU_KEY);

            Bundle extras = getIntent().getExtras();
            Bitmap bmp = (Bitmap) extras.getParcelable("IMAGE_KEY");

            imageProfile.setImageBitmap(bmp );

            fullnameInput.setText(fullname);
            emailInput.setText(email);
            homePageInput.setText(homepage);
            aboutInput.setText(about);

        }
    }
    public void handleVisit(View view) {

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String homepageText = bundle.getString(HOMEPAGE_KEY);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+homepageText));
            startActivity(intent);
        }
    }
}
