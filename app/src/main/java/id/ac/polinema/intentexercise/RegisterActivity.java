package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    private ImageView avatarImage;
    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASS_KEY = "password";
    public static final String CPASS_KEY = "confirmpassword";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUTYOU_KEY = "about";
    private EditText fullnameInput;
    private EditText emailInput;
    private EditText passInput;
    private EditText cpassInput;
    private EditText homepageInput;
    private EditText aboutyouInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        avatarImage = findViewById(R.id.image_profile);
        fullnameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passInput = findViewById(R.id.text_password);
        cpassInput = findViewById(R.id.text_confirm_password);
        homepageInput = findViewById(R.id.text_homepage);
        aboutyouInput = findViewById(R.id.text_about);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        avatarImage.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                avatarImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
    public void handleChangeImage(View view) {
        selectImage(RegisterActivity.this);
    }

    public void handleSubmit(View view) {
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String pass = passInput.getText().toString();
        String cpass = cpassInput.getText().toString();
        String homepage = homepageInput.getText().toString();
        String about = aboutyouInput.getText().toString();

        if (fullname.matches("")) {
            fullnameInput.setError("Nama Diperlukan");
        } else if (email.matches("")) {
            emailInput.setError("Email diperlukan!");
        } else if (pass.matches("")) {
            passInput.setError("Password diperlukan!");
        } else if (pass.matches("")) {
            cpassInput.setError("Confirm Password diperlukan!");
        } else if (!pass.equals(cpass)) {
            cpassInput.setError("Password harus sama!");
        } else if (homepage.matches("")) {
            homepageInput.setError("Homepage diperlukan!");
        } else if (about.matches("")) {
            aboutyouInput.setError("About diperlukan!");
        } else {
            Intent intent = new Intent(this, ProfileActivity.class);
            avatarImage.buildDrawingCache();
            Bitmap image = avatarImage.getDrawingCache();
            Bundle extras = new Bundle();
            extras.putParcelable("IMAGE_KEY", image);
            intent.putExtras(extras);

            intent.putExtra(FULLNAME_KEY, fullname);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(HOMEPAGE_KEY, homepage);
            intent.putExtra(ABOUTYOU_KEY, about);

            startActivity(intent);
        }
    }
    private void selectImage(Context context) {
        final CharSequence[] options = {"Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}
