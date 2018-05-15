package wu.seal.jsontokotlin

import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

/**
 * Converter annotation class declare writer
 * Created by Seal.Wu on 2018/2/7.
 */
interface IImportClassWriter {

    fun insertGsonImportClass(project: Project?, editFile: Document)


    fun insertJackSonImportClass(project: Project?, editFile: Document)


    fun insertFastJsonImportClass(project: Project?, editFile: Document)


    fun insertImportClassCode(project: Project?, editFile: Document)

    fun insertMoShiImportClass(project: Project?, editFile: Document)

    fun insertLoganSquareImportClass(project: Project?, editFile: Document)

    fun insertCustomImportClass(project: Project?, editFile: Document)
}