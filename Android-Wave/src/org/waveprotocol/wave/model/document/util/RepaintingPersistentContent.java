// Copyright 2009 Google Inc. All Rights Reserved.

package org.waveprotocol.wave.model.document.util;

import org.waveprotocol.wave.model.document.raw.RawDocument;

/**
 * Extension to persistent content that is aware of the need to repaint new
 * text nodes and provides the logic to do so.
 *
 * @author danilatos@google.com (Daniel Danilatos)
 */
public abstract class RepaintingPersistentContent<N, E extends N, T extends N>
    extends PersistentContent<N, E, T> {

  private final RawDocument<N, E, T> fullDoc;

  /**
   * @see PersistentContent#PersistentContent(RawDocument, ElementManager)
   */
  public RepaintingPersistentContent(RawDocument<N, E, T> fullDoc,
      ElementManager<E> elementManager) {
    super(fullDoc, elementManager);
    this.fullDoc = fullDoc;
  }

  // We override create text node and insertData to schedule annotation repaints.
  // New text nodes obviously need it, for example after starting a new paragraph
  // that is styled according to the data structure, but there is no paint node
  // around. Insert data at position zero is also needed, as we sometimes need to
  // *remove* styling from an already styled node, e.g. if it's at the start of
  // a paragraph at an annotation boundary.

  @Override
  public T createTextNode(String data,
      E parent, N nodeAfter) {

    final T textNode = super.createTextNode(data, parent, nodeAfter);
    schedulePaint(textNode);
    return textNode;
  }

  @Override
  public void insertData(T textNode, int offset, String arg) {
    super.insertData(textNode, offset, arg);
    if (offset == 0) {
      schedulePaint(textNode);
    }
  }

  @Override
  public void removeChild(E parent, N oldChild) {
    // Schedule repaint for this node if emptied (The annotation painter
    // may then remove the node, but not necessarily). We do this to clean
    // up empty paint annotations after the contents have been removed.
    E fullViewParent = fullDoc.getParentElement(oldChild);
    super.removeChild(parent, oldChild);

    if (fullDoc.getFirstChild(fullViewParent) == null) {
      schedulePaint(fullViewParent);
    }
  }

  /** Schedule a node for repainting with the annotation painter */
  abstract protected void schedulePaint(N node) ;
}
