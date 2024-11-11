package github.xCykrix.records;

import java.io.InputStream;

public record Resource(String id, String parentFolder, InputStream resource) {
}
