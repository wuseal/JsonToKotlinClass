package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class MakeKotlinClassActionTest {

    @Test
    fun before() {
        TestConfig.isTestModel = true
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForEmtpyString() {
        val action = MakeKotlinClassAction()
        val emptyString = ""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`

    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForOnlyPackageNameDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = "package wu.seal.jsontokotlin"
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndCommentDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter

                            import org.junit.Before
                            import org.junit.Test
                            import wu.seal.jsontokotlin.test.TestConfig.isTestModel

                            /**
                             *
                             * Created by Seal.Wu on 2017/11/1.
                             */"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndOnlyOneClassNameDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyOnlyParentheses() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test()"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyOnlyParenthesesAndCurlyBraces() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test(){     }"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyWithProperty() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test(val a:String="1")"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyWithFunction() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test{fun c(){}}"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndNotOnlyOneClassNameDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test
                            class Test2"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndOnlyOneClassNameWithCommentContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                            class Test
                            """
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndOnlyOneClassNameWithPackageNameContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlinclass.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                            class Test
                            """
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndOnlyOneClassNameWithImportPackageNameContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                             import wu.seal.jsontokotlinclass.test.TestConfig.isTestModel
                            class Test
                            """
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }


    @Test
    fun getCurrentEditFileTemClassNameForPackageAndOnlyOneClassNameDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test
                            """
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }


    @Test
    fun getCurrentEditFileTemClassNameForInsertCodeWithClassBodyOnlyParentheses() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test()"""
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }

    @Test
    fun getCurrentEditFileTemClassNameForPackageAndOnlyOneClassNameWithCommentContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                            class Test
                            """
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }

    @Test
    fun getCurrentEditFileTemClassNameForPackageAndOnlyOneClassNameWithPackageNameContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlinclass.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                            class Test
                            """
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }

    @Test
    fun getCurrentEditFileTemClassNameForPackageAndOnlyOneClassNameWithImportPackageNameContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                             import wu.seal.jsontokotlinclass.test.TestConfig.isTestModel
                            class Test(){}
                            """
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }

    @Test
    fun getCleanTextForPackageAndOnlyOneClassNameDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test"""
        action.getCleanText(emptyString).trim().should.be.equal("package wu.seal.jsontokotlin.supporter")
    }


    @Test
    fun getCleanTextForInsertCodeWithClassBodyOnlyParentheses() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test()"""
        action.getCleanText(emptyString).trim().should.be.equal("package wu.seal.jsontokotlin.supporter")
    }

    @Test
    fun getCleanTextForInsertCodeWithClassBodyOnlyParenthesesAndCurlyBraces() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test(){     }"""
        action.getCleanText(emptyString).trim().should.be.equal("package wu.seal.jsontokotlin.supporter")
    }
    @Test
    fun getCleanTextForPackageAndOnlyOneClassNameWithCommentContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                            class Test
                            """
        action.getCleanText(emptyString).trim().should.be.equal(
                            """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */""".trim())
    }

    @Test
    fun getCleanTextForPackageAndOnlyOneClassNameWithPackageNameContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlinclass.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                            class Test
                            """
        action.getCleanText(emptyString).trim().should.be.equal("""
                            package wu.seal.jsontokotlinclass.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */""".trim())
    }

    @Test
    fun getCleanTextForPackageAndOnlyOneClassNameWithImportPackageNameContainsClassStringDeclare() {
        val action = MakeKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                             import wu.seal.jsontokotlinclass.test.TestConfig.isTestModel
                            class Test(){}
                            """
        action.getCleanText(emptyString).trim().should.be.equal("""
                            package wu.seal.jsontokotlin.supporter
                            /**
                             *
                             * Created by Seal.Wu.class on 2017/11/1.
                             */
                             import wu.seal.jsontokotlinclass.test.TestConfig.isTestModel""".trim())
    }
}
