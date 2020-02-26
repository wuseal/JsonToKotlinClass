package wu.seal.jsontokotlin.feedback

import com.google.gson.Gson
import com.winterbe.expekt.should
import org.junit.AfterClass
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class PersistCacheTest {

    private val exceptionInfo = "Test exception"

    @Before
    fun before() {
        isTestModel = true
    }

    @Test
    fun saveExceptionInfo() {
        PersistCache.saveExceptionInfo(exceptionInfo)
    }

    @Test
    fun readAllCachedExceptionInfo() {
        PersistCache.readAllCachedExceptionInfo()
    }

    @Test
    fun deleteAllExceptionInfoCache() {
        PersistCache.deleteAllExceptionInfoCache()
    }

    @Test
    fun saveActionInfo() {
        PersistCache.saveActionInfo(Gson().toJson(StartAction()))
    }

    @Test
    fun readAllCachedActionInfo() {
        PersistCache.readAllCachedActionInfo()
    }

    @Test
    fun deleteAllActionInfo() {
        PersistCache.deleteAllActionInfo()
    }

    @Test
    fun testSaveActionInfoAndReadActionInfo() {

        val startActionJSONString = Gson().toJson(StartAction())

        PersistCache.saveActionInfo(startActionJSONString)

        val readStartActionJSONString = PersistCache.readAllCachedActionInfo().dropLast(1)

        readStartActionJSONString.should.be.equal(startActionJSONString)

    }


    @Test
    fun testSaveExceptionInfoAndReadExceptionInfo() {

        PersistCache.saveExceptionInfo(exceptionInfo)

        val readExceptionInfo = PersistCache.readAllCachedExceptionInfo().dropLast(1)

        readExceptionInfo.should.be.equal(exceptionInfo)

    }

    companion object {
        @AfterClass
        @JvmStatic
        fun afterClass() {
            PersistCache.deleteAllActionInfo()
            PersistCache.deleteAllExceptionInfoCache()
        }
    }
}