package wu.seal.jsontokotlin.regression

import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by kezhenxu at 2018/12/21 22:48
 *
 * @author kezhenxu (kezhenxu94 at 163 dot com)
 */
class Issue087Test {
    private val testJson = """
    {
      "version": "语文-人教版-必修2",
      "subjectID": "12",
      "chapterList": [
        {
          "chapterID": "973",
          "chapterName": "第一单元",
          "rate": "56",
          "sub": [
            {
              "chapterID": "974",
              "chapterName": "1、荷塘月色",
              "rate": "56"
            },
            {
              "chapterID": "975",
              "chapterName": "2、故都的秋",
              "rate": "56"
            }
          ]
        },
        {
          "chapterID": "977",
          "chapterName": "第二单元",
          "rate": "56",
          "vip": 1,
          "sub": [
            {
              "chapterID": "978",
              "chapterName": "4、《诗经》两首",
              "rate": "56"
            },
            {
              "chapterID": "979",
              "chapterName": "5、离骚",
              "rate": "56"
            },
            null
          ]
        }
      ]
    }
    """.trimIndent()

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun testIssue087() {
        KotlinCodeMaker("Test", testJson).makeKotlinData()
    }
}
