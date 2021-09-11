package by.kos.firebasehomework.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.kos.firebasehomework.model.User;
import by.kos.firebasehomework.databinding.UserItemBinding;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users;

    public UserAdapter() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        private UserItemBinding binding;

        public UserViewHolder(UserItemBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.textViewFName.setText(user.getfName());
        holder.binding.textViewLName.setText(user.getlName());
        holder.binding.textViewAge.setText(String.format("%s", user.getAge()));
        holder.binding.textViewSex.setText(user.getSex());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
