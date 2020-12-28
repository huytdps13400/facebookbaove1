package com.example.facebookbaove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class Share extends AppCompatActivity {
    Button btnShareImage,btnShareLink;
    EditText tvLinkShare;
    TextView mail,ten;
    ImageView imgShare,anh;
    Bitmap bitmap;
    private Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        addControl();
        Bundle i = getIntent().getExtras();
        ten.setText(i.getString("name"));
        mail.setText(i.getString("email"));
        Glide.with(Share.this).load(i.getString("profile_pic")).into(anh);


        addEvent();

    }
    public void shareLinkFB(String title, String linkShare, String imgThumnal) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setImageUrl(Uri.parse(imgThumnal))
                .setContentUrl(Uri.parse(linkShare))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#quanlisinhvien")
                        .build())
                .build();
        ShareDialog.show(this, content);
    }



    public  void sharePhoto(Bitmap b, String caption) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(b)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo).build();
        ShareDialog.show(this, content);
    }
    public void runTimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(Share.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(Share.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(Share.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }
            }
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            if(data != null)
            {
                selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(Share.this.getContentResolver(), selectedImageUri);
                    imgShare.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void addEvent() {
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTimePermission();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
        btnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String capTion = "Học Lập Trình Android";
                sharePhoto(bitmap,capTion);
            }
        });
        btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "Lập Trinh Android";
                String imageThumnal = "data:image/jpeg;base64,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";
                String linkShare = tvLinkShare.getText().toString();
                shareLinkFB(title,linkShare,imageThumnal);
            }
        });
    }

    private void addControl() {
        tvLinkShare =  findViewById(R.id.tv_link);
        btnShareImage = (Button) findViewById(R.id.btn_share_image);
        btnShareLink = (Button) findViewById(R.id.btn_share_link);
        imgShare = (ImageView) findViewById(R.id.img_share);
        anh = findViewById(R.id.anh);
        mail = findViewById(R.id.mail);
        ten = findViewById(R.id.ten);
    }


}