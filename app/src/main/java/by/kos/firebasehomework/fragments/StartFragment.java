package by.kos.firebasehomework.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import by.kos.firebasehomework.R;
import by.kos.firebasehomework.adapters.UserAdapter;
import by.kos.firebasehomework.databinding.FragmentStartBinding;
import by.kos.firebasehomework.model.User;

public class StartFragment extends Fragment {
    private FragmentStartBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        db = FirebaseFirestore.getInstance();
        navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        userAdapter = new UserAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(userAdapter);

        binding.floatingActionButton.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_addUserFragment));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    List<User> users = value.toObjects(User.class);
                    userAdapter.setUsers(users);
                    userAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}