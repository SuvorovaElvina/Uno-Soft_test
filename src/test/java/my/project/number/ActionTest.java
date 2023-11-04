package my.project.number;

import my.project.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    private final Action action = new Action();

    @Test
    void processByGZip() throws NotFoundException {
        action.process("src\\test\\resources\\test_action.txt.gz");
        File file = new File("src\\test\\resources\\final.txt");

        assertTrue(file.exists(), "Не создаёт новый файл");
        assertTrue(file.isFile(), "создала не файл");
    }

    @Test
    void processByTxt() throws NotFoundException, IOException {
        File file = new File("src\\test\\resources\\test_action_txt.txt");
        if (file.createNewFile()) {
            action.process("src\\test\\resources\\test_action_txt.txt");
            file = new File("src\\test\\resources\\final.txt");
        }

        assertTrue(file.exists(), "Не создаёт новый файл");
        assertTrue(file.isFile(), "создала не файл");
    }

    @Test
    void noProcessByZip() {
        Throwable thrown = assertThrows(NotFoundException.class, () -> {
            action.process("src\\test\\resources\\test_for_zip.zip");
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    void noProcessWithoutExtension() {
        Throwable thrown = assertThrows(NotFoundException.class, () -> {
            action.process("src\\test\\resources\\test_without_extension");
        });

        assertNotNull(thrown.getMessage());
    }
}