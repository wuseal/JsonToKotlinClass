package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig

class Issue269Test {

    private val json = """
        {
          "categories": [
            {
              "id": 1,
              "name": " Casuals",
              "products": [
                {
                  "id": 1,
                  "name": "Nike Sneakers",
                  "date_added": "2016-12-09T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 1,
                      "color": "Blue",
                      "size": 42,
                      "price": 1000
                    },
                    {
                      "id": 2,
                      "color": "Red",
                      "size": 42,
                      "price": 1000
                    },
                    {
                      "id": 3,
                      "color": "Blue",
                      "size": 44,
                      "price": 1200
                    },
                    {
                      "id": 4,
                      "color": "Red",
                      "size": 44,
                      "price": 1200
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 2,
                  "name": "Adidas Running Shoes",
                  "date_added": "2016-11-05T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 5,
                      "color": "White",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 6,
                      "color": "Black",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 7,
                      "color": "White",
                      "size": 44,
                      "price": 2200
                    },
                    {
                      "id": 8,
                      "color": "Red",
                      "size": 44,
                      "price": 2200
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 21,
                  "name": "Roadster Loafers",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 65,
                      "color": "Black",
                      "size": 44,
                      "price": 3500
                    },
                    {
                      "id": 66,
                      "color": "Blue",
                      "size": 44,
                      "price": 3200
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 22,
                  "name": "Light Loafers",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 67,
                      "color": "Blue",
                      "size": 42,
                      "price": 2800
                    },
                    {
                      "id": 68,
                      "color": "Yellow",
                      "size": 42,
                      "price": 2800
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 23,
                  "name": "Floaters",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 69,
                      "color": "Black",
                      "size": 40,
                      "price": 3500
                    },
                    {
                      "id": 70,
                      "color": "Red",
                      "size": 40,
                      "price": 3500
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 2,
              "name": "Jeans",
              "products": [
                {
                  "id": 3,
                  "name": "Spykar Denim",
                  "date_added": "2017-01-05T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 9,
                      "color": "Blue",
                      "size": 30,
                      "price": 1200
                    },
                    {
                      "id": 10,
                      "color": "Black",
                      "size": 32,
                      "price": 1400
                    },
                    {
                      "id": 11,
                      "color": "Blue",
                      "size": 34,
                      "price": 1400
                    },
                    {
                      "id": 12,
                      "color": "Blue",
                      "size": 36,
                      "price": 1500
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 4,
                  "name": "Lee Cotton Jeans",
                  "date_added": "2017-01-25T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 13,
                      "color": "Blue",
                      "size": 30,
                      "price": 1500
                    },
                    {
                      "id": 14,
                      "color": "Black",
                      "size": 32,
                      "price": 1500
                    },
                    {
                      "id": 15,
                      "color": "White",
                      "size": 34,
                      "price": 1700
                    },
                    {
                      "id": 16,
                      "color": "Black",
                      "size": 36,
                      "price": 1800
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 24,
                  "name": "Denim Wash",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 71,
                      "color": "Blue",
                      "size": 30,
                      "price": 40000
                    },
                    {
                      "id": 72,
                      "color": "Grey",
                      "size": 30,
                      "price": 35000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 25,
                  "name": "Pepe Jeans Slim Fit",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 73,
                      "color": "Blue",
                      "size": 32,
                      "price": 35000
                    },
                    {
                      "id": 74,
                      "color": "Light Blue",
                      "size": 32,
                      "price": 35000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 26,
                  "name": "Spykar Funky Regular",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 75,
                      "color": "Blue",
                      "size": 34,
                      "price": 2600
                    },
                    {
                      "id": 76,
                      "color": "Black",
                      "size": 32,
                      "price": 2800
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 7,
              "name": "T-Shirts",
              "products": [
                {
                  "id": 5,
                  "name": "Polo Collar T-Shirt",
                  "date_added": "2016-12-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 17,
                      "color": "Blue",
                      "size": 42,
                      "price": 2000
                    },
                    {
                      "id": 18,
                      "color": "Red",
                      "size": 42,
                      "price": 1800
                    },
                    {
                      "id": 19,
                      "color": "White",
                      "size": 44,
                      "price": 1800
                    },
                    {
                      "id": 20,
                      "color": "Red",
                      "size": 44,
                      "price": 2000
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 6,
                  "name": "Adidas Nylon",
                  "date_added": "2017-01-28T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 21,
                      "color": "White",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 22,
                      "color": "Yellow",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 23,
                      "color": "Green",
                      "size": 44,
                      "price": 2200
                    },
                    {
                      "id": 24,
                      "color": "Red",
                      "size": 44,
                      "price": 2200
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 27,
                  "name": "Being Human Collar T-shirt",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 77,
                      "color": "Blue",
                      "size": 40,
                      "price": 2600
                    },
                    {
                      "id": 78,
                      "color": "Black",
                      "size": 42,
                      "price": 2800
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 28,
                  "name": "V - Neck Smart T-Shirt",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 79,
                      "color": "Blue",
                      "size": 44,
                      "price": 2600
                    },
                    {
                      "id": 80,
                      "color": "Black",
                      "size": 42,
                      "price": 2800
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 29,
                  "name": "Manchester United",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 81,
                      "color": "Red",
                      "size": 40,
                      "price": 2600
                    },
                    {
                      "id": 82,
                      "color": "Red",
                      "size": 44,
                      "price": 2600
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 8,
              "name": "Tracks & Trousers",
              "products": [
                {
                  "id": 7,
                  "name": "Comfort Tracks",
                  "date_added": "2016-12-22T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 25,
                      "color": "Blue",
                      "size": 42,
                      "price": 2000
                    },
                    {
                      "id": 26,
                      "color": "Red",
                      "size": 42,
                      "price": 1800
                    },
                    {
                      "id": 27,
                      "color": "White",
                      "size": 44,
                      "price": 1800
                    },
                    {
                      "id": 28,
                      "color": "Red",
                      "size": 44,
                      "price": 2000
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 8,
                  "name": "Adidas Trousers",
                  "date_added": "2017-01-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 29,
                      "color": "White",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 30,
                      "color": "Yellow",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 31,
                      "color": "Green",
                      "size": 44,
                      "price": 2200
                    },
                    {
                      "id": 32,
                      "color": "Red",
                      "size": 44,
                      "price": 2200
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 30,
                  "name": "Superdry track",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 83,
                      "color": "Red",
                      "size": 36,
                      "price": 2600
                    },
                    {
                      "id": 84,
                      "color": "Blue",
                      "size": 38,
                      "price": 2900
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 31,
                  "name": "Night Comfy Track",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 85,
                      "color": "Red",
                      "size": 36,
                      "price": 3800
                    },
                    {
                      "id": 86,
                      "color": "Black",
                      "size": 32,
                      "price": 3600
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 32,
                  "name": "Superdry Joggers",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 87,
                      "color": "Red",
                      "size": 30,
                      "price": 2800
                    },
                    {
                      "id": 88,
                      "color": "Blue",
                      "size": 32,
                      "price": 2600
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 9,
              "name": "Formals",
              "products": [
                {
                  "id": 9,
                  "name": "Bata Lace up Shoes",
                  "date_added": "2016-12-22T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 33,
                      "color": "Black",
                      "size": 42,
                      "price": 2000
                    },
                    {
                      "id": 34,
                      "color": "Brown",
                      "size": 42,
                      "price": 2000
                    },
                    {
                      "id": 35,
                      "color": "Black",
                      "size": 44,
                      "price": 1800
                    },
                    {
                      "id": 36,
                      "color": "Brown",
                      "size": 44,
                      "price": 1800
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 10,
                  "name": "Franco Leather",
                  "date_added": "2017-01-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 37,
                      "color": "Black",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 38,
                      "color": "Brown",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 39,
                      "color": "Black",
                      "size": 44,
                      "price": 2200
                    },
                    {
                      "id": 40,
                      "color": "Brown",
                      "size": 44,
                      "price": 2200
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 10,
              "name": "Shirts",
              "products": [
                {
                  "id": 11,
                  "name": "Wrangler Checked Shirt",
                  "date_added": "2017-01-22T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 41,
                      "color": "Blue",
                      "size": 42,
                      "price": 2000
                    },
                    {
                      "id": 42,
                      "color": "Red",
                      "size": 42,
                      "price": 2000
                    },
                    {
                      "id": 43,
                      "color": "Black",
                      "size": 44,
                      "price": 1800
                    },
                    {
                      "id": 44,
                      "color": "White",
                      "size": 44,
                      "price": 1800
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 12,
                  "name": "Printed Shirt",
                  "date_added": "2017-01-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 45,
                      "color": "Blue",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 46,
                      "color": "Black",
                      "size": 40,
                      "price": 2000
                    },
                    {
                      "id": 47,
                      "color": "Red",
                      "size": 38,
                      "price": 2200
                    },
                    {
                      "id": 48,
                      "color": "Brown",
                      "size": 38,
                      "price": 2200
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 14,
              "name": "Apple",
              "products": [
                {
                  "id": 13,
                  "name": "Iphone 6S",
                  "date_added": "2017-01-10T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 49,
                      "color": "Silver",
                      "size": null,
                      "price": 60000
                    },
                    {
                      "id": 50,
                      "color": "Golden",
                      "size": null,
                      "price": 62000
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 14,
                  "name": "Iphone 7",
                  "date_added": "2016-12-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 51,
                      "color": "Black",
                      "size": null,
                      "price": 70000
                    },
                    {
                      "id": 52,
                      "color": "Silver",
                      "size": null,
                      "price": 72000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 33,
                  "name": "Iphone 6",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 89,
                      "color": "Silver",
                      "size": null,
                      "price": 35000
                    },
                    {
                      "id": 90,
                      "color": "Golden",
                      "size": null,
                      "price": 40000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 34,
                  "name": "Iphone 6s Plus",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 91,
                      "color": "Silver",
                      "size": null,
                      "price": 64000
                    },
                    {
                      "id": 92,
                      "color": "Golden",
                      "size": null,
                      "price": 66000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 35,
                  "name": "Iphone 7 Plus",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 93,
                      "color": "Black",
                      "size": null,
                      "price": 78000
                    },
                    {
                      "id": 94,
                      "color": "Grey",
                      "size": null,
                      "price": 78000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 15,
              "name": "Samsung",
              "products": [
                {
                  "id": 15,
                  "name": "Galaxy S7 Edge",
                  "date_added": "2017-01-15T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 53,
                      "color": "Black",
                      "size": null,
                      "price": 50000
                    },
                    {
                      "id": 54,
                      "color": "White",
                      "size": null,
                      "price": 50000
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 16,
                  "name": "Galaxy J5",
                  "date_added": "2016-12-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 55,
                      "color": "Black",
                      "size": null,
                      "price": 30000
                    },
                    {
                      "id": 56,
                      "color": "White",
                      "size": null,
                      "price": 30000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 36,
                  "name": "Galaxy J7",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 95,
                      "color": "Black",
                      "size": null,
                      "price": 34000
                    },
                    {
                      "id": 96,
                      "color": "White",
                      "size": null,
                      "price": 34000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 37,
                  "name": "Galaxy Grand Prime",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 97,
                      "color": "Black",
                      "size": null,
                      "price": 25000
                    },
                    {
                      "id": 98,
                      "color": "White",
                      "size": null,
                      "price": 25000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                },
                {
                  "id": 38,
                  "name": "Note 4",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 99,
                      "color": "Black",
                      "size": null,
                      "price": 40000
                    },
                    {
                      "id": 100,
                      "color": "White",
                      "size": null,
                      "price": 40000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 16,
              "name": "Dell",
              "products": [
                {
                  "id": 17,
                  "name": "Dell Inspiron Core",
                  "date_added": "2017-01-10T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 57,
                      "color": "Black",
                      "size": null,
                      "price": 40000
                    },
                    {
                      "id": 58,
                      "color": "Red",
                      "size": null,
                      "price": 40000
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 18,
                  "name": "Dell Inspiron 11",
                  "date_added": "2016-12-20T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 59,
                      "color": "Black",
                      "size": null,
                      "price": 35000
                    },
                    {
                      "id": 60,
                      "color": "Red",
                      "size": null,
                      "price": 35000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 17,
              "name": "Toshiba",
              "products": [
                {
                  "id": 19,
                  "name": "Satellite Pro",
                  "date_added": "2017-01-15T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 61,
                      "color": "Black",
                      "size": null,
                      "price": 40000
                    },
                    {
                      "id": 62,
                      "color": "Red",
                      "size": null,
                      "price": 40000
                    }
                  ],
                  "tax": {
                    "name": "VAT",
                    "value": 12.5
                  }
                },
                {
                  "id": 20,
                  "name": "Satellite P50",
                  "date_added": "2016-01-18T11:16:11.000Z",
                  "variants": [
                    {
                      "id": 63,
                      "color": "Black",
                      "size": null,
                      "price": 35000
                    },
                    {
                      "id": 64,
                      "color": "Red",
                      "size": null,
                      "price": 35000
                    }
                  ],
                  "tax": {
                    "name": "VAT4",
                    "value": 4
                  }
                }
              ],
              "child_categories": []
            },
            {
              "id": 3,
              "name": "Mens Wear",
              "products": [],
              "child_categories": [
                4,
                5,
                6
              ]
            },
            {
              "id": 4,
              "name": "Bottom Wear",
              "products": [],
              "child_categories": [
                2,
                8
              ]
            },
            {
              "id": 5,
              "name": "Foot Wear",
              "products": [],
              "child_categories": [
                1,
                9
              ]
            },
            {
              "id": 6,
              "name": "Upper Wear",
              "products": [],
              "child_categories": [
                7,
                10
              ]
            },
            {
              "id": 11,
              "name": "Electronics",
              "products": [],
              "child_categories": [
                12,
                13
              ]
            },
            {
              "id": 12,
              "name": "Mobiles",
              "products": [],
              "child_categories": [
                14,
                15
              ]
            },
            {
              "id": 13,
              "name": "Laptops",
              "products": [],
              "child_categories": [
                16,
                17
              ]
            }
          ],
          "rankings": [
            {
              "ranking": "Most Viewed Products",
              "products": [
                {
                  "id": 1,
                  "view_count": 56700
                },
                {
                  "id": 2,
                  "view_count": 60000
                },
                {
                  "id": 3,
                  "view_count": 74000
                },
                {
                  "id": 4,
                  "view_count": 12000
                },
                {
                  "id": 5,
                  "view_count": 66000
                },
                {
                  "id": 6,
                  "view_count": 23456
                },
                {
                  "id": 7,
                  "view_count": 65783
                },
                {
                  "id": 8,
                  "view_count": 23456
                },
                {
                  "id": 9,
                  "view_count": 78965
                },
                {
                  "id": 10,
                  "view_count": 23456
                },
                {
                  "id": 11,
                  "view_count": 65784
                },
                {
                  "id": 12,
                  "view_count": 34756
                },
                {
                  "id": 13,
                  "view_count": 67543
                },
                {
                  "id": 14,
                  "view_count": 20000
                },
                {
                  "id": 15,
                  "view_count": 35000
                },
                {
                  "id": 16,
                  "view_count": 22000
                },
                {
                  "id": 17,
                  "view_count": 21000
                },
                {
                  "id": 18,
                  "view_count": 28000
                },
                {
                  "id": 19,
                  "view_count": 87694
                },
                {
                  "id": 20,
                  "view_count": 78645
                },
                {
                  "id": 21,
                  "view_count": 54673
                },
                {
                  "id": 22,
                  "view_count": 76894
                },
                {
                  "id": 23,
                  "view_count": 34567
                },
                {
                  "id": 24,
                  "view_count": 23456
                },
                {
                  "id": 25,
                  "view_count": 54678
                }
              ]
            },
            {
              "ranking": "Most OrdeRed Products",
              "products": [
                {
                  "id": 1,
                  "order_count": 5600
                },
                {
                  "id": 2,
                  "order_count": 4300
                },
                {
                  "id": 3,
                  "order_count": 2000
                },
                {
                  "id": 8,
                  "order_count": 9873
                },
                {
                  "id": 10,
                  "order_count": 2354
                },
                {
                  "id": 38,
                  "order_count": 3456
                },
                {
                  "id": 37,
                  "order_count": 6543
                },
                {
                  "id": 36,
                  "order_count": 4312
                },
                {
                  "id": 35,
                  "order_count": 2309
                },
                {
                  "id": 34,
                  "order_count": 1346
                },
                {
                  "id": 33,
                  "order_count": 3456
                },
                {
                  "id": 32,
                  "order_count": 1890
                },
                {
                  "id": 31,
                  "order_count": 9876
                },
                {
                  "id": 30,
                  "order_count": 3455
                },
                {
                  "id": 29,
                  "order_count": 3456
                },
                {
                  "id": 28,
                  "order_count": 6754
                },
                {
                  "id": 27,
                  "order_count": 5467
                },
                {
                  "id": 26,
                  "order_count": 7645
                },
                {
                  "id": 25,
                  "order_count": 9872
                },
                {
                  "id": 24,
                  "order_count": 2345
                },
                {
                  "id": 23,
                  "order_count": 8769
                },
                {
                  "id": 22,
                  "order_count": 3457
                },
                {
                  "id": 21,
                  "order_count": 3567
                },
                {
                  "id": 20,
                  "order_count": 8769
                },
                {
                  "id": 19,
                  "order_count": 2365
                },
                {
                  "id": 18,
                  "order_count": 5467
                },
                {
                  "id": 16,
                  "order_count": 7690
                },
                {
                  "id": 15,
                  "order_count": 5690
                },
                {
                  "id": 14,
                  "order_count": 4359
                },
                {
                  "id": 13,
                  "order_count": 7869
                },
                {
                  "id": 12,
                  "order_count": 9876
                },
                {
                  "id": 11,
                  "order_count": 9786
                }
              ]
            },
            {
              "ranking": "Most ShaRed Products",
              "products": [
                {
                  "id": 10,
                  "shares": 1800
                },
                {
                  "id": 11,
                  "shares": 2600
                },
                {
                  "id": 12,
                  "shares": 3245
                },
                {
                  "id": 21,
                  "shares": 7654
                },
                {
                  "id": 31,
                  "shares": 2345
                },
                {
                  "id": 27,
                  "shares": 5670
                },
                {
                  "id": 16,
                  "shares": 2346
                },
                {
                  "id": 23,
                  "shares": 8769
                },
                {
                  "id": 18,
                  "shares": 4316
                },
                {
                  "id": 24,
                  "shares": 8654
                }
              ]
            }
          ]
        }
    """.trimIndent()


