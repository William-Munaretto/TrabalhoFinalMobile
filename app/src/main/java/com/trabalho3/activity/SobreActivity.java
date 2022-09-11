package com.trabalho3.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.trabalho3.R;

public class SobreActivity extends AppCompatActivity {


        private ImageView imagem;
        private TextView versao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        imagem = findViewById(R.id.imagem);
        versao = findViewById(R.id.versao);

        imagem.setImageResource(R.drawable.eu);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        float verCode = pInfo.versionCode;
        versao.setText(String.valueOf(verCode));
    }
}
