package net.codjo.i18n.gui;
import java.awt.Component;

public interface InternationalizableComponent<G extends Component> extends Internationalizable {
    G getComponent();
}
