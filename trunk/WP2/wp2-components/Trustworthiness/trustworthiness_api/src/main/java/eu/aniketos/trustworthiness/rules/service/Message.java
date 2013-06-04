package eu.aniketos.trustworthiness.rules.service;

import java.util.List;

/**
 * represents one error/warning validation message
 *
 * @author Hisain Elshaafi (TSSG)
 *
 */
public interface Message {
  public enum Type {
    ERROR, WARNING
  }

  /**
   * @return type of this message
   */
  Type getType();

  /**
   * @return key of this message 
   */
  String getMessageKey();
  
  /**
   * objects in the context must be ordered from the least 
   * specific to most specific
   * @return list of objects in this message's context
   */
  List<Object> getContextOrdered();
}

