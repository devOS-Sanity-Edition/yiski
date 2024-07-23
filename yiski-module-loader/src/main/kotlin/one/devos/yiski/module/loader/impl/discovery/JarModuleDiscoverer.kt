package one.devos.yiski.module.loader.impl.discovery

import one.devos.yiski.module.loader.api.discovery.ModuleDiscoverer
import java.nio.file.FileVisitOption
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.EnumSet

class JarModuleDiscoverer(val baseDirectory: Path) : ModuleDiscoverer {

    override fun discover(): Set<Path> {
        val file = baseDirectory.toFile()
        if (!file.exists() && !file.mkdirs()) {
            throw IllegalStateException("Failed to create directory!")
        }

        if (!file.isDirectory) {
            throw IllegalStateException("Base directory is not a directory!")
        }

        val result = mutableSetOf<Path>()
        Files.walkFileTree(baseDirectory, EnumSet.of(FileVisitOption.FOLLOW_LINKS), 1, FileVisitor(result))
        return result
    }

}

private class FileVisitor(
    val value: MutableSet<Path>
) : SimpleFileVisitor<Path>() {

    override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        if (file.isModFile())
            value.add(file)

        return FileVisitResult.CONTINUE
    }

    private fun Path.isModFile(): Boolean {
        if (!Files.isRegularFile(this))
            return false

        try {
            if (Files.isHidden(this))
                return false
        } catch (t: Throwable) {
            return false
        }

        val fileName = fileName.toString()
        return fileName.endsWith(".jar") && !fileName.startsWith(".")
    }

}
