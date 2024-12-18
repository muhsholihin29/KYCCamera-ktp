package com.gaurav.kyccameraexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.gaurav.kyccamera.camera.KYCCamera;

public class MainActivity extends AppCompatActivity {
    private ImageView acf;
    private ImageView acb;
    private ImageView pcf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acf = findViewById(R.id.acf);
        acb = findViewById(R.id.acb);
        pcf = findViewById(R.id.pcf);

    }

    public void ktp(View view) {
        KYCCamera.create(this).openCamera(KYCCamera.TYPE_KTP);
    }

    public void selfie(View view) {
        KYCCamera.create(this).openCamera(KYCCamera.TYPE_SELFIE);
    }

    public void selfieKtp(View view) {
        KYCCamera.create(this).openCamera(KYCCamera.TYPE_SELFIE_KTP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == KYCCamera.RESULT_CODE) {
            //Get image path，display image
            final String path = KYCCamera.getImagePath(data);
            if (!TextUtils.isEmpty(path)) {
                if (requestCode == KYCCamera.TYPE_KTP) { //Front of AADHAAR card
                    acf.setImageBitmap(BitmapFactory.decodeFile(path));
                } else if (requestCode == KYCCamera.TYPE_SELFIE) {  //Reverse side of AADHAAR card
                    acb.setImageBitmap(BitmapFactory.decodeFile(path));
                } else if (requestCode == KYCCamera.TYPE_SELFIE_KTP) {  //Front of PAN card
                    pcf.setImageBitmap(BitmapFactory.decodeFile(path));
                }

            }
        }
    }
}