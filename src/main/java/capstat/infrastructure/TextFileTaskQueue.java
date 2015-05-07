package capstat.infrastructure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class TextFileTaskQueue implements ITaskQueue {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private File taskFile;
    private List<String> taskList;

    public TextFileTaskQueue(File taskFile) throws IOException {
        this.taskFile = taskFile.getAbsoluteFile();
        this.taskList = new LinkedList<>(readAllLines(this.taskFile));
    }

    private static List<String> readAllLines(File file) throws IOException {
        List<String> ret = new LinkedList<>();

        Path path = file.toPath();
        Stream<String> lines = Files.lines(path, UTF8_CHARSET);

        lines.map(line -> ret.add(line));
        return ret;
    }

    private void writeFile() throws IOException {
        FileWriter fw = new FileWriter(this.taskFile);
        BufferedWriter out = new BufferedWriter(fw);
        for (String s : this.taskList) {
            out.write(s);
            out.newLine();
        }
        out.close();
    }

    @Override
    public void add(String task) throws IOException {
        this.taskList.add(task);
        this.writeFile();
    }

    @Override
    public String peek() throws NoSuchElementException {
        if (!this.hasElements()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return this.taskList.get(0);
    }

    @Override
    public String pop() throws NoSuchElementException, IOException {
        if (!this.hasElements()) {
            throw new NoSuchElementException("Queue is empty");
        }
        String task = this.taskList.remove(0);
        this.writeFile();
        return task;
    }

    @Override
    public void clear() throws IOException {
        this.taskList.clear();
        this.writeFile();
    }

    @Override
    public int size() {
        return this.taskList.size();
    }

    @Override
    public boolean hasElements() {
        return this.taskList.size() > 0;
    }

    @Override
    public boolean contains(String task) {
        return this.taskList.contains(task);
    }

}
