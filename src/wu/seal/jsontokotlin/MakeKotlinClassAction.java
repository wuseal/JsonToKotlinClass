package wu.seal.jsontokotlin;

import com.google.gson.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBDimension;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.IllegalFormatFlagsException;

/**
 * Created by Seal.Wu on 2017/8/18.
 */
public class MakeKotlinClassAction extends AnAction {
    // If you register the action from Java code, this constructor is used to set the menu item name
    // (optionally, you can specify the menu description and an icon to display next to the menu item).
    // You can omit this constructor when registering the action in the plugin.xml file.
    public MakeKotlinClassAction() {
        // Set the menu item name.
        super("MakeKotlinClass");
        // Set the menu item name, description and icon.
        // super("Text _Boxes","Item description",IconLoader.getIcon("/Mypackage/icon.png"));
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        final Caret caret = event.getData(PlatformDataKeys.CARET);
        final Editor editor = event.getData(PlatformDataKeys.EDITOR_EVEN_IF_INACTIVE);
        if (editor == null) {
            Messages.showWarningDialog("Please open a file in editor state for insert Kotlin code!", "No Editor File");
            return;
        }
        final String className = Messages.showInputDialog(project, "Please input the Class Name for Insert", "Input ClassName", Messages.getInformationIcon());
        if (className == null || className.isEmpty()) {
            return;
        }
        final Messages.InputDialog inputDialog = new Messages.InputDialog(project, "Please input the Json Data", "Input Json"
                , Messages.getInformationIcon(), "", new InputValidator() {
            @Override
            public boolean checkInput(String inputString) {
                try {
                    JsonElement jsonElement = new JsonParser().parse(inputString);
                    return jsonElement.isJsonObject() || jsonElement.isJsonArray();
                } catch (JsonSyntaxException e) {
                    return false;
                }
            }

            @Override
            public boolean canClose(String inputString) {
                return true;
            }
        }) {
            @NotNull
            protected JPanel createMessagePanel() {
                JPanel messagePanel = new JPanel(new BorderLayout());
                if (myMessage != null) {
                    JComponent textComponent = createTextComponent();
                    messagePanel.add(textComponent, BorderLayout.NORTH);
                }

                myField = createTextFieldComponent();


                messagePanel.add(createScrollableTextComponent(), BorderLayout.CENTER);
                JButton settingButton = new JButton("Config Settings");
                settingButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ConfigSettingDialog(false).show();
                    }
                });
                JPanel settingContainer = new JPanel();
                BoxLayout boxLayout = new BoxLayout(settingContainer, BoxLayout.LINE_AXIS);
                settingContainer.setLayout(boxLayout);
                settingButton.setHorizontalAlignment(SwingConstants.RIGHT);
                settingContainer.add(settingButton);
                messagePanel.add(settingContainer, BorderLayout.SOUTH);
                return messagePanel;
            }

            @Override
            protected JTextComponent createTextFieldComponent() {
                JTextArea jTextArea = new JTextArea(15, 100);
                jTextArea.setMinimumSize(new JBDimension(800, 500));
                jTextArea.setMaximumSize(new JBDimension(1000, 700));
                jTextArea.setLineWrap(true);
                jTextArea.setWrapStyleWord(true);
                jTextArea.setAutoscrolls(true);
                return jTextArea;
            }


            protected JComponent createScrollableTextComponent() {
                return new JBScrollPane(myField);
            }
        };
        inputDialog.show();
        String jsonString = inputDialog.getInputString();
        if (jsonString == null || jsonString.isEmpty()) {
            return;
        }
        final Document document = editor.getDocument();
        ImportClassWriter.INSTANCE.insertImportClassCode(project, document);

        final KotlinMaker maker;
        try {
            maker = new KotlinMaker(className, jsonString);
        } catch (IllegalFormatFlagsException e) {
            e.printStackTrace();
            Messages.showErrorDialog(e.getMessage(), "Unsupport Json");
            return;
        }

        CommandProcessor.getInstance().executeCommand(project, new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {

                        int offset = 0;

                        if (caret != null) {

                            offset = caret.getOffset();
                            if (offset == 0) {
                                offset = document.getTextLength() - 1;
                            }
                        } else {
                            offset = document.getTextLength() - 1;
                        }
                        document.insertString(Math.max(offset, 0), maker.makeKotlinData());

                    }
                });
            }
        }, "insertKotlin", "JsonToKotlin");
        Messages.showMessageDialog(project, "Kotlin Code insert successfully!", "Information", Messages.getInformationIcon());
    }
}