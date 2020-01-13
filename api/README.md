# j2k

## API Documentation

This is a lightweight web service, (REST interface), which provides an easy way to access **`j2k`** data.
The API works through simple commands, so there should not be a problem coding some nice applications.
This API contains total **1** route(s)


## API Endpoints

All the API endpoints return the same data structure as below

|Returned Key|Description|Example|
|------------|-----------|-------|
|error|The returned status for the API call, can be either 'true' or 'false'|true|
|message|Either the error message or the successful message|OK|
|data|If 'error' is returned as 'false' the API query results will be inside 'data'|data|


**Success Response Format**

```json
{
  "error": false,
  "message": "SUCCESS_MESSAGE",
  "data": {}
}
```

**Error Response Format**

```json
{
  "error": true,
  "message": "ERROR_MESSAGE"
}
```

## Routes



#### 1 . /api/generate

- Method : **POST**
- URL : [/api/generate](http://baseapiurlgoeshere.com/api/generate)
- MockURL : [api/generate](http://theapache64.com/mock_api/get_json/j2k/api/generate?json=sem&class_name=quam&is_skip_auth=true)

**Descriptions**

To generate kotlin code from given input JSON


| Parameter | Required | Type | Default Value | Description |
|-----------|----------|------|---------------|-------------|
|json|true|String||JSON input string|
|class_name|true|String||Class name of the output parent class|
|annotation_lib|false|String|`None`|Annotation library to be used. Possible values are `None`,`NoneWithCamelCase`,`Gson`,`FastJson`,`Jackson`,`MoShi`,`LoganSquare`,`Custom`,`MoshiCodeGen`,`Serializable`|
|class_suffix|false|String||Suffix to be added to every class name|
|default_value_strategy|false|String|None|Possible values are `AvoidNull`, `AllowNull`, `None`|
|indent|false|Integer|4|Output indent|
|is_comments_enabled|false|Boolean|false||
|is_create_annotation_only_when_needed_enabled|false|Boolean|false||
|is_enable_var_properties|false|Boolean|false||
|is_force_init_default_value_with_origin_json_value_enabled|false|Boolean|false||
|is_force_primitive_type_non_nullable_enabled|false|Boolean|false||
|is_inner_class_model_enabled|false|Boolean|false||
|is_keep_annotation_on_class_androidx_enabled|false|Boolean|false||
|is_keep_annotation_on_class_enabled|false|Boolean|false||
|is_map_type_enabled|false|Boolean|false||
|is_order_by_alphabetic_enabled|false|Boolean|false||
|is_parcelable_support_enabled|false|Boolean|false||
|is_property_and_annotation_in_same_line_enabled|false|Boolean|false||
|package_name|false|String|||
|parent_class_template|false|String|||
|property_prefix|false|String|||
|property_suffix|false|String|||
|property_type_strategy|false|String|`AutoDeterMineNullableOrNot`|Possible values are `NotNullable`, `Nullable`,`AutoDeterMineNullableOrNot`.|


**Response Body**
```json
{
  "data" : {
    "code" : "data class MyJsonClass(\n    val name: String\n)"
  },
  "error" : true,
  "error_code" : -1,
  "message" : "OK"
}
```



