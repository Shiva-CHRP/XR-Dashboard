package superadmin.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestInfo {
	String author() default "Shiva";

	String module() default "";

	String description() default "";

	String priority() default "Medium";
}
