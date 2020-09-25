package me.shib.test.analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

public class PermissionAnalyzer implements Analyzer {

    private static final Set<PosixFilePermission> insecureFilePermissions = new HashSet<>();

    static {
        insecureFilePermissions.add(PosixFilePermission.OWNER_EXECUTE);
        insecureFilePermissions.add(PosixFilePermission.GROUP_WRITE);
        insecureFilePermissions.add(PosixFilePermission.GROUP_EXECUTE);
        insecureFilePermissions.add(PosixFilePermission.OTHERS_READ);
        insecureFilePermissions.add(PosixFilePermission.OTHERS_WRITE);
        insecureFilePermissions.add(PosixFilePermission.OTHERS_EXECUTE);
    }

    @Override
    public String analyze(File file) throws IOException {
        PosixFileAttributeView posixView = Files.getFileAttributeView(file.toPath(), PosixFileAttributeView.class);
        PosixFileAttributes attributes = posixView.readAttributes();
        for (PosixFilePermission permission : attributes.permissions()) {
            if (insecureFilePermissions.contains(permission)) {
                return "Insecure File Permissions found: " + PosixFilePermissions.toString(attributes.permissions());
            }
        }
        return null;
    }
}
