package com.mfh;

import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.junit.Test;

/**
 * @author mfh
 * @date 2019/12/15 18:41
 */
public class CrowdTest {
    @Test
    public void testMd5() {
        final String result = CrowdFundingUtils.md5("123123");
        System.out.printf(result);
    }
}
