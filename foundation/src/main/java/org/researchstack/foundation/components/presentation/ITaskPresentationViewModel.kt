package org.researchstack.foundation.components.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import org.researchstack.foundation.core.interfaces.IStep
import org.researchstack.foundation.core.models.result.StepResult
import org.researchstack.foundation.core.models.result.TaskResult
import org.researchstack.foundation.core.models.task.Task

interface ITaskPresentationViewModel<StepType : IStep> {
    /**
     * Add a StepResult to the TaskResult.
     */
    @VisibleForTesting
    fun addStepResult(result: StepResult<*>)

    /**
     * Request forward navigation.
     */
    fun goForward()

    /**
     * Request backward navigation.
     */
    fun goBack()

    /**
     *
     */
    fun getTaskNavigatorStateLiveData(): LiveData<TaskNavigatorState<StepType>>

    /**
     * Data class the represents the full navigation state.
     */
    data class TaskNavigatorState<StepType>(
            @NavDirection val navDirection: Int, val currentStep: StepType?, val previousStep: StepType?,
            val nextStep: StepType?, val taskProgress: Task.TaskProgress?, val taskResult: TaskResult)
}