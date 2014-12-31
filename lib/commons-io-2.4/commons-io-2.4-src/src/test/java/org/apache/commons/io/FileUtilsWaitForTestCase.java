/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io;

import java.io.File;

import org.apache.commons.io.testtools.FileBasedTestCase;

/**
 * This is used to test FileUtils.waitFor() method for correctness.
 *
 * @version $Id: FileUtilsWaitForTestCase.java 1302056 2012-03-18 03:03:38Z ggregory $
 * @see FileUtils
 */
public class FileUtilsWaitForTestCase extends FileBasedTestCase {
    // This class has been broken out from FileUtilsTestCase
    // to solve issues as per BZ 38927

    public FileUtilsWaitForTestCase(String name) {
        super(name);
    }

    /** @see junit.framework.TestCase#setUp() */
    @Override
    protected void setUp() throws Exception {
        getTestDirectory().mkdirs();
    }

    /** @see junit.framework.TestCase#tearDown() */
    @Override
    protected void tearDown() throws Exception {
        FileUtils.deleteDirectory(getTestDirectory());
    }

    //-----------------------------------------------------------------------
    public void testWaitFor() {
        FileUtils.waitFor(new File(""), -1);
        FileUtils.waitFor(new File(""), 2);
    }

}
