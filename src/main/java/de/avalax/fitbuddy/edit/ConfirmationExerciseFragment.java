package de.avalax.fitbuddy.edit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import de.avalax.fitbuddy.R;
import de.avalax.fitbuddy.UpdateableActivity;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class ConfirmationExerciseFragment extends RoboFragment {
    private EditableExercise editableExercise;

    @InjectView(R.id.exerciseNameEditText)
    EditText exerciseNameEditText;

    @InjectView(R.id.buttonCancel)
    Button buttonCancel;
    private UpdateableActivity updateableActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.edit_confirmation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateableActivity = (UpdateableActivity) getActivity();
        editableExercise = (EditableExercise)getArguments().getSerializable("editableExercise");
        exerciseNameEditText.setText(editableExercise.getName());
        if (editableExercise instanceof ExistingEditableExercise) {
            //TODO: extract to resources
            buttonCancel.setText("delete");
        }
        exerciseNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editableExercise.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateableActivity.notifyDataSetChanged();
            }
        });
    }
}
