package org.kitchen.booting;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.kitchen.booting.domain.enums.CookingTime;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class EnumTest {

//    @Test
    public void eum(){
        System.out.println(CookingTime.getCookingTime(1));
    }


}
