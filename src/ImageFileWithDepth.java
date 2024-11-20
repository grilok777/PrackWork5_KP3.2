import java.io.File;
// Клас для збереження файлу та його глибини

class ImageFileWithDepth {
    public final File file;
    public final int depth;

    public ImageFileWithDepth(File file, int depth) {
        this.file = file;
        this.depth = depth;
    }
}
