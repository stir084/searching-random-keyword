package com.example.searchingrandomkeyword;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@SpringBootTest
class SearchingRandomKeywordApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void givenIP_whenFetchingCity_thenReturnsCityData()
			throws IOException, GeoIp2Exception {
		String ip = "116.37.136.233";
		String dbLocation = "your-path-to-mmdb";

	//	File database = new File("/src/main/resources/GeoLite2-City.mmdb");
	//	String dbLocation = "/GeoLite2-City.mmdb"; // 리소스 디렉토리를 기준으로 경로를 지정
		File uploads = new File("C:/GeoLite2-City.mmdb");
	//	File database = new ClassPathResource("/src/main/resources/GeoLite2-City.mmdb").getFile();
		DatabaseReader dbReader = new DatabaseReader.Builder(uploads)
				.build();

		InetAddress ipAddress = InetAddress.getByName(ip);
		CityResponse response = dbReader.city(ipAddress);

		String countryName = response.getCountry().getName();
		String cityName = response.getCity().getName();
		String postal = response.getPostal().getCode();
		String state = response.getLeastSpecificSubdivision().getName();
	}

}
