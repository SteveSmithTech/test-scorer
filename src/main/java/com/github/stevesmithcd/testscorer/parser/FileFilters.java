package com.github.stevesmithcd.testscorer.parser;

import java.io.File;
import java.io.FileFilter;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

public final class FileFilters {
    private FileFilters() {}

    static FileFilter isSerenityResultsFile() { return file -> file.getName().equals("results.csv"); }

    public static FileFilter isSerenityResultsDirectory() { return allOf(isDirectory(), hasChild(isSerenityResultsFile())); }

    private static FileFilter hasChild(FileFilter fileFilter) { return file -> ofNullable(file.listFiles(fileFilter)).map(a -> a.length > 0).orElse(false); }

    private static FileFilter allOf(FileFilter... fileFilters) { return file -> stream(fileFilters).allMatch(f -> f.accept(file)); }

    private static FileFilter isDirectory() { return File::isDirectory; }
}
