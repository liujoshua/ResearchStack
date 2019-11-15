package org.sagebionetworks.research.interop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.researchstack.foundation.components.presentation.ITaskPresentationViewModel
import org.researchstack.foundation.components.presentation.StepPresentationViewModel
import org.researchstack.foundation.core.interfaces.IStep
import org.researchstack.foundation.core.models.result.StepResult
import org.sagebionetworks.research.domain.result.interfaces.Result
import org.sagebionetworks.research.domain.result.interfaces.TaskResult
import org.sagebionetworks.research.domain.step.interfaces.Step
import org.sagebionetworks.research.domain.task.navigation.NavDirection.SHIFT_LEFT
import org.sagebionetworks.research.domain.task.navigation.StepAndNavDirection
import org.sagebionetworks.research.domain.task.navigation.TaskProgress
import org.sagebionetworks.research.presentation.perform_task.PerformTaskViewModel

/**
 * Factory for the StepPresentationViewModel.
 *
 * Providing ViewModelProvider.Factory allows us to inject dependencies and pass parameters
 * to an instance since the Android framework controls the instantiation of ViewModels.
 */
class StepPresentationViewModelFactory<StepType : IStep>
(val taskPresentationViewModel: ITaskPresentationViewModel<StepType>, val mapper: Mapper) : ViewModel() {
    fun create(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(StepPresentationViewModel::class.java)) {

                    @Suppress("UNCHECKED_CAST")
                    return StepPresentationViewModel(taskPresentationViewModel) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    interface Mapper {
        fun map(stepResult: StepResult<*>): Result
    }

    class Wrapper<StepType : IStep>(val presentTaskViewModel: PerformTaskViewModel, val mapper: Mapper) : ITaskPresentationViewModel<StepType> {
        val taskNavigatorStateLiveData: MutableLiveData<ITaskPresentationViewModel.TaskNavigatorState<StepType>> = MutableLiveData()

        override fun addStepResult(result: StepResult<*>) {
            presentTaskViewModel.addStepResult(mapper.map(result))
        }

        override fun goForward() {
            presentTaskViewModel.goForward()
        }

        override fun goBack() {
            presentTaskViewModel.goBack()
        }

        override fun getTaskNavigatorStateLiveData(): LiveData<ITaskPresentationViewModel.TaskNavigatorState<StepType>> {

            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        fun map(currentStep: Step?, nextStep: StepAndNavDirection?, previousStep: Step?, taskProgress: TaskProgress, taskResult: TaskResult)
                : ITaskPresentationViewModel.TaskNavigatorState<StepType> {

            val direction: Int = nextStep?.let {
                if (nextStep.navDirection == SHIFT_LEFT) org.researchstack.foundation.components.presentation.NavDirection.SHIFT_LEFT else org.researchstack.foundation.components.presentation.NavDirection.SHIFT_RIGHT
            } ?: org.researchstack.foundation.components.presentation.NavDirection.SHIFT_LEFT

            return ITaskPresentationViewModel.TaskNavigatorState(direction, null, null, null, null, map(taskResult))
        }

        fun map(taskResult: TaskResult): org.researchstack.foundation.core.models.result.TaskResult {
            // TODO joliu map step results
            return org.researchstack.foundation.core.models.result.TaskResult(taskResult.identifier,
                    taskResult.taskUUID, taskResult.startTime, taskResult.endTime!!, HashMap())
        }

    }
}
