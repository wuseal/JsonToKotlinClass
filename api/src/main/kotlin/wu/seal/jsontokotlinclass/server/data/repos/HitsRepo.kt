package wu.seal.jsontokotlinclass.server.data.repos

import org.springframework.data.repository.CrudRepository
import wu.seal.jsontokotlinclass.server.data.entities.Hit

interface HitsRepo : CrudRepository<Hit, Int> {
}