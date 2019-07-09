package org.jd.gui.service.sourcesaver;

import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;
import org.jd.core.v1.api.loader.LoaderException;
import org.jd.gui.util.decompiler.ContainerLoader;

import java.util.HashMap;
import java.util.Map;

public class ProcyonTypeLoader implements ITypeLoader {

    private final Map<String, byte[]> importantData = new HashMap<>();
    ContainerLoader loader;

    public ProcyonTypeLoader(Map<String, byte[]> importantClasses, ContainerLoader loader) {
        this.importantData.putAll(importantClasses);
        this.loader = loader;
    }

    @Override
    public boolean tryLoadType(String s, Buffer buffer) {
        if (importantData.containsKey(s)) {
            byte[] data = importantData.get(s);
            buffer.putByteArray(data, 0, data.length);
            buffer.position(0);
            return true;
        } else {
            if (loader.canLoad(s)) {

                try {
                    byte[] data = loader.load(s);
                    buffer.putByteArray(data, 0, data.length);
                    buffer.position(0);
                    return true;
                } catch (LoaderException e) {
                    return false;
                }

            } else {
                return false;
            }
        }

        //here will try to load file from other file, if a very big file, takes too long time
        //return backLoader.tryLoadType(s, buffer);
    }
}