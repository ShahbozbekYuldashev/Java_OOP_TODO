import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    private LocalDate deadline;

    public DeadlineTask(int id, String title, Priority priority, LocalDate deadline) {
        super(id, title, priority);
        this.deadline = deadline;
    }

    public DeadlineTask(int id, String title, boolean completed, Priority priority, LocalDateTime createdAt, LocalDate deadline) {
        super(id, title, completed, priority, createdAt);
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String status = isCompleted() ? "[X]" : "[ ]";
        String deadStr = deadline != null ? " | 🕒 Deadline: " + deadline.toString() : "";
        return String.format("%s %d. %s (⏰ %s | ⭐ %s%s)",
                status, getId(), getTitle(),
                getCreatedAt().format(fmt), getPriority(), deadStr);
    }
}
