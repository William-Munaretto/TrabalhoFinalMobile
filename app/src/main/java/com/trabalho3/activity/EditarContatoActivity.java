package com.trabalho3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.trabalho3.R;
import com.trabalho3.data.Contato;
import com.trabalho3.data.ContatoDao;

public class EditarContatoActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtNome;
    private EditText edtTelefone;
    private Button btnProcessar;
    private Button btnCancelar;
    private Contato contato;
    private ContatoDao contatoDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contato);

        edtNome = findViewById(R.id.edt_nome);
        edtTelefone = findViewById(R.id.edt_Telefone);
        btnProcessar = findViewById(R.id.btnProcessar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnProcessar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        contatoDao = ContatoDao.getInstance(this);

        contato = (Contato) getIntent().getSerializableExtra("contato");

        if (contato != null){
            edtNome.setText(contato.getNome());
            edtTelefone.setText(contato.getTelefone());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProcessar){
            String nome = edtNome.getText().toString();
            String telefone = edtTelefone.getText().toString();
            String msg;

            if (contato == null) {
                Contato contato = new Contato(nome, telefone);
                contatoDao.save(contato);
                msg = "Contato gravado com ID = " + contato.getId();

            } else {
                contato.setNome(nome);
                contato.setTelefone(telefone);
                contatoDao.update(contato);
                msg = "Contato atualizado com ID = " + contato.getId();
            }

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
        else if (view.getId() == R.id.btnCancelar){
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
