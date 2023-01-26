package com.epam.rd.qa.at;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * @author D. Kolesnikov
 */
public class TaskTest {

	@Test
	void mainTest() {
		final PrintStream stdOut = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
		try {
			Task.main(new String[] {});
		} finally {
			System.setOut(stdOut);
		}
		String res = baos.toString();
		System.out.println(res);
		List<Integer> actual = res.lines()
			.map(s ->  Arrays.stream(s.split(" "))
					.map(Integer::valueOf)
					.reduce(1, (x, y) -> x * y)
			)
			.toList();
		List<Integer> expected = IntStream.rangeClosed(1, 9)
				.boxed()
				.map(x -> Math.pow(x, 6))
				.map(Math::round)
				.map(Math::toIntExact)
				.toList();
		Assertions.assertIterableEquals(expected, actual);
	}

	@Test
	void complianceTest() throws Exception {
		JavaClass jc = Repository.lookupClass(Task.class);
		java.lang.reflect.Method method = Task.class.getDeclaredMethod("main", String[].class);
		Method m = jc.getMethod(method);
		String code = m.getCode().toString(false);
		Matcher matcher = Pattern.compile("(?m)^.+\\s+goto\\s+.*$").matcher(code);
		Assertions.assertTrue(matcher.find(), () -> "PlayerImpl#main method must use loop");
		Assertions.assertTrue(!matcher.find(), () -> "There is must be just only one loop");
	}

}
