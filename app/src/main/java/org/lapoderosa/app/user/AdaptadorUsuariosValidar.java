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

public class AdaptadorUsuariosValidar extends RecyclerView.Adapter<AdaptadorUsuariosValidar.UserViewHolder>  {
    private Context context;
    private List<Usuario> listaUsuariosValidar;

    public AdaptadorUsuariosValidar(Context context, List<Usuario> listaUsuariosValidar) {
        this.context = context;
        this.listaUsuariosValidar = listaUsuariosValidar;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_usuario_validar, viewGroup, false);
        return new AdaptadorUsuariosValidar.UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.user.setText(listaUsuariosValidar.get(i).getUsuario());
        userViewHolder.fullName.setText(listaUsuariosValidar.get(i).getFullName());
        userViewHolder.asamblea.setText(listaUsuariosValidar.get(i).getAsamblea());
    }

    @Override
    public int getItemCount() {
        return listaUsuariosValidar.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView user, asamblea, fullName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.userEmail);
            fullName = itemView.findViewById(R.id.fullName);
            asamblea = itemView.findViewById(R.id.tipoAsamblea);
        }
    }

    public void filtrar(ArrayList<Usuario> filtroUsuarios) {
        this.listaUsuariosValidar = filtroUsuarios;
        notifyDataSetChanged();
    }
}
