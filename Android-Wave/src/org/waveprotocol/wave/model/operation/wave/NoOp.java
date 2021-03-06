/**
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.waveprotocol.wave.model.operation.wave;

import org.waveprotocol.wave.model.wave.data.WaveletData;

/**
 * Operation class for a no-op.
 */
public final class NoOp extends WaveletOperation {

  /**
   * The singleton instance.
   */
  public static final NoOp INSTANCE = new NoOp();

  private NoOp() {}

  /**
   * Does nothing.
   */
  @Override
  protected void doApply(WaveletData target) {
    // do nothing.
  }

  @Override
  public WaveletOperation getInverse() {
    return this;
  }

  @Override
  public String toString() {
    return "NoOp()";
  }

}
