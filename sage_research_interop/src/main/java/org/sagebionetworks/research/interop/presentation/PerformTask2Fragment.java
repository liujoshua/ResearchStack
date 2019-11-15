package org.sagebionetworks.research.interop.presentation;

import org.researchstack.foundation.components.presentation.ITaskPresentationFragment;
import org.sagebionetworks.research.mobile_ui.perform_task.PerformTaskFragment;

public class PerformTask2Fragment extends PerformTaskFragment implements ITaskPresentationFragment {
    @Override
    public void showConfirmExitDialog() {
        cancelTask(true);
    }
}
