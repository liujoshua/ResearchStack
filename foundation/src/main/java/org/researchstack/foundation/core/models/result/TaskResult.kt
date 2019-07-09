package org.researchstack.foundation.core.models.result

import org.researchstack.foundation.core.interfaces.IResult
import org.threeten.bp.Instant
import java.util.*

/**
 * An TaskResult object is a result that contains all the step results generated from one run of a
 * task or ordered task (that is, [org.researchstack.foundation.task.Task] or [ ]).
 *
 *
 * A task result is typically generated by the framework as the task proceeds. When the task
 * completes, it may be appropriate to serialize it for transmission to a server, or to immediately
 * perform analysis on it.
 *
 *
 * The `results` property contains the step results for the task.
 */
data class TaskResult @JvmOverloads constructor(override val identifier: String,
                      val taskRunUUID: UUID,
                      override var startTimestamp: Instant = Instant.now(),
                      override var endTimestamp: Instant = Instant.now(),
                      val results: MutableMap<String, StepResult<*>> = HashMap()) : IResult {
    override val type: String
        get() = TYPE_KEY

    companion object {
        @JvmField
        val TYPE_KEY = "task"
    }

    /**
     * Returns a step result for the specified step identifier, if one exists.
     *
     * @param identifier The identifier for which to search.
     * @return The result for the specified step, or [nil] for none.
     */
    fun getStepResult(identifier: String): StepResult<*>? {
        return results[identifier]
    }

    /**
     * Sets the result for the step using the step's identifier as a key.
     *
     * @param identifier the Step and StepResult's identifier
     * @param stepResult the StepResult for this identifier
     */
    fun setStepResultForStepIdentifier(identifier: String, stepResult: StepResult<*>?) {
        stepResult?.let { results[identifier] = stepResult } ?: results.remove(identifier)
    }
}
