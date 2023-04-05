package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.Manifest;

public class PhoneDialerActivity extends AppCompatActivity {
    private EditText numberText;
    private ImageButton eraseButton;
    private ImageButton callButton;
    private ImageButton declineButton;
    private Button keyboardButton;

    private KeyboardButtonClickListener keyboardButtonClickListener = new KeyboardButtonClickListener();

    private class KeyboardButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            numberText = findViewById(R.id.number_edit_text);
            numberText.setText(String.valueOf(numberText.getText()) + ((Button)view).getText());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
//        setContentView(R.layout.layout_land);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        numberText = findViewById(R.id.number_edit_text);

        eraseButton = findViewById(R.id.delete_button);
        callButton = findViewById(R.id.call_button);
        declineButton = findViewById(R.id.miss_button);

        eraseButton.setOnClickListener(view -> {
            String phoneNumber = String.valueOf(numberText.getText());
            if (!phoneNumber.isEmpty()) {
                numberText.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
            }
        });

        callButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numberText.getText().toString()));
                startActivity(intent);
            }
        });

        declineButton.setOnClickListener(view -> finish());

        for (int i = 0; i < Constants.keyboardIds.length; ++i) {
            keyboardButton = findViewById(Constants.keyboardIds[i]);
            keyboardButton.setOnClickListener(keyboardButtonClickListener);
        }
    }
}