package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Created by Nstd on 2020/6/30 15:57.
 */
abstract class BaseListCodeBuilder(
        override val name: String,
        override val modifiable: Boolean,
        val generic: KotlinClass,
        val referencedClasses: List<KotlinClass>
        ): ICodeBuilder