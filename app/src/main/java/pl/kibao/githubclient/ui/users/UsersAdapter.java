package pl.kibao.githubclient.ui.users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.kibao.githubclient.data.User;
import pl.kibao.githubclient.databinding.OrganizationItemBinding;
import pl.kibao.githubclient.databinding.UserItemBinding;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> items = new ArrayList<>();
    private LayoutInflater inflater;
    private static final int TYPE_USER = 0;
    private static final int TYPE_ORGANIZATION = 1;

    @Inject
    public UsersAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ORGANIZATION) {
            return OrganizationViewHolder.create(inflater, parent);
        }
        return UserViewHolder.create(inflater, parent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isUser() ? TYPE_USER : TYPE_ORGANIZATION;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = items.get(position);
        if (holder.getItemViewType() == TYPE_ORGANIZATION) {
            ((OrganizationViewHolder) holder).bindTo(user);
        } else {
            ((UserViewHolder) holder).bindTo(user);
        }
    }

    public void updateItems(List<User> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private UserItemBinding binding;

        public UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(User user) {
            binding.setUser(user);
        }

        public static UserViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            UserItemBinding binding = UserItemBinding.inflate(inflater, parent, false);
            return new UserViewHolder(binding);
        }
    }

    static class OrganizationViewHolder extends RecyclerView.ViewHolder {
        private OrganizationItemBinding binding;

        public OrganizationViewHolder(OrganizationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(User user) {
            binding.setUser(user);
        }

        public static OrganizationViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            OrganizationItemBinding binding =
                OrganizationItemBinding.inflate(inflater, parent, false);
            return new OrganizationViewHolder(binding);
        }
    }
}
