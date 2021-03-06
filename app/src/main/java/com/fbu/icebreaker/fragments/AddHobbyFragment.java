package com.fbu.icebreaker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.adapters.MultiSelectionAdapter;
import com.fbu.icebreaker.subclasses.Hobby;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHobbyFragment extends DialogFragment {

    private static final String TAG = "AddHobbyFragment";

    private MultiSelectionAdapter adapter;
    private List<Hobby> allHobbies;
    private FloatingActionButton btnAdd;
    private Button btnCreateNewHobby;
    private RecyclerView rvHobbySelector;

    public AddHobbyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_hobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getDialog()).getWindow().setLayout(getResources().getDisplayMetrics().widthPixels - 30, getResources().getDisplayMetrics().heightPixels - 30);

        // Get field from view
        btnAdd = view.findViewById(R.id.btnAdd);
        btnCreateNewHobby = view.findViewById(R.id.btnCreateNewHobby);
        rvHobbySelector = view.findViewById(R.id.rvHobbySelector);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHobbySelector.setLayoutManager(layoutManager);

        allHobbies = new ArrayList<>();
        adapter = new MultiSelectionAdapter(getContext(), allHobbies);
        rvHobbySelector.setAdapter(adapter);

        queryHobbies();

        btnAdd.setOnClickListener(v -> {
            if (adapter.getSelected().size() > 0) {

                for (int i = 0; i < adapter.getSelected().size(); i++) {

                    adapter.getSelected().get(i).getRelation("usersWithHobby").add(ParseUser.getCurrentUser());
                    adapter.getSelected().get(i).saveInBackground(e -> {
                        if (e != null) {
                            Log.e(TAG, "Error while saving hobby.", e);
                            Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "Hobby save successful");
                    });
                }
            }
            Objects.requireNonNull(getDialog()).dismiss();
        });

        btnCreateNewHobby.setOnClickListener(v -> createHobbyFragment());
    }

    private void queryHobbies() {

        // TODO -- This currently gets all hobbies, it eventually has to only include hobbies the user doesn't already have
        // Specify data to query
        ParseQuery<Hobby> query = ParseQuery.getQuery(Hobby.class);
        query.findInBackground((hobbies, e) -> {
            // Check for errors
            if (e != null) {
                Log.e(TAG, "Issue with getting hobbies.", e);
            }

            for (Hobby hobby : hobbies) {
                Log.i(TAG, "Hobby: " + hobby.getName());
            }
            Log.i(TAG, "Gets here" + hobbies);
            allHobbies.addAll(hobbies);
            adapter.notifyDataSetChanged();
        });
    }

    private void queryHobbiesUpdate() {
        // Specify data to query
        ParseQuery<Hobby> query = ParseQuery.getQuery(Hobby.class);
        query.findInBackground((hobbies, e) -> {
            // Check for errors
            if (e != null) {
                Log.e(TAG, "Issue with getting hobbies.", e);
            }

            allHobbies.clear();
            adapter.notifyDataSetChanged();

            for (Hobby hobby : hobbies) {
                Log.i(TAG, "Hobby: " + hobby.getName());
            }
            Log.i(TAG, "Gets here" + hobbies);
            allHobbies.addAll(hobbies);
            adapter.notifyDataSetChanged();
        });
    }

    private void createHobbyFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateNewHobby createNewHobby = new CreateNewHobby();
        assert fragmentManager != null;
        createNewHobby.show(fragmentManager, "createNewHobby");

        fragmentManager.executePendingTransactions();
        Objects.requireNonNull(createNewHobby.getDialog()).setOnDismissListener(dialog -> queryHobbiesUpdate());
    }
}