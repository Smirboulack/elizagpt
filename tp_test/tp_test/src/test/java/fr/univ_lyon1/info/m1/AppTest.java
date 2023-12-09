package fr.univ_lyon1.info.m1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void myFirstTest() {
        assertEquals(2, 2);
        // Cette ligne échouera le test délibérément.
        fail("Le test a échoué délibérément avec fail().");
    }
}
