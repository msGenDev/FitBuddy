package de.avalax.fitbuddy.core.workout;

import java.io.Serializable;
import java.util.List;

public interface Workout  extends Serializable {
    Exercise getExercise(int index);
    int getExerciseCount();
    int getReps(int index);
    void setReps(int index, int reps);
    void setTendency(int index, Tendency tendency);
    void incrementSet(int index);
    Set getCurrentSet(int index);
    void setName(String name);
    String getName();
    double getProgress(int index);
    //TODO: move to WorkoutDAO
    void addExerciseBefore(int index, Exercise exercise);
    void addExerciseAfter(int index, Exercise exercise);
    void setExercise(int index, Exercise exercise);
    void removeExercise(int index);
    void addExercise(Exercise exercise);
    List<Exercise> getExercises();
}
