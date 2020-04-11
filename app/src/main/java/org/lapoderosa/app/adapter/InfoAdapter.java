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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.lapoderosa.app.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.lapoderosa.app.model.Informacion;
import org.lapoderosa.app.util.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        infoViewHolder.tvTitleDato.setText(informacionList.get(i).getvName());
        infoViewHolder.etDato.setText(informacionList.get(i).getContenido());
        infoViewHolder.dbFila = informacionList.get(i).getDbName();
        infoViewHolder.dbtabla = informacionList.get(i).getDbTable();
        infoViewHolder.id = informacionList.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return informacionList.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        String dbtabla, dbFila,id;
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
                    etDato.setEnabled(false);
                    modificar();
                    editOff();
                    break;

                case R.id.ivPenEdit:
                    etDato.setEnabled(true);
                    editOn();
                    break;
            }
        }

        private void editOff() {
            ivSaveEdit.setVisibility(View.GONE);
            ivPenEdit.setVisibility(View.VISIBLE);
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
                    }
                }
            });
        }


        private void modificar() {
            String URL = context.getResources().getString(R.string.HOST) + context.getResources().getString(R.string.URL_EDIT_REPORT);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(context, jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    JSONObject jsonObjec = new JSONObject();
                    try {
                        jsonObjec.put("id", id);
                        jsonObjec.put("tabla", dbtabla);
                        jsonObjec.put("fila", dbFila);
                        jsonObjec.put("valor", etDato.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("info", jsonObjec.toString());
                    return parametros;
                }
            };
            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        }
    }
}
