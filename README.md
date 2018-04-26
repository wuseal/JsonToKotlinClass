![JsonToKotlinClass](https://github.com/wuseal/JsonToKotlinClass/blob/master/title.png)

[![GitHub release](https://img.shields.io/github/release/wuseal/JsonToKotlinClass.svg?style=flat)](https://github.com/wuseal/JsonToKotlinClass/releases)
[![GitHub stars](https://img.shields.io/github/stars/wuseal/JsonToKotlinClass.svg?style=social&label=Stars&style=plastic)](https://github.com/wuseal/JsonToKotlinClass/stargazers)
[![JetBrains Plugin Download](https://img.shields.io/jetbrains/plugin/d/9960-json-to-kotlin-class-jsontokotlinclass-.svg)]
[![GitHub closed issues](https://img.shields.io/github/issues-closed/wuseal/JsonToKotlinClass.svg)](https://github.com/wuseal/JsonToKotlinClass/issues?q=is%3Aissue+is%3Aclosed)
[![license](https://img.shields.io/github/license/wuseal/JsonToKotlinClass.svg)](https://github.com/wuseal/JsonToKotlinClass/blob/master/LICENSE)

[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)
[![IntelliJ Idea Plugin](https://img.shields.io/badge/plugin-IntelliJ%20%20Idea-blue.svg)](https://plugins.jetbrains.com/plugin/9960-jsontokotlinclass)
[![Android Studio Plugin](https://img.shields.io/badge/plugin-AndroidStudio-green.svg)](
https://plugins.jetbrains.com/plugin/9960-jsontokotlinclass)

# JsonToKotlinClass

Hi,Welcome to come to see me!
I am a plugin for Kotlin generate Kotlin data class code from a JSON string
also as say a plugin for Kotlin to convert JSON String into Kotlin data class code (Json to Kotlin)

### Overview
Hi,This is a very cool tool for Kotlin developers ,It can convert the Json String into Kotlin Data Class code ,and paste it into your editor file ,The tool could recognize the Primitive Type of Json String and make Type Identifier respectively ,It taste easily ,Just have test,guys! Just press shortcut key `ALT` + `K` for Windows or `Option` + `K` for Mac,And then,start your Kotlin program travel ! JsonToKotlinClass make program more happy!

### Easy Use
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18032.png)

#### Want to generate classes code in inner class model? Do like this.
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18031.png)

#### Want to generate Kotlin data class file directory? Do like this.
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18030.png)

#### Want to generate Kotlin data classes in single file directory? Do like this.
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18029.png)

### Usage
* Search 'JsonToKotlinClass' in Intellij Idea Plugin Repositroy Or AndroidStudio Plugin Repository And Install it.</br>
    File --> Settings --> Plugins --> Browse Repositories -->Search JsonToKotlinClass
* Restart your Develop tools 
* Press shortcut key `ALT` + `K` for Windows or `Option` + `K` for Mac Or right click on package -> `New`->`Kotlin data clas file from JSON`　And Then you will know how to use

### Features
* Generate Kotlin data class from any legal JSON text.
* Generate Kotlin data class File form any legal JSON text when right click on directory and select `New` -> `Kotlin data class File from JSON`.
* Support JSON Serialize Lib Annotation(Gson,Jackson,Fastjson,MoShi and LoganSquare,kotlinx.serialization(default custom value))
* Support customize your own Annotation
* Support init property with default value
* Support make property type to be nullable(?)
* Support auto make sure property type to be nullable(?) or not 
* Support auto rename property name to be camelCase when select a target JSON lib Annotation．
* Support generate kotlin data class code in split model
* Support generate kotlin data class code in inner class model
* Support generate kotlin data class File in inner class model 
* Support generate kotlin data class File in split files 
* Support format any legal JSON string
* Support Generate Map Type when json key is primitive type

### Generate Example 
* Default

    ```kotlin
        data class FD(
            val programmers: List<Programmer>,
            val authors: List<Author>,
            val musicians: List<Musician>
        )
        
        data class Musician(
            val firstName: String, 
            val lastName: String, 
            val instrument: String 
        )
        
        data class Author(
            val firstName: String, 
            val lastName: String, 
            val genre: String 
        )
        
        data class Programmer(
            val firstName: String, 
            val lastName: String, 
            val email: String 
        )

    ```
* Example with gson option on and init with default value option on in settings

    ```kotlin
    
       data class TestData(
           @SerializedName("ticketInfo") val ticketInfo: TicketInfo = TicketInfo(),
           @SerializedName("trainInfo") val trainInfo: TrainInfo = TrainInfo(),
           @SerializedName("trainScheduleHead") val trainScheduleHead: List<String> = listOf(),
           @SerializedName("extInfo") val extInfo: ExtInfo = ExtInfo(),
           @SerializedName("trainScheduleBody") val trainScheduleBody: List<TrainScheduleBody> = listOf()
       )
       
       data class TrainScheduleBody(
           @SerializedName("mxl") val mxl: Long = 0, 
           @SerializedName("content") val content: List<Int> = listOf()
       )
       
       data class TrainInfo(
           @SerializedName("T110") val t110: T110 = T110()
       )
     
  
    ```

### Chinese Detail Document (中文文档)
* http://blog.csdn.net/wuseal/article/details/77508585

### Others
* Welcome anyone to raise new issue.
* Welcome anyone to push a pull request to improve me.

### Thanks
* Thank [@davidbilik](https://github.com/davidbilik) give me first awesome advice.
* Thank [@cgoodroe](https://github.com/cgoodroe) raise many awesome issues for me,Help me improve myself
* Thank [@wangzhenguang](https://github.com/wangzhenguang) remains me the details of problem

### Find me useful ? :heart:
* Support me by clicking the :star: button on the upper right of this page. :v:
* Spread to others to let more people have a better develope expierience :heart:
