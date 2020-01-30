package com.medaptivehealth.researchstack.backbone.interop;

import org.researchstack.foundation.core.interfaces.IStep;
import com.medaptivehealth.researchstack.backbone.step.Step;

public interface StepAdapterFactory {
    Step create(IStep step);
    IStep create(Step step);
}
