import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class ImageSearchTask extends RecursiveTask<List<ImageFileWithDepth>> {
    private final File directory;
    private final int depth;
    // Підтримувані формати зображень
    private static final String[] IMAGE_EXTENSIONS = {".png", ".jpg", ".jpeg", ".bmp", ".gif"};

    public ImageSearchTask(File directory, int depth) {
        this.directory = directory;
        this.depth = depth;
    }

    @Override
    protected List<ImageFileWithDepth> compute() {
        List<ImageSearchTask> subtasks = new ArrayList<>();
        List<ImageFileWithDepth> imageFiles = new CopyOnWriteArrayList<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Створюємо підзадачу для кожної вкладеної директорії, збільшуючи рівень глибини
                    ImageSearchTask subtask = new ImageSearchTask(file, depth + 1);
                    subtask.fork();
                    subtasks.add(subtask);
                } else if (isImageFile(file)) {
                    imageFiles.add(new ImageFileWithDepth(file, depth));
                }
            }

            for (ImageSearchTask subtask : subtasks) {
                imageFiles.addAll(subtask.join());
            }
        }
        return imageFiles;
    }

    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        for (String extension : IMAGE_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
