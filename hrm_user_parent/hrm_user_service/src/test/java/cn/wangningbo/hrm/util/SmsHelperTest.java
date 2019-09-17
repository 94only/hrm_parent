package cn.wangningbo.hrm.util;


import cn.wangningbo.hrm.User9008Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = User9008Application.class)
public class SmsHelperTest {

    @Autowired
    private SmsHelper smsHelper;

    @Test
    public void sendSms() {
        smsHelper.sendSms("17752526745","1",new String[]{"8888","5"});
    }
}