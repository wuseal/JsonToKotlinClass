package wu.seal.jsontokotlin

import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

/**
 * to be a helper to insert Import class declare code
 * Created by Seal.Wu on 2017/9/18.
 */


interface IImportClassWriter {

    fun insertGsonImportClass(project: Project?, editFile: Document)


    fun insertJackSonImportClass(project: Project?, editFile: Document)


    fun insertFastJsonImportClass(project: Project?, editFile: Document)


    fun insertImportClassCode(project: Project?, editFile: Document)


}


object ImportClassWriter : IImportClassWriter {
    override fun insertImportClassCode(project: Project?, editFile: Document) {

        when (ConfigManager.targetJsonConverterLib) {

            TargetJsonConverter.Gson -> insertGsonImportClass(project, editFile)
            TargetJsonConverter.FastJson -> insertFastJsonImportClass(project, editFile)
            TargetJsonConverter.Jackson -> insertFastJsonImportClass(project, editFile)

            else -> {
                println("No need to import any Class code")
            }
        }
    }


    override fun insertFastJsonImportClass(project: Project?, editFile: Document) {
    }

    override fun insertJackSonImportClass(project: Project?, editFile: Document) {
    }

    override fun insertGsonImportClass(project: Project?, editFile: Document) {
        val text = editFile.text
        if (GsonSupporter.gsonAnotationImportString !in text) {

            val packageIndex = text.indexOf("package ")
            val importIndex = Math.max(text.lastIndexOf("import"), packageIndex)
            val insertIndex = if (importIndex == -1) 0 else editFile.getLineEndOffset(editFile.getLineNumber(importIndex))


            executeCouldRollBackAction(project) {
                editFile.insertString(insertIndex, "\n" + GsonSupporter.gsonAnotationImportString + "\n")
            }

        }
    }

}


