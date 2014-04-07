package de.avalax.fitbuddy.core.workout.basic;


import de.avalax.fitbuddy.core.workout.Exercise;
import de.avalax.fitbuddy.core.workout.Set;
import de.avalax.fitbuddy.core.workout.Tendency;
import de.avalax.fitbuddy.core.workout.exceptions.SetNotAvailableException;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

@RunWith(HierarchicalContextRunner.class)
public class BasicExerciseTest {

    Exercise exercise;
    List<Set> sets;

    @Before
    public void setUp() throws Exception {
        sets = new ArrayList<>();
        exercise = new BasicExercise("Bankdrücken", sets, 2.5);
    }

    @Test
    public void getName_shouldReturnNameBankdrücken() throws Exception {
        assertThat(exercise.getName(), equalTo("Bankdrücken"));
    }

    @Test
    public void getMaxSets_shouldReturnNumberOfSets() throws Exception {
        sets.add(mock(Set.class));
        sets.add(mock(Set.class));
        sets.add(mock(Set.class));
        sets.add(mock(Set.class));

        assertThat(exercise.getMaxSets(), equalTo(4));
    }

    @Test
    public void getMaxReps_shouldReturnMaxRepsFromCurrentSet() throws Exception {
        Set set = mock(Set.class);
        int maxReps = 12;
        when(set.getMaxReps()).thenReturn(maxReps);
        sets.add(set);

        assertThat(exercise.getMaxReps(), equalTo(maxReps));
    }

    @Test
    public void getTendency_shouldReturnTendencyNeutralOnCreation() throws Exception {
        assertThat(exercise.getTendency(), equalTo(Tendency.NEUTRAL));
    }

    @Test
    public void getTendency_shouldReturnTendencyPlus() throws Exception {
        exercise.setTendency(Tendency.PLUS);

        assertThat(exercise.getTendency(), equalTo(Tendency.PLUS));
    }

    @Test
    public void getReps_shouldReturnRepsFromSet() throws Exception {
        Set set = mock(Set.class);
        sets.add(set);
        when(set.getReps()).thenReturn(12);

        assertThat(exercise.getReps(), equalTo(12));
    }

    @Test
    public void getCurrentSet_shouldReturnCurrentSetOnStartup() throws Exception {
        Set set = mock(Set.class);
        sets.add(set);

        assertThat(exercise.getCurrentSet(), equalTo(set));
    }

    @Test
    public void getCurrentSet_shouldReturnSecondSet() throws Exception {
        Set set = mock(Set.class);

        sets.add(mock(Set.class));
        sets.add(set);

        exercise.setCurrentSet(2);

        assertThat(exercise.getCurrentSet(), equalTo(set));
    }

    @Test
    public void setSetNumber_shouldSetSetNumberToZero() throws Exception {
        sets.add(mock(Set.class));

        exercise.setCurrentSet(-1);

        assertThat(exercise.getSetNumber(), equalTo(1));
    }

    @Test
    public void setCurrentSet_ShouldSetToLastSetWhenSizeExceeded() throws Exception {
        sets.add(mock(Set.class));

        exercise.setCurrentSet(sets.size() + 1);
    }

    @Test
    public void incrementCurrentSet_shouldIncrementToTheNextSet() throws Exception {
        Set set = mock(Set.class);

        sets.add(mock(Set.class));
        sets.add(set);

        exercise.incrementCurrentSet();

        assertThat(exercise.getCurrentSet(), equalTo(set));
        assertThat(exercise.getSetNumber(), equalTo(2));
    }

    @Test
    public void setReps_shouldCallSetRepsForCurrentSet() throws Exception {
        Set set = mock(Set.class);
        sets.add(set);

        exercise.setReps(12);

        verify(set).setReps(12);
    }

    @Test
    public void getWeight_shouldReturnWeightOfCurrentSet() throws Exception {
        Set set = mock(Set.class);
        sets.add(set);

        when(set.getWeight()).thenReturn(50.0);

        assertThat(exercise.getWeight(), equalTo(50.0));
    }

    @Test
    public void getWeightRaise_shouldGetWeightRaise() throws Exception {
        exercise = new BasicExercise("NeutralTendency", sets, 5.0);

        assertThat(exercise.getWeightRaise(), equalTo(5.0));
    }

