package wu.seal.jsontokotlin.utils

import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project

/**
 *  annotation and so on class import declaration writer
 * Created by Seal.Wu on 2018/2/7.
 */
interface IClassImportDeclarationWriter {

    fun insertImportClassCode(project: Project?, editFile: Document)

}