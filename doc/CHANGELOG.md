# Change Log

## [Unreleased]

## [3.7.7](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.7) (2025-04-19)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.6...3.7.7)

**Bugfix**

- Fix bug in file duplicate name handling logic [\#447](https://github.com/wuseal/JsonToKotlinClass/pull/447) ([@wuseal](https://github.com/wuseal))
- Fix JSON Schema  reference resolution: kotlin.NotImplementedError: Cannot resolve ref path: \#/$defs/CreditTransferTransaction39 [\#446](https://github.com/wuseal/JsonToKotlinClass/pull/446) ([@wuseal](https://github.com/wuseal))
- Plugin does not work on Android Studio Meerkat | 2024.3.1 Canary 1 [\#440](https://github.com/wuseal/JsonToKotlinClass/issues/440)

## [3.7.6](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.6) (2024-12-03)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.5...3.7.6)

**Bugfix**

- \[fix\] runtime error exception when run in  IntelliJ IDEA 2024.3 and also Android Studio Meerkat [\#443](https://github.com/wuseal/JsonToKotlinClass/pull/443) ([@wuseal](https://github.com/wuseal))

## [3.7.5](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.5) (2024-08-31)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.4...3.7.5)

**Bugfix**

- Fix \#431: Properties named "list" result in empty names of classes [\#432](https://github.com/wuseal/JsonToKotlinClass/pull/432) ([@ark-1](https://github.com/ark-1))
- Support nullable array elements [\#429](https://github.com/wuseal/JsonToKotlinClass/issues/429)
- Fix \#425: Similar classes get incorrectly deduplicated [\#433](https://github.com/wuseal/JsonToKotlinClass/pull/433) ([@ark-1](https://github.com/ark-1))
- Fixed negative numbers could not be converted to Long [\#410](https://github.com/wuseal/JsonToKotlinClass/pull/410) ([@AppleSa](https://github.com/AppleSa))

**Enhancement**

- Feature: Base Class Support [\#419](https://github.com/wuseal/JsonToKotlinClass/pull/419) ([@AZKZero](https://github.com/AZKZero))
- Release library version: 3.7.4 [\#402](https://github.com/wuseal/JsonToKotlinClass/pull/402) ([@wuseal](https://github.com/wuseal))
- jcenter will be deprecated [\#350](https://github.com/wuseal/JsonToKotlinClass/issues/350)
- Migrate jsontokotlin library from JCenter to MavenCentral [\#398](https://github.com/wuseal/JsonToKotlinClass/pull/398) ([@wuseal](https://github.com/wuseal))

## [3.7.4](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.4) (2022-05-22)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.3...3.7.4)

**Bugfix**

- Write\-access exception [\#308](https://github.com/wuseal/JsonToKotlinClass/issues/308)
- Nullable Types and Non Nullable Default Value Strategy [\#391](https://github.com/wuseal/JsonToKotlinClass/issues/391)
- Repeating classes instead of making a relation between them\! [\#381](https://github.com/wuseal/JsonToKotlinClass/issues/381)

**Enhancement**

- auto determine nullable or not from json value optional null values [\#307](https://github.com/wuseal/JsonToKotlinClass/issues/307)
- Does not detect nullable objects in some cases [\#379](https://github.com/wuseal/JsonToKotlinClass/issues/379)
- Merging data classes of the same structure [\#390](https://github.com/wuseal/JsonToKotlinClass/issues/390)
- Option to handle classes with same name [\#375](https://github.com/wuseal/JsonToKotlinClass/issues/375)

## [3.7.3](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.3) (2022-04-16)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.2...3.7.3)

**Bugfix**

- \(3.7.2\)Throwable: Assertion failed: Write access is allowed inside write\-action only [\#380](https://github.com/wuseal/JsonToKotlinClass/issues/380)
- 特殊字符报错 [\#383](https://github.com/wuseal/JsonToKotlinClass/issues/383)
- Fix  Write access error when format json paste json/obtain json from net [\#378](https://github.com/wuseal/JsonToKotlinClass/pull/378) ([@wuseal](https://github.com/wuseal))
- Long values being generated as Int [\#295](https://github.com/wuseal/JsonToKotlinClass/issues/295)

**Enhancement**

- optimize code builder & bugfix: when enable \[Replace constructor parameters by member variables\], the annotation not go to next line. [\#373](https://github.com/wuseal/JsonToKotlinClass/pull/373) ([@Nstd](https://github.com/Nstd))

## [3.7.2](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.2) (2021-07-31)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.1...3.7.2)

**Bugfix**

- Assertion failed: Write access is allowed inside write\-action only  [\#367](https://github.com/wuseal/JsonToKotlinClass/issues/367)
- Text config always loss in Adavnced \-\-\> Extension Tab [\#371](https://github.com/wuseal/JsonToKotlinClass/pull/371) ([@wuseal](https://github.com/wuseal))
- 🐛 fix JsonSchema type error when refer to non\-object definitions [\#368](https://github.com/wuseal/JsonToKotlinClass/pull/368) ([@TedZen](https://github.com/TedZen))
- Exception in JsonToKotlin 3.6.1 \- AGP 4.1.2 [\#320](https://github.com/wuseal/JsonToKotlinClass/issues/320)

**Enhancement**

- Optimize Extensions' UI [\#370](https://github.com/wuseal/JsonToKotlinClass/pull/370) ([@wuseal](https://github.com/wuseal))
- Update icon and project title picture for JsonToKotlinClass [\#363](https://github.com/wuseal/JsonToKotlinClass/issues/363)

**Features**

- Support adding \`Expose\` Annotation for Gson by new Extension [\#366](https://github.com/wuseal/JsonToKotlinClass/pull/366) ([@wuseal](https://github.com/wuseal))

## [3.7.1](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.1) (2021-06-30)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.0...3.7.1)

**Bugfix**

- Crash Report : java.lang.IndexOutOfBoundsException: Empty list doesn't contain element at index 0. [\#353](https://github.com/wuseal/JsonToKotlinClass/issues/353)
- Crash Report: java.lang.IllegalStateException: properties used kotlin classes size should be equal referenced classes size\! [\#354](https://github.com/wuseal/JsonToKotlinClass/issues/354)
- Strange crash \(RuntimeException\)  [\#357](https://github.com/wuseal/JsonToKotlinClass/issues/357)

**Features**

- Add internal modifier option\(新增internal可见性选项\) [\#347](https://github.com/wuseal/JsonToKotlinClass/issues/347)
- Custom None nullable type in generated Kotlin data class [\#358](https://github.com/wuseal/JsonToKotlinClass/issues/358)
- Add buildFromJson function into data class to build data class object from JSONObject [\#348](https://github.com/wuseal/JsonToKotlinClass/issues/348)

## [3.7.0](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.0) (2021-05-18)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.6.1...3.7.0)

**Bugfix**

- Field value in Double format generates Int instead [\#334](https://github.com/wuseal/JsonToKotlinClass/issues/334)
- Handling already escaped quotes \\" with "Force init Default Value With Origin Json Value" checked [\#301](https://github.com/wuseal/JsonToKotlinClass/issues/301)
- Not escaping kotlin keywords [\#290](https://github.com/wuseal/JsonToKotlinClass/issues/290)
- Conflicting declarations [\#297](https://github.com/wuseal/JsonToKotlinClass/issues/297)
- Latest plugin \(3.6.0\) throws  IllegalArgument exception. [\#284](https://github.com/wuseal/JsonToKotlinClass/issues/284)

**Enhancement**

- Add an option to switch autodetect JSONSchema [\#223](https://github.com/wuseal/JsonToKotlinClass/issues/223)
- Generated Parcelable data class uses deprecated annotation from package android.parcel [\#335](https://github.com/wuseal/JsonToKotlinClass/issues/335)
- Host library in jCenter [\#298](https://github.com/wuseal/JsonToKotlinClass/issues/298)
- Create a demo project for \`JsonToKotlinClass\` Library usage [\#268](https://github.com/wuseal/JsonToKotlinClass/issues/268)
- Release version 3.6.1 of the library [\#321](https://github.com/wuseal/JsonToKotlinClass/issues/321)

**Features**

- Support no parameter constructor [\#291](https://github.com/wuseal/JsonToKotlinClass/issues/291)
- class prefix support [\#322](https://github.com/wuseal/JsonToKotlinClass/issues/322)
- provide a switch case for sending analytics [\#296](https://github.com/wuseal/JsonToKotlinClass/issues/296)

## [V3.6.1](https://github.com/wuseal/JsonToKotlinClass/tree/V3.6.1) (2020-03-25)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.6.0...V3.6.1)

**Bugfix**

- Wrong generation of data class [\#269](https://github.com/wuseal/JsonToKotlinClass/issues/269)
- Generated model not matching JSON [\#241](https://github.com/wuseal/JsonToKotlinClass/issues/241)
- java.lang.IllegalStateException: Can't generate Kotlin Data Class from a no JSON Object [\#243](https://github.com/wuseal/JsonToKotlinClass/issues/243)

**Enhancement**

- JsonSchema doesn't generate class  [\#222](https://github.com/wuseal/JsonToKotlinClass/issues/222)

**Features**

- Generate Kotlin classes from JSON Schema [\#108](https://github.com/wuseal/JsonToKotlinClass/issues/108)
- Useless \`data\` modifier [\#263](https://github.com/wuseal/JsonToKotlinClass/issues/263)

## [V3.6.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.6.0) (2020-03-19)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.5.1...V3.6.0)

**Bugfix**

- Using the Library [\#265](https://github.com/wuseal/JsonToKotlinClass/issues/265)
- Moshi\(CodeGen\) and Keep annotation can't coexist [\#260](https://github.com/wuseal/JsonToKotlinClass/issues/260)

**Enhancement**

- @keep 不支持androidx [\#244](https://github.com/wuseal/JsonToKotlinClass/issues/244)

**Features**

- Library Support [\#258](https://github.com/wuseal/JsonToKotlinClass/issues/258)

## [V3.5.1](https://github.com/wuseal/JsonToKotlinClass/tree/V3.5.1) (2019-11-17)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.5.0...V3.5.1)

**Bugfix**

- java.io.FileNotFoundException: /Users/apple/Library/Caches/.jsontokotlin/actionInfo/1572435095855 \(No such file or directory\) [\#246](https://github.com/wuseal/JsonToKotlinClass/issues/246)

**Features**

- Respect dot as a word separator [\#249](https://github.com/wuseal/JsonToKotlinClass/issues/249)
- Making default values as values from Json with option to disable [\#84](https://github.com/wuseal/JsonToKotlinClass/issues/84)

## [V3.5.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.5.0) (2019-09-04)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.4.1...V3.5.0)

**Bugfix**

- a redundant comma appears after data class generated without sort by alphabet [\#225](https://github.com/wuseal/JsonToKotlinClass/issues/225)
- json生成kotlin类出现错误格式 [\#224](https://github.com/wuseal/JsonToKotlinClass/issues/224)
- fix kotlin data class file generate action doesn't works with interce… [\#238](https://github.com/wuseal/JsonToKotlinClass/pull/238) ([@wuseal](https://github.com/wuseal))
- fix an exception generate split class code with json: [\#236](https://github.com/wuseal/JsonToKotlinClass/pull/236) ([@wuseal](https://github.com/wuseal))
- fix \#224 [\#237](https://github.com/wuseal/JsonToKotlinClass/pull/237) ([@wuseal](https://github.com/wuseal))
- PrefixSupport will go wrong if Property.name is empty [\#227](https://github.com/wuseal/JsonToKotlinClass/issues/227)
- name.first\(\) went wrong in Property.getCode\(\)  [\#218](https://github.com/wuseal/JsonToKotlinClass/issues/218)
- 更新v3.4.0后用不了 [\#215](https://github.com/wuseal/JsonToKotlinClass/issues/215)

**Enhancement**

- refactor JSON\-\>Data Class Flow [\#233](https://github.com/wuseal/JsonToKotlinClass/pull/233) ([@wuseal](https://github.com/wuseal))
- Add filter to Prefix and Suffix input panel [\#226](https://github.com/wuseal/JsonToKotlinClass/issues/226)
- Upgrade MakeKeywordNamedPropertyValidInterceptor to FinalKotlinDataClassWrapperInterceptor [\#219](https://github.com/wuseal/JsonToKotlinClass/issues/219)

**Features**

- \[Feature Request\]Add an option that force primitive type to be non\-nullable into Extensions Tab [\#234](https://github.com/wuseal/JsonToKotlinClass/issues/234)

## [V3.4.1](https://github.com/wuseal/JsonToKotlinClass/tree/V3.4.1) (2019-08-05)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.4.0...V3.4.1)


## [V3.4.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.4.0) (2019-07-31)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.3.0...V3.4.0)

**Bugfix**

- java.lang.IndexOutOfBoundsException: Index: 0, Size: 0 [\#195](https://github.com/wuseal/JsonToKotlinClass/issues/195)
- The pattern of the property name needs to be checked [\#198](https://github.com/wuseal/JsonToKotlinClass/issues/198)
- The pattern of the new kt file name needs to be checked [\#197](https://github.com/wuseal/JsonToKotlinClass/issues/197)
- Generated Kotlin Data Class File Reference is Incorrect [\#193](https://github.com/wuseal/JsonToKotlinClass/issues/193)
- While Looking into the issue, I ran into another bug. Please check. [\#136](https://github.com/wuseal/JsonToKotlinClass/issues/136)

**Enhancement**

- Android Parcelable Support by Extension module [\#194](https://github.com/wuseal/JsonToKotlinClass/issues/194)
- Ask/Notify about analytics explicitly  [\#140](https://github.com/wuseal/JsonToKotlinClass/issues/140)

## [V3.3.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.3.0) (2019-06-30)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.2.0...V3.3.0)

**Bugfix**

- The property   generate by "Auto Determine Nullable Or Not From JSON Value" is NotNullable，While I expect a Nullable property。 [\#170](https://github.com/wuseal/JsonToKotlinClass/issues/170)
- the property type is not what i expect when some json property value is JsonNull. [\#173](https://github.com/wuseal/JsonToKotlinClass/issues/173)
- Generated code is broken based on json. [\#176](https://github.com/wuseal/JsonToKotlinClass/issues/176)

**Enhancement**

- Encrypting the data when reporting problematic JSON string? [\#142](https://github.com/wuseal/JsonToKotlinClass/issues/142)
- Add an option for \`kotlinx.serialization\` [\#131](https://github.com/wuseal/JsonToKotlinClass/issues/131)
- Please change the current code style to mine. Since the current is very terrible to read .... [\#169](https://github.com/wuseal/JsonToKotlinClass/issues/169)

**Features**

- \[Feature Request\]Suffix appending behind of all class name [\#165](https://github.com/wuseal/JsonToKotlinClass/issues/165)
- Add An Extension to add @Keep annotation to generated classes [\#146](https://github.com/wuseal/JsonToKotlinClass/issues/146)
- \[Feature request\]Generate Kotlin Data class init with default value = null when it's property is nullable [\#152](https://github.com/wuseal/JsonToKotlinClass/issues/152)

## [v3.2.0](https://github.com/wuseal/JsonToKotlinClass/tree/v3.2.0) (2019-04-26)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/v3.2.0-EAP...v3.2.0)


## [V3.2.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.2.0) (2019-04-26)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/v3.2.0...V3.2.0)


## [v3.2.0\-EAP](https://github.com/wuseal/JsonToKotlinClass/tree/v3.2.0-EAP) (2019-04-22)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.0.1...v3.2.0-EAP)

**Bugfix**

- wu.seal.jsontokotlin.UnSupportJsonException: Unsupported Json String [\#122](https://github.com/wuseal/JsonToKotlinClass/issues/122)
- File Already Exists [\#123](https://github.com/wuseal/JsonToKotlinClass/issues/123)
- Json Format change the value of  Json Content [\#130](https://github.com/wuseal/JsonToKotlinClass/issues/130)
- Kotlin Data Class don't generated as expected [\#121](https://github.com/wuseal/JsonToKotlinClass/issues/121)
- \[Bug\] Incorrect properties orders [\#119](https://github.com/wuseal/JsonToKotlinClass/issues/119)
- java.lang.IllegalStateException: Not a JSON Array:  [\#87](https://github.com/wuseal/JsonToKotlinClass/issues/87)
- java.util.NoSuchElementException: Char sequence is empty. [\#114](https://github.com/wuseal/JsonToKotlinClass/issues/114)

**Enhancement**

- Change Action tip [\#135](https://github.com/wuseal/JsonToKotlinClass/issues/135)
- Folder .jsontokotlin in $HOME [\#139](https://github.com/wuseal/JsonToKotlinClass/issues/139)
- refactored 'settings' to 'advanced' in Pop Up Dialog [\#127](https://github.com/wuseal/JsonToKotlinClass/pull/127) ([@SuuSoJeat](https://github.com/SuuSoJeat))
- Fix typo in README.md [\#129](https://github.com/wuseal/JsonToKotlinClass/pull/129) ([@codeteo](https://github.com/codeteo))
- Wrong background color with Darcula theme [\#109](https://github.com/wuseal/JsonToKotlinClass/issues/109)

## [V3.0.1](https://github.com/wuseal/JsonToKotlinClass/tree/V3.0.1) (2019-03-03)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.0.0...V3.0.1)

**Bugfix**

- java.lang.IndexOutOfBoundsException: Index: 1, Size: 1 [\#89](https://github.com/wuseal/JsonToKotlinClass/issues/89)
- please test this data   \-\>   repetition \-\>class [\#82](https://github.com/wuseal/JsonToKotlinClass/issues/82)
- "Import statements" go after class declaration [\#83](https://github.com/wuseal/JsonToKotlinClass/issues/83)
- JSON converstion not works as expected [\#90](https://github.com/wuseal/JsonToKotlinClass/issues/90)

**Features**

- JSON input from file [\#76](https://github.com/wuseal/JsonToKotlinClass/issues/76)

## [V3.0.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.0.0) (2019-01-18)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.0-EAP-2...V3.0.0)