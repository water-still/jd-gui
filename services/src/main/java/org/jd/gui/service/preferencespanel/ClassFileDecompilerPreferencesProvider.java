/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.preferencespanel;

import org.jd.gui.spi.PreferencesPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ClassFileDecompilerPreferencesProvider extends JPanel implements PreferencesPanel {
    protected static final String ESCAPE_UNICODE_CHARACTERS = "ClassFileDecompilerPreferences.escapeUnicodeCharacters";
    protected static final String REALIGN_LINE_NUMBERS = "ClassFileDecompilerPreferences.realignLineNumbers";
    protected static final String DECOMPILE_ENGINE = "ClassFileDecompilerPreferences.decompileEngine";
    
    protected static final String ENGINE_JDCORE = "JD-CORE";
    protected static final String ENGINE_CFR = "CFR";
    protected static final String ENGINE_PROCYON = "Procyon";
    protected static final String DECOMPILERS[] = {ENGINE_JDCORE, ENGINE_CFR, ENGINE_PROCYON};

    protected PreferencesPanel.PreferencesPanelChangeListener listener = null;
    protected JCheckBox escapeUnicodeCharactersCheckBox;
    protected JCheckBox realignLineNumbersCheckBox;
    protected JLabel selectDecompiler;
    protected JComboBox decompileEngine;

    public ClassFileDecompilerPreferencesProvider() {
        super(new GridLayout(0,1));

        escapeUnicodeCharactersCheckBox = new JCheckBox("Escape unicode characters");
        realignLineNumbersCheckBox = new JCheckBox("Realign line numbers");
        selectDecompiler = new JLabel("Select Decompile Engine: ");
        decompileEngine = new JComboBox(DECOMPILERS);

        add(escapeUnicodeCharactersCheckBox);
        add(realignLineNumbersCheckBox);
        add(selectDecompiler);
        add(decompileEngine);
    }

    // --- PreferencesPanel --- //
    @Override public String getPreferencesGroupTitle() { return "Decompiler"; }
    @Override public String getPreferencesPanelTitle() { return "Class file"; }
    @Override public JComponent getPanel() { return this; }

    @Override public void init(Color errorBackgroundColor) {}

    @Override public boolean isActivated() { return true; }

    @Override
    public void loadPreferences(Map<String, String> preferences) {
        escapeUnicodeCharactersCheckBox.setSelected("true".equals(preferences.get(ESCAPE_UNICODE_CHARACTERS)));
        realignLineNumbersCheckBox.setSelected(!"false".equals(preferences.get(REALIGN_LINE_NUMBERS)));
        String engine = preferences.get(DECOMPILE_ENGINE);
        if (engine == null) {
            decompileEngine.setSelectedItem(ENGINE_JDCORE);
        } else {
            decompileEngine.setSelectedItem(engine);
        }
    }

    @Override
    public void savePreferences(Map<String, String> preferences) {
        preferences.put(ESCAPE_UNICODE_CHARACTERS, Boolean.toString(escapeUnicodeCharactersCheckBox.isSelected()));
        preferences.put(REALIGN_LINE_NUMBERS, Boolean.toString(realignLineNumbersCheckBox.isSelected()));
        preferences.put(DECOMPILE_ENGINE, decompileEngine.getSelectedItem().toString());
    }

    @Override public boolean arePreferencesValid() { return true; }

    @Override public void addPreferencesChangeListener(PreferencesPanel.PreferencesPanelChangeListener listener) {}
}
