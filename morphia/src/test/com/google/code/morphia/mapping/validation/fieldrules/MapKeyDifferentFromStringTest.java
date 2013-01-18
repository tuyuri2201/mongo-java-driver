/*
 * Copyright (c) 2008 - 2012 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 */
package com.google.code.morphia.mapping.validation.fieldrules;

import com.google.code.morphia.TestBase;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Serialized;
import com.google.code.morphia.testutil.AssertedFailure;
import com.google.code.morphia.testutil.TestEntity;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
@SuppressWarnings("unchecked")
public class MapKeyDifferentFromStringTest extends TestBase {

    static class MapWithWrongKeyType1 extends TestEntity {
        private static final long serialVersionUID = 1L;
        @Serialized
        private final Map<Integer, Integer> shouldBeOk = new HashMap();

    }

    static class MapWithWrongKeyType2 extends TestEntity {
        private static final long serialVersionUID = 1L;
        @Reference
        private final Map<Integer, Integer> shouldBeOk = new HashMap();

    }

    static class MapWithWrongKeyType3 extends TestEntity {
        private static final long serialVersionUID = 1L;
        @Embedded
        private final Map<BigDecimal, Integer> shouldBeOk = new HashMap();

    }

    @Test
    public void testCheck() {
        morphia.map(MapWithWrongKeyType1.class);

        new AssertedFailure() {
            public void thisMustFail() throws Throwable {
                morphia.map(MapWithWrongKeyType2.class);
            }
        };

        new AssertedFailure() {
            public void thisMustFail() throws Throwable {
                morphia.map(MapWithWrongKeyType3.class);
            }
        };
    }

}