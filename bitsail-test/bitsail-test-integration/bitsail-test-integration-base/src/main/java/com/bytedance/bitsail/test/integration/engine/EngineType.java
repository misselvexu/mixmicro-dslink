/*
 * Copyright 2022-2023 Bytedance Ltd. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytedance.bitsail.test.integration.engine;

import com.bytedance.bitsail.common.BitSailException;
import com.bytedance.bitsail.common.exception.CommonErrorCode;
import com.bytedance.bitsail.test.integration.engine.flink.Flink11Engine;

public enum EngineType {
  FLINK_1_11(Flink11Engine.class);

  private final Class<? extends IntegrationEngine> clazz;

  EngineType(Class<? extends IntegrationEngine> clazz) {
    this.clazz = clazz;
  }

  public IntegrationEngine getInstance() {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      throw BitSailException.asBitSailException(CommonErrorCode.RUNTIME_ERROR,
          "Failed to create integration test engine for " + this.name(), e);
    }
  }
}
