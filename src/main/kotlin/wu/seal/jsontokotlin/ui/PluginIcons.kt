package wu.seal.jsontokotlin.ui

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon


/**
 * Created by Seal.Wu on 2018/4/18.
 */
object PluginIcons {
    @JvmField
    val generateKotlinFileActionIcon: Icon = {
        val text = ""
         IconLoader.getIcon("/icons/action.png")
    }()
}