package com.project.products;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mockStatic;

@RunWith(SpringRunner.class)
class ProductsApplicationTests {

	@Test
	public void applicationStarts() {

		try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

			mocked.when(() -> {
						SpringApplication.run(ProductsApplication.class,
								new String[]{"foo", "bar"});
					})
					.thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

			ProductsApplication.main(new String[]{"foo", "bar"});

			mocked.verify(() -> {
				SpringApplication.run(ProductsApplication.class,
						new String[]{"foo", "bar"});
			});
		}
	}

}
