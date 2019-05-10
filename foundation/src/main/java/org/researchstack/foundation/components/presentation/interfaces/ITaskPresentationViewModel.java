package org.researchstack.foundation.components.presentation.interfaces;

import androidx.lifecycle.LiveData;

import org.researchstack.foundation.components.presentation.livedata.TaskNavigatorState;
import org.researchstack.foundation.core.interfaces.IResult;
import org.researchstack.foundation.core.interfaces.IStep;

public interface ITaskPresentationViewModel<S extends IStep, R extends IResult> {

    void addStepResult(IResult stepResult);

    void goForward();

    void goBackward();

    LiveData<TaskNavigatorState<S>> getTaskNavigatorState();
}
