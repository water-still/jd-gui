package org.jd.gui.service.sourcesaver;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;


class DataSource implements ClassFileSource {
    byte[] data;
    private String name;

    public DataSource(byte[] data, String name) {
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
        if (!s.equals(name)) {
            throw new FileNotFoundException("Not reading " + s);
        }
        return Pair.make(data, name);
    }
}