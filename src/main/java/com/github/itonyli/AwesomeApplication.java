package com.github.itonyli;

import com.github.itonyli.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AwesomeApplication {

	@Autowired
	private RedisLock redisLock;

	public static void main(String[] args) {
		SpringApplication.run(AwesomeApplication.class, args);
	}

	@GetMapping("index")
	public Object index() {
		redisLock.test();
		return ResponseEntity.noContent().build();
	}
}
