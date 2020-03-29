package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue090Test {

    private val rawJson = """{
  "id": 1441,
  "name": "SOAP DISPENSER WITH ALL-PURPOSE VALVE",
  "slug": "soap-dispenser-with-all-purpose-valve",
  "permalink": "https://resourceserver.in/demo/vp/product/soap-dispenser-with-all-purpose-valve/",
  "date_created": "2018-11-12T10:51:24",
  "date_created_gmt": "2018-11-12T10:51:24",
  "date_modified": "2018-12-04T06:35:46",
  "date_modified_gmt": "2018-12-04T06:35:46",
  "type": "simple",
  "status": "publish",
  "featured": false,
  "catalog_visibility": "visible",
  "description": "\u003cp\u003eSoap dispenser\u003c/p\u003e\n\u003cp\u003eAll-purpose valve\u003cbr /\u003e\nPlastic push button with antibacterial-soap-resistant plastic cylinder and stainless steel spring\u003cbr /\u003e\nClear plastic view level indicator\u003cbr /\u003e\n40 oz. capacity of liquid lotion soaps\u003cbr /\u003e\nRemovable filler cap at top of unit is keyed to resist vandalism\u003cbr /\u003e\n8-1/4\" height x 4-7/8\" width x 3\" projection from wall\u003cbr /\u003e\nSatin stainless steel\u003c/p\u003e\n",
  "short_description": "",
  "sku": "",
  "price": "4636",
  "regular_price": "4636",
  "sale_price": "",
  "date_on_sale_from": null,
  "date_on_sale_from_gmt": null,
  "date_on_sale_to": null,
  "date_on_sale_to_gmt": null,
  "price_html": "\u003cspan class\u003d\"woocommerce-Price-amount amount\"\u003e\u003cspan class\u003d\"woocommerce-Price-currencySymbol\"\u003e\u0026#8377;\u003c/span\u003e4,636.00\u003c/span\u003e",
  "on_sale": false,
  "purchasable": true,
  "total_sales": 0,
  "virtual": false,
  "downloadable": false,
  "downloads": [],
  "download_limit": -1,
  "download_expiry": -1,
  "external_url": "",
  "button_text": "",
  "tax_status": "taxable",
  "tax_class": "",
  "manage_stock": true,
  "stock_quantity": 8,
  "in_stock": true,
  "backorders": "no",
  "backorders_allowed": false,
  "backordered": false,
  "sold_individually": false,
  "weight": "",
  "dimensions": {
    "length": "",
    "width": "",
    "height": ""
  },
  "shipping_required": true,
  "shipping_taxable": true,
  "shipping_class": "",
  "shipping_class_id": 0,
  "reviews_allowed": true,
  "average_rating": "0.00",
  "rating_count": 0,
  "related_ids": [
    1370,
    1424,
    1408,
    1410,
    1406
  ],
  "upsell_ids": [],
  "cross_sell_ids": [],
  "parent_id": 0,
  "purchase_note": "",
  "categories": [
    {
      "id": 63,
      "name": "Accessories",
      "slug": "accessories-laundry"
    },
    {
      "id": 60,
      "name": "Laundry",
      "slug": "laundry"
    }
  ],
  "tags": [
    {
      "id": 91,
      "name": "Sale",
      "slug": "sale"
    }
  ],
  "images": [
    {
      "id": 1442,
      "date_created": "2018-11-12T10:51:09",
      "date_created_gmt": "2018-11-12T10:51:09",
      "date_modified": "2018-11-12T10:51:09",
      "date_modified_gmt": "2018-11-12T10:51:09",
      "src": "https://resourceserver.in/demo/vp/wp-content/uploads/2018/11/TY-0223024_usn.jpg",
      "name": "TY-0223024_usn",
      "alt": "",
      "position": 0
    }
  ],
  "attributes": [
    {
      "id": 1,
      "name": "Color",
      "position": 0,
      "visible": true,
      "variation": false,
      "options": [
        "Satin Stainless Steel"
      ]
    },
    {
      "id": 0,
      "name": "Dispenser/Dish",
      "position": 1,
      "visible": true,
      "variation": false,
      "options": [
        "Soap Dispenser"
      ]
    }
  ],
  "default_attributes": [],
  "variations": [],
  "grouped_products": [],
  "menu_order": 0,
  "meta_data": [
    {}
  ],
  "_links": {
    "self": [
      {
        "href": "https://resourceserver.in/demo/vp/wp-json/wc/v2/products/1441"
      }
    ],
    "collection": [
      {
        "href": "https://resourceserver.in/demo/vp/wp-json/wc/v2/products"
      }
    ]
  }
}"""
    val expectedResult = """data class Test(
    @SerializedName("attributes")
    val attributes: List<Attribute> = listOf(),
    @SerializedName("average_rating")
    val averageRating: String = "", // 0.00
    @SerializedName("backordered")
    val backordered: Boolean = false, // false
    @SerializedName("backorders")
    val backorders: String = "", // no
    @SerializedName("backorders_allowed")
    val backordersAllowed: Boolean = false, // false
    @SerializedName("button_text")
    val buttonText: String = "",
    @SerializedName("catalog_visibility")
    val catalogVisibility: String = "", // visible
    @SerializedName("categories")
    val categories: List<Category> = listOf(),
    @SerializedName("cross_sell_ids")
    val crossSellIds: List<Any> = listOf(),
    @SerializedName("date_created")
    val dateCreated: String = "", // 2018-11-12T10:51:24
    @SerializedName("date_created_gmt")
    val dateCreatedGmt: String = "", // 2018-11-12T10:51:24
    @SerializedName("date_modified")
    val dateModified: String = "", // 2018-12-04T06:35:46
    @SerializedName("date_modified_gmt")
    val dateModifiedGmt: String = "", // 2018-12-04T06:35:46
    @SerializedName("date_on_sale_from")
    val dateOnSaleFrom: Any = Any(), // null
    @SerializedName("date_on_sale_from_gmt")
    val dateOnSaleFromGmt: Any = Any(), // null
    @SerializedName("date_on_sale_to")
    val dateOnSaleTo: Any = Any(), // null
    @SerializedName("date_on_sale_to_gmt")
    val dateOnSaleToGmt: Any = Any(), // null
    @SerializedName("default_attributes")
    val defaultAttributes: List<Any> = listOf(),
    @SerializedName("description")
    val description: String = "", // <p>Soap dispenser</p><p>All-purpose valve<br />Plastic push button with antibacterial-soap-resistant plastic cylinder and stainless steel spring<br />Clear plastic view level indicator<br />40 oz. capacity of liquid lotion soaps<br />Removable filler cap at top of unit is keyed to resist vandalism<br />8-1/4" height x 4-7/8" width x 3" projection from wall<br />Satin stainless steel</p>
    @SerializedName("dimensions")
    val dimensions: Dimensions = Dimensions(),
    @SerializedName("download_expiry")
    val downloadExpiry: Int = 0, // -1
    @SerializedName("download_limit")
    val downloadLimit: Int = 0, // -1
    @SerializedName("downloadable")
    val downloadable: Boolean = false, // false
    @SerializedName("downloads")
    val downloads: List<Any> = listOf(),
    @SerializedName("external_url")
    val externalUrl: String = "",
    @SerializedName("featured")
    val featured: Boolean = false, // false
    @SerializedName("grouped_products")
    val groupedProducts: List<Any> = listOf(),
    @SerializedName("id")
    val id: Int = 0, // 1441
    @SerializedName("images")
    val images: List<Image> = listOf(),
    @SerializedName("in_stock")
    val inStock: Boolean = false, // true
    @SerializedName("_links")
    val links: Links = Links(),
    @SerializedName("manage_stock")
    val manageStock: Boolean = false, // true
    @SerializedName("menu_order")
    val menuOrder: Int = 0, // 0
    @SerializedName("meta_data")
    val metaData: List<MetaData> = listOf(),
    @SerializedName("name")
    val name: String = "", // SOAP DISPENSER WITH ALL-PURPOSE VALVE
    @SerializedName("on_sale")
    val onSale: Boolean = false, // false
    @SerializedName("parent_id")
    val parentId: Int = 0, // 0
    @SerializedName("permalink")
    val permalink: String = "", // https://resourceserver.in/demo/vp/product/soap-dispenser-with-all-purpose-valve/
    @SerializedName("price")
    val price: String = "", // 4636
    @SerializedName("price_html")
    val priceHtml: String = "", // <span class="woocommerce-Price-amount amount"><span class="woocommerce-Price-currencySymbol">&#8377;</span>4,636.00</span>
    @SerializedName("purchasable")
    val purchasable: Boolean = false, // true
    @SerializedName("purchase_note")
    val purchaseNote: String = "",
    @SerializedName("rating_count")
    val ratingCount: Int = 0, // 0
    @SerializedName("regular_price")
    val regularPrice: String = "", // 4636
    @SerializedName("related_ids")
    val relatedIds: List<Int> = listOf(),
    @SerializedName("reviews_allowed")
    val reviewsAllowed: Boolean = false, // true
    @SerializedName("sale_price")
    val salePrice: String = "",
    @SerializedName("shipping_class")
    val shippingClass: String = "",
    @SerializedName("shipping_class_id")
    val shippingClassId: Int = 0, // 0
    @SerializedName("shipping_required")
    val shippingRequired: Boolean = false, // true
    @SerializedName("shipping_taxable")
    val shippingTaxable: Boolean = false, // true
    @SerializedName("short_description")
    val shortDescription: String = "",
    @SerializedName("sku")
    val sku: String = "",
    @SerializedName("slug")
    val slug: String = "", // soap-dispenser-with-all-purpose-valve
    @SerializedName("sold_individually")
    val soldIndividually: Boolean = false, // false
    @SerializedName("status")
    val status: String = "", // publish
    @SerializedName("stock_quantity")
    val stockQuantity: Int = 0, // 8
    @SerializedName("tags")
    val tags: List<Tag> = listOf(),
    @SerializedName("tax_class")
    val taxClass: String = "",
    @SerializedName("tax_status")
    val taxStatus: String = "", // taxable
    @SerializedName("total_sales")
    val totalSales: Int = 0, // 0
    @SerializedName("type")
    val type: String = "", // simple
    @SerializedName("upsell_ids")
    val upsellIds: List<Any> = listOf(),
    @SerializedName("variations")
    val variations: List<Any> = listOf(),
    @SerializedName("virtual")
    val virtual: Boolean = false, // false
    @SerializedName("weight")
    val weight: String = ""
) {
    data class Attribute(
        @SerializedName("id")
        val id: Int = 0, // 1
        @SerializedName("name")
        val name: String = "", // Color
        @SerializedName("options")
        val options: List<String> = listOf(),
        @SerializedName("position")
        val position: Int = 0, // 0
        @SerializedName("variation")
        val variation: Boolean = false, // false
        @SerializedName("visible")
        val visible: Boolean = false // true
    )

    data class Category(
        @SerializedName("id")
        val id: Int = 0, // 63
        @SerializedName("name")
        val name: String = "", // Accessories
        @SerializedName("slug")
        val slug: String = "" // accessories-laundry
    )

    data class Dimensions(
        @SerializedName("height")
        val height: String = "",
        @SerializedName("length")
        val length: String = "",
        @SerializedName("width")
        val width: String = ""
    )

    data class Image(
        @SerializedName("alt")
        val alt: String = "",
        @SerializedName("date_created")
        val dateCreated: String = "", // 2018-11-12T10:51:09
        @SerializedName("date_created_gmt")
        val dateCreatedGmt: String = "", // 2018-11-12T10:51:09
        @SerializedName("date_modified")
        val dateModified: String = "", // 2018-11-12T10:51:09
        @SerializedName("date_modified_gmt")
        val dateModifiedGmt: String = "", // 2018-11-12T10:51:09
        @SerializedName("id")
        val id: Int = 0, // 1442
        @SerializedName("name")
        val name: String = "", // TY-0223024_usn
        @SerializedName("position")
        val position: Int = 0, // 0
        @SerializedName("src")
        val src: String = "" // https://resourceserver.in/demo/vp/wp-content/uploads/2018/11/TY-0223024_usn.jpg
    )

    data class Links(
        @SerializedName("collection")
        val collection: List<Collection> = listOf(),
        @SerializedName("self")
        val self: List<Self> = listOf()
    ) {
        data class Collection(
            @SerializedName("href")
            val href: String = "" // https://resourceserver.in/demo/vp/wp-json/wc/v2/products
        )

        data class Self(
            @SerializedName("href")
            val href: String = "" // https://resourceserver.in/demo/vp/wp-json/wc/v2/products/1441
        )
    }

    class MetaData(
    )

    data class Tag(
        @SerializedName("id")
        val id: Int = 0, // 91
        @SerializedName("name")
        val name: String = "", // Sale
        @SerializedName("slug")
        val slug: String = "" // sale
    )
}
"""

    /**
     * test before to init test enviroment
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }


    /**
     * Test issue #90 if fixed
     */
    @Test
    fun testIssue090() {
        val result = KotlinClassCodeMaker(KotlinClassMaker("Test", rawJson).makeKotlinClass()).makeKotlinClassCode()
        result.trim().should.equal(expectedResult.trim())
    }

}