    private val expectInnerClassResult = """
        data class Output(
            val categories: List<Category>,
            val rankings: List<Ranking>
        ) {
            data class Category(
                val id: Int,
                val name: String,
                val products: List<Product>,
                val child_categories: List<Int>
            ) {
                data class Product(
                    val id: Int,
                    val name: String,
                    val date_added: String,
                    val variants: List<Variant>,
                    val tax: Tax
                ) {
                    data class Variant(
                        val id: Int,
                        val color: String,
                        val size: Int?,
                        val price: Int
                    )

                    data class Tax(
                        val name: String,
                        val value: Double
                    )
                }
            }

            data class Ranking(
                val ranking: String,
                val products: List<Product>
            ) {
                data class Product(
                    val id: Int,
                    val view_count: Int,
                    val order_count: Int,
                    val shares: Int
                )
            }
        }
    """.trimIndent()

    private val expectSplitClassResult = """
        data class Output(
            val categories: List<Category>,
            val rankings: List<Ranking>
        )

        data class Category(
            val id: Int,
            val name: String,
            val products: List<Product>,
            val child_categories: List<Int>
        )

        data class Ranking(
            val ranking: String,
            val products: List<ProductX>
        )

        data class Product(
            val id: Int,
            val name: String,
            val date_added: String,
            val variants: List<Variant>,
            val tax: Tax
        )

        data class Variant(
            val id: Int,
            val color: String,
            val size: Int?,
            val price: Int
        )

        data class Tax(
            val name: String,
            val value: Double
        )

        data class ProductX(
            val id: Int,
            val view_count: Int,
            val order_count: Int,
            val shares: Int
        )
    """.trimIndent()


    @Test
    fun testIssue269ForInnerClassModel() {
        TestConfig.setToTestInitState()
        TestConfig.isCommentOff = true
        TestConfig.isNestedClassModel = true
        TestConfig.defaultValueStrategy = DefaultValueStrategy.None
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.isOrderByAlphabetical = false
        json.generateKotlinClassCode("Output").should.be.equal(expectInnerClassResult)
    }

    @Test
    fun testIssue269ForSplitClassModel() {
        TestConfig.setToTestInitState()
        TestConfig.isCommentOff = true
        TestConfig.isNestedClassModel = false
        TestConfig.defaultValueStrategy = DefaultValueStrategy.None
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.isOrderByAlphabetical = false
        json.generateKotlinClassCode("Output").should.be.equal(expectSplitClassResult)
    }

}