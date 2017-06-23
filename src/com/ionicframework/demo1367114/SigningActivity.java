package com.ionicframework.demo1367114;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class SigningActivity extends Activity {

    private ImageView iv;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private String qm_path;

    private String titleColor = "#a266c4";

    private void title() {
        LinearLayout ll = (LinearLayout) findViewById(com.lisn.signaturelibrary.R.id.ll_title);
        ImageView iv = (ImageView) findViewById(com.lisn.signaturelibrary.R.id.back);
        TextView tv = (TextView) findViewById(com.lisn.signaturelibrary.R.id.tv_title);
        tv.setText("电子签名");
        String sColor = getIntent().getStringExtra("titleColor");
        if (!TextUtils.isEmpty(sColor)) {
            titleColor = sColor;
        }
        ll.setBackgroundColor(Color.parseColor(titleColor));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentd=new Intent();
//                intentd.putExtra("qm_path", "null");
//                setResult(QMrequestCode,intentd);
                finish();
            }
        });

        ImageView iv_info = (ImageView) findViewById(com.lisn.signaturelibrary.R.id.iv_info);
        iv_info.setVisibility(View.GONE);
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private int flag88 = 88;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == flag88) {
                int width = iv.getWidth();
                int height = iv.getHeight();
                baseBitmap = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);

                canvas = new Canvas(baseBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.lisn.signaturelibrary.R.layout.activity_signing);
        title();
        iv = (ImageView) findViewById(com.lisn.signaturelibrary.R.id.iv);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);// 设置外边缘
        paint.setStrokeCap(Paint.Cap.ROUND);// 形状
        paint.setStrokeWidth(8);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        handler.sendEmptyMessageDelayed(flag88, 500);
        iv.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        canvas.drawPoint(startX, startY, paint);
                        iv.setImageBitmap(baseBitmap);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int newX = (int) event.getX();
                        int newY = (int) event.getY();
                        canvas.drawLine(startX, startY, newX, newY, paint);
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        iv.setImageBitmap(baseBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }
    public void clear(View view) {
        canvas.drawColor(Color.WHITE);
        iv.setImageBitmap(baseBitmap);
    }
    private int QMrequestCode=1888;
    public void save(View view) {
        try {
            String path = this.getFilesDir().getPath();
            File file1 = new File(String.valueOf(Environment.getExternalStorageDirectory()));
//            File file1 = new File(path + File.separator + "Qm");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            String child = System.currentTimeMillis() + ".jpg";
            qm_path=(file1.getAbsolutePath()+File.separator+ child);
            File file = new File(file1, child);

            FileOutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            Toast.makeText(this, "签名保存成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
//            intent.setAction(intent.ACTION_MEDIA_MOUNTED);
            intent.setAction(intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(Environment
                    .getExternalStorageDirectory()));
            sendBroadcast(intent);

            Intent intentd=new Intent();
            intentd.putExtra("qm_path", qm_path);
            setResult(QMrequestCode,intentd);
//            setResult(RESULT_OK);
            Log.e("签名保存成功", "save: ");
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "签名保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e("000000", "save: " + e);
        }
    }

}
