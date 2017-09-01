package com.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: tree
 * @version: 1.0
 * date: 2017/9/1 17:57
 * @description: xxx
 * own: Aratek
 */
public class CommonStringUtilTest {

    @Test
    public void testSubStringByRule() throws Exception {
        Assert.assertEquals("bstri",CommonStringUtil.subStringByRule("substring","u","n"));
    }
}