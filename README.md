![JsonToKotlinClass](https://github.com/wuseal/JsonToKotlinClass/blob/master/title.png)

[![Build Status](https://travis-ci.org/wuseal/JsonToKotlinClass.svg?branch=master)](https://travis-ci.org/wuseal/JsonToKotlinClass)
[![GitHub release](https://img.shields.io/github/release/wuseal/JsonToKotlinClass.svg?style=flat)](https://github.com/wuseal/JsonToKotlinClass/releases)
[![GitHub stars](https://img.shields.io/github/stars/wuseal/JsonToKotlinClass.svg?style=social&label=Stars&style=plastic)](https://github.com/wuseal/JsonToKotlinClass/stargazers)
[![JetBrains Plugin Download](https://img.shields.io/jetbrains/plugin/d/9960-json-to-kotlin-class-jsontokotlinclass-.svg)](https://plugins.jetbrains.com/plugin/9960-jsontokotlinclass)
[![GitHub closed issues](https://img.shields.io/github/issues-closed/wuseal/JsonToKotlinClass.svg)](https://github.com/wuseal/JsonToKotlinClass/issues?q=is%3Aissue+is%3Aclosed)
[![license](https://img.shields.io/github/license/wuseal/JsonToKotlinClass.svg)](https://github.com/wuseal/JsonToKotlinClass/blob/master/LICENSE)

[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)
[![IntelliJ Idea Plugin](https://img.shields.io/badge/plugin-IntelliJ%20%20Idea-blue.svg)](https://plugins.jetbrains.com/plugin/9960-jsontokotlinclass)
[![Android Studio Plugin](https://img.shields.io/badge/plugin-AndroidStudio-green.svg)](https://plugins.jetbrains.com/plugin/9960-jsontokotlinclass)

# JsonToKotlinClass

Hi, Welcome! This is a plugin to generate Kotlin `data class` from JSON string, in another word, a plugin that converts JSON string to Kotlin `data class` (Json to Kotlin).
We also have a `Java/Kotlin` [library](LIBRARY.md). With this you can generate Kotlin `data class` from JSON string **programmatically**. 


### Overview

This is a very cool tool for Kotlin developers, it can convert a JSON string to Kotlin `data class`. The tool could not only recognize the primitive types but also auto create complex types. It's easily accessible, we provide shortcut keymap `ALT + K` for Windows and `Option + K` for Mac, have a try and you'll fall in love with it! JsonToKotlinClass just makes programming more enjoyable, enjoy coding!

### Easy Use
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18032.png)

#### Want to generate classes code in inner class model? Do like this.
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18031.png)

#### Want to generate Kotlin data class file directory? Do like this.
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18030.png)

#### Want to generate Kotlin data classes in single file directory? Do like this.
![alt text](https://plugins.jetbrains.com/files/9960/screenshot_18029.png)

### Usage
* Search 'JsonToKotlinClass' in Intellij Idea Plugin Repository Or AndroidStudio Plugin Repository And Install it.

> File --> Settings --> Plugins --> Browse Repositories --> Search JsonToKotlinClass

* Restart your IDE

* Press `ALT + K` for Windows or `Option + K` for Mac or right click on package -> `New`->`Kotlin data clas file from JSON` and continue as guided.

### Advanced usage
Have a try with the advanced dialog :stuck_out_tongue_winking_eye:

### Features
* Generating Kotlin data class from any legal JSON string/JSONSchema or any **URLs that returns a JSON string/JSONSchema as response** or **local file that contains JSON string/JSONSchema**, for more detail about supported source types, refer to [the documentation](https://github.com/wuseal/JsonToKotlinClass/blob/master/doc_for_json_sources.md)
* Generating Kotlin data class from any legal JSON text when right click on directory and select `New` -> `Kotlin data class File from JSON`
* Supporting (almostly) all kinds of JSON libs' annotation(Gson, Jackson, Fastjson, MoShi and LoganSquare, kotlinx.serialization(default custom value))
* [Customizing your own annotations](https://github.com/wuseal/JsonToKotlinClass/issues/19)
* [Initializing properties with default values](https://github.com/wuseal/JsonToKotlinClass/issues/6)
* Allowing properties to be nullable(?)
* [Determining property nullability automatically](https://github.com/wuseal/JsonToKotlinClass/issues/23)
* Renaming property names to be camelCase style when selecting a target JSON lib annotation．
* Generating Kotlin `data class` as individual classes
* Generating Kotlin `data class` as inner classes
* [Formatting any legal JSON string](https://github.com/wuseal/JsonToKotlinClass/issues/18)
* Generating Map Type when json key is primitive type
* [Android Parcelable support](https://github.com/wuseal/JsonToKotlinClass/issues/194)
* [Customize parent class declaration](https://github.com/wuseal/JsonToKotlinClass/issues/44)
* Generating kotlin data class property order by alphabetical
* [Support customizing this plugin by `Extensions` module](https://github.com/wuseal/JsonToKotlinClass/blob/master/doc_for_extensions.md)

### Generate Example
This is the example JSON from json.org

```json
{
    "glossary":{
        "title":"example glossary",
        "GlossDiv":{
            "title":"S",
            "GlossList":{
                "GlossEntry":{
                    "ID":"SGML",
                    "SortAs":"SGML",
                    "GlossTerm":"Standard Generalized Markup Language",
                    "Acronym":"SGML",
                    "Abbrev":"ISO 8879:1986",
                    "GlossDef":{
                        "para":"A meta-markup language, used to create markup languages such as DocBook.",
                        "GlossSeeAlso":[
                            "GML",
                            "XML"
                        ]
                    },
                    "GlossSee":"markup"
                }
            }
        }
    }
}
```
And with this plugin converting, Kotlin data classes would generate like this by default

```kotlin
data class Example(
    val glossary: Glossary
)

data class Glossary(
    val GlossDiv: GlossDiv,
    val title: String
)

data class GlossDiv(
    val GlossList: GlossList,
    val title: String
)

data class GlossList(
    val GlossEntry: GlossEntry
)

data class GlossEntry(
    val Abbrev: String,
    val Acronym: String,
    val GlossDef: GlossDef,
    val GlossSee: String,
    val GlossTerm: String,
    val ID: String,
    val SortAs: String
)

data class GlossDef(
    val GlossSeeAlso: List<String>,
    val para: String
)
```

### Build From Source

Want to try out the newest features?

```shell
$ git clone https://github.com/wuseal/JsonToKotlinClass
$ cd JsonToKotlinClass
$ ./gradlew buildPlugin
```

And you're done! Go to directory `build/distributions` and you'll find `JsonToKotlinClass-x.x.zip`, which can be installed via **Install plugin from disk...**.

### Contribute to This Repo

Find it useful and want to contribute? All sorts of contributions are welcome, including but not limited to:

- Open an issue [here](https://github.com/wuseal/JsonToKotlinClass/issues) if you find a bug;

- Help test the EAP version and report bugs:

Go to the "Plugins" settings, click "Browse repositories..." => "Manage repositories..." and click the "+" button to add the EAP channel repository URL "https://plugins.jetbrains.com/plugins/eap/list". Optionally, you can also add the Alpha and Beta channel repository URLs "https://plugins.jetbrains.com/plugins/alpha/list" and "https://plugins.jetbrains.com/plugins/beta/list".

> Kindly note that the "EAP" or "Alpha" or "Beta" channel update may be unstable and tend to be buggy, if you want to get back to the stable version, remove the "EAP" or "Alpha" or "Beta" version and reinstall this plugin from the "JetBrains Plugin Repository" channel, which can be filtered by the drop-down menu next to the search input field.

- Contribute your code:

```shell
$ git clone https://github.com/wuseal/JsonToKotlinClass
$ cd JsonToKotlinClass
```

Open the `build.gradle` in IntelliJ, open "Gradle" tool window, expand the project view to "JsonToKotlinClass | Tasks | intellij | runIde", right-click and choose "Debug ...", and you're done! Create your PR [here](https://github.com/wuseal/JsonToKotlinClass/pulls)!

### Chinese Detail Document (中文文档)
* http://blog.csdn.net/wuseal/article/details/77508585

### Others
* Any kind of issues are welcome.
* Pull Requests are highly appreciated.

### Thanks
* Thank [@davidbilik](https://github.com/davidbilik) for giving me the first awesome advice.
* Thank [@cgoodroe](https://github.com/cgoodroe) for opening many awesome issues for me, help me improve myself
* Thank [@wangzhenguang](https://github.com/wangzhenguang) for reminding me of the details of the problem
* Thank [@kezhenxu94](https://github.com/kezhenxu94/) for introducing CI/CD to save me a lot of time :)
* Thank [iqbalhood](https://github.com/iqbalhood) for contributing logo for this project
* Thank [akindone](https://github.com/akindone) for adding `order by alphabetical` feature for `JsonToKotlinClass`
* Thank [rafalbednarczuk](https://github.com/rafalbednarczuk) for adding `make keyword property valid` feature for `JsonToKotlinClass`


### Find it useful ? :heart:
* Support and encourage me by clicking the :star: button on the upper right of this page. :v:
* Share to others to help more people have a better develope expierience :heart:

### Authors
This project exists thanks to all the people who contribute.

<a href="https://github.com/wuseal/JsonToKotlinClass/graphs/contributors"><img src="https://opencollective.com/jsontokotlin/contributors.svg?width=890" /></a>

### Acknowledgement

The development of this plugin is powerly driven by JetBrains.

### Join the Community

| Slack |
| -------- |
| [Join JsonToKotlinClass Slack](https://join.slack.com/t/jsontokotlinclass/shared_invite/enQtNjk2NjM1MDM3MDA5LWYxYTI3ZDRlZTMyNDI2Mjg5NzZmZjQzM2ZlNjE5OTFmNWYyY2E0ZGEzZTQ3OWNhODEwOTUwYmU0YmRjZjQ5OGQ")

### Note：
We analytics anonymous user behavior for improving plugin production, If you don't want this behavior enable, please contact us, we will provide a switch case for that.
### Stargazers over time

[![Stargazers over time](https://starcharts.herokuapp.com/wuseal/JsonToKotlinClass.svg)](https://starcharts.herokuapp.com/wuseal/JsonToKotlinClass)
