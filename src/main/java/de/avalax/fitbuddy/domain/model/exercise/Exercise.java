package de.avalax.fitbuddy.domain.model.exercise;

import de.avalax.fitbuddy.domain.model.set.Set;
import de.avalax.fitbuddy.domain.model.set.SetNotFoundException;

import java.io.Serializable;

public interface Exercise extends Serializable {
    String getName();

    int indexOfCurrentSet() throws SetNotFoundException;

    double getProgress() throws SetNotFoundException;

    void setCurrentSet(int index) throws SetNotFoundException;

    Set setAtPosition(int index) throws SetNotFoundException;

    void setExerciseId(ExerciseId exerciseId);

    int countOfSets();

    ExerciseId getExerciseId();

    void setName(String name);

    Set createSet();

    void removeSet(Set set);

    Iterable<Set> setsOfExercise();
}
