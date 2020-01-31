package org.lapoderosa.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lapoderosa.app.R;

import org.lapoderosa.app.util.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>  {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_user_row, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
        User user = userList.get(position);

        userViewHolder.user.setText(user.getUsuario());
        userViewHolder.fullName.setText(user.getFullName());
        userViewHolder.asamblea.setText(user.getAsamblea());
    }

    @Override
    public int getItemCount() {
        return userList.size();
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

    public void filtrar(ArrayList<User> filtroUsers) {
        this.userList = filtroUsers;
        notifyDataSetChanged();
    }
}
