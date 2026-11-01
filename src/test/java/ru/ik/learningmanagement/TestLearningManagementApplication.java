package ru.ik.learningmanagement;

import org.springframework.boot.SpringApplication;

public class TestLearningManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(LearningManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
