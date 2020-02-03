import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class SETest {

    @Test
    public void seTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE , -1);
        Date yesterday = calendar.getTime();
        System.out.println(yesterday);
    }
}
