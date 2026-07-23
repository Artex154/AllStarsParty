package be.artex.allstars.registry;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Registry key, a replica of minecraft Key system to make elements unique.
 * <br>
 * This implementation is inspired off kyori adventure key implementation.
 *
 * @implNote Due to the way keys in minecraft are usually handled they should be limited to a certain maximum length, since we don't need to sync this with the client, no length check is actually ran.
 */
public class RegistryKey {
    public static final char DEFAULT_SEPARATOR = ':';
    public static final String MINECRAFT_NAMESPACE = "minecraft";

    private final String namespace;
    private final String path;

    /**
     * Create a new registry key instance.
     *
     * @param namespace namespace of the key.
     * @param path path of the key.
     */
    public RegistryKey(@NotNull String namespace, @NotNull String path) {
        Preconditions.checkArgument(isValidNamespace(namespace), "Namespace is invalid");
        Preconditions.checkArgument(isValidPath(path), "Path is invalid");

        this.namespace = namespace;
        this.path = path;
    }

    /**
     * Parse a key from a string value.
     *
     * @param raw raw string to parse.
     *
     * @return null if the input was null or invalid, otherwise the parsed registry key.
     */
    public static @Nullable RegistryKey fromString( String raw) {
        if (raw == null || raw.isEmpty())
            return null;

        int index = raw.indexOf(DEFAULT_SEPARATOR);
        if (index < 1)
            return null;

        String namespace = raw.substring(0, index);
        String path = raw.substring(index + 1);

        if (!isValidNamespace(namespace) || !isValidPath(path))
            return null;

        return new RegistryKey(namespace, path);
    }

    /**
     * Checks whether the namespace is valid.
     *
     * @param namespace namespace to check.
     *
     * @return true if the namespace is valid, false otherwise.
     */
    public static boolean isValidNamespace(String namespace) {
        if (namespace == null)
            return false;

        for (int i = 0, length = namespace.length(); i < length; i++) {
            if (!allowedInNamespace(namespace.charAt(i)))
                return false;
        }

        return true;
    }

    /**
     * Checks whether the path is valid.
     *
     * @param path path to check.
     *
     * @return true if the path is valid, false otherwise.
     */
    public static boolean isValidPath(String path) {
        if (path == null)
            return false;

        for (int i = 0, length = path.length(); i < length; i++) {
            if (!allowedInPath(path.charAt(i)))
                return false;
        }

        return true;
    }

    /**
     * Retrieve the namespace of the key.
     *
     * @return namespace of the key.
     */
    public @NotNull String getNamespace() {
        return this.namespace;
    }

    /**
     * Retrieve the path of the key.
     *
     * @return path of the key.
     */
    public @NotNull String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RegistryKey))
            return false;

        RegistryKey key = (RegistryKey) obj;
        return this.namespace.equals(key.namespace) && this.path.equals(key.path);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return this.namespace + DEFAULT_SEPARATOR + this.path;
    }

    @ApiStatus.Internal
    private static boolean allowedInNamespace(char character) {
        return character == '_' || character == '-' || (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == '.';
    }

    @ApiStatus.Internal
    private static boolean allowedInPath(char character) {
        return character == '_' || character == '-' || (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == '.' || character == '/';
    }
}
