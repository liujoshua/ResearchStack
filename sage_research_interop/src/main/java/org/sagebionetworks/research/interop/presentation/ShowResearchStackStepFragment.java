package org.sagebionetworks.research.interop.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import org.researchstack.foundation.components.presentation.StepPresentationFragment;
import org.researchstack.foundation.components.presentation.StepPresentationViewModelFactory;
import org.researchstack.foundation.components.presentation.interfaces.IStepFragmentProvider;
import org.researchstack.foundation.core.interfaces.IResult;
import org.researchstack.foundation.core.interfaces.UIStep;
import org.sagebionetworks.research.interop.R;
import org.sagebionetworks.research.mobile_ui.perform_task.PerformTaskFragment;
import org.sagebionetworks.research.presentation.model.interfaces.StepView;
import org.sagebionetworks.research.presentation.perform_task.PerformTaskViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ShowResearchStackStepFragment extends StepPresentationFragment<UIStep, IResult> {
    @NonNull
    public static ShowResearchStackStepFragment newInstance(@NonNull StepView stepView, StepPresentationViewModelFactory<UIStep> stepStepPresentationViewModelFactory) {
        if (!(stepView instanceof ResearchStackStepView)) {
            throw new IllegalArgumentException("Step view: " + stepView + " is not an ActiveUIStepView.");
        }

        ShowResearchStackStepFragment fragment = new ShowResearchStackStepFragment();
        fragment.inject(stepStepPresentationViewModelFactory);
        return fragment;
    }

    @Inject
    protected IStepFragmentProvider<UIStep> researchStackStepFragmentProvider;

    @Inject
    protected PerformTaskFragment performTaskFragment;

    protected PerformTaskViewModel performTaskViewModel;


    @Override
    protected int getLayoutId() {
        return R.layout.rsf_fragment_step_compat;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);

        super.onAttach(context);

        // gets the PerformTaskViewModel instance of performTaskFragment
        this.performTaskViewModel = ViewModelProviders.of(performTaskFragment).get(PerformTaskViewModel.class);
    }


}
