import java.io.File;
import java.awt.Desktop;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        // Вибір директорії користувачем
        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть директорію: ");
        String directoryPath = sc.nextLine();
        //String directoryPath = "..src/test";

        File directory = new File(directoryPath);
        while (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Невірний шлях до директорії. Спробуйте ще раз.");
            System.out.print("Введіть директорію: ");
            directoryPath = sc.nextLine();
            directory = new File(directoryPath);
        }

        // Використання ForkJoinPool для паралельної обробки
        ForkJoinPool pool = new ForkJoinPool();
        ImageSearchTask task = new ImageSearchTask(directory, 0);

        try {
            List<ImageFileWithDepth> imageFiles = pool.invoke(task);
            System.out.println("Кількість знайдених зображень: " + imageFiles.size());

            if (!imageFiles.isEmpty()) {
                // Знайти файл із найбільшою глибиною
                ImageFileWithDepth lastImageFile = imageFiles.stream()
                        .max((f1, f2) -> Integer.compare(f1.depth, f2.depth))
                        .orElse(null);

                if (lastImageFile != null) {
                    System.out.println("Відкриття файлу: " + lastImageFile.file.getAbsolutePath());
                    openFile(lastImageFile.file);
                }
            } else {
                System.out.println("Зображення не знайдено.");
            }
        } finally {
            pool.shutdown();
        }
    }



    private static void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println("Не вдалося відкрити файл: " + e.getMessage());
            }
        } else {
            System.out.println("Операція не підтримується на даній платформі.");
        }
    }




}