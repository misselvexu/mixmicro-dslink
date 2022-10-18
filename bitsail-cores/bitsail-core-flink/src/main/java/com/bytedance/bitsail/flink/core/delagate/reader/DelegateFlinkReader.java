/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.bytedance.bitsail.flink.core.delagate.reader;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.connector.source.SourceReader;
import org.apache.flink.api.connector.source.SourceReaderContext;
import org.apache.flink.api.connector.source.SourceSplit;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.runtime.operators.coordination.OperatorEventGateway;
import org.apache.flink.streaming.api.operators.SourceOperator;
import org.apache.flink.streaming.runtime.tasks.ProcessingTimeService;
import org.apache.flink.util.function.FunctionWithException;

public class DelegateFlinkReader<T, SplitT extends SourceSplit> extends SourceOperator<T, SplitT> {

  public DelegateFlinkReader(
      FunctionWithException<SourceReaderContext,
          SourceReader<T, SplitT>, Exception> readerFactory,
      OperatorEventGateway operatorEventGateway,
      SimpleVersionedSerializer<SplitT> splitSerializer,
      WatermarkStrategy<T> watermarkStrategy,
      ProcessingTimeService timeService,
      Configuration configuration, String localHostname) {
    super(readerFactory, operatorEventGateway, splitSerializer, watermarkStrategy, timeService, configuration, localHostname);
  }
}
