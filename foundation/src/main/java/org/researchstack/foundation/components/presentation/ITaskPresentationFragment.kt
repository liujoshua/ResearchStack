package org.researchstack.foundation.components.presentation

import org.researchstack.foundation.core.interfaces.IResult
import org.researchstack.foundation.core.interfaces.ITask
import org.researchstack.foundation.core.interfaces.UIStep
import org.researchstack.foundation.core.models.result.TaskResult

interface ITaskPresentationFragment<StepType : UIStep, ResultType : IResult, TaskType : ITask> {
    interface OnTaskExitListener {
        enum class Status {
            CANCELLED, FINISHED
        }

        fun onTaskExit(status: Status, taskResult: IResult)
    }

    fun showConfirmExitDialog()
}