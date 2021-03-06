package com.fbu.icebreaker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.LoginActivity;
import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ImageView ivProfilePicture;

    private TextView tvBio;
    private TextView tvHobbiesNumber;
    private TextView tvUsername;

    private Button btnEditProfile;
    private Button btnLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvHobbiesNumber = view.findViewById(R.id.tvHobbiesNumber);
        tvBio = view.findViewById(R.id.tvBio);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        ParseUser user = ParseUser.getCurrentUser();

        tvUsername.setText(user.getUsername());
        tvBio.setText(user.getString("bio"));

        ParseQuery<Hobby> query = ParseQuery.getQuery(Hobby.class);

        try {
            tvHobbiesNumber.setText(String.valueOf(query.whereEqualTo("usersWithHobby", user).count()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(requireContext())
                .load(Objects.requireNonNull(user.getParseFile("profilePicture")).getUrl())
                .into(ivProfilePicture);

        btnEditProfile.setOnClickListener(v -> addEditProfileDialog());

        btnLogout.setOnClickListener(v -> {
            ParseUser.logOut();

            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);

            requireActivity().finish();
        });
    }

    private void addEditProfileDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        assert fragmentManager != null;
        editProfileFragment.show(fragmentManager, "editProfileFragment");

        fragmentManager.executePendingTransactions();
        Objects.requireNonNull(editProfileFragment.getDialog()).setOnDismissListener(dialog -> {
            tvUsername.setText(ParseUser.getCurrentUser().getUsername());
            tvBio.setText(ParseUser.getCurrentUser().getString("bio"));
            Glide.with(requireContext())
                    .load(Objects.requireNonNull(ParseUser.getCurrentUser().getParseFile("profilePicture")).getUrl())
                    .into(ivProfilePicture);
        });
    }
}