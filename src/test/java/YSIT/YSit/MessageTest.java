package YSIT.YSit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Testable
public class MessageTest {
    @Autowired
    MessageSource messageSource;

    @Test
    public void hello() {
        String result = messageSource.getMessage("noCode", new Object[]{"spring"}, Locale.KOREAN);
        Assert.assertEquals("noCode", result);
    }
}
