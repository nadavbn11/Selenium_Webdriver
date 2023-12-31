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

package org.openqa.selenium.remote;

import static org.openqa.selenium.remote.Browser.CHROME;
import static org.openqa.selenium.remote.Browser.EDGE;
import static org.openqa.selenium.remote.Browser.OPERA;

import java.util.function.Predicate;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.logging.HasLogEvents;

public class AddHasLogEvents implements AugmenterProvider<HasLogEvents> {

  private static final Predicate<String> IS_CHROMIUM_BROWSER =
      name -> CHROME.is(name) || EDGE.is(name) || OPERA.is(name);

  @Override
  public Predicate<Capabilities> isApplicable() {
    return caps -> IS_CHROMIUM_BROWSER.test(caps.getBrowserName());
  }

  @Override
  public Class<HasLogEvents> getDescribedInterface() {
    return HasLogEvents.class;
  }

  @Override
  public HasLogEvents getImplementation(Capabilities capabilities, ExecuteMethod executeMethod) {
    return new HasLogEvents() {
      @Override
      public <X> void onLogEvent(EventType<X> kind) {
        if (((RemoteExecuteMethod) executeMethod).getWrappedDriver() instanceof HasDevTools) {
          WebDriver driver = ((RemoteExecuteMethod) executeMethod).getWrappedDriver();
          kind.initializeListener(driver);
        }
      }
    };
  }
}
