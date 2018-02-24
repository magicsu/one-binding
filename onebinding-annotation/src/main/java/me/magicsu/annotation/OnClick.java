package me.magicsu.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by sushun on 2018/2/22.
 */
@Retention(CLASS)
@Target(METHOD)
public @interface OnClick {
    int[] value();
}
