package org.sagebionetworks.researchstack.backbone.model.navigable;

import org.sagebionetworks.researchstack.backbone.answerformat.AnswerFormat;
import org.sagebionetworks.researchstack.backbone.result.StepResult;

public class EqualsNavigationClause extends StepNavigationClauseRule {

    public EqualsNavigationClause(String sourceStepIdentifier, Object value, AnswerFormat.Type ruleType, RuleClauseOperand operand) {
        super(sourceStepIdentifier, value, ruleType, operand);
    }

    @Override
    protected boolean evalClause(StepResult stepResult) {
        if (stepResult.getResult() != null) {
            Integer compareResult = compareResult(stepResult);
            if (compareResult != null) {
                return compareResult.equals(0);
            }
        }
        return false;
    }
}
