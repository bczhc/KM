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
import java.io.ByteArrayOutputStream;
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
    private int[] Fs, FsS_i;
    private String ESD, MP;
    private TextView tv;

    {
        try {
            ESD = Environment.getExternalStorageDirectory().getCanonicalPath();
            MP = ESD + "/mNote";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            File[] ds = {
                    new File(MP),
                    new File(MP + "/t"),
                    new File(MP + "/hR")
            };
            for (File d : ds) {
                if (!d.exists()) {
                    if (!d.mkdir()) {
                        Toast.makeText(this, "mkdirError", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            mA();
            tv = findViewById(R.id.textView);
            firstShow(tv);
        }
        System.out.println("ESD = " + ESD);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            System.out.println("Arrays.toString(grantResults) = " + Arrays.toString(grantResults));
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Alert(MainActivity.this);
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

        ImageView iv = findViewById(R.id.imgV);
        iv.setImageResource(R.raw.r);
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
                sbmB.setOnClickListener(v12 -> {
                    File iF = new File(MP + "/i");
                    int i = 0;
                    try {
                        File d = new File(MP + "/");
                        if (!d.exists()) System.out.println(d.mkdir());
                        if (!iF.exists()) {
                            System.out.println("iF.createNewFile() = " + iF.createNewFile());
                        } else {
//                            setFs(true);
                            InputStream iIs = new FileInputStream(iF);
                            InputStreamReader iIsr = new InputStreamReader(iIs, "GBK");
                            BufferedReader iBr = new BufferedReader(iIsr);
                            i = Integer.parseInt(iBr.readLine());
                            iBr.close();
                            iIs.close();
                            iIsr.close();
                        }
                        File f = new File(MP + "/t/" + i + ".n");
                        System.out.println(f.createNewFile());
                        OutputStream os = new FileOutputStream(f, false);
                        os.write(new byte[]{-2});
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
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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
            try {
                File f = tvAc(tv);
                wtFFB(f, (byte) 1);
                mvAc(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        m2.setOnClickListener(v -> {
            try {
                File f = tvAc(tv);
                wtFFB(f, (byte) 2);
                mvAc(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        m3.setOnClickListener(v -> {
            try {
                File f = tvAc(tv);
                wtFFB(f, (byte) 3);
                mvAc(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    private File tvAc(TextView textView) {
        if (Fs.length == 0) {
            File[] hR_Fs = new File(MP + "/hR/").listFiles();

        }
        List<Integer> hvT = new ArrayList<>();
        int exceptI = -1;
        File f = null;
        try {
            setFs(false);
            setFsS_i();
            while (true) {
                int rI = getRI_F(exceptI);
                int i = fNInArr_fI(FsS_i, rI);
                if (i != -1) {
                    f = new File(MP + "/t/" + Fs[i] + ".n");
                    textView.setText(getFCtt(f).toString());
                    break;
                } else {
                    exceptI = rI;
                    hvT.add(rI);
                    if (hvT.size() >= 3) break;
                }
            }
            /*
            for (int i = 0; i < Fs.length; i++) {
                if (rI == FsS_i[i]) {
                    b = true;
                    textView.setText(getFCtt(new File(MP + "/t/" + i + ".n")).toString());
                    break;
                }
            }
            if (!b) {

            }*/
            /*for (int i : Fs) {
                File f = new File(MP + "/t/" + i + ".n");
                int currFI = getFF(f);
                FileInputStream fis = new FileInputStream(f);
                System.out.println("fis.skip(1L) = " + fis.skip(1L));
                if (rI == currFI) {
                    InputStreamReader isr = new InputStreamReader(fis, "GBK");
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String r;
                    if ((r = br.readLine()) != null) {
                        sb.append(r).append("\n");
                    }
                    textView.setText(sb.toString());
                    break;
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return f;
    }

    private void firstShow(TextView textView) {
        /*int max = setFs().length;
        File f = new File(Environment.getExternalStorageDirectory() + "/mNote/" + Fs[Random.ran_sc(0, max - 1)]);
        try {
            textView.setText(u_File.o.fileOpen_String_OneLine(f, "GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if (setFs(true).length != 0) {
            try {
                FileInputStream fis = new FileInputStream(MP + "/t/" + Random.ran_sc(0, Fs.length - 1) + ".n");
                BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GBK"));
                StringBuilder sb = new StringBuilder();
                String r = br.readLine();
                while (r != null) {
                    sb.append(r).append("\n");
                    r = br.readLine();
                }
                textView.setText(sb.toString());
                br.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
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

    private void Alert(Context ctx) {
        new AlertDialog.Builder(ctx).setTitle("需要申请存储权限");
    }

    private void scanNumFile_i() {
        File f = new File(MP + "/i");
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
                            String fileName = file.getName();
                            if (ckF(file)) {
                                try {
                                    System.out.println("file.getCanonicalPath() = " + file.getCanonicalPath());
                                    l.add(Integer.parseInt(fileName.split("\\.")[0]));
                                    ++cNF[0];
                                    {
                                        System.out.println("fileName = " + fileName);
                                        System.out.println("fileName.split(\"\\\\.\")[0] = " + fileName.split("\\.")[0]);
                                        System.out.println("u_File.o.getFileExtension(file) = " + u_File.o.getFileExtension(file));
                                    }
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

    private int[] setFs(boolean wF) {
        File p = new File(MP + "/");
        final List<Integer> l = new ArrayList<>();
        new u_File.TraversalFile(p).Do(new u_File.TraversalFileDo() {
            @Override
            public void f(File file) {
                String fileName = file.getName();
                if (ckF(file)) {
                    l.add(Integer.parseInt(fileName.split("\\.")[0]));
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
        if (wF) {
            try {
                File f = new File(MP + "/i");
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, false), "GBK"));
                OutputStream os = new FileOutputStream(f, false);
                OutputStreamWriter osw = new OutputStreamWriter(os, "GBK");
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(String.valueOf(Fs.length));
                bw.flush();
                System.out.println("Fs.length = " + Fs.length);
                bw.close();
                osw.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        return Fs;
    }

    private int getRI_F(int non) {
        int r;
        while (true) {
            int rR = Random.ran_sc(1, 6);
            r = (rR == 1) ? 1 : ((rR == 2 || rR == 3) ? 2 : 3);
            if (r != non) return r;
        }
    }

    /*private int m2FC() {
        File d = new File(ESD + "/t/");
        int c = 0;
        try {
            File[] listFiles = d.listFiles();
            for (File file : listFiles) {
                String fileName = file.getName();
                if (ckF(file)) {
                    ++c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            return 0;
        }
        return c;
    }*/

    private boolean ckF(File file) {
        String fileName = file.getName();
        return new IsNum().isNum(fileName.split("\\.")[0]) && u_File.o.getFileExtension(file).equals("n");
    }

    private int getFF(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        byte[] b = new byte[1];
        System.out.println("is.read(b) = " + is.read(b));
        is.close();
        return -b[0];
    }

    private void setFsS_i() {
        setFs(false);
        FsS_i = new int[Fs.length];
        for (int i = 0; i < Fs.length; i++) {
            try {
                FsS_i[i] = getFF(new File(MP + "/t/" + Fs[i] + ".n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private StringBuilder getFCtt(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        System.out.println("is.skip(1L) = " + is.skip(1L));
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
        String r;
        while ((r = br.readLine()) != null) {
            sb.append(r).append("\n");
        }
        is.close();
        return sb;
    }

    private int fNInArr_fI(int[] ints, int i) {
        for (int j = 0; j < ints.length; j++) {
            if (i == ints[j]) return j;
        }
        return -1;
    }

    private void wtFFB(File f, byte b) throws IOException {
        if (f == null) return;
        InputStream is = new FileInputStream(f);
        System.out.println("is.skip(1L) = " + is.skip(1L));
        @SuppressWarnings("SpellCheckingInspection") ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(new byte[]{b});
        baos.flush();
        u_File.StreamWrite(is, baos);
        OutputStream os = new FileOutputStream(f, false);
        u_File.StreamWrite(u_File.StreamParse(baos), os);
        is.close();
    }

    private void mvAc(File f) {
        try {
            u_File.FileCopy(f, new File(MP + "/hR/" + f.getName().split("\\.")[0]));
            if (!f.delete())
                Toast.makeText(this, "move false", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "move " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}