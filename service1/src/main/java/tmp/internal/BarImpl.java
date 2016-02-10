package tmp.internal;

import org.osgi.service.component.annotations.Component;
import tmp.Bar;

@Component
public class BarImpl implements Bar {

  public int doSomething() {
    return 2;
  }

}
