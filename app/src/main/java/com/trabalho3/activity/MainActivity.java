package com.trabalho3.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.trabalho3.R;
import com.trabalho3.adapter.ContatoAdapter;
import com.trabalho3.data.Contato;
import com.trabalho3.data.ContatoDao;
import com.trabalho3.dialog.DeleteDialog;

import java.util.List;

public class MainActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, DeleteDialog.OnDeleteListener {

    private ListView lista;
    private ContatoAdapter contatoAdapter;
    private ContatoDao contatoDao;
    private static final int REQ_EDIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista);

        contatoAdapter = new ContatoAdapter(this);

        lista.setAdapter(contatoAdapter);

        lista.setOnItemClickListener(this);
        lista.setOnItemLongClickListener(this);

        contatoDao = ContatoDao.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), EditarContatoActivity.class);
            startActivityForResult(intent, REQ_EDIT);
            return true;
        }else if(item.getItemId() == R.id.action_info){
            Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK) {
            updateList();
        }
    }

    private void updateList() {
        List<Contato> contatos = contatoDao.list();
        contatoAdapter.setItems(contatos);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        Intent intent = new Intent(getApplicationContext(), EditarContatoActivity.class);
        intent.putExtra("contato", contatoAdapter.getItem(i));
        startActivityForResult(intent, REQ_EDIT);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
        Contato contato = contatoAdapter.getItem(i);

        DeleteDialog dialog = new DeleteDialog();
        dialog.setContato(contato);
        dialog.show(getSupportFragmentManager(), "deleteDialog");
        return true;
    }

    @Override
    public void onDelete(Contato contato) {
        contatoDao.delete(contato);
        String msg = "Contato excluído!";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        updateList();
    }
}