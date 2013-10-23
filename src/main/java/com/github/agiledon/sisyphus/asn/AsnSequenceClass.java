package com.github.agiledon.sisyphus.asn;

public class AsnSequenceClass extends AsnSubClass {
    public AsnSequenceClass() {
        super();
    }

    public AsnSequenceClass(String fieldName) {
        super(fieldName);
    }

    @Override
    protected void setField(Object mainObject, Class<?> aClass) throws NoSuchFieldException, IllegalAccessException {
        if (getParentAsnClass() != null && getParentAsnClass().isVector()) {
            addElement(mainObject);
        } else {
            setCurrentField(mainObject, aClass);
        }
    }

    @Override
    public boolean isVector() {
        return false;
    }
}
