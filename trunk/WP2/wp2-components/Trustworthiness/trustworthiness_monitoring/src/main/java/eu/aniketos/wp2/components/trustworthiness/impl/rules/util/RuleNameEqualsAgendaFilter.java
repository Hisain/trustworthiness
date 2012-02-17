package eu.aniketos.wp2.components.trustworthiness.impl.rules.util;

import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.AgendaFilter;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class RuleNameEqualsAgendaFilter implements
    AgendaFilter {
  private final String  name;

  private final boolean accept;

  public RuleNameEqualsAgendaFilter(final String name) {
      this( name,
            true );
  }

  public RuleNameEqualsAgendaFilter(final String name,
                                    final boolean accept) {
      this.name = name;
      this.accept = accept;
  }

  public boolean accept(Activation activation) {
      if ( activation.getRule().getName().equals( this.name ) ) {
          return this.accept;
      } else {
          return !this.accept;
      }
  }

}
