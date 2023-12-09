package fr.univ_lyon1.info.m1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.tp_test.CharManipulator;

public class CharManipulatorTest {

    private CharManipulator manipulator;

    @BeforeEach
    public void setUp() {
        manipulator = new CharManipulator();
    }

    @Test
    void TestInvertNormalString() {
        assertEquals("DCBA", manipulator.invertOrder("ABCD"));
        assertEquals("BA", manipulator.invertOrder("AB"));
        assertEquals("654321", manipulator.invertOrder("123456"));
    }

    @Test
    public void orderEmptyString()
    {
        assertEquals("", manipulator.invertOrder(""));
    }

    @Test
    public void TestInvertCase(){
        assertEquals("ABcd", manipulator.invertCase("abCD"));
        assertEquals("abcd", manipulator.invertCase("ABCD"));
        assertEquals("ABCD", manipulator.invertCase("abcd"));
        assertEquals("", manipulator.invertCase(""));
    }

    @Test
    public void TestremovePattern(){
        assertEquals("cc", manipulator.removePattern("coucou","ou"));
        assertEquals("copc", manipulator.removePattern("copcou","ou"));
        assertEquals("copcliolu", manipulator.removePattern("copcoulouiolouu","ou"));
        assertEquals("", manipulator.removePattern("","ou"));
        assertEquals("", manipulator.removePattern("",""));
        assertEquals("coucou comment vas-tu ?", manipulator.removePattern("coucou comment vas-tu ?",""));
        assertEquals("coucoucommentvas-tu?", manipulator.removePattern("coucou comment vas-tu ?"," "));
        
    }

}
