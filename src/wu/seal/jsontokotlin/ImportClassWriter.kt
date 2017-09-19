package wu.seal.jsontokotlin

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

/**
 * to be a helper to insert Import class declare code
 * Created by Seal.Wu on 2017/9/18.
 */


interface IImportClassWriter {

    fun insertGsonImportClass(project: Project, editFile: Document)


    fun insertJackSonImportClass(project: Project, editFile: Document)


    fun insertFastJsonImportClass(project: Project, editFile: Document)


    fun insertImportClassCode(project: Project, editFile: Document)


}


object ImportClassWriter : IImportClassWriter {
    override fun insertImportClassCode(project: Project, editFile: Document) {

        when (ConfigManager.targetJsonConverterLib) {

            TargetJsonConverter.Gson -> insertGsonImportClass(project, editFile)
            TargetJsonConverter.FastJson -> insertFastJsonImportClass(project, editFile)
            TargetJsonConverter.Jackson -> insertFastJsonImportClass(project, editFile)

            else -> {
                println("No need to import any Class code")
            }
        }
    }


    override fun insertFastJsonImportClass(project: Project, editFile: Document) {
    }

    override fun insertJackSonImportClass(project: Project, editFile: Document) {
    }

    override fun insertGsonImportClass(project: Project, editFile: Document) {
        val text = editFile.text
        if (GsonSupporter.gsonAnotationImportString !in text) {

            val index = Math.max(text.lastIndexOf("import"), 0)
            val tobeInsertEndline = editFile.getLineNumber(index)
            val insertIndex = if (index == 0) index else editFile.getLineEndOffset(tobeInsertEndline)

            CommandProcessor.getInstance().executeCommand(project, {
                ApplicationManager.getApplication().runWriteAction {
                    editFile.insertString(insertIndex, "\n" + GsonSupporter.gsonAnotationImportString + "\n")

                }
            }, "insertKotlin", "JsonToKotlin")

        }
    }

}


