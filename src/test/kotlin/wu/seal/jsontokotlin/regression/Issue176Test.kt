package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig


class Issue176Test {

    val testJson = """{
  "recipeInstructions": [
    "Cook pasta in a pan of boiling salted water according to packet instructions. Drain, reserving 1 cup (250ml) liquid.",
    "Return pasta and liquid to pan with pecorino, pepper and half the butter. Stir to coat and keep warm.",
    "Melt remaining 40g butter in a frypan over high heat. Add mushrooms and sage.",
    "Cook, tossing, for 3-4 minutes until golden. Season and serve with pasta."
  ],
  "recipeIngredient": [
    "400g spaghetti (we used Woolworths Macro organic spaghetti)",
    "3 cups (240g) finely grated pecorino, plus extra to serve",
    "1 tbs crushed black peppercorns",
    "80g unsalted butter",
    "500g mixed mushrooms (such as Swiss brown and chestnut)",
    "1\/3 cup sage leaves"
  ],
  "recipeCuisine": "italian",
  "cookingMethod": null,
  "cookTime": "PT0S",
  "prepTime": "PT0S",
  "totalTime": null,
  "recipeYield": 4,
  "nutrition": {
    "calories": null,
    "fatContent": null,
    "saturatedFatContent": null,
    "carbohydrateContent": null,
    "sugarContent": null,
    "fibreContent": null,
    "proteinContent": null,
    "cholesterolContent": null,
    "sodiumContent": null,
    "@context": "http:\/\/schema.org",
    "@type": "NutritionInformation"
  },
  "aggregateRating": {},
  "recipeCategory": "dinner, main",
  "keywords": "Mushrooms, sage, butter, spaghetti, simple pasta, Italian flavours, Italian food, Italian cooking, Italian recipes, five ingredient pasta, 5 ingredient pasta, Phoebe Wood, mushroom pasta, cacio e pepe pasta, sage mushrooms, crispy sage, Vegetarian, Easy vegetarian, Vegetarian recipes, Vegetarian dinner, Vegetable mains, Meat free Monday, Meat-free Monday, Meat free mains, portobello mushrooms, roasted mushrooms, Fast food, Quick meals, Easy dinner, Easy recipes, Easy cooking",
  "name": "Cacio e pepe with sage mushrooms",
  "description": "Need a break from meat tonight? This simple vegetarian pasta is a winner. With just five ingredients, it's the perfect mid-week recipe to add to your weekly meal plan, yum!",
  "datePublished": "2016-06-10",
  "dateCreated": "2016-06-17",
  "mainEntityOfPage": null,
  "dateModified": "2019-01-23",
  "author": {
    "name": "Phoebe Wood",
    "@context": "http:\/\/schema.org",
    "@type": "person"
  },
  "publisher": {
    "name": null,
    "logo": null,
    "@context": "http:\/\/schema.org",
    "@type": "Organization"
  },
  "image": {
    "url": "https:\/\/img.delicious.com.au\/UvMnowlO\/del\/2016\/05\/cacio-e-pepe-with-sage-mushrooms-30645-1.jpg",
    "width": 1500,
    "height": 1000,
    "@context": "http:\/\/schema.org",
    "@type": "ImageObject"
  },
  "video": {
    "name": "5348771529001-5981831265001",
    "description": "5348771529001-5981831265001",
    "thumbnailUrl": "https:\/\/img.delicious.com.au\/UvMnowlO\/del\/2016\/05\/cacio-e-pepe-with-sage-mushrooms-30645-1.jpg",
    "uploadDate": "2019-01-23",
    "url": "https:\/\/resources.newscdn.com.au\/cs\/video\/vjs\/stable\/build\/index.html?id=5348771529001-5981831265001&=domain=del",
    "@context": "http:\/\/schema.org",
    "@type": "VideoObject"
  },
  "@context": "http:\/\/schema.org",
  "@type": "Recipe"
}"""

    private val expected = """data class Test(
    val @context: String,
    val @type: String,
    val aggregateRating: AggregateRating,
    val author: Author,
    val cookTime: String,
    val cookingMethod: Any,
    val dateCreated: String,
    val dateModified: String,
    val datePublished: String,
    val description: String,
    val image: Image,
    val keywords: String,
    val mainEntityOfPage: Any,
    val name: String,
    val nutrition: Nutrition,
    val prepTime: String,
    val publisher: Publisher,
    val recipeCategory: String,
    val recipeCuisine: String,
    val recipeIngredient: List<String>,
    val recipeInstructions: List<String>,
    val recipeYield: Int,
    val totalTime: Any,
    val video: Video
) {
    class AggregateRating(
    )

    data class Author(
        val @context: String,
        val @type: String,
        val name: String
    )

    data class Image(
        val @context: String,
        val @type: String,
        val height: Int,
        val url: String,
        val width: Int
    )

    data class Nutrition(
        val @context: String,
        val @type: String,
        val calories: Any,
        val carbohydrateContent: Any,
        val cholesterolContent: Any,
        val fatContent: Any,
        val fibreContent: Any,
        val proteinContent: Any,
        val saturatedFatContent: Any,
        val sodiumContent: Any,
        val sugarContent: Any
    )

    data class Publisher(
        val @context: String,
        val @type: String,
        val logo: Any,
        val name: Any
    )

    data class Video(
        val @context: String,
        val @type: String,
        val description: String,
        val name: String,
        val thumbnailUrl: String,
        val uploadDate: String,
        val url: String
    )
}"""



    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
        TestConfig.defaultValueStrategy = DefaultValueStrategy.None
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.isCommentOff = true
    }

    /**
     * test issue #700 of Github Project issue
     */
    @Test
    fun testIssue176() {
        val generated =  KotlinCodeMaker("Test", testJson).makeKotlinData()
        generated.trim().should.be.equal(expected)
    }
}