package mg.tonymushah.dbconnection.utils.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Sequence {
    Class<? extends mg.tonymushah.dbconnection.utils.Sequence> to_use_Sequence();
}
