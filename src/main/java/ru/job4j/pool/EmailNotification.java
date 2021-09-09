package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
   private final ExecutorService poll = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        poll.submit(() -> {
            String email = user.getEmail();
            String username = user.getUsername();
            String subject = String.format("Notification %s to email %s", username, email);
            String body = String.format("Add a new event to %s", username);
            send(subject, body, email);
        });
    }

    public void close() {
        poll.shutdown();
        while (!poll.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        User user = new User("Artos", "danmer@gmail.com");
        emailNotification.emailTo(user);
        emailNotification.close();
    }
}
