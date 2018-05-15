package wu.seal.jsontokotlin.feedback

import com.google.gson.Gson
import org.junit.AfterClass
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class PersistCacheTest {
    val exceptionInfo = "Test exception"
    @Test
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

    companion object {
        @AfterClass
        @JvmStatic
        fun afterClass() {
            PersistCache.deleteAllActionInfo()
            PersistCache.deleteAllExceptionInfoCache()
        }
    }
}