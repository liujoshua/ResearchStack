package org.sagebionetworks.research.interop;

import org.jetbrains.annotations.NotNull;
import org.researchstack.foundation.components.presentation.TaskPresentationFragment;
import org.researchstack.foundation.core.models.result.TaskResult;

/**
 * Replicates the behavior of ViewTaskActivity while running :backbone tasks on :foundation.
 */
public class ViewSageResearchInteropTaskActivity implements TaskPresentationFragment.OnTaskExitListener {


    @Override
    public void onTaskExit(@NotNull Status status, @NotNull TaskResult taskResult) {

    }
}
