package by.kos.firebasehomework.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import by.kos.firebasehomework.MainActivity;
import by.kos.firebasehomework.R;
import by.kos.firebasehomework.databinding.FragmentAddUserBinding;
import by.kos.firebasehomework.model.User;

public class AddUserFragment extends Fragment {
    private FragmentAddUserBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sex, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSex.setSelection(0);
        binding.spinnerSex.setAdapter(spinnerAdapter);
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });


        return view;
    }

    private void saveUser(){
        User user = new User();
        user.setfName(binding.editTextFName.getText().toString().trim());
        user.setlName(binding.editTextLName.getText().toString().trim());
        user.setAge(Integer.parseInt(binding.editTextAge.getText().toString()));
        user.setSex(binding.spinnerSex.getSelectedItem().toString());
        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), R.string.success_add, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), R.string.fail_add, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}