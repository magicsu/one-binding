package me.magicsu.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import me.magicsu.annotation.BindView;

/**
 * Created by sushun on 2018/2/22.
 */
public class BindingViewField {

    private VariableElement mFieldElement;
    private int mResId;

    public BindingViewField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(
                String.format("Only fields can be annotated with @%s", BindView.class.getSimpleName()));
        }

        this.mFieldElement = (VariableElement) element;
        this.mResId = mFieldElement.getAnnotation(BindView.class).value();
    }

    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    public int getResId() {
        return mResId;
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }
}
