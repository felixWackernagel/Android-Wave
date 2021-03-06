// Copyright 2009 Google Inc. All Rights Reserved.

package org.waveprotocol.wave.model.util;

/**
 * Utility base class for implementing chained data.
 *
 * @author danilatos@google.com (Daniel Danilatos)
 *
 * @see DataDomain for details on the type parameters
 */
public class ChainedData<R, T extends R> {

  private final DataDomain<R, T> domain;

  protected final ChainedData<R, T> parent;

  private boolean frozen = false;

  private final T overlay;
  private final T cache;
  private final R roCache;

  // 2 billion is probably big enough for everyone, but since on the client everything's
  // a doublle anyway, this doesn't really hurt.
  private double version = 0;
  private double knownParentVersion = 0;

  public ChainedData(DataDomain<R, T> domain) {
    this.domain = domain;
    this.parent = null;
    this.overlay = domain.empty();
    this.cache = overlay;
    this.roCache = domain.readOnlyView(cache);
  }

  public ChainedData(ChainedData<R, T> parent) {
    this.domain = parent.domain;
    this.parent = parent;
    this.overlay = domain.empty();
    this.cache = domain.empty();
    this.roCache = domain.readOnlyView(cache);
  }

  /**
   * Must be called whenever inspecting the current state, which is returned.
   * The state must not be modified without calling the {@link #modify()} method.
   */
  public R inspect() {
    maybeUpdateCache();
    return roCache;
  }

  /**
   * Must be called when updating the local overrides, and the changes
   * are applied to the return value
   */
  public T modify() {
    version++;
    knownParentVersion = -1;
    return overlay;
  }

  /**
   * Freezes the collection so that changes to ancestors, or from calls to
   * {@link #modify()} do not affect the return value of {@link #inspect()}
   *
   * First, updates the cache to get an up-to-date view.
   */
  public void freeze() {
    if (parent == null) {
      throw new UnsupportedOperationException("Cannot freeze the root collection");
    }
    maybeUpdateCache();
    frozen = true;
  }

  /**
   * Returns to normal mode.
   */
  public void unfreeze() {
    frozen = false;
  }

  private void maybeUpdateCache() {
    if (frozen) {
      return;
    }
    if (parent != null) {
      parent.maybeUpdateCache();
      if (parent.version != knownParentVersion) {
        domain.compose(cache, overlay, parent.roCache);
        knownParentVersion = parent.version;
        version++;
      }
    }
  }

  /** @VisibleForTesting */
  public double debugGetVersion() {
    return version;
  }

  /** @VisibleForTesting */
  public double debugGetKnownParentVersion() {
    return knownParentVersion;
  }
}
