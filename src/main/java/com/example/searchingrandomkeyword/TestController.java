package com.example.searchingrandomkeyword;

import com.github.javafaker.Faker;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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
    public String getTest2() throws IOException, GeoIp2Exception {

        File uploads = new File("C:/GeoLite2-City.mmdb");
        Faker faker = new Faker();




        //	File database = new ClassPathResource("/src/main/resources/GeoLite2-City.mmdb").getFile();
        DatabaseReader dbReader = new DatabaseReader.Builder(uploads)
                .build();
        long startTime = System.currentTimeMillis();


        for (int i=0; i<1; i++){
            String ipaddr = faker.internet().ipV4Address();
            InetAddress ipAddress = InetAddress.getByName(ipaddr);
            CityResponse response;
            try {
                response = dbReader.city(ipAddress);
            } catch (AddressNotFoundException e) {
                continue;
            }


            String countryName = response.getCountry().getName();
            String cityName = response.getCity().getName();
            String postal = response.getPostal().getCode();
            String state = response.getLeastSpecificSubdivision().getName();
           // System.out.println(countryName +"--"+cityName+"--"+postal+"--"+state);
        }
        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;

        System.out.println(Thread.currentThread().getName());
        System.out.println("Execution time in milliseconds: " + timeElapsed);
        return "test";
    }
}
