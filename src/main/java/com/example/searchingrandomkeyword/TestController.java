package com.example.searchingrandomkeyword;

import com.github.javafaker.Faker;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class TestController {
    private static PythonInterpreter intPre;
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
        System.setProperty("python.import.site", "false");
        intPre = new PythonInterpreter();
        intPre.execfile("src/main/java/com/example/searchingrandomkeyword/test.py");
        intPre.exec("print(testFunc(5,10))");

        PyFunction pyFuntion = (PyFunction) intPre.get("testFunc", PyFunction.class);
        int a = 10, b = 20;
        PyObject pyobj = pyFuntion.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyobj.toString());

        return pyobj.toString();
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String getTest2(){
        Faker faker = new Faker();

        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton
        String ipaddr = faker.internet().ipV4Address();
        String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449

        Faker faker2 = new Faker(new Locale("ko"));

        String name2 = faker.name().fullName();
        String firstName2 = faker.name().firstName();
        String lastName2 = faker.name().lastName();

        return "test";
    }
}
