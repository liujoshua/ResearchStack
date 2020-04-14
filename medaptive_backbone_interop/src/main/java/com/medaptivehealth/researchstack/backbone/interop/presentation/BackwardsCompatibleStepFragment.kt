package com.medaptivehealth.researchstack.backbone.interop.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.medaptivehealth.researchstack.backbone.interop.ResultFactory
import com.medaptivehealth.researchstack.backbone.interop.StepAdapterFactory
import org.researchstack.foundation.R
import org.researchstack.foundation.components.presentation.ActionType
import org.researchstack.foundation.components.presentation.StepPresentationFragment
import org.researchstack.foundation.components.presentation.StepPresentationViewModelFactory
import org.researchstack.foundation.core.interfaces.IResult
import org.researchstack.foundation.core.interfaces.UIStep
import org.researchstack.backbone.result.StepResult
import org.researchstack.backbone.step.Step
import org.researchstack.backbone.ui.callbacks.StepCallbacks
import org.researchstack.backbone.ui.step.layout.StepLayout
import org.researchstack.foundation.components.presentation.interfaces.OnBackPressed

/**
 * Delegates between :backbone StepLayout, StepCallback classes and :foundation Fragments.
 */
class BackwardsCompatibleStepFragment : StepPresentationFragment<UIStep, IResult>(), StepCallbacks, OnBackPressed {

    companion object {
        /**
         * Returns an instance of this fragment that delegates for a given StepLayout.
         */
        @JvmStatic
        fun newInstance(stepLayout: StepLayout, stepAdapterFactory: StepAdapterFactory, stepPresentationViewModelProviderFactory: ViewModelProvider.Factory, resultFactory: ResultFactory): BackwardsCompatibleStepFragment {
            val fragment = BackwardsCompatibleStepFragment()
            fragment.stepLayout = stepLayout
            fragment.inject(stepPresentationViewModelProviderFactory)
            fragment.inject(resultFactory)
            fragment.inject(stepAdapterFactory)
            return fragment
        }
    }

    // inject
    private lateinit var resultFactory: ResultFactory

    fun inject(resultFactory: ResultFactory) {
        this.resultFactory = resultFactory
    }

    //inject
    private lateinit var stepAdapterFactory: StepAdapterFactory

    fun inject(stepAdapterFactory: StepAdapterFactory) {
        this.stepAdapterFactory = stepAdapterFactory
    }

    //this will implement the traditional step layout
    lateinit var stepLayout: StepLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        val containerView: FrameLayout = view.findViewById(R.id.rsf_content_layout)

        val step = stepPresentationViewModel.step
        val backboneStep = stepAdapterFactory.create(step)
        val stepResult = taskPresentationViewModel.getTaskNavigatorStateLiveData().value!!.taskResult.getStepResult(step.identifier)
        val backboneStepResult = stepResult?.let { resultFactory.create(it) }

        stepLayout.initialize(backboneStep, backboneStepResult)
        stepLayout.setCallbacks(this)
        val lp = getLayoutParams(stepLayout)
        containerView.addView(stepLayout.layout, 0, lp)

        return view
    }

    private fun getLayoutParams(stepLayout: StepLayout): FrameLayout.LayoutParams {
        var lp: FrameLayout.LayoutParams? = stepLayout.layout.layoutParams?.let {
            it as? FrameLayout.LayoutParams
        }
        if (lp == null) {
            lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT)
        }
        return lp
    }

    /**
     * Delegates StepCallbacks Step actions to :foundation equivalents.
     */
    override fun onSaveStep(action: Int, step: Step?, result: StepResult<*>?) {
        result?.let {
            stepPresentationViewModel.addStepResult(resultFactory.create(result))
        }
        if (action == StepCallbacks.ACTION_NEXT) {
            stepPresentationViewModel.handleAction(ActionType.FORWARD)
        } else if (action == StepCallbacks.ACTION_PREV) {
            stepPresentationViewModel.handleAction(ActionType.BACKWARD)
        } else if (action == StepCallbacks.ACTION_END) {
            stepPresentationViewModel.handleAction(ActionType.CANCEL)
        } else if (action == StepCallbacks.ACTION_NONE) {
            // Used when onSaveInstanceState is called of a view. No action is taken.
        } else {
            throw IllegalArgumentException("Action with value " + action + " is invalid. " +
                    "See StepCallbacks for allowable arguments")
        }
    }

    override fun onCancelStep() {
        taskPresentationFragment.showConfirmExitDialog()
    }

    override fun getLayoutId(): Int {
        return R.layout.rsf_fragment_step_compat
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            notifyStepOfBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        return notifyStepOfBackPressed()
    }

    fun notifyStepOfBackPressed(): Boolean {
        stepLayout.isBackEventConsumed
        return true
    }
}