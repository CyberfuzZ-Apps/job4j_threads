package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentMap<Integer, User> map = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return map.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return map.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = map.get(fromId);
        User to = map.get(toId);
        if (from == null || to == null
                || from.getAmount() < amount) {
            return false;
        }
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
        return true;
    }
}
