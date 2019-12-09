### Update Log
#### 1.5.1
* Fix a bug about only number property name [#15](https://github.com/wuseal/JsonToKotlinClass/issues/15)
#### 1.5
* support for LoganSquare and Moshi. 
* support for cutomize annotation. 
* simply usage steps.
#### 1.4.1
  * Fix an issue about Fastjson property name [#Issue9](https://github.com/wuseal/JsonToKotlinClass/issues/9)
  * Fix an issue about using in none network condition it will be stuck [#Issue10](https://github.com/wuseal/JsonToKotlinClass/issues/10) 
#### 1.4
 * Add supporter for Jackson annotation generate,supporter json lib --Jackson.
 * Add supporter for Fastjson annotation generate,supporter json lib --Fastjson.
 * Beautify the config settings dialog.
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
