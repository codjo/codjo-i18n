package net.codjo.i18n.gui;
import java.awt.Component;
/**
 *
 */
public abstract class AbstractInternationalizableComponent<G extends Component>
      implements InternationalizableComponent {
    private String key;


    protected AbstractInternationalizableComponent(String key) {
        this.key = key;
    }


    protected String getKey() {
        return key;
    }
}
