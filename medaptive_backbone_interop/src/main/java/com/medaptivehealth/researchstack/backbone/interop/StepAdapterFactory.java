package com.medaptivehealth.researchstack.backbone.interop;

import org.researchstack.foundation.core.interfaces.IStep;
import org.researchstack.backbone.step.Step;

public interface StepAdapterFactory {
    Step create(IStep step);
    IStep create(Step step);
}
