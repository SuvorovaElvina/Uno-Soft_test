package my.project.file;

import my.project.exception.NotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class FileContents {
    public void decompressGzipFile(String path, String newPath) throws NotFoundException {
        try (FileInputStream fis = new FileInputStream(path);
             GZIPInputStream gis = new GZIPInputStream(fis);
             FileOutputStream fos = new FileOutputStream(newPath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new NotFoundException("Невозможно разархивировать файл. Возможно, файл не находится в нужной директории.");
        }
    }

    public String readFileContents(String path) throws NotFoundException {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new NotFoundException("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
        }
    }

    public void deleteFile(String path) {
        File file = new File(path);
        file.deleteOnExit();
    }

    public void writeNewFile(List<List<String>> groups, String path) throws NotFoundException {
        long k = groups.stream()
                .filter(group -> group.size() > 1)
                .count();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("Всего групп с более чем 1 элементом: " + k);
            for (int i = 0; i < groups.size(); i++) {
                bw.write("\nГруппа №" + (i + 1));
                groups.get(i).forEach(s -> {
                    try {
                        bw.write("\n" + s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            throw new NotFoundException("Невозможно записать в файл. Возможно, файл не находится в нужной директории.");
        }
    }
}
