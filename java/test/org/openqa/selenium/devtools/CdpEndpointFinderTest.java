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

package org.openqa.selenium.devtools;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.json.Json;

@Tag("UnitTests")
class CdpEndpointFinderTest {

  @Test
  void shouldReturnEmptyIfNoDebuggerAddressIsGiven() {
    Optional<URI> uri =
        CdpEndpointFinder.getReportedUri("foo:options", new ImmutableCapabilities());

    assertThat(uri).isEmpty();
  }

  @Test
  void shouldReturnUriIfPresent() {
    Capabilities caps =
        new Json()
            .toType(
                "{\"ms:edgeOptions\": { \"debuggerAddress\": \"localhost:55498\" }}",
                Capabilities.class);

    Optional<URI> uri = CdpEndpointFinder.getReportedUri("ms:edgeOptions", caps);

    assertThat(uri).contains(URI.create("http://localhost:55498"));
  }

  @Test
  void shouldReturnUriIfPresentAndIsAtTopLevel() {
    Capabilities caps =
        new Json().toType("{\"moz:debuggerAddress\": \"localhost:93487\" }", Capabilities.class);

    Optional<URI> uri = CdpEndpointFinder.getReportedUri("moz:debuggerAddress", caps);

    assertThat(uri).contains(URI.create("http://localhost:93487"));
  }
}
