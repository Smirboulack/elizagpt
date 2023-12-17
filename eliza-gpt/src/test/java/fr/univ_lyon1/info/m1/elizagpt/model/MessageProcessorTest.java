package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for Processor.
 */
public class MessageProcessorTest {
        @Test
        void testFirstToSecondPerson() throws CsvValidationException {
                // Given
                Processor p = new Processor();

                // Then
                assertThat(p.firstToSecondPerson("Je pense à mon chien."),
                                is("vous pensez à votre chien."));

                assertThat(p.firstToSecondPerson("Je suis heureux."),
                                is("vous êtes heureux."));

                assertThat(p.firstToSecondPerson("Je dis bonjour."),
                                is("vous dites bonjour."));

                assertThat(p.firstToSecondPerson("Je vais à la mer."),
                                is("vous allez à la mer."));

                assertThat(p.firstToSecondPerson("Je finis mon travail."),
                                is("vous finissez votre travail."));
        }
}
