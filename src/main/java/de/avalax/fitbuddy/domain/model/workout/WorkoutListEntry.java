package de.avalax.fitbuddy.domain.model.workout;

public class WorkoutListEntry {
    private final WorkoutId workoutId;
    private final String name;

    public WorkoutListEntry(WorkoutId workoutId, String name) {
        this.workoutId = workoutId;
        this.name = name;
    }

    public WorkoutId getWorkoutId() {
        return workoutId;
    }

    @Override
    public String toString() {
        //TODO: unnamed workout from resources & move to a ui helper
        if (name.length() == 0) {
            return "unnamed workout";
        }
        return name;
    }
}
