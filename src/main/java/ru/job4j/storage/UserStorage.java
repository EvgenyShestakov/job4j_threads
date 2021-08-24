package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        boolean flag = false;
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            flag = true;
        }
        return flag;

    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = users.get(fromId);
        User to = users.get(toId);
        boolean flag = false;
        if (from != null && to != null && from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            flag = true;
        }
        return flag;
    }
}
