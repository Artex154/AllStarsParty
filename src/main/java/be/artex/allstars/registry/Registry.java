package be.artex.allstars.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Basic registry implementation, hold a list of elements attached with a unique key.
 *
 * @param <T> type of values stored in the registry.
 */
public class Registry<T> {
    private final BiMap<RegistryKey, T> values;

    public Registry() {
        this.values = HashBiMap.create();
    }

    /**
     * Register an element to the registry.
     *
     * @param key key of the element.
     * @param value value of the element.
     *
     * @throws NullPointerException if the key or value is null
     * @throws IllegalArgumentException if an element with the same key has already been registered.
     */
    public void register(@NotNull RegistryKey key, @NotNull T value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        Preconditions.checkArgument(!this.values.containsKey(key), "Key " + key + " has been registered multiple times!");
    }

    /**
     * Retrieve the value from a key.
     *
     * @param key key of the value.
     *
     * @return found value, or null if none was found.
     */
    public @Nullable T getValue(@NotNull RegistryKey key) {
        return this.values.get(key);
    }

    /**
     * Retrieve the key from a value.
     *
     * @param value value to get the key from.
     *
     * @return found key, or null if none was found.
     */
    public @Nullable RegistryKey getKey(@NotNull T value) {
        return this.values.inverse().get(value);
    }

    /**
     * Checks whether the registry is empty.
     *
     * @return true, if empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.values.isEmpty();
    }
}
