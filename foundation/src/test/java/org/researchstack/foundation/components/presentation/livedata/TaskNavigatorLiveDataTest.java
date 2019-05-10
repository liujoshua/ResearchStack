package org.researchstack.foundation.components.presentation.livedata;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.researchstack.foundation.components.presentation.interfaces.ITaskNavigator;
import org.researchstack.foundation.core.interfaces.IResult;
import org.researchstack.foundation.core.interfaces.IStep;
import org.researchstack.foundation.core.models.task.Task.TaskProgress;

public class TaskNavigatorLiveDataTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ITaskNavigator taskNavigator;

    @Mock
    private IStep initialStep;

    @Mock
    private IResult initialResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testInitialState() {

        IStep expectedPreviousStep = mock(IStep.class);
        when(taskNavigator.getStepBeforeStep(initialStep, initialResult))
                .thenReturn(expectedPreviousStep);

        IStep expectedNextStep = mock(IStep.class);
        when(taskNavigator.getStepAfterStep(initialStep, initialResult))
                .thenReturn(expectedNextStep);

        TaskProgress expectedTaskProgress = mock(TaskProgress.class);
        when(taskNavigator.getProgressOfCurrentStep(initialStep, initialResult))
                .thenReturn(expectedTaskProgress);

        TaskNavigatorLiveData taskNavigatorLiveData = new TaskNavigatorLiveData(initialStep, initialResult,
                taskNavigator);

        TaskNavigatorState taskNavigatorState = (TaskNavigatorState) taskNavigatorLiveData.getTaskNavigatorLiveData()
                .getValue();

        verify(taskNavigator);

        assertEquals(initialStep, taskNavigatorState.getCurrentStep());
        assertEquals(expectedNextStep, taskNavigatorState.getNextStep());
        assertEquals(expectedPreviousStep, taskNavigatorState.getPreviousStep());
        assertEquals(expectedTaskProgress, taskNavigatorState.getTaskProgress());

    }

}