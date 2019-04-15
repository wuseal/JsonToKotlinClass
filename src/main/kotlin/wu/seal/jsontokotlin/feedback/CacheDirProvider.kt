package wu.seal.jsontokotlin.feedback

import wu.seal.jsontokotlin.utils.LogUtil
import java.lang.System.getProperty
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Provides directory to store plugin cache files.
 *
 * @author Ruslan Ibragimov
 */
interface CacheDirProvider {
    fun get(): Path
}

/**
 * For some reason `XDG_CACHE_HOME` not available in System.getenv,
 * so approach with checking is system cache folder exist used.
 *
 * @author Ruslan Ibragimov
 */
class DefaultCacheDirProvider : CacheDirProvider {
    override fun get(): Path {
        val home = Paths.get(getProperty(USER_HOME))
        val xdg = home.resolve(".cache")
        val mac = home.resolve("Library").resolve("Caches")

        return when {
            Files.exists(xdg) -> xdg
            Files.exists(mac) -> mac
            else -> home
        }.also {
            LogUtil.i("Cache directory is: $it")
        }
    }

    companion object {
        const val USER_HOME = "user.home"
    }
}
