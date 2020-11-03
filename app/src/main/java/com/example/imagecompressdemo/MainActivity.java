package com.example.imagecompressdemo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_before)
    ImageView mIvBefore;
    @BindView(R.id.iv_after)
    ImageView mIvAfter;
    @BindView(R.id.iv_after2)
    ImageView mIvAfter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 666);
        init();
    }

    private void init() {

    }

    Uri originUri;
    Uri compressUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == RESULT_OK) {
            originUri = data.getData();
            mIvBefore.setImageURI(originUri);
        }
    }

    @OnClick({R.id.btn, R.id.btn1, R.id.btn2, R.id.iv_after, R.id.iv_before})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_before:
                Intent intent = new Intent(this, ImageActivity.class);
                intent.setData(originUri);
                startActivity(intent);
                break;
            case R.id.iv_after:
                Intent intent2 = new Intent(this, ImageActivity.class);
                intent2.setData(compressUri);
                startActivity(intent2);
                break;
            case R.id.btn:
                Intent pic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pic, 888);
                break;
            case R.id.btn1:
                luban();
                break;
            case R.id.btn2:
                compressor();
                break;
        }
    }

    String TAG = "aaaa";

    private void luban() {
        String path1 = getExternalCacheDir() + "/aaa";
        if (!new File(path1).exists()) {
            new File(path1).mkdir();
        }

        String fileName = path1 + "/okk.jpeg";
        File file = PathUtil.copyFileToPath(this, originUri, fileName);


        Luban.with(this)
                .load(file.getAbsoluteFile())
                .ignoreBy(100)
                .setTargetDir(path1)
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.i(TAG, "onSuccess: " + file.getAbsolutePath());
                        compressUri = Uri.parse(file.getAbsolutePath());
                        Glide.with(MainActivity.this)
                                .load(file)
                                .into(mIvAfter);
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e);
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    private void compressor() {


    }
}