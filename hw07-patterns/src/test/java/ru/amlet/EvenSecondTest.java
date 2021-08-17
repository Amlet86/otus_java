package ru.amlet;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.amlet.model.Message;
import ru.amlet.processor.ProcessorEvenSecondException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EvenSecondTest {

    @Test
    @DisplayName("Тестируем вызов процессора без выбрасывания исключения в нечетную секунду")
    void evenProcessorsTest() {
        //given
        var evenSecond = LocalDateTime.now().withSecond(1);
        var message = new Message.Builder(1L).build();
        var processorsException = new ProcessorEvenSecondException(() -> evenSecond);

        //when
        assertDoesNotThrow(() -> processorsException.process(message));
    }

    @Test
    @DisplayName("Тестируем вызов процессора с выбрасыванием исключения в четную секунду")
    void exceptionProcessorsTest() {
        //given
        var evenSecond = LocalDateTime.now().withSecond(2);
        var message = new Message.Builder(1L).build();
        var processorsException = new ProcessorEvenSecondException(() -> evenSecond);

        //when
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processorsException.process(message));
    }
}
