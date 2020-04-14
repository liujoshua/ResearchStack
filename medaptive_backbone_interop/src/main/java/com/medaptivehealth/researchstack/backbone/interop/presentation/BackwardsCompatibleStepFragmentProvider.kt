package com.medaptivehealth.researchstack.backbone.interop.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import org.researchstack.foundation.components.presentation.StepPresentationViewModelFactory
import org.researchstack.foundation.components.presentation.interfaces.IStepFragmentProvider
import org.researchstack.foundation.core.interfaces.UIStep
import com.medaptivehealth.researchstack.backbone.interop.ResultFactory
import com.medaptivehealth.researchstack.backbone.interop.StepAdapterFactory
import com.medaptivehealth.researchstack.backbone.ui.step.layout.StepLayout

/**
 * Creates a foundation-compatible Fragment that hosts the behavior for a backbone Step.
 */
class BackwardsCompatibleStepFragmentProvider(val context: Context, val stepAdapterFactory: StepAdapterFactory, val resultFactory: ResultFactory) : IStepFragmentProvider<UIStep> {

    // this should really take a backbone step, taking a foundation UIStep for now to make it simpler to iterate on dependencies
    override fun stepFragment(step: UIStep, stepPresentationViewModelFactory: StepPresentationViewModelFactory<UIStep>): Fragment? {

        try {
            val backboneStep = stepAdapterFactory.create(step)
            val stepLayout = backboneStep.stepLayoutClass?.let {
                val constructor = it.getConstructor(Context::class.java)
                constructor.newInstance(context)
            } as? StepLayout

            if (stepLayout != null) {
                val fragment = BackwardsCompatibleStepFragment.newInstance(stepLayout, stepAdapterFactory, stepPresentationViewModelFactory.create(step), resultFactory)
                return fragment
            } else {
                throw RuntimeException("Could not instantiate Step Layout")
            }

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}