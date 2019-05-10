package org.researchstack.foundation.components.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.researchstack.foundation.components.presentation.interfaces.ITaskNavigator;
import org.researchstack.foundation.components.presentation.interfaces.ITaskPresentationViewModel;
import org.researchstack.foundation.components.presentation.livedata.TaskNavigatorLiveData;
import org.researchstack.foundation.components.presentation.livedata.TaskNavigatorState;
import org.researchstack.foundation.core.interfaces.IResult;
import org.researchstack.foundation.core.interfaces.IStep;
import org.researchstack.foundation.core.models.result.TaskResult;

public class TaskPresentationViewModel<S extends IStep, R extends IResult> extends ViewModel
        implements ITaskPresentationViewModel {
    public static class Factory<S extends IStep, R extends IResult> implements ViewModelProvider.Factory {
        @NonNull
        private final String taskIdentifier;

        @NonNull
        private final ITaskNavigator<S, R> taskNavigator;

        @Nullable
        private final S currentStep;

        @NonNull
        final R currentResult;

        public Factory(@NonNull String taskIdentifier,
                @NonNull ITaskNavigator<S, R> taskNavigator, @Nullable S currentStep, @NonNull R currentResult) {
            this.taskIdentifier = taskIdentifier;
            this.taskNavigator = taskNavigator;
            this.currentStep = currentStep;
            this.currentResult = currentResult;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TaskPresentationViewModel<S, R>(taskIdentifier,
                    new TaskNavigatorLiveData<S, R>(currentStep, currentResult, taskNavigator));
        }
    }

    private static final String TAG = "TaskPresentationVM";

    private final TaskNavigatorLiveData<S, R> taskNavigatorLiveData;

    private final MutableLiveData<S> currentStepLiveData;

    private final TaskResult taskResult;

    @VisibleForTesting
    TaskPresentationViewModel(String taskIdentifier, TaskNavigatorLiveData<S, R> taskNavigatorLiveData) {
        this.taskNavigatorLiveData = taskNavigatorLiveData;
        currentStepLiveData = new MutableLiveData<>();
        taskResult = new TaskResult(taskIdentifier);
    }

    @Override
    public void addStepResult(final IResult stepResult) {
        IStep currentStep = currentStepLiveData.getValue();
        if (currentStep == null) {
            Log.w(TAG, "No current step, unable to add result for step");
            return;
        }
        taskResult.setStepResultForStepIdentifier(currentStep.getIdentifier(), stepResult);
    }

    @Override
    public void goForward() {
        taskNavigatorLiveData.goForward();
    }

    @Override
    public void goBackward() {
        taskNavigatorLiveData.goBackward();
    }

    @Override
    public LiveData<TaskNavigatorState<S>> getTaskNavigatorState() {
        return taskNavigatorLiveData.getNavigatorStateLiveData();
    }

}
