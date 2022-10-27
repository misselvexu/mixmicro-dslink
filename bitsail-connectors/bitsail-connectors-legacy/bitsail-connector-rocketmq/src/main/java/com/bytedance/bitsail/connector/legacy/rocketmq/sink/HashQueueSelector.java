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

package com.bytedance.bitsail.connector.legacy.rocketmq.sink;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class HashQueueSelector implements MessageQueueSelector {

  private int nullKeyCount;

  public HashQueueSelector() {
    super();
    nullKeyCount = 0;
  }

  @Override
  public MessageQueue select(List<MessageQueue> mqList, Message message, Object partitionKeys) {
    int queueId;

    if (partitionKeys != null) {
      queueId = partitionKeys.hashCode() % mqList.size();
    } else {
      queueId = nullKeyCount % mqList.size();
      nullKeyCount = (nullKeyCount + 1) % mqList.size();
    }

    return mqList.get(queueId);
  }
}

