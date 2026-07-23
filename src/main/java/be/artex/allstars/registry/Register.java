package be.artex.allstars.registry;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Register<T> {
    private final String namespace;
    private final Registry<T> registry;
    private final Map<RegistryKey, T> values;
    private boolean registered;

    public Register(@NotNull String namespace, @NotNull Registry<T> registry) {
        Preconditions.checkArgument(RegistryKey.isValidNamespace(namespace), "Invalid namespace!");
        Preconditions.checkNotNull(registry);

        this.namespace = namespace;
        this.registry = registry;
        this.values = new HashMap<>();
        this.registered = false;
    }

    /**
     * Registers a new element to be registered later in the registry.
     *
     * @param path path of the key for the element.
     * @param value value.
     *
     * @return value.
     */
    public T register(@NotNull String path, @NotNull T value) {
        if (this.registered)
            throw new IllegalStateException("Register is already registered!");

        Preconditions.checkArgument(RegistryKey.isValidPath(path), "Path is invalid.");
        Preconditions.checkNotNull(value, "value");

        final RegistryKey key = new RegistryKey(this.namespace, path);
        if (this.values.containsKey(key))
            throw new IllegalArgumentException("Element with the path " + path + " has been registered multiple times!");

        this.values.put(key, value);
        return value;
    }

    /**
     * Registers (flushes) the element to registry.
     */
    public void register() {
        this.values.forEach(this.registry::register); // Flush the registered value to the registry.

        this.registered = true;
        this.values.clear(); // Used to reduce the memory a bit footprint (Only removes the unused references)
    }
}
