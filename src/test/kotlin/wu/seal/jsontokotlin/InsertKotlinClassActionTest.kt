package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class InsertKotlinClassActionTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForEmtpyString() {
        val action = InsertKotlinClassAction()
        val emptyString = ""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`

    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForSpecialString() {
        TestConfig.setToTestInitState()
        val action = InsertKotlinClassAction()

        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(specialString).should.be.`false`

    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForOnlyPackageNameDeclare() {
        val action = InsertKotlinClassAction()
        val emptyString = "package wu.seal.jsontokotlin"
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndCommentDeclare() {
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyOnlyParentheses() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test()"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyOnlyParenthesesAndCurlyBraces() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test(){     }"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`true`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyWithProperty() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test(val a:String="1")"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeWithClassBodyWithFunction() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test{fun c(){}}"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndNotOnlyOneClassNameDeclare() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test
                            class Test2"""
        action.couldGetAndReuseClassNameInCurrentEditFileForInsertCode(emptyString).should.be.`false`
    }

    @Test
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCodeForPackageAndOnlyOneClassNameWithCommentContainsClassStringDeclare() {
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test
                            """
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }


    @Test
    fun getCurrentEditFileTemClassNameForInsertCodeWithClassBodyOnlyParentheses() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test()"""
        action.getCurrentEditFileTemClassName(emptyString).should.be.equal("Test")
    }

    @Test
    fun getCurrentEditFileTemClassNameForPackageAndOnlyOneClassNameWithCommentContainsClassStringDeclare() {
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test"""
        action.getCleanText(emptyString).trim().should.be.equal("package wu.seal.jsontokotlin.supporter")
    }


    @Test
    fun getCleanTextForInsertCodeWithClassBodyOnlyParentheses() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test()"""
        action.getCleanText(emptyString).trim().should.be.equal("package wu.seal.jsontokotlin.supporter")
    }

    @Test
    fun getCleanTextForInsertCodeWithClassBodyOnlyParenthesesAndCurlyBraces() {
        val action = InsertKotlinClassAction()
        val emptyString = """
                            package wu.seal.jsontokotlin.supporter
                            class Test(){     }"""
        action.getCleanText(emptyString).trim().should.be.equal("package wu.seal.jsontokotlin.supporter")
    }

    @Test
    fun getCleanTextForPackageAndOnlyOneClassNameWithCommentContainsClassStringDeclare() {
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
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
        val action = InsertKotlinClassAction()
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

    private val specialString = """package cn.com.iresearch.phonemonitor;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dh.foundation.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.iresearch.phonemonitor.diaoyantong.R;

/**
 * 对话框碎片
 * Created By: Seal.Wu
 * Date: 2016/4/8
 * Time: 14:32
 */
public class ViewDialogFragment extends DialogFragment {

    @Bind(R.id.tv_tips)
    TextView tvTips;
    @Bind(R.id.btn_left)
    Button btnLeft;
    @Bind(R.id.btn_right)
    Button btnRight;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.spacer)
    View spacer;
    @Bind(R.id.ll_btn_layout)
    LinearLayout llBtnLayout;
    @Bind(R.id.line_bt_btn)
    ImageView line;
    @Bind(R.id.fl_tips)
    LinearLayout flTips;

    private Runnable runnable;

    private View customView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (customView != null) {
            return customView;
        }
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        ButterKnife.bind(this, view);
        if (runnable != null) {
            runnable.run();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                break;
            case R.id.btn_right:
                break;
        }
    }

}


"""
}
