package AgileCinemas;

import org.junit.jupiter.api.Test;

public class MangerTest {
    @Test
    public void testCanceledTransactionReport(){
        Manager manger = new Manager("1636");
        manger.reportCancelledTransactions();
    }
}
