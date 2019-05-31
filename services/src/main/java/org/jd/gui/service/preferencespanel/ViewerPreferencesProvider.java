/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.preferencespanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.jd.gui.spi.PreferencesPanel;
import org.jd.gui.util.exception.ExceptionUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class ViewerPreferencesProvider extends JPanel implements PreferencesPanel, DocumentListener {
    protected static final int MIN_VALUE = 2;
    protected static final int MAX_VALUE = 40;
    protected static final String FONT_SIZE_KEY = "ViewerPreferences.fontSize";
    protected static final String THEME = "ViewerPreferences.theme";

    protected static final String THEMES[] = {"dark", "eclipse", "idea", "monokai", "vs"};

    protected PreferencesPanel.PreferencesPanelChangeListener listener = null;
    protected JTextField fontSizeTextField;
    protected Color errorBackgroundColor = Color.RED;
    protected Color defaultBackgroundColor;
    protected JLabel selectTheme;
    protected JComboBox themeBox;

    public ViewerPreferencesProvider() {
        super(new GridLayout(0,1));

        add(new JLabel("Font size (" + MIN_VALUE + ".." + MAX_VALUE + "): "), BorderLayout.WEST);

        fontSizeTextField = new JTextField();
        fontSizeTextField.getDocument().addDocumentListener(this);
        add(fontSizeTextField, BorderLayout.CENTER);

        defaultBackgroundColor = fontSizeTextField.getBackground();

        selectTheme = new JLabel("Select theme: ");
        themeBox = new JComboBox(THEMES);
        add(selectTheme);
        add(themeBox);
    }

    // --- PreferencesPanel --- //
    @Override public String getPreferencesGroupTitle() { return "Viewer"; }
    @Override public String getPreferencesPanelTitle() { return "Appearance"; }
    @Override public JComponent getPanel() { return this; }

    @Override public void init(Color errorBackgroundColor) {
        this.errorBackgroundColor = errorBackgroundColor;
    }

    @Override public boolean isActivated() { return true; }

    @Override
    public void loadPreferences(Map<String, String> preferences) {
        String fontSize = preferences.get(FONT_SIZE_KEY);

        if (fontSize == null) {
            // Search default value for the current platform
            RSyntaxTextArea textArea = new RSyntaxTextArea();

            try {
                Theme theme = Theme.load(getClass().getClassLoader().getResourceAsStream("rsyntaxtextarea/themes/eclipse.xml"));
                theme.apply(textArea);
            } catch (IOException e) {
                assert ExceptionUtil.printStackTrace(e);
            }

            fontSize = String.valueOf(textArea.getFont().getSize());
        }

        fontSizeTextField.setText(fontSize);
        fontSizeTextField.setCaretPosition(fontSizeTextField.getText().length());

        String theme = preferences.get(THEME);
        if (theme == null) {
            themeBox.setSelectedItem("eclipse");
        } else {
            themeBox.setSelectedItem(theme);
        }
    }

    @Override
    public void savePreferences(Map<String, String> preferences) {
        preferences.put(FONT_SIZE_KEY, fontSizeTextField.getText());
        preferences.put(THEME, themeBox.getSelectedItem().toString());
    }

    @Override
    public boolean arePreferencesValid() {
        try {
            int i = Integer.valueOf(fontSizeTextField.getText());
            return (i >= MIN_VALUE) && (i <= MAX_VALUE);
        } catch (NumberFormatException e) {
            assert ExceptionUtil.printStackTrace(e);
            return false;
        }
    }

    @Override
    public void addPreferencesChangeListener(PreferencesPanel.PreferencesPanelChangeListener listener) {
        this.listener = listener;
    }

    // --- DocumentListener --- //
    @Override public void insertUpdate(DocumentEvent e) { onTextChange(); }
    @Override public void removeUpdate(DocumentEvent e) { onTextChange(); }
    @Override public void changedUpdate(DocumentEvent e) { onTextChange(); }

    public void onTextChange() {
        fontSizeTextField.setBackground(arePreferencesValid() ? defaultBackgroundColor : errorBackgroundColor);

        if (listener != null) {
            listener.preferencesPanelChanged(this);
        }
    }
}
