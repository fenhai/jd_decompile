package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.CLASS)
/* compiled from: TbsSdkJava */
public @interface RequiresPermission {

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    /* compiled from: TbsSdkJava */
    public @interface Read {
        RequiresPermission value() default @RequiresPermission;
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    /* compiled from: TbsSdkJava */
    public @interface Write {
        RequiresPermission value() default @RequiresPermission;
    }

    String[] allOf() default {};

    String[] anyOf() default {};

    boolean conditional() default false;

    String value() default "";
}
