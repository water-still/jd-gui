/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use, 
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.fileloader;

import org.jd.gui.api.API;
import org.jd.gui.util.exception.ExceptionUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;

public class DirFileLoaderProvider extends AbstractFileLoaderProvider {
    protected static final String[] EXTENSIONS = { " " };

    @Override public String[] getExtensions() { return EXTENSIONS; }
    @Override public String getDescription() { return "Directory (*)"; }

    @Override
    public boolean accept(API api, File file) {
        return file.exists() && file.isDirectory() && file.canRead();
    }

    @Override
    public boolean load(API api, File file) {
        return load(api, file, file.toPath()) != null;
    }
}
