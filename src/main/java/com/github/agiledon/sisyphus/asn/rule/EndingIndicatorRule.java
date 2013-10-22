package com.github.agiledon.sisyphus.asn.rule;

import com.github.agiledon.sisyphus.asn.AsnClass;

public class EndingIndicatorRule extends ParsingRule {
    public boolean match(String line) {
        return line.trim().contains("}");
    }

    @Override
    public AsnClass generate(AsnClass currentClass, String line) {
        return navigateToParent(currentClass);
    }

    private AsnClass navigateToParent(AsnClass currentClass) {
        AsnClass parentAsnClass = currentClass.getParentAsnClass();
        if (parentAsnClass != null) {
            currentClass = parentAsnClass;
        }
        return currentClass;
    }
}