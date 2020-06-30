package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.Property

/**
 * Created by Nstd on 2020/6/30 14:29.
 */
abstract class BaseClassCodeBuilder(
        override val name: String,
        override val modifiable: Boolean,
        open val annotations: List<Annotation> = listOf(),
        open val properties: List<Property> = listOf(),
        open val parentClassTemplate: String = "",
        open val comments: String = "",
        open val fromJsonSchema: Boolean = false
        ): ICodeBuilder