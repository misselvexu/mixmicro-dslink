/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytedance.bitsail.base.serializer;

import org.junit.Assert;
import org.junit.Test;

public class SerializerTest {

  @Test
  public void testBinarySerializer() throws Exception {
    BinarySerializer<String> serializer = new SimpleBinarySerializer<>();
    Assert.assertArrayEquals(new byte[0], serializer.serialize("test_str"));
    Assert.assertNull(serializer.deserialize(new byte[] {1, 2, 3}));
  }
}
