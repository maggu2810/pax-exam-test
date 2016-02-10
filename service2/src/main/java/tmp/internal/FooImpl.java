package tmp.internal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import tmp.Bar;
import tmp.Foo;

@Component
public class FooImpl implements Foo {

  @Reference
  private Bar bar;

  public int doWhatever() {
    return 2 * bar.doSomething();
  }
}
