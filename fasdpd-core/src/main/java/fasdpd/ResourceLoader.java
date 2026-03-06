package fasdpd;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

public final class ResourceLoader {
	private ResourceLoader() {}

	public static Optional<URL> findResourceUrl(
		Class<?> anchor,
		String resourcePath
	) {
		Objects.requireNonNull(anchor, "anchor");
		String normalized = normalize(resourcePath);
		URL url = anchor.getResource("/" + normalized);
		if (url == null) {
			ClassLoader classLoader = anchor.getClassLoader();
			if (classLoader != null) {
				url = classLoader.getResource(normalized);
			}
		}
		if (url == null) {
			ClassLoader contextClassLoader =
				Thread.currentThread().getContextClassLoader();
			if (contextClassLoader != null) {
				url = contextClassLoader.getResource(normalized);
			}
		}
		return Optional.ofNullable(url);
	}

	public static Optional<InputStream> openResourceStream(
		Class<?> anchor,
		String resourcePath
	) {
		Objects.requireNonNull(anchor, "anchor");
		String normalized = normalize(resourcePath);
		InputStream stream = anchor.getResourceAsStream("/" + normalized);
		if (stream == null) {
			ClassLoader classLoader = anchor.getClassLoader();
			if (classLoader != null) {
				stream = classLoader.getResourceAsStream(normalized);
			}
		}
		if (stream == null) {
			ClassLoader contextClassLoader =
				Thread.currentThread().getContextClassLoader();
			if (contextClassLoader != null) {
				stream = contextClassLoader.getResourceAsStream(normalized);
			}
		}
		return Optional.ofNullable(stream);
	}

	public static Optional<String> readUtf8Resource(
		Class<?> anchor,
		String resourcePath
	) {
		Optional<InputStream> streamOpt = openResourceStream(anchor, resourcePath);
		if (streamOpt.isEmpty()) {
			return Optional.empty();
		}
		try (InputStream stream = streamOpt.get()) {
			return Optional.of(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	public static Optional<Path> resolveReadablePath(String pathValue) {
		Path path = Path.of(pathValue).toAbsolutePath().normalize();
		if (Files.isRegularFile(path) && Files.isReadable(path)) {
			return Optional.of(path);
		}
		return Optional.empty();
	}

	public static Optional<Path> materializeResourceToTempFile(
		Class<?> anchor,
		String resourcePath,
		String prefix,
		String suffix
	) {
		Optional<InputStream> streamOpt = openResourceStream(anchor, resourcePath);
		if (streamOpt.isEmpty()) {
			return Optional.empty();
		}
		try (InputStream stream = streamOpt.get()) {
			Path temp = Files.createTempFile(prefix, suffix);
			Files.copy(stream, temp, StandardCopyOption.REPLACE_EXISTING);
			temp.toFile().deleteOnExit();
			return Optional.of(temp);
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	private static String normalize(String resourcePath) {
		String trimmed = Objects.requireNonNull(resourcePath, "resourcePath").trim();
		if (trimmed.startsWith("/")) {
			return trimmed.substring(1);
		}
		return trimmed;
	}
}
