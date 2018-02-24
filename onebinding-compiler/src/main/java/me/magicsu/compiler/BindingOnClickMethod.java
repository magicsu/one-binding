package me.magicsu.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

import me.magicsu.annotation.OnClick;

/**
 * Created by sushun on 2018/2/22.
 */

public class BindingOnClickMethod {

    private Name mMethodName;
    public int[] mIds;

    public BindingOnClickMethod(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(
                String.format("Only methods can be annotated with @%s", OnClick.class.getSimpleName()));
        }

        ExecutableElement methodElement = (ExecutableElement) element;
        this.mMethodName = methodElement.getSimpleName();
        this.mIds = methodElement.getAnnotation(OnClick.class).value();
    }

    public Name getMethodName() {
        return mMethodName;
    }
}
