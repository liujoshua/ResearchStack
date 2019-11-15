package org.sagebionetworks.research.interop.presentation;

import androidx.annotation.NonNull;

import org.researchstack.foundation.core.interfaces.IStep;
import org.sagebionetworks.research.presentation.model.interfaces.StepView;

public class ResearchStackStepView implements StepView {
    private final IStep researchStackStep;

    public ResearchStackStepView(IStep researchStackStep) {
        this.researchStackStep = researchStackStep;
    }

    @NonNull
    @Override
    public String getIdentifier() {
        return researchStackStep.getIdentifier();
    }

    @NonNull
    @Override
    public String getType() {
        return "Foundation";
    }

    public IStep getResearchStackStep() {
        return researchStackStep;
    }
}
