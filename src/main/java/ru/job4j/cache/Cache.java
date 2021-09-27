package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return memory.computeIfPresent(model.getId(), (id, base) -> {
            Base stored = memory.get(id);
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            int version = stored.getVersion();
            Base updatedBase = new Base(id, version + 1);
            updatedBase.setName(model.getName());
            return updatedBase;
                }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }

}
