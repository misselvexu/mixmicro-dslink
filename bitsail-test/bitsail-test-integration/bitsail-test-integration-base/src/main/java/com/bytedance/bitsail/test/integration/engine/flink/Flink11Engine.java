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

package com.bytedance.bitsail.test.integration.engine.flink;

import com.bytedance.bitsail.base.packages.PluginFinder;
import com.bytedance.bitsail.base.packages.PluginFinderFactory;
import com.bytedance.bitsail.common.catalog.TableCatalogOptions;
import com.bytedance.bitsail.common.configuration.BitSailConfiguration;
import com.bytedance.bitsail.common.option.CommonOptions;
import com.bytedance.bitsail.core.api.command.CoreCommandArgs;
import com.bytedance.bitsail.core.api.program.UnifiedProgram;
import com.bytedance.bitsail.core.flink.bridge.program.FlinkProgram;
import com.bytedance.bitsail.test.integration.engine.IntegrationEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Flink11Engine implements IntegrationEngine {
  private static final Logger LOG = LoggerFactory.getLogger(Flink11Engine.class);

  private static final long DEFAULT_JOB_ID = -1L;

  @Override
  public void submitJob(BitSailConfiguration jobConf) throws Exception {
    if (Objects.isNull(jobConf)) {
      LOG.error("Submit failed, configuration is empty.");
      throw new IllegalStateException("Submit failed, configuration is empty");
    }
    overwriteConfiguration(jobConf);
    LOG.info("Final Configuration: {}.\n", jobConf.desensitizedBeautify());
    CoreCommandArgs coreCommandArgs = new CoreCommandArgs();
    coreCommandArgs.setEngineName("flink");
    UnifiedProgram unifiedProgram = new FlinkProgram();
    PluginFinder pluginFinder = PluginFinderFactory.getPluginFinder(jobConf.get(CommonOptions.PLUGIN_FINDER_NAME));
    pluginFinder.configure(jobConf);
    unifiedProgram.configure(pluginFinder, jobConf, coreCommandArgs);
    unifiedProgram.submit();
  }

  private static void overwriteConfiguration(BitSailConfiguration jobConf) {
    jobConf.set(CommonOptions.JOB_ID, DEFAULT_JOB_ID)
        .set(TableCatalogOptions.SYNC_DDL, false);
  }
}
