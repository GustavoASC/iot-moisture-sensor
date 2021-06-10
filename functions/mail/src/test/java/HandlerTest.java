import org.junit.Test;
import static org.junit.Assert.*;

import com.cassel.function.mail.Handler;
import com.openfaas.model.IHandler;

public class HandlerTest {
    @Test public void handlerIsNotNull() {
        IHandler handler = new Handler();
        assertTrue("Expected handler not to be null", handler != null);
    }
}
