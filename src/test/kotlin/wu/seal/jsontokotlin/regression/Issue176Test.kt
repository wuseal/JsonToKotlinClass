package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.NormalClassesCodeParser


class Issue176Test {

    val testJson = """data class Test(
    val recipeInstructions: List<String>,
    val recipeIngredient: List<String>,
    val recipeCuisine: String, // italian
    val cookingMethod: Any, // null
    val cookTime: String, // PT0S
    val prepTime: String, // PT0S
    val totalTime: Any, // null
    val recipeYield: Int, // 4
    val nutrition: Nutrition,
    val aggregateRating: AggregateRating,
    val recipeCategory: String, // dinner, main
    val keywords: String, // Mushrooms, sage, butter, spaghetti, simple pasta, Italian flavours, Italian food, Italian cooking, Italian recipes, five ingredient pasta, 5 ingredient pasta, Phoebe Wood, mushroom pasta, cacio e pepe pasta, sage mushrooms, crispy sage, Vegetarian, Easy vegetarian, Vegetarian recipes, Vegetarian dinner, Vegetable mains, Meat free Monday, Meat-free Monday, Meat free mains, portobello mushrooms, roasted mushrooms, Fast food, Quick meals, Easy dinner, Easy recipes, Easy cooking
    val name: String, // Cacio e pepe with sage mushrooms
    val description: String, // Need a break from meat tonight? This simple vegetarian pasta is a winner. With just five ingredients, it's the perfect mid-week recipe to add to your weekly meal plan, yum!
    val datePublished: String, // 2016-06-10
    val dateCreated: String, // 2016-06-17
    val mainEntityOfPage: Any, // null
    val dateModified: String, // 2019-01-23
    val author: Author,
    val publisher: Publisher,
    val image: Image,
    val video: Video,
    val @context: String, // http://schema.org
    val @type: String // Recipe
)"""

    private val expected = """data class Test(
    val @context: String, // http://schema.org
    val @type: String, // Recipe
    val aggregateRating: AggregateRating,
    val author: Author,
    val cookTime: String, // PT0S
    val cookingMethod: Any, // null
    val dateCreated: String, // 2016-06-17
    val dateModified: String, // 2019-01-23
    val datePublished: String, // 2016-06-10
    val description: String, // Need a break from meat tonight? This simple vegetarian pasta is a winner. With just five ingredients, it's the perfect mid-week recipe to add to your weekly meal plan, yum!
    val image: Image,
    val keywords: String, // Mushrooms, sage, butter, spaghetti, simple pasta, Italian flavours, Italian food, Italian cooking, Italian recipes, five ingredient pasta, 5 ingredient pasta, Phoebe Wood, mushroom pasta, cacio e pepe pasta, sage mushrooms, crispy sage, Vegetarian, Easy vegetarian, Vegetarian recipes, Vegetarian dinner, Vegetable mains, Meat free Monday, Meat-free Monday, Meat free mains, portobello mushrooms, roasted mushrooms, Fast food, Quick meals, Easy dinner, Easy recipes, Easy cooking
    val mainEntityOfPage: Any, // null
    val name: String, // Cacio e pepe with sage mushrooms
    val nutrition: Nutrition,
    val prepTime: String, // PT0S
    val publisher: Publisher,
    val recipeCategory: String, // dinner, main
    val recipeCuisine: String, // italian
    val recipeIngredient: List<String>,
    val recipeInstructions: List<String>,
    val recipeYield: Int, // 4
    val totalTime: Any, // null
    val video: Video
)"""



    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
        TestConfig.defaultValueStrategy = DefaultValueStrategy.None
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
    }

    /**
     * test issue #700 of Github Project issue
     */
    @Test
    fun testIssue176() {
        val generated =  NormalClassesCodeParser(testJson).parse()[0].applyInterceptors(InterceptorManager.getEnabledKotlinDataClassInterceptors()).getCode()
        generated.trim().should.be.equal(expected)
    }
}