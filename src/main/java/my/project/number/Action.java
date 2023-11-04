package my.project.number;

import my.project.exception.NotFoundException;
import my.project.file.FileContents;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Action {
    public void process(String path) throws NotFoundException {
        FileContents contents = new FileContents();
        ActionByNumber action = new ActionByNumber();
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            String newPath = path;
            if (path.endsWith(".gz")) {
                newPath = path.substring(0, path.lastIndexOf("."));
                contents.decompressGzipFile(path, newPath);
            }
            String content = contents.readFileContents(newPath);
            contents.deleteFile(newPath);
            List<String> lines = Arrays.stream(content.split("\n")).collect(Collectors.toList());
            String finalFile = path.substring(0, path.lastIndexOf("\\")) + "\\final.txt";
            contents.writeNewFile(action.findGroups(lines), finalFile);
        } else {
            throw new NotFoundException("Такого файла не существует или он является директорией");
        }
    }
}
