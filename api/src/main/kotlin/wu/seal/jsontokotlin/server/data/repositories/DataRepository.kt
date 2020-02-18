package wu.seal.jsontokotlin.server.data.repositories

import org.springframework.data.repository.CrudRepository
import wu.seal.jsontokotlin.server.data.models.ActionInfo
import wu.seal.jsontokotlin.server.data.models.ConfigInfo


/**
 * 数据仓库类
 * Created by Seal.Wu on 2017/9/26.
 */

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

interface ActionRepository : CrudRepository<ActionInfo, Long>

interface ConfigInfoRepository : CrudRepository<ConfigInfo, Long>