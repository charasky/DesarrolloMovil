package org.lapoderosa.app.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import org.lapoderosa.app.model.Informacion;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {
    private Context context;
    private List<Informacion> informacionList;

    public InfoAdapter(Context context, List<Informacion> informacionList) {
        this.context = context;
        this.informacionList = informacionList;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_datoreport_row, viewGroup, false);
        return new InfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder infoViewHolder, int i) {
        infoViewHolder.tvTitleDato.setText(informacionList.get(i).getDato());
        infoViewHolder.etDato.setText(informacionList.get(i).getInformacion());
    }

    @Override
    public int getItemCount() {
        return informacionList.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitleDato;
        EditText etDato;
        ImageView ivSaveEdit, ivPenEdit;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleDato = itemView.findViewById(R.id.tvTitleDato);
            etDato = itemView.findViewById(R.id.etDato);
            ivSaveEdit = itemView.findViewById(R.id.ivSaveEdit);
            ivPenEdit = itemView.findViewById(R.id.ivPenEdit);

            ivPenEdit.setOnClickListener(this);
            ivSaveEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivSaveEdit:

                    break;

                case R.id.ivPenEdit:
                    etDato.setEnabled(true);
                    editOn();
                    break;
            }
        }

        public void editOn() {
            String et = etDato.getText().toString();
            ivPenEdit.setVisibility(View.GONE);

            etDato.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String algo;
                    algo = (s + " " + start + " " + before + " " + count);
                    if (s.length() > 1) {

                        Log.e("reponse", "menor" + et + " " + etDato.getText().toString());
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!String.valueOf(s).equals(et)) {
                        //imageView.setVisibility(View.VISIBLE);
                        ivSaveEdit.setVisibility(View.VISIBLE);
                    } else {
                        ivSaveEdit.setVisibility(View.GONE);
                        Log.e("reponse", "guardar" + "se fue :v");
                    }
                }
            });
        }

    }
}
