// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.openqa.selenium.docker;

import java.util.List;
import org.openqa.selenium.internal.Require;

public class ContainerLogs {

  private final List<String> logLines;
  private final ContainerId id;

  public ContainerLogs(ContainerId id, List<String> logLines) {
    this.logLines = Require.nonNull("Container logs", logLines);
    this.id = Require.nonNull("Container id", id);
  }

  public List<String> getLogLines() {
    return logLines;
  }

  public ContainerId getId() {
    return id;
  }

  @Override
  public String toString() {
    return "ContainerInfo{" + "containerLogs=" + logLines.toString() + ", id=" + id + '}';
  }
}
