Here we currently support 3 types of JSON sources: `clipboard/input manually`, `Http Url` and `local file`, each of them are used as follows:

- clipboard/input manually

With this source type, you can edit the JSON string directly in the text area in the dialog,

[![type_directly](https://user-images.githubusercontent.com/15965696/51297231-72fbe380-1a5a-11e9-8d28-1294ecc3d221.gif)]

or paste JSON string from clipboard using shortcut `Ctrl+V` or `Command+V`.

- Http Url

With this source type, you can retrieve JSON string from a http GET request, to do this, right click on the text area and choose "Retrieve content from Http URL",

[![http](https://user-images.githubusercontent.com/15965696/51297482-6af07380-1a5b-11e9-8f3d-8b398dd82f34.gif)]

- Local File

With this source type, you can load a JSON string from a local file, to do this, right click on the text area and choose "Load from local file",

![local_file](https://user-images.githubusercontent.com/15965696/51298233-c5d79a00-1a5e-11e9-9a99-a65f09d90efc.gif)

If you want more types of source, please open an issue [here](https://github.com/wuseal/JsonToKotlinClass/issues) or implement yourself and create a pull request [here](https://github.com/wuseal/JsonToKotlinClass/pulls).
