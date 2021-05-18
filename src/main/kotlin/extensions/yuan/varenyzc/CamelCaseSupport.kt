package extensions.yuan.varenyzc

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.utils.LogUtil
import java.lang.StringBuilder
import javax.swing.JPanel

object CamelCaseSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "top.varenyzc.camel_case_enable"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            checkBox(
                "Enable Build From JsonObject",
                getConfig(configKey).toBoolean()
            ) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            }()
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {
            LogUtil.i(kotlinClass.toString())
            return if (getConfig(configKey).toBoolean()) {
                val originProperties = kotlinClass.properties
                val newProperties = originProperties.map {
                    val oldName = it.name
                    if (oldName.isNotEmpty() && oldName.contains("_")) {
                        val newName = StringBuilder().run {
                            val list = oldName.split("_")
                            for (s in list) {
                                if (this.isEmpty()) {
                                    append(s)
                                } else {
                                    append(s.substring(0, 1).toUpperCase())
                                    append(s.substring(1).toLowerCase())
                                }
                            }
                            toString()
                        }
                        it.copy(name = newName)
                    } else it
                }
                kotlinClass.copy(properties = newProperties)
            } else {
                kotlinClass
            }
        } else {
            return kotlinClass
        }
    }
}