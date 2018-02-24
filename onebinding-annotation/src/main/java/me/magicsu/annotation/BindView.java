package me.magicsu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by sushun on 2018/2/22.
 */
@Retention(CLASS)
@Target(FIELD)
public @interface BindView {
    int value();
}
