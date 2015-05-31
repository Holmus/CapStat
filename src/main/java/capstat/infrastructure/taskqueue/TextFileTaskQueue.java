package capstat.infrastructure.taskqueue;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    /**
     * Create a new instance of this class, using the file specified by
     * <code>taskFile</code> to write to. The file is created if, and only if,
     * it does not already exist.
     *
     * @throws IOException if any IO error occurs, other than the file not
     * existing
     */
    public TextFileTaskQueue(File taskFile) throws IOException {
        this.taskFile = taskFile.getAbsoluteFile();
        ensureFileExists(this.taskFile);
        this.taskList = new LinkedList<>(readAllLines(this.taskFile));
    }

    private static void ensureFileExists(File file) throws IOException {
        //Return value ignored, since it does not matter if file existed
        // previously or not.
        file.createNewFile();
    }
    private static List<String> readAllLines(File file) throws IOException {
        return Files.readAllLines(file.toPath(), UTF8_CHARSET);
    }

    private void writeFile() throws IOException {
        //This assumes all files are in standard encoding, which is enough for
        // the purposes of this project.
        FileWriter fw = new FileWriter(this.taskFile);
        BufferedWriter out = new BufferedWriter(fw);
        try {
            for (String s : this.taskList) {
                out.write(s);
                out.newLine();
            }
        } finally {
            out.close();
        }
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

    @Override
    public void delete() {
        //Return value ignored, since it does not matter if the file existed
        // or not. If the file can't be deleted, an exception will be thrown.
        this.taskFile.delete();
    }

}
