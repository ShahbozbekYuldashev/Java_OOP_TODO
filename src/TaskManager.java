import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private static final String FILE_NAME = "tasks.txt";
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TaskManager() {
        tasks = new ArrayList<>();
        loadFromFile();
    }

    public void addTask(String title, Priority priority) {
        int id = tasks.size() + 1;
        Task newTask = new Task(id, title, priority);
        tasks.add(newTask);
        saveToFile();
        System.out.println("✅ Oddiy vazifa qo‘shildi: " + title);
    }

    public void addDeadlineTask(String title, Priority priority, LocalDate deadline) {
        int id = tasks.size() + 1;
        DeadlineTask newTask = new DeadlineTask(id, title, priority, deadline);
        tasks.add(newTask);
        saveToFile();
        System.out.println("⏰ Vazifa qo‘shildi: " + title + " (muddat: " + deadline + ")");
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("🚫 Hozircha hech qanday vazifa yo‘q.");
            return;
        }

        System.out.println("\n📋 Vazifalar ro‘yxati:");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void markAsDone(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                saveToFile();
                System.out.println("✅ Vazifa bajarilgan deb belgilandi: " + task.getTitle());
                return;
            }
        }
        System.out.println("⚠️ Bunday ID topilmadi!");
    }

    public void removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                System.out.println("🗑️ Vazifa o‘chirildi: " + tasks.get(i).getTitle());
                tasks.remove(i);
                saveToFile();
                return;
            }
        }
        System.out.println("⚠️ Bunday ID topilmadi!");
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                String type = (task instanceof DeadlineTask) ? "DEADLINE" : "TASK";
                String base = task.getId() + "," + type + "," + task.getTitle() + "," + task.isCompleted() + "," +
                        task.getPriority() + "," + task.getCreatedAt().format(fmt);
                if (task instanceof DeadlineTask) {
                    DeadlineTask d = (DeadlineTask) task;
                    base += "," + d.getDeadline();
                }
                writer.write(base);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Faylga yozishda xatolik: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                String title = parts[2];
                boolean completed = Boolean.parseBoolean(parts[3]);
                Priority priority = Priority.valueOf(parts[4]);
                LocalDateTime createdAt = LocalDateTime.parse(parts[5], fmt);

                if (type.equals("DEADLINE")) {
                    LocalDate deadline = LocalDate.parse(parts[6]);
                    tasks.add(new DeadlineTask(id, title, completed, priority, createdAt, deadline));
                } else {
                    tasks.add(new Task(id, title, completed, priority, createdAt));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Fayldan o‘qishda xatolik: " + e.getMessage());
        }
    }
}
