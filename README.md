# JsonToKotlinClass

Hi,Welcome to come to see me!
I am a plugin for Kotlin generate Kotlin data class code from a json string

I am a plugin for Kotlin to convert Json String into Kotlin data class code (Json to Kotlin)

### Update Log
#### 1.2
* Add support for generate anotations for target json lib --gson
* Add Comment option to switch the comment content to append

#### 1.1
* Add property keyword config setting like ('var' or 'val'),the default keyword changed to 'val'.

### How to use
* Search 'JsonToKotlin' in Intellij Idea Plugin Repositroy Or AndroidStudio Repository And Install it.
* Restart your Develop tools 
* Press short key ALT + K And Then you will know how to use

![alt text](https://plugins.jetbrains.com/files/9960/screenshot_17276.png)

### Generate Example
* example with none json lib support and comment option on

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
* example with gson option on

    ```kotlin
        data class FD(
        		@SerializedName("214123addre++/-*ssbook") val addressbook: List<addressbook>
        )
        
        data class addressbook(
        		@SerializedName("*-/-+address") val address: address,
        		@SerializedName("name") val name: String, //Ann Michaels
        		@SerializedName("phoneNumbers") val phoneNumbers: List<String>
        )
        
        data class address(
        		@SerializedName("city") val city: List<String>,
        		@SerializedName("*-/32432-*/4street") val street: List<String>,
        		@SerializedName("zip") val zip: List<Int>
        )
    ```

### More Detail Document
* http://blog.csdn.net/wuseal/article/details/77508585

### Others
* Welcome anyone to raise new issue.
* Welcome anyone to push a pull request to improve me.

### Find me useful ? :heart:
* Support me by clicking the :star: button on the upper right of this page. :v:
