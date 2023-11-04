package org.javen.framework.io;

import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public final class NativeLibraryResource extends AbstractResource {
    NativeLibraryResource(@NotNull Path resourcePath, @NotNull ClassLoader classLoader) {
        super(resourcePath, classLoader);
    }

    @Override
    protected boolean exist(@NotNull Path path) {
        return Files.exists(path);
    }
}
