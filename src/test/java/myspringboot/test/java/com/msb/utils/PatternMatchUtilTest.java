/**
 * 
 */
package myspringboot.test.java.com.msb.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.msb.myspringboot.utils.PatternMatchUtil;

/**
 * @author Stickerleee
 * @since 2021年4月23日 上午9:11:26
 */
public class PatternMatchUtilTest {

	@Test
	void testMatch() {
		assertTrue(PatternMatchUtil.simpleMatch("com.msb.jsonboot.utils", "com.msb.jsonboot.utils"));
		assertTrue(PatternMatchUtil.simpleMatch("com.msb.jsonboot.utils*", "com.msb.jsonboot.utils"));
		assertTrue(PatternMatchUtil.simpleMatch("com.msb.jsonboot.utils", "com.msb.jsonboot.utils"));
	}
}
