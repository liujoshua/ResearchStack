package org.sagebionetworks.research.interop.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.researchstack.foundation.components.presentation.StepPresentationViewModelFactory;
import org.researchstack.foundation.components.presentation.interfaces.IStepFragmentProvider;
import org.researchstack.foundation.core.interfaces.UIStep;
import org.sagebionetworks.research.mobile_ui.inject.ShowStepModule;
import org.sagebionetworks.research.presentation.model.interfaces.StepView;

import javax.inject.Inject;

public class ShowResearchStackStepFragmentFactory implements ShowStepModule.ShowStepFragmentFactory {
    private final IStepFragmentProvider<UIStep> researchStackFragmentProvider;
    private final StepPresentationViewModelFactory<UIStep> stepStepPresentationViewModelFactory;

    @Inject
    public ShowResearchStackStepFragmentFactory(IStepFragmentProvider<UIStep> researchStackFragmentProvider,
                                                StepPresentationViewModelFactory<UIStep> stepStepPresentationViewModelFactory) {
        this.researchStackFragmentProvider = researchStackFragmentProvider;
        this.stepStepPresentationViewModelFactory = stepStepPresentationViewModelFactory;
    }

    @NonNull
    @Override
    public Fragment create(@NonNull StepView stepView) {
        if (!(stepView instanceof ResearchStackStepView)) {
            throw new IllegalArgumentException("Step view: " + stepView + " is not an ResearchStackStepView.");
        }

        return ShowResearchStackStepFragment.newInstance(stepView, stepStepPresentationViewModelFactory);
    }
}
