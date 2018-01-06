[![GitHub release](https://img.shields.io/github/release/wuseal/JsonToKotlinClass.svg?style=flat)](https://github.com/wuseal/JsonToKotlinClass/releases)
[![GitHub stars](https://img.shields.io/github/stars/wuseal/JsonToKotlinClass.svg?style=social&label=Stars&style=plastic)](https://github.com/wuseal/JsonToKotlinClass/stargazers)
[![GitHub issues](https://img.shields.io/github/issues/wuseal/JsonToKotlinClass.svg)](https://github.com/wuseal/JsonToKotlinClass/issues)
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

### Functions
* Generate Kotlin data class from any legal JSON text.
* Support JSON Serialize Lib Annotation(Gson,Jackson,Fastjson,MoShi and LoganSquare)
* Support customize your own Annotation
* Support init property with default value
* Support make property type to be nullable(?)
* Support auto rename property name to be legal when select a target JSON lib.[See demo](#configure-with-gson-support-on-and-init-with-deault-value-on-and-comment-off)

### How to use
* Search 'JsonToKotlinClass' in Intellij Idea Plugin Repositroy Or AndroidStudio Plugin Repository And Install it.</br>
    File --> Settings --> Plugins --> Browse Repositories -->Search JsonToKotlinClass
* Restart your Develop tools 
* Press shortcut key `ALT` + `K` for Windows or `Option` + `K` for Mac And Then you will know how to use
### Demos
#### Default :
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_17468.png)

#### Configure with Gson support on and init with deault value on and comment off
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_17469.png)

#### Config with custome your own annotations support on and init with deault value on

![alt text](https://plugins.jetbrains.com/files/9960/screenshot_17470.png)

### Generate Example
* Example with none json lib support and comment option on

    ```kotlin
        data class FD(
                val programmers: List<Programmer>,
                val authors: List<Author>,
                val musicians: List<Musician>
        )
        
        data class Musician(
                val firstName: String, //Eric
                val lastName: String, //Clapton
                val instrument: String //guitar
        )
        
        data class Author(
                val firstName: String, //Isaac
                val lastName: String, //Asimov
                val genre: String //science fiction
        )
        
        data class Programmer(
                val firstName: String, //Brett
                val lastName: String, //McLaughlin
                val email: String //aaaa
        )

    ```
* Example with gson option on and init with default value option on

    ```kotlin
    
       data class TestData(
       		@SerializedName("ticketInfo") val ticketInfo: TicketInfo = TicketInfo(),
       		@SerializedName("trainInfo") val trainInfo: TrainInfo = TrainInfo(),
       		@SerializedName("trainScheduleHead") val trainScheduleHead: List<String> = listOf(),
       		@SerializedName("extInfo") val extInfo: ExtInfo = ExtInfo(),
       		@SerializedName("trainScheduleBody") val trainScheduleBody: List<TrainScheduleBody> = listOf()
       )
       
       data class TrainScheduleBody(
       		@SerializedName("mxl") val mxl: Long = 0, //12490639969101
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
