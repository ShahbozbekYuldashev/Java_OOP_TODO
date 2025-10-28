import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        while (true) {
            try {
                showMenu();
                int choice = getInt("Tanlang: ");

                switch (choice) {
                    case 1 -> addSimpleTask();
                    case 2 -> addDeadlineTask();
                    case 3 -> manager.listTasks();
                    case 4 -> markTaskDone();
                    case 5 -> removeTask();
                    case 0 -> {
                        System.out.println("👋 Xayr, ko'rishguncha!");
                        return;
                    }
                    default -> System.out.println("⚠️ Noto‘g‘ri tanlov!");
                }
            } catch (Exception e) {
                System.out.println("❌ Xatolik: " + e.getMessage());
                scanner.nextLine();            }
        }
    }

    private static void showMenu() {
        System.out.println("\n==== TODO MENU ====");
        System.out.println("1. Oddiy vazifa qo‘shish");
        System.out.println("2. Deadline bilan vazifa qo‘shish");
        System.out.println("3. Vazifalarni ko‘rish");
        System.out.println("4. Vazifani bajarilgan deb belgilash");
        System.out.println("5. Vazifani o‘chirish");
        System.out.println("0. Chiqish");
    }

    private static void addSimpleTask() {
        String title = getString("Vazifa nomi: ");
        Priority priority = getPriority();
        manager.addTask(title, priority);
    }

    private static void addDeadlineTask() {
        String title = getString("Vazifa nomi: ");
        Priority priority = getPriority();
        LocalDate deadline = getDate("Muddat (YYYY-MM-DD): ");
        manager.addDeadlineTask(title, priority, deadline);
    }

    private static void markTaskDone() {
        int id = getInt("Vazifa ID: ");
        manager.markAsDone(id);
    }

    private static void removeTask() {
        int id = getInt("Vazifa ID: ");
        manager.removeTask(id);
    }

    private static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int num = Integer.parseInt(scanner.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Iltimos, faqat raqam kiriting!");
            }
        }
    }

    private static LocalDate getDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Sana formati noto‘g‘ri! (Masalan: 2025-12-31)");
            }
        }
    }

    private static Priority getPriority() {
        while (true) {
            try {
                System.out.print("Ustuvorlik (LOW, MEDIUM, HIGH): ");
                String input = scanner.nextLine().trim().toUpperCase();
                return Priority.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Faqat quyidagilardan birini kiriting: LOW, MEDIUM, HIGH");
            }
        }
    }
}