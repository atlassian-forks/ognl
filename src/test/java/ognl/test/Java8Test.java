package ognl.test;

import junit.framework.TestCase;
import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.List;

public class Java8Test extends TestCase {

    public void testDefaultMethodOnClass() {
        /* defaultMethod(); */
        List defaultMethod = OgnlRuntime.getMethods(ClassWithDefaults.class, "defaultMethod", false);
        assertNotNull(defaultMethod);
        Method method = OgnlRuntime.getReadMethod(ClassWithDefaults.class, "defaultMethod");
        assertNotNull(method);
    }

    public void testDefaultMethodOnSubClass() {
        /* defaultMethod(); */
        List defaultMethod = OgnlRuntime.getMethods(SubClassWithDefaults.class, "defaultMethod", false);
        assertNotNull(defaultMethod);
        Method method = OgnlRuntime.getReadMethod(SubClassWithDefaults.class, "defaultMethod");
        assertNotNull(method);
    }

    public void testGetDeclaredMethods() throws IntrospectionException, OgnlException {
        List defaultMethod = OgnlRuntime.getDeclaredMethods(SubClassWithDefaults.class, "name", false);
        assertNotNull(defaultMethod);
        defaultMethod = OgnlRuntime.getDeclaredMethods(ClassWithDefaults.class, "name", false);
        assertNotNull(defaultMethod);
    }

    public void testAccessingDefaultMethod() throws OgnlException {
        ClassWithDefaults root = new ClassWithDefaults();
        Object value = Ognl.getValue("name", Ognl.createDefaultContext(root), root);

        assertEquals("name", value);
    }

}

class SubClassWithDefaults extends ClassWithDefaults {

    public String getName() {
        return "name";
    }

}

class ClassWithDefaults implements SubInterfaceWithDefaults {

}

interface InterfaceWithDefaults {
    default void defaultMethod() {
    }

    default String getName() {
        return "name";
    }
}

interface SubInterfaceWithDefaults extends InterfaceWithDefaults {
}
