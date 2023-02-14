package com.streamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		System.out.println("---------------------------------------------------");

		// map & collect
		List<Employee> empList = employees.stream().map(employee -> new Employee(
				employee.getFirstName(),
				employee.getLastName(),
				employee.getSalary() * 1.10
		)).collect(Collectors.toList());
		System.out.println(empList);
		System.out.println("---------------------------------------------------");

		// filter & map & collect
		List<Employee> filteredEmployee = employees.stream().filter(employee -> employee.getSalary() > 5000.0)
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary() * 1.10,
						employee.getProjects()
				)).collect(Collectors.toList());
		System.out.println(filteredEmployee);
		System.out.println("---------------------------------------------------");

		// findFirst method
		Employee firstEmployee = employees.stream().filter(employee -> employee.getSalary() > 7000.0)
				.map(employee -> new Employee(
						employee.getFirstName(),
						employee.getLastName(),
						employee.getSalary() * 1.10,
						employee.getProjects()
				)).findFirst().orElse(null);
		System.out.println(firstEmployee);
		System.out.println("---------------------------------------------------");

		// flatMap
		String projects = employees.stream()
				.map(employee -> employee.getProjects())
				.flatMap(strings -> strings.stream())
				.collect(Collectors.joining(","));
		System.out.println(projects);
		System.out.println("---------------------------------------------------");

		// short circuit opeartions
		List<Employee> shortCircuit = employees.stream()
				.skip(1)
				.limit(2)
				.collect(Collectors.toList());
		System.out.println(shortCircuit);
		System.out.println("---------------------------------------------------");

		// Finite Data
		Stream.generate(Math::random)
				.limit(5)
				.forEach(value -> System.out.println(value));
		System.out.println("---------------------------------------------------");

		// sorting
		List<Employee> sortedEmployee = employees.stream()
				.sorted((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
				.collect(Collectors.toList());
		System.out.println(sortedEmployee);
		System.out.println("---------------------------------------------------");

		// max or min
		Employee maxSalary = employees.stream()
				.max(Comparator.comparing(Employee::getSalary))
				.orElseThrow(NoSuchElementException::new);
		System.out.println(maxSalary);
		System.out.println("---------------------------------------------------");

		// reduce
		Double totalSalary = employees.stream()
				.map(employee -> employee.getSalary())
				.reduce(0.0, Double::sum);
		System.out.println(totalSalary);
		System.out.println("---------------------------------------------------");

		Consumer<Integer> consumer = (a) -> System.out.println(a);
		consumer.accept(10);
		System.out.println("---------------------------------------------------");



		// write a program to print all the employees name whose name starts with "S"
		employees.stream().filter(emp -> emp.getFirstName().startsWith("S"))
				.forEach(System.out::println);
		System.out.println("---------------------------------------------------");



		// write a program to get all the employees names into separate list
		List<String> list = employees.stream().map(emp -> emp.getFirstName()).collect(Collectors.toList());
		System.out.println("List of employees firstName " + list);
		System.out.println("---------------------------------------------------");



		// write a program to print the name of the employee which has minimum salary
		String name = employees.stream().min(Comparator.comparing(Employee::getSalary)).get().getFirstName();
		System.out.println("Min salary employee name is " + name);
		System.out.println("---------------------------------------------------");



		// write a program to print the name of the employee which has maximum salary
		Employee employee =  employees.stream().max((e1, e2) -> (int) (e1.getSalary() - e2.getSalary())).get();
		System.out.println("Max salary employee name is " + employee.getFirstName() + " and its salary is " + employee.getSalary());
		System.out.println("---------------------------------------------------");


		// write a program to remove duplicates from arraylist
		List<Integer> numList = Arrays.asList(101, 20, 10, 20, 30, 502, 801, 10, 941, 621);
		List<Integer> distinctNum =  numList.stream().distinct().collect(Collectors.toList());
		System.out.println("List of distinct numbers " + distinctNum);
		System.out.println("---------------------------------------------------");



		// write a program to sort the element of the arraylist
		List<Integer> sortedList = numList.stream().sorted().collect(Collectors.toList());
		System.out.println("List of sorted numbers " + sortedList);
		System.out.println("---------------------------------------------------");



		// write a program to check a string is palindrome or not;
		String str = "madam";
		boolean check = IntStream.range(0, str.length() / 2)
				.allMatch(i -> str.charAt(i) == str.charAt(str.length() - 1 - i));
		System.out.println("Is palindrome: " + check);
		System.out.println("---------------------------------------------------");



		// flatmap does one-to-many mapping whereas map does one-to-one mapping
		List<Integer> numList1 = Arrays.asList(-72, -237, -83, -92, -87, 87, -83);
		List<List<Integer>> flatList = new ArrayList<>();
		flatList.add(numList);
		flatList.add(numList1);

		List<Integer> result = flatList.stream().flatMap(num -> num.stream()).collect(Collectors.toList());
		System.out.println("FlatList is " + result);
		System.out.println("---------------------------------------------------");

	}

}
