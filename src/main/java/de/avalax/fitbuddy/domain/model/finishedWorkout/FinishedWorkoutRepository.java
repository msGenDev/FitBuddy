package de.avalax.fitbuddy.domain.model.finishedWorkout;

import java.util.Collection;
import java.util.List;

import de.avalax.fitbuddy.domain.model.workout.Workout;

public interface FinishedWorkoutRepository {
    FinishedWorkoutId saveWorkout(Workout workout);

    FinishedWorkout load(FinishedWorkoutId finishedWorkoutId) throws FinishedWorkoutNotFoundException;

    List<FinishedWorkout> loadAll();
}
