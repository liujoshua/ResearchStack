package org.researchstack.foundation.components.presentation.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.researchstack.foundation.components.presentation.interfaces.ITaskNavigator
import org.researchstack.foundation.core.interfaces.IResult
import org.researchstack.foundation.core.interfaces.IStep
import org.researchstack.foundation.core.models.task.Task.TaskProgress

data class TaskNavigatorState<StepType : IStep>(val currentStep: StepType?, val nextStep: StepType?,
        val previousStep: StepType?, val taskProgress: TaskProgress?)

class TaskNavigatorLiveData<StepType : IStep, ResultType : IResult>(private var currentStep: StepType?,
        private var currentTaskResult: ResultType, private val taskNavigator: ITaskNavigator<StepType, ResultType>) {

    val taskNavigatorLiveData = MutableLiveData<TaskNavigatorState<StepType>>()

    init {
        updateTaskNavigatorState(currentStep, currentTaskResult)
    }

    fun getNavigatorStateLiveData(): LiveData<TaskNavigatorState<StepType>> {
        return taskNavigatorLiveData
    }

    fun updateTaskResult(taskResult: ResultType) {
        updateTaskNavigatorState(currentStep, taskResult)
    }

    fun goForward() {
        var nextStep = taskNavigator.getStepAfterStep(currentStep, currentTaskResult)

        updateTaskNavigatorState(nextStep, currentTaskResult)
    }

    fun goBackward() {
        var nextStep = taskNavigator.getStepBeforeStep(currentStep, currentTaskResult)

        updateTaskNavigatorState(nextStep, currentTaskResult)
    }

    fun updateTaskNavigatorState(step: StepType?, taskResult: ResultType) {

        currentStep = step
        currentTaskResult = taskResult

        val taskNavigatorState = TaskNavigatorState(step,
                taskNavigator.getStepAfterStep(step, taskResult),
                taskNavigator.getStepBeforeStep(step, taskResult),
                step?.let { taskNavigator.getProgressOfCurrentStep(step, taskResult) })

        taskNavigatorLiveData.postValue(taskNavigatorState)
    }
}
