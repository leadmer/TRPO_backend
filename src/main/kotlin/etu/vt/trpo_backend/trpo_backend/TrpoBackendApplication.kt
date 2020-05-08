package etu.vt.trpo_backend.trpo_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.util.unit.DataSize
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import javax.servlet.MultipartConfigElement


@SpringBootApplication
class TrpoBackendApplication

	@Bean
	fun multipartConfigElement(): MultipartConfigElement? {
		val factory = MultipartConfigFactory()
		factory.setMaxFileSize(DataSize.ofMegabytes(20))
		factory.setMaxRequestSize(DataSize.ofMegabytes(20))
		return factory.createMultipartConfig()
	}

	fun main(args: Array<String>) {
		runApplication<TrpoBackendApplication>(*args)
	}
