# JsonToKotlinClass

Hi,Welcome to come to see me!
I am a plugin for Kotlin generate Kotlin data class code from a json string

I am a plugin for Kotlin to convert Json String into Kotlin data class code (Json to Kotlin)

### Update Log
#### 1.3
 * Add property init with default value option in property tab.(Recommend to select,with this selected you can avoid NullPointException in the following time,You can also unSelect the nullable option to avoid null check when use the data value).
 * Add property could be nullable option in property tab.
 * Fix a bug when the property name is 'list' and it's type is array then the plugin will broken.
 * Beautify dialog layout.

#### 1.2.1
* Fix insert improt class code upon package declare
* Format property name and class name to camelcase name

#### 1.2
* Add support for generate anotations for target json lib --gson
* Add Comment option to switch the comment content to append

#### 1.1
* Add property keyword config setting like ('var' or 'val'),the default keyword changed to 'val'.

### How to use
* Search 'JsonToKotlin' in Intellij Idea Plugin Repositroy Or AndroidStudio Plugin Repository And Install it.
* Restart your Develop tools 
* Press short key ALT + K And Then you will know how to use

#### Default:
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_17340.png)

#### Config with Gson support on and init with deault value on

![alt text](https://plugins.jetbrains.com/files/9960/screenshot_17359.png)

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

### More Detail Document
* http://blog.csdn.net/wuseal/article/details/77508585

### Others
* Welcome anyone to raise new issue.
* Welcome anyone to push a pull request to improve me.

### Thanks
* Thank [@davidbilik](https://github.com/davidbilik) give me first awesome advice.
* Thank [@cgoodroe](https://github.com/cgoodroe) raise many awesome issues for me,Help me improve myself
* Thank [@wangzhenguang](https://github.com/wangzhenguang) remaining the details of the problem

### Find me useful ? :heart:
* Support me by clicking the :star: button on the upper right of this page. :v:
