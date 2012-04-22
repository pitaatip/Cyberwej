package pl.edu.agh.cyberwej;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SecondTest {

	@Test
	public void testJenkins() {
		List<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("def");
		assertEquals(2, list.size());
	}

}
