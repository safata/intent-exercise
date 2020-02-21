package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText Fullname;
    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.Email
    private EditText Email;
    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.Password
    private EditText Password;
    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.ConfirmPassword
    private EditText ConfirmPassword;
    @NotEmpty
    private EditText Homepage;
    @NotEmpty
    private EditText AboutYou;
    protected Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void handleOkRegister(View view) {
            validator.validate(true);
    }
}
