package anot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by tish on 27.09.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name();
}
