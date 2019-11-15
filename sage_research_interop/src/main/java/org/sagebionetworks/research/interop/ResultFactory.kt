package org.sagebionetworks.research.interop

import org.sagebionetworks.research.domain.result.interfaces.Result as Result1


interface ResultFactory {
    fun <E> create(result: org.researchstack.foundation.core.models.result.StepResult<E>): Result1
    fun <E> create(result: Result1): org.researchstack.foundation.core.models.result.StepResult<E>
}
