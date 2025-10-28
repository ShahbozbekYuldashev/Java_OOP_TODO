import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
        private int id;
        private String title;
        private boolean completed;
        private Priority priority;
        private LocalDateTime createdAt;

        public Task(int id, String title, Priority priority) {
            this.id = id;
            this.title = title;
            this.priority = priority;
            this.completed = false;
            this.createdAt = LocalDateTime.now();
        }

        public Task(int id, String title, boolean completed, Priority priority, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.completed = completed;
            this.priority = priority;
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public Priority getPriority() {
            return priority;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        @Override
        public String toString() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String status = completed ? "[X]" : "[ ]";
            return String.format("%s %d. %s (⏰ %s | ⭐ %s)",
                    status, id, title, createdAt.format(fmt), priority);
        }


}
