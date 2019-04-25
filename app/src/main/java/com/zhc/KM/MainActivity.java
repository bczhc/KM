package com.zhc.KM;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhc.u.Random;
import com.zhc.u.common.IsNum;
import com.zhc.u.u_File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] Fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
            }, 0);
        } else {
            setContentView(R.layout.activity_main);
            firstShow(findViewById(R.id.textView));
            mA();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            System.out.println("Arrays.toString(grantResults) = " + Arrays.toString(grantResults));
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Alert(MainActivity.this, "需要申请存储权限");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                }, 1);
            } else {
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_main);
                mA();
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("ResourceType")
    private void mA() {
        File d0 = new File(Environment.getExternalStorageDirectory() + "/mNote/");
        if (!d0.exists()) System.out.println(d0.mkdir());
        ImageView iv = findViewById(R.id.imgV);
        iv.setImageResource(R.raw.r);
        TextView tv = findViewById(R.id.textView);
        tvAc(tv);
        Button btn1 = findViewById(R.id.btn1);
        iv.setOnClickListener(v_iv -> scanNumFile_i());
        btn1.setOnClickListener(v -> {
            setContentView(R.layout.wt);
            try {
                Button bkB = findViewById(R.id.bkB);
                bkB.setOnClickListener(v1 -> {
                    setContentView(R.layout.activity_main);
                    mA();
                });

                Button sbmB = findViewById(R.id.submitB);
                final EditText et = findViewById(R.id.editText);
                final File eP = Environment.getExternalStorageDirectory();
                sbmB.setOnClickListener(v12 -> {
                    File iF = new File(eP + "/mNote/i");
                    int i = 0;
                    try {
                        File d = new File(eP + "/mNote/");
                        if (!d.exists()) System.out.println(d.mkdir());
                        if (!iF.exists()) {
                            System.out.println(iF.createNewFile());
                        } else {
                            InputStream iIs = new FileInputStream(iF);
                            InputStreamReader iIsr = new InputStreamReader(iIs, "GBK");
                            BufferedReader iBr = new BufferedReader(iIsr);
                            i = Integer.parseInt(iBr.readLine());
                            iBr.close();
                            iIs.close();
                            iIsr.close();
                        }
                        File f = new File(eP + "/mNote/" + i);
                        System.out.println(f.createNewFile());
                        OutputStream os = new FileOutputStream(f);
                        BufferedWriter bw;
                        OutputStreamWriter osw = new OutputStreamWriter(os, "GBK");
                        bw = new BufferedWriter(osw);
                        String eS = et.getText().toString();
                        bw.write(eS);
                        bw.flush();
                        bw.close();
                        os.close();
                        OutputStream iOs = new FileOutputStream(iF);
                        OutputStreamWriter iOsw = new OutputStreamWriter(iOs, "GBK");
                        BufferedWriter iBw = new BufferedWriter(iOsw);
                        iBw.write(String.valueOf((i++) + 1));
                        iBw.flush();
                        iBw.close();
                        iOs.close();
                        iOsw.close();
                        Toast.makeText(MainActivity.this, "第" + i + "个笔记", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button m1 = findViewById(R.id.m1),
                m2 = findViewById(R.id.m2),
                m3 = findViewById(R.id.m3);
        m1.setOnClickListener(v -> {
        });
    }

    /*private void tBAc(TextView tv_) {
        try {
            int i = 0;
            try {
                File f = new File(Environment.getExternalStorageDirectory() + "/mNote/i");
                InputStream is = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(is, "GBK");
                BufferedReader br = new BufferedReader(isr);
                i = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
            int rI = ran(i - 1);
            File fO = new File(Environment.getExternalStorageDirectory() + "/mNote/" + rI);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fO), "GBK"
                    )
            );
            StringBuilder sb = new StringBuilder();
            String r = br.readLine();
            while (r != null) {
                sb.append(r);
                r = br.readLine();
            }
            r = sb.toString();
            tv_.setText(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    private void tvAc(TextView textView) {

    }

    private void firstShow(TextView textView) {
        int max = setFs().length;
        File f = new File(Environment.getExternalStorageDirectory() + "/mNote/" + Fs[Random.ran_sc(0, max - 1)]);
        try {
            textView.setText(u_File.o.fileOpen_String_OneLine(f, "GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void restartApplication(Context context) {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }*/

    private void Alert(Context ctx, String s) {
        new AlertDialog.Builder(ctx).setTitle(s);
    }

    private void scanNumFile_i() {
        File f = new File(Environment.getExternalStorageDirectory() + "/mNote/i");
        try {
            if (!f.exists()) System.out.println(f.createNewFile());
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            BufferedWriter bw = new BufferedWriter(osw);
            final List<Integer> l = new ArrayList<>();
            final int[] cNF = {0};
            new u_File.TraversalFile(f.getParentFile().getCanonicalFile()).Do(
                    new u_File.TraversalFileDo() {
                        @Override
                        public void f(File file) {
                            if (new IsNum().isNum(file.getName())) {
                                try {
                                    System.out.println("file.getCanonicalPath() = " + file.getCanonicalPath());
                                    l.add(Integer.parseInt(file.getName()));
                                    ++cNF[0];
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void d(File file) {

                        }

                        @Override
                        public void a(File file) {

                        }
                    }
            );
            bw.write(String.valueOf(cNF[0]));
            bw.flush();
            bw.close();
            osw.close();
            fos.close();
            Fs = new int[l.size()];
            for (int i = 0; i < l.size(); i++) {
                Fs[i] = Integer.parseInt(String.valueOf(l.get(i)));
            }
            System.out.println("Fs = " + Arrays.toString(Fs));
            Toast.makeText(this, "有" + cNF[0] + "条笔记", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int[] setFs() {
        File p = new File(Environment.getExternalStorageDirectory() + "/mNote/");
        final List<Integer> l = new ArrayList<>();
        new u_File.TraversalFile(p).Do(new u_File.TraversalFileDo() {
            @Override
            public void f(File file) {
                if (new IsNum().isNum(file.getName())) {
                    l.add(Integer.parseInt(file.getName()));
                }
            }

            @Override
            public void d(File file) {

            }

            @Override
            public void a(File file) {

            }
        });
        Fs = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            Fs[i] = Integer.parseInt(String.valueOf(l.get(i)));
        }

        return Fs;
    }

    public static void main(String[] args) {
    }
}