package de.avalax.fitbuddy.app.manageWorkout.editExercise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.avalax.fitbuddy.app.R;
import de.avalax.fitbuddy.app.dialog.EditWeightDialogFragment;
import de.avalax.fitbuddy.core.workout.Exercise;

public class EditExerciseDialogFragment extends Fragment {

    private static final String ARGS_EXERCISE = "exercise";

    @InjectView(R.id.exerciseNameEditText)
    protected TextView exerciseNameEditText;

    @InjectView(R.id.exerciseWeightExitText)
    protected TextView exerciseWeightExitText;

    @InjectView(R.id.exerciseSetsTextView)
    protected TextView exerciseSetsTextView;

    @InjectView(R.id.exerciseRepsTextView)
    protected TextView exerciseRepsTextView;

    private Exercise exercise;

    public static EditExerciseDialogFragment newInstance(Exercise exercise) {
        EditExerciseDialogFragment fragment = new EditExerciseDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_edit, container, false);
        ButterKnife.inject(this, view);
        this.exercise = (Exercise) getArguments().getSerializable(ARGS_EXERCISE);
        init();
        return view;
    }

    protected void init() {
        exerciseNameEditText.setText(exercise.getName());
        exerciseWeightExitText.setText(String.valueOf(exercise.getWeight()));
        exerciseRepsTextView.setText(String.valueOf(exercise.getMaxReps()));
        exerciseSetsTextView.setText(String.valueOf(exercise.getMaxSets()));
    }

    @OnClick(R.id.exerciseName)
    protected void changeName() {
        Toast.makeText(getActivity(), "changeName", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.exerciseWeight)
    protected void changeWeight() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        double weight = exercise.getWeight();
        EditWeightDialogFragment.newInstance(weight).show(fm, "fragment_edit_name");
    }

    @OnClick(R.id.exerciseSets)
    protected void changeSets() {
        Toast.makeText(getActivity(), "changeSets", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.exerciseReps)
    protected void changeReps() {
        Toast.makeText(getActivity(), "changeReps", Toast.LENGTH_SHORT).show();
    }
}