    @Test
    public void getWeightRaise_shouldGetWeightRaiseForNeutralTendency() throws Exception {
        exercise = new BasicExercise("NeutralTendency", sets, 5.0);
        Set set = mock(Set.class);
        sets.add(set);

        when(set.getWeight()).thenReturn(2.5);

        assertThat(exercise.getWeightRaise(Tendency.NEUTRAL), equalTo(2.5));
    }

    @Test
    public void getWeightRaise_shouldGetWeightRaiseForPositiveTendency() {
        exercise = createExercise(2.5, 5.0);

        assertThat(exercise.getWeightRaise(Tendency.PLUS), equalTo(7.5));
    }

    @Test
    public void getWeightRaise_shouldGetWeightRaiseForMinusTendency() {
        exercise = createExercise(15.0, 5.0);

        assertThat(exercise.getWeightRaise(Tendency.MINUS), equalTo(10.0));
    }

    @Test
    public void getWeightRaise_shouldGetWeightRaiseForMinusTendencyWhenRaiseWouldBeNegative() {
        exercise = createExercise(2.5, 5.0);

        assertThat(exercise.getWeightRaise(Tendency.MINUS), equalTo(0.0));
    }

    @Test(expected = SetNotAvailableException.class)
    public void getCurrentSet_shouldThrowSetNotFoundExceptionWhenNoSetsAvailable() throws Exception {
        exercise.getCurrentSet();
    }

    public class givenAnExerciseProgress {
        @Test(expected = SetNotAvailableException.class)
        public void withoutSets_shouldHaveZeroProgress() throws Exception {
            exercise.getProgress();
        }

        @Test
        public void oneSetWithoutReps_shouldHaveZeroProgress() throws Exception {
            Set set = mock(Set.class);
            when(set.getMaxReps()).thenReturn(100);
            when(set.getReps()).thenReturn(0);
            sets.add(set);
            assertThat(exercise.getProgress(), equalTo(0.0));
        }

        @Test
        public void oneSetWithMaxReps_shouldHaveFullProgress() throws Exception {
            Set set = mock(Set.class);
            when(set.getMaxReps()).thenReturn(100);
            when(set.getReps()).thenReturn(100);
            sets.add(set);
            assertThat(exercise.getProgress(), equalTo(1.0));
        }

        @Test
        public void oneSetWithMaxReps_shouldHaveHalfProgress() throws Exception {
            Set set = mock(Set.class);
            when(set.getMaxReps()).thenReturn(100);
            when(set.getReps()).thenReturn(50);
            sets.add(set);
            assertThat(exercise.getProgress(), equalTo(0.5));
        }

        @Test
        public void twoSetsWithoutReps_shouldHaveHalfProgress() throws Exception {
            sets.add(mock(Set.class));
            Set set = mock(Set.class);
            when(set.getMaxReps()).thenReturn(100);
            when(set.getReps()).thenReturn(0);
            sets.add(set);
            exercise.setCurrentSet(2);
            assertThat(exercise.getProgress(), equalTo(0.5));
        }

        @Test
        public void twoSetsWithMaxReps_shouldHaveFallProgress() throws Exception {
            sets.add(mock(Set.class));
            Set set = mock(Set.class);
            when(set.getMaxReps()).thenReturn(100);
            when(set.getReps()).thenReturn(100);
            sets.add(set);
            exercise.setCurrentSet(2);
            assertThat(exercise.getProgress(), equalTo(1.0));
        }

        @Test
        public void twoSetsWithHalfReps_shouldHave75Progress() throws Exception {
            sets.add(mock(Set.class));
            Set set = mock(Set.class);
            when(set.getMaxReps()).thenReturn(100);
            when(set.getReps()).thenReturn(50);
            sets.add(set);
            exercise.setCurrentSet(2);
            assertThat(exercise.getProgress(), equalTo(0.75));
        }
    }

    private Exercise createExercise(double weight, double weightRaise) {
        Exercise exercise = new BasicExercise("MinusTendency", sets, weightRaise);
        Set set = mock(Set.class);
        sets.add(set);

        when(set.getWeight()).thenReturn(weight);

        return exercise;
    }
}
