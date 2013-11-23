package com.github.agiledon.sisyphus.sis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;

public class SisListClass extends SisCollectionClass {
    private Class<?> elementClass;

    public SisListClass() {
        super();
    }

    public SisListClass(String fieldName) {
        super(fieldName);
    }

    private void setElementClass(Class<?> elementClass) {
        this.elementClass = elementClass;
    }

    @Override
    protected <T> void setClassField(T currentObject, Class<T> currentClass) throws NoSuchFieldException, IllegalAccessException {
        Field field = currentClass.getDeclaredField(this.getFieldName());
        if (isList(field.getType())) {
            this.setElementClass(getListElementClass(field));
        }

        field.set(currentObject, this.instantiate(field.getType()));
    }

    private Class<?> getListElementClass(Field childField) {
        ParameterizedType integerListType = (ParameterizedType) childField.getGenericType();
        return (Class<?>) integerListType.getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T newInstance(Class<T> currentClass) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        if (isList(currentClass)) {
            T currentObject = (T) newArrayList();
            addElementsForArrayList(currentObject);
            return currentObject;
        }

        T currentObject = currentClass.newInstance();
        addElementsForCustomizedList(currentObject);
        return currentObject;
    }

    private <T> boolean isList(Class<T> currentClass) {
        return currentClass.getSimpleName().equals("List");
    }

    @SuppressWarnings("unchecked")
    protected void addElementsForArrayList(Object currentObject) {
        Collection currentCollection = (Collection) currentObject;
        addElements(currentCollection, this.elementClass);
    }

    @SuppressWarnings("unchecked")
    protected void addElementsForCustomizedList(Object currentObject) {
        Collection currentCollection = (Collection) currentObject;
        addElements(currentCollection, getElementClass(currentCollection));
    }

    @Override
    protected void printStartIndicator(StringBuilder stringBuilder) {
        stringBuilder.append("<");
    }

    @Override
    protected void printEndIndicator(StringBuilder stringBuilder) {
        stringBuilder.append(">");
    }

    private Class getElementClass(Collection currentCollection) {
        final ParameterizedType genericSuperclass = (ParameterizedType) currentCollection.getClass().getGenericSuperclass();
        return (Class) genericSuperclass.getActualTypeArguments()[0];
    }
}
