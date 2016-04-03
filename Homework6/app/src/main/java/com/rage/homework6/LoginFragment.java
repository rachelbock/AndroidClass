package com.rage.homework6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Fragment to handle user login.
 */
public class LoginFragment extends Fragment {

    private UserLocalDatabaseSQLiteHelper userLocalDatabaseSQLiteHelper;

    @Bind(R.id.login_fragment_edit_text)
    EditText userName;
    @Bind(R.id.login_fragment_globe_image)
    ImageView globeImage;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        Picasso.with(getActivity()).load(R.drawable.globe).fit().centerCrop().into(globeImage);

        userLocalDatabaseSQLiteHelper = UserLocalDatabaseSQLiteHelper.getInstance(getContext());

        return rootView;
    }


    @OnClick(R.id.login_fragment_add_user_button)
    public void onAddUserButtonClicked(Button button) {

        if (userName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter a user name", Toast.LENGTH_SHORT).show();
        }

        else {

            User user = new User(userName.getText().toString().trim());

            boolean userExists = doesUserExist(user);

            if (userExists) {
                Toast.makeText(getContext(), R.string.user_already_exists, Toast.LENGTH_SHORT).show();
            } else {
                userLocalDatabaseSQLiteHelper.insertUser(user);
                int id = userLocalDatabaseSQLiteHelper.getIdForUserWithName(user.getName());
                user.setIdNumber(id);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_frame_layout, MapTagFragment.newInstance(user));
                transaction.commit();
            }
        }
    }

    @OnClick(R.id.login_fragment_login_button)
    public void onLoginButtonClicked(Button button) {

        if (userName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please enter a user name", Toast.LENGTH_SHORT).show();
        }

        else {
            User user = new User(userName.getText().toString().trim());
            boolean userExists = doesUserExist(user);


            if (userExists) {
                int id = userLocalDatabaseSQLiteHelper.getIdForUserWithName(user.getName());
                user.setIdNumber(id);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_frame_layout, MapTagFragment.newInstance(user));
                transaction.commit();
            } else {
                Toast.makeText(getContext(), R.string.user_does_not_exist, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean doesUserExist(User user) {
        ArrayList<User> users = userLocalDatabaseSQLiteHelper.getAllUsers();
        boolean userExists = false;
        for (int i = 0; i < users.size(); i++) {
            User oneUser = users.get(i);
            String userName = oneUser.getName();
            if (userName.equals(user.getName())) {
                userExists = true;
            }
        }
        return userExists;
    }
}
