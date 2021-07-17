# Change Log

## Unreleased (2021-07-17)

**Features**

- Support adding \`Expose\` Annotation for Gson by new Extension [\#366](https://github.com/wuseal/JsonToKotlinClass/pull/366) ([@wuseal](https://github.com/wuseal))

## [3.7.2\-EAP\-3](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.2-EAP-3) (2021-07-17)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.2-EAP-2...3.7.2-EAP-3)


## [3.7.2\-EAP\-2](https://github.com/wuseal/JsonToKotlinClass/tree/3.7.2-EAP-2) (2021-07-06)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/3.7.1...3.7.2-EAP-2)

**Bugfix**

- Exception in JsonToKotlin 3.6.1 \- AGP 4.1.2 [\#320](https://github.com/wuseal/JsonToKotlinClass/issues/320)

**Enhancement**

- Update icon and project title picture for JsonToKotlinClass [\#363](https://github.com/wuseal/JsonToKotlinClass/issues/363)

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
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V3.0.1...v3.2.0)

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

## [V3.2.0](https://github.com/wuseal/JsonToKotlinClass/tree/V3.2.0) (2019-04-26)
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/v3.2.0...V3.2.0)


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
[View commits](https://github.com/wuseal/JsonToKotlinClass/compare/V2.3.0...V3.0.0)

**Bugfix**

- java.lang.IndexOutOfBoundsException: Collection doesn't contain element at index 31. [\#35](https://github.com/wuseal/JsonToKotlinClass/issues/35)
- Lists with primitives/strings are throwing exceptions [\#62](https://github.com/wuseal/JsonToKotlinClass/issues/62)
- Nested list, generation fail [\#67](https://github.com/wuseal/JsonToKotlinClass/issues/67)
- Plugin crash while gernating pojo of wikipedia suggestion api json [\#68](https://github.com/wuseal/JsonToKotlinClass/issues/68)
- Generated default value dosen't match the property type [\#77](https://github.com/wuseal/JsonToKotlinClass/issues/77)

**Enhancement**

- The Moshi converter option doesn't support moshi\-codegen [\#69](https://github.com/wuseal/JsonToKotlinClass/issues/69)
