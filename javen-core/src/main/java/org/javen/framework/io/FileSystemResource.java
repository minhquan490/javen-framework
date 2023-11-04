package org.javen.framework.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public final class FileSystemResource extends AbstractResource implements WriteableResource {
    FileSystemResource(@NotNull String resourcePath, ClassLoader classLoader) {
        super(resolvePath(resourcePath), classLoader);
    }

    private static Path resolvePath(String path) {
        String resolvedPath;
        if (path.startsWith("classpath:")) {
            resolvedPath = path.replace("classpath:", "");
        } else {
            resolvedPath = path;
        }
        return Path.of(resolvedPath);
    }

    @Override
    protected boolean exist(@NotNull Path path) {
        return Files.exists(path);
    }

    @NotNull
    @Override
    public OutputStream getOutputStream() throws IOException {
        byte[] data = readDataAsByteArray();
        OutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(data);
        return outputStream;
    }

    @Override
    public boolean isWriteable() {
        return Files.isWritable(getResourcePath());
    }

    @NotNull
    @Override
    public WritableByteChannel writableChannel() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(getResourcePath().toFile(), ResourceMode.READ_WRITE.getMode());
        return randomAccessFile.getChannel();
    }
}
