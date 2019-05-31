package org.jd.gui.view.component;


import com.strobel.assembler.InputTypeLoader;
import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;

import java.util.HashMap;
import java.util.Map;

public class ProcyonTypeLoader implements ITypeLoader {

    private final Map<String, byte[]> importantData = new HashMap<>();

    private final InputTypeLoader backLoader = new InputTypeLoader();

    public ProcyonTypeLoader(Map<String, byte[]> importantClasses) {
        this.importantData.putAll(importantClasses);
    }

    @Override
    public boolean tryLoadType(String s, Buffer buffer) {
        if (importantData.containsKey(s)) {
            byte[] data = importantData.get(s);
            buffer.putByteArray(data, 0, data.length);
            buffer.position(0);
            return true;
        } else {
            return false;
        }

        //here will try to load file from other file, if a very big file, takes too long time 
        //return backLoader.tryLoadType(s, buffer);
    }
}