package org.lapoderosa.app.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder> {

    private Context context;
    private List<Reporte> listaUsuarios;

    public AdaptadorUsuarios(Context context, List<Reporte> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_usuario, viewGroup, false);
        return new UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder usuarioViewHolder, int i) {
        usuarioViewHolder.tvLugar.setText(listaUsuarios.get(i).getPais());
        usuarioViewHolder.tvCiudad.setText(listaUsuarios.get(i).getCiudad());
        usuarioViewHolder.tvName.setText(listaUsuarios.get(i).getFullName());
        usuarioViewHolder.tvDate.setText(listaUsuarios.get(i).getFecha());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvLugar, tvCiudad, tvName, tvDate;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvCiudad = itemView.findViewById(R.id.tvCiudad);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    public void filtrar(ArrayList<Reporte> filtroUsuarios) {
        this.listaUsuarios = filtroUsuarios;
        notifyDataSetChanged();
    }
}
