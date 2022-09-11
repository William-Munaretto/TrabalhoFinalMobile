package com.trabalho3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.trabalho3.data.Contato;

public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private Contato contato;
    private OnDeleteListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja excluir o contato "  + contato.getNome() + "?");
        builder.setPositiveButton("Sim", this);
        builder.setNegativeButton("Não", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            listener.onDelete(contato);
        }
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof OnDeleteListener)) {
            throw new RuntimeException("A activity deve implementar DialogFragment.OnDeleteListener");
        }

        this.listener = (OnDeleteListener) context;
    }

    public interface OnDeleteListener {
        public void onDelete(Contato contato);
    }
}
