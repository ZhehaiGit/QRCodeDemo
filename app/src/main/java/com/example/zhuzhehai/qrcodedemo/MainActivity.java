package com.example.zhuzhehai.qrcodedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ((Button) findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content  = ((EditText)findViewById(R.id.text)).getText().toString();
                Log.v("shuru", content);

                QRCodeWriter writer = new QRCodeWriter();
                int width = 0;
                int height = 0;
                Bitmap bmp = null;
                try {
                    BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
                    width = bitMatrix.getWidth();
                    height = bitMatrix.getHeight();
                    bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    ((ImageView) findViewById(R.id.image)).setImageBitmap(bmp);

                } catch (WriterException e) {
                    e.printStackTrace();


                }

            }
        });
        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }

            }
        });


//        ImageView view = ((ImageView) findViewById(R.id.image));
//
//        Bitmap bitmap = view.getDrawingCache();
//
//
//        Bitmap bMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);;
//
//        String contents = null;
//
//        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
//        //copy pixel data from the Bitmap into the 'intArray' array
//        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
//
//        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//
//        Reader reader = new MultiFormatReader();// use this otherwise ChecksumException
//        try {
//            Result result = reader.decode(bitmap);
//            contents = result.getText();
//            ((EditText) findViewById(R.id.text)).setText(contents);
//            //byte[] rawBytes = result.getRawBytes();
//            //BarcodeFormat format = result.getBarcodeFormat();
//            //ResultPoint[] points = result.getResultPoints();
//        } catch (NotFoundException e) { e.printStackTrace(); }
//        catch (ChecksumException e) { e.printStackTrace(); }
//        catch (FormatException e) { e.printStackTrace(); }





//        Bitmap bMap = Bitmap.createBitmap(mTwod.width(), mTwod.height(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(mTwod, bMap);

//        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
//        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
//
//        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(),intArray);
//
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Reader reader = new DataMatrixReader();
//        try {
//            Result result = reader.decode(bitmap);
//            ((EditText) findViewById(R.id.text)).setText(result.getText());
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (ChecksumException e) {
//            e.printStackTrace();
//        } catch (FormatException e) {
//            e.printStackTrace();
//        }




//        if (data != null) {
//            Uri uri = data.getData();
//            ContentResolver cr = getContentResolver();
//            try {
//                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片
//
//                CodeUtils.analyzeBitmap(mBitmap, new CodeUtils.AnalyzeCallback() {
//                    @Override
//                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                        Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onAnalyzeFailed() {
//                        Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//                if (mBitmap != null) {
//                    mBitmap.recycle();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                ((EditText) findViewById(R.id.text)).setText(contents);
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
}
