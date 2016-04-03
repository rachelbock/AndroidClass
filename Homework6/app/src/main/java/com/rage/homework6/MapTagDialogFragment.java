package com.rage.homework6;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Dialog Fragment to collect title and description for map tag.
 */
public class MapTagDialogFragment extends DialogFragment {

//TODO: Will need to get the user so the user ID can be set


    @Bind(R.id.dialog_fragment_description_edit_text)
    EditText descriptionEditText;
    @Bind(R.id.dialog_fragment_title_edit_text)
    EditText titleEditText;

    MapTagFragment mapTagFragment;

    public MapTagDialogFragment() {
        // Required empty public constructor
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mapTagFragment = (MapTagFragment) getTargetFragment();
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_map_tag_dialog, null);

        ButterKnife.bind(this, rootView);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(rootView)
                .setTitle(getString(R.string.location_information))
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = titleEditText.getText().toString();
                        String description = descriptionEditText.getText().toString();

                        mapTagFragment.onPositiveButtonClicked(title, description);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        return dialog;
    }

}
