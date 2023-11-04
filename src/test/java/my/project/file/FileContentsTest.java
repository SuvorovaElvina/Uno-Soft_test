package my.project.file;

import my.project.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileContentsTest {
    private final FileContents contents = new FileContents();

    @BeforeAll
    static void forDelete() {
        new File("src\\test\\resources\\test_delete.txt");
    }

    @Test
    void decompressGzipFileTest() throws NotFoundException {
        contents.decompressGzipFile("src\\test\\resources\\test_dec.txt.gz",
                "src\\test\\resources\\test_dec_new.txt");
        File file = new File("src\\test\\resources\\test_dec_new.txt");

        assertTrue(file.exists(), "Не создаёт новый файл");
        assertTrue(file.isFile(), "создала не файл");
    }

    @Test
    void notDecompressGzipFileTest() {
        Throwable thrown = assertThrows(NotFoundException.class, () -> {
            contents.decompressGzipFile("src\\test\\resources\\dec.txt", "");
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    void readFileContentsTest() throws NotFoundException {
        String content = contents.readFileContents("src\\test\\resources\\test_read.txt");

        assertEquals(content, "\"прочитано.\"", "Не читает файл.");
    }

    @Test
    void notReadFileContentsTest() {
        Throwable thrown = assertThrows(NotFoundException.class, () -> {
            contents.readFileContents("src\\test\\resources\\read.txt");
        });

        assertNotNull(thrown.getMessage());
    }

    @Test
    void deleteFile() {
        contents.deleteFile("src\\test\\resources\\test_delete.txt");
    }

    @Test
    void writeNewFile() throws NotFoundException {
        List<List<String>> groups = List.of(List.of("\"79189701474\";\"79455321178\";\"79986590996\"",
                "\"79189701474\";\"79455321178\";\"79986590996\""));
        contents.writeNewFile(groups, "src\\test\\resources\\test_write_new.txt");
        File file = new File("src\\test\\resources\\test_write_new.txt");

        assertTrue(file.exists(), "Не создаёт новый файл");
        assertTrue(file.isFile(), "создала не файл");
    }

    @Test
    void notWriteNewFile() {
        Throwable thrown = assertThrows(NotFoundException.class, () -> {
            contents.readFileContents("src\\test\\resources\\write.txt");
        });

        assertNotNull(thrown.getMessage());
    }
}