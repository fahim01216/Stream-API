package com.streamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// lazy loading -> until and unless the data is needed by the operators, operators don't call those functions.

@SpringBootApplication
public class StreamApiApplication {

	static List<Employee> employees = new ArrayList<>();
	static {
		employees.add(
				new Employee("Fahim", "Raj", 5000.0, List.of("Project1", "Project2"))
		);

		employees.add(
				new Employee("Sunny", "Kumar", 6000.0, List.of("Project2", "Project3"))
		);

		employees.add(
				new Employee("Afreen", "Sheikh", 8000.0, List.of("Project1", "Project3"))
		);

	}

	public static void main(String[] args) {
		//SpringApplication.run(StreamApiApplication.class, args);

		//Stream.of(employees);
		employees.stream().forEach(employee -> System.out.println(employee));

		// map & collect
		List<Employee> empList = employees.stream().map(employee -> new Employee(
				employee.getFirstName(),
				employee.getLastName(),
				employee.getSalary() * 1.10,
				employee.getProjects()
		)).collect(Collectors.toList());
		System.out.println(empList);

		// filter & map & collect
		List<Employee> filteredEmployee = employees.stream().filter(employee -> employee.getSalary() > 5000.0)
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary() * 1.10,
						employee.getProjects()
				)).collect(Collectors.toList());
		System.out.println(filteredEmployee);

		// findFirst method
		Employee firstEmployee = employees.stream().filter(employee -> employee.getSalary() > 9000.0)
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary() * 1.10,
						employee.getProjects()
				)).findFirst().orElse(null);
		System.out.println(firstEmployee);

		// flatMap
		String projects = employees.stream()
				.map(employee -> employee.getProjects())
				.flatMap(strings -> strings.stream())
				.collect(Collectors.joining(","));
		System.out.println(projects);

		// short circuit opeartions
		List<Employee> shortCircuit = employees.stream()
				.skip(1)
				.limit(2)
				.collect(Collectors.toList());
		System.out.println(shortCircuit);

		// Finite Data
		Stream.generate(Math::random)
				.limit(5)
				.forEach(value -> System.out.println(value));

		// sorting
		List<Employee> sortedEmployee = employees.stream()
				.sorted((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
				.collect(Collectors.toList());
		System.out.println(sortedEmployee);

		// max or min
		Employee maxSalary = employees.stream()
				.max(Comparator.comparing(Employee::getSalary))
				.orElseThrow(NoSuchElementException::new);
		System.out.println(maxSalary);

		// reduce
		Double totalSalary = employees.stream()
				.map(employee -> employee.getSalary())
				.reduce(0.0, Double::sum);
		System.out.println(totalSalary);
	}

}
