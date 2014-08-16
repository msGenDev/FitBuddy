package de.avalax.fitbuddy.presentation.edit.exercise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.avalax.fitbuddy.domain.model.exercise.Exercise;
import de.avalax.fitbuddy.presentation.FitbuddyApplication;
import de.avalax.fitbuddy.presentation.R;
import de.avalax.fitbuddy.presentation.dialog.EditNameDialogFragment;
import de.avalax.fitbuddy.presentation.dialog.EditRepsDialogFragment;
import de.avalax.fitbuddy.presentation.dialog.EditSetsDialogFragment;
import de.avalax.fitbuddy.presentation.dialog.EditWeightDialogFragment;
import de.avalax.fitbuddy.presentation.helper.ExerciseViewHelper;

import javax.inject.Inject;

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

    @Inject
    ExerciseViewHelper exerciseViewHelper;

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
        ((FitbuddyApplication) getActivity().getApplication()).inject(this);
        this.exercise = (Exercise) getArguments().getSerializable(ARGS_EXERCISE);
        init();
        return view;
    }

    protected void init() {
        exerciseNameEditText.setText(exerciseViewHelper.nameOfExercise(exercise));
        exerciseWeightExitText.setText(exerciseViewHelper.weightOfExercise(exercise));
        exerciseRepsTextView.setText(String.valueOf(exerciseViewHelper.maxRepsOfExercise(exercise)));
        exerciseSetsTextView.setText(String.valueOf(exerciseViewHelper.setCountOfExercise(exercise)));
    }



    @OnClick(R.id.exerciseName)
    protected void changeName() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        String name = exercise.getName();
        String hint = getResources().getString(R.string.new_exercise_name);
        EditNameDialogFragment.newInstance(name, hint).show(fm, "fragment_edit_name");
    }


    @OnClick(R.id.exerciseWeight)
    protected void changeWeight() {
        if (exercise.getSets().isEmpty()) {
            return;
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        double weight = exercise.getCurrentSet().getWeight();
        EditWeightDialogFragment.newInstance(weight).show(fm, "fragment_edit_weight");
    }

    @OnClick(R.id.exerciseSets)
    protected void changeSets() {
        //TODO: better fallback
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int sets = exercise.getSets().size();
        EditSetsDialogFragment.newInstance(sets).show(fm, "fragment_edit_sets");
    }

    @OnClick(R.id.exerciseReps)
    protected void changeReps() {
        //TODO: better fallback
        if (exercise.getSets().isEmpty()) {
            return;
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int reps = exercise.getCurrentSet().getMaxReps();
        EditRepsDialogFragment.newInstance(reps).show(fm, "fragment_edit_reps");
    }
}
