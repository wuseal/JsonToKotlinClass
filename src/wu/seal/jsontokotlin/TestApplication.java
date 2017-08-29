package wu.seal.jsontokotlin;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by LENOVO on 2017/8/21.
 */
public class TestApplication implements ApplicationComponent {
    public TestApplication() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "wu.seal.wu.seal.jsontokotlin.TestApplication";
    }
}
