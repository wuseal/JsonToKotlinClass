package wu.seal.jsontokotlinclass.server.data.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import wu.seal.jsontokotlin.DefaultValueStrategy
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "hits")
class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    lateinit var client: String
    lateinit var className: String
    lateinit var annotationLib: String
    var classSuffix: String? = null
    lateinit var defaultValueStrategy: String
    var indent: Int? = null
    var isCommentsEnabled : Boolean? = null
    var is

}