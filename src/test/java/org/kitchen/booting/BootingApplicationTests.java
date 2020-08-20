package org.kitchen.booting;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.kitchen.booting.special.Special;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootingApplicationTests {
    @Autowired
    Special special;

    @Before
    void setup() {

    }

    @Test
    void contextLoads() {
        special.makeNewUser();

    }

}
