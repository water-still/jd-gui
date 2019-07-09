package org.jd.gui.view.component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.jd.core.v1.api.loader.LoaderException;
import org.jd.gui.util.decompiler.ContainerLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;


class DataSource implements ClassFileSource {
    ContainerLoader loader;
    byte[] data;
    private String name;

    public DataSource(ContainerLoader loader, byte[] data, String name) {
        this.loader = loader;
        this.data = data;
        this.name = name;
    }

    @Override
    public void informAnalysisRelativePathDetail(String usePath, String classFilePath) {
    }

    @Override
    public Collection<String> addJar(String jarPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPossiblyRenamedPath(String s) {
        return s;
    }

    @Override
    public Pair<byte[], String> getClassFileContent(String s) throws IOException {
        if (s.equals(name)) {
            return Pair.make(data, name);
        }

        byte[] data;
        assert s.endsWith(".class");
        String internalName = s.substring(0, s.length() - 6);
        try {
            data = loader.load(internalName);
        } catch (LoaderException e) {
            throw new FileNotFoundException("Not reading " + s);
        }
        return Pair.make(data, internalName);
    }
}