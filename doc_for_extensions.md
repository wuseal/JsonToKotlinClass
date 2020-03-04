If you want to customize you own `JsonToKotlinClass` Plugin or want to contribute some new featrues for this project, This guide will help you.

#### Customize Your Own Plugin

Customize your own plugin by these steps, and you will have your own plugin, in these guide we will create a extension that will make all the property name to be all upper case

1. Fork `JsonToKotlinClass` from Github

2. Clone your forked project to local

3. Open Project with Intellij Idea

4. Create package under ..src/main/kotlin/extensions

   ![image](https://user-images.githubusercontent.com/9211902/51250876-ccb8cb00-19d2-11e9-9620-9c45c8975bfa.png)

   Package name could be your own domain name. in this case we will use `wu.seal`

5. Create a Extension object class file under your created package

   ![image](https://user-images.githubusercontent.com/9211902/51249232-a47a9d80-19cd-11e9-866f-b7e283da4853.png)

6. Now the created file would like this:

   ![image](https://user-images.githubusercontent.com/9211902/51249378-0fc46f80-19ce-11e9-8abd-0c926e8a726f.png)

7. Add interfaces to be implemente

   ![image](https://user-images.githubusercontent.com/9211902/51249511-75b0f700-19ce-11e9-9e74-ab014d3d77e3.png)

8. Implement all the needed interface

   ```kotlin
   package extensions.wu.seal

   import extensions.Extension
   import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
   import javax.swing.JPanel

   object AllUpperCase :Extension(){

       override fun createUI(): JPanel {

       }

       override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

       }
   }
   ```

9. Then we create a new UI for switching to enable all property name to be all upper case

   ```kotlin
    override fun createUI(): JPanel {

           val configKey = "wu.seal.all_to_be_upper_case"

           val checkBox = JCheckBox("Make all properties name to be all upper case").apply {
               isSelected = getConfig(configKey).toBoolean()
               addActionListener {
                   setConfig(configKey, isSelected.toString())
               }
           }

           return panel {
               row {
                   checkBox()
               }
           }

       }
   ```

10. And make the properties names to be all upper case

    ```kotlin
    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        return if (getConfig(configKey).toBoolean()) {
            //make all properties name to be all upper case
            val newProperties = kotlinDataClass.properties.map { it.copy(name = it.name.toUpperCase()) }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }
    }
    ```

    We use `map`method to create a new properties which all properties names are translate to be all upper case, And copy the incoming parameter `kotlinDataClass`  with replacing the new created properties, and return it to the system.

11. Add the new object class into `ExtensionsCollector` object class which inside extensions package

    ```kotlin
    /**
     * extension collect, all extensions will be hold by this class's extensions property
     */
    object ExtensionsCollector {
        /**
         * all extensions
         */
        val extensions = listOf(
                PropertyPrefixSupport,
                PropertySuffixSupport,
                AllUpperCase
        )
    }
    ```

12. Run the plugin by clicking`runIde`task

    ![image](https://user-images.githubusercontent.com/9211902/51250409-40f26f00-19d1-11e9-962c-7f44fb5549e4.png)

    Then we will see the new IDE opened

13. Create a new project in new opened IDE and create a new Kotlin File for inserting new Kotlin Data Class code, and open `JsonToKotlinClass` Plugin, Then we will see that new Extension was added in the extensions tab,Let's selected the new added checkbox and have a test if it works ok.

    ![image](https://user-images.githubusercontent.com/9211902/51250670-15bc4f80-19d2-11e9-9cee-798489108adf.png)

14. Then Let's see the tested result:

    ![image](https://user-images.githubusercontent.com/9211902/51250749-5a47eb00-19d2-11e9-9824-c8f9ec0cc664.png)

    ```kotlin
    import com.google.gson.annotations.SerializedName
    data class TestCode(
        @SerializedName("hello")
        val HELLO: String = ""
    )
    ```

    Yeah! That what we want. Greate we have created the first our style plugin.

    Any Question? Welcome to raise an issue [here](https://github.com/wuseal/JsonToKotlinClass/issues)

    Demo could be found [here](https://github.com/wuseal/JsonToKotlinClass/tree/3.0-extension-demo)


