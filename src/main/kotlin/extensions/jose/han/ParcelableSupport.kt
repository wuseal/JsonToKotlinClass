package extensions.jose.han

import com.intellij.ui.layout.panel
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Method
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.utils.LogUtil
import javax.swing.JCheckBox
import javax.swing.JPanel

object ParcelableSupport : Extension() {


    val configKey = "jose.han.add_parcelable_enable"

    override fun createUI(): JPanel {

        val checkBox = JCheckBox("Enable Parcelable To The Class").apply {
            isSelected = ParcelableSupport.getConfig(configKey).toBoolean()
            addActionListener {
                ParcelableSupport.setConfig(configKey, isSelected.toString())
            }
        }

        return panel {
            row {
                checkBox()
            }
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        return if (ParcelableSupport.getConfig(ParcelableSupport.configKey).toBoolean()) {
            val contructMethod = Method();
            contructMethod.code = getContructCode(kotlinDataClass.properties)
            val describeMethod = Method()
            describeMethod.code = getDescribeMethodCode()
            var writeToParcelMethod = Method()
            writeToParcelMethod.code = getWriteToParcelMethodCode(kotlinDataClass.properties)
            var creatorMethod = Method()
            creatorMethod.code = getCreatorMethod(kotlinDataClass.name)

            return kotlinDataClass.copy(parentClassTemplate = "Parcelable", methods = listOf(contructMethod,
                    describeMethod, writeToParcelMethod, creatorMethod))
        } else {
            kotlinDataClass
        }
    }

    fun getContructCode(properties: List<Property>): String {
        val code = buildString {
            val blankMethod = "    "
            append(blankMethod).append("constructor(source: Parcel) : this(").append("\n")
            val blankRead = "            "
            properties.forEach { p ->
                when (p.type) {
                    "String" -> {
                        append(blankRead).append("source.readString()")
                        if (!p.isLast) append(",")
                        append("\n")
                    }
                    "Int" -> {
                        append(blankRead).append("source.readInt()")
                        if (!p.isLast) append(",")
                        append("\n")
                    }
                    "Float" -> {
                        append(blankRead).append("source.readFloat()")
                        if (!p.isLast) append(",")
                        append("\n")
                    }
                    "Double" -> {
                       append(blankRead).append("source.readDouble()")
                       if (!p.isLast) append(",")
                       append("\n")
                    }
                    "Boolean" -> {
                        append(blankRead).append("1 == source.readInt()")
                        if (!p.isLast) append(",")
                        append("\n")
                    }
                    "List<String>" -> {
                        append(blankRead).append("source.createStringArrayList()")
                        if (!p.isLast) append(",")
                        append("\n")
                    }
                    "List<Int>" -> {
                        append(blankRead).append("ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) }")
                        if (!p.isLast) append(",")
                        append("\n")
                    }
                }
                if(p.refTypeId!=-1){
                    val name = p.name
                    append(blankRead).append("$name = in.readParcelable($name.class.getClassLoader())")
                    if (!p.isLast) append(",")
                    append("\n")
                }
            }
            append(blankMethod).append(")").append("\n")
        }
        return code;
    }

    fun getDescribeMethodCode(): String {
        var code = buildString {
            val blankMethod = "    "
            append(blankMethod).append("override fun describeContents() = 0").append("\n")
        }
        return code
    }

    fun getWriteToParcelMethodCode(properties: List<Property>): String {
        var code = buildString {
            val blankMethod = "    "
            append(blankMethod).append("override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {").append("\n")
            val blankWrite = "        "
            properties.forEach { p ->
                val name = p.name
                when (p.type) {
                    "String" -> {
                        append(blankWrite).append("writeString($name)").append("\n")
                    }
                    "Int" -> {
                        append(blankWrite).append("writeInt($name)").append("\n")
                    }
                    "Float" -> {
                        append(blankWrite).append("writeFloat($name)").append("\n")
                    }
                    "Double" -> {
                        append(blankWrite).append("writeDouble($name)").append("\n")
                    }
                    "Boolean" -> {
                        append(blankWrite).append("writeInt((if ($name) 1 else 0))").append("\n")
                    }
                    "List<String>" -> {
                       append(blankWrite).append("writeStringList($name)").append("\n")
                     }
                    "List<Int>" -> {
                        append(blankWrite).append("writeList($name)").append("\n")
                    }
                }
                if(p.refTypeId!=-1){
                    append(blankWrite).append("writeParcelable(this.$name, flags)").append("\n")
                }
            }
            append(blankMethod).append("}").append("\n")
        }
        return code
    }

    fun getCreatorMethod(name: String): String {
        var code = buildString {
            val blankMethod = "    "
            val blankWrite = "        "
            val blankRead = "            "
            append(blankMethod).append("companion object {").append("\n")
            append(blankWrite).append("@JvmField").append("\n")
            append(blankWrite).append("val CREATOR: Parcelable.Creator<$name> = object:Parcelable.Creator<$name>").append("{").append("\n")
            append(blankRead).append("override fun createFromParcel(source: Parcel): $name = $name(source)").append("\n")
            append(blankRead).append("override fun newArray(size: Int): Array<$name?> = arrayOfNulls(size)").append("\n")
            append(blankWrite).append("}").append("\n")
            append(blankMethod).append("}").append("\n")
            append("}")
        }

        return code
    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import android.os.Parcel".append("import android.os.Parcelable")

        return if (ParcelableSupport.getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }

}