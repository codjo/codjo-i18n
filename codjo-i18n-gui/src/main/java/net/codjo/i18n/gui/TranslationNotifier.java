package net.codjo.i18n.gui;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import org.apache.log4j.Logger;

public class TranslationNotifier {
    private static final Logger LOG = Logger.getLogger(TranslationNotifier.class);
    public static final String TRANSLATION_NOTIFIER_PROPERTY = "TranslationNotifier";

    private List<Internationalizable> internationalizableObjects = new ArrayList<Internationalizable>();
    private List<InternationalizableComponent> internationalizableComponents =
          new ArrayList<InternationalizableComponent>();

    private Language language;
    private TranslationManager translator;


    public TranslationNotifier(Language defaultLanguage, TranslationManager translator) {
        this.language = defaultLanguage;
        this.translator = translator;
    }


    public void setLanguage(Language language) {
        this.language = language;

        if (LOG.isDebugEnabled()) {
            LOG.debug("i18n components count = " + internationalizableComponents.size());
        }
        for (InternationalizableComponent internationalizableComponent : internationalizableComponents) {
            internationalizableComponent.updateTranslation(this.language, translator);
            Component realComponent = internationalizableComponent.getComponent();
            if (realComponent != null) {
                realComponent.repaint();
            }
        }

        for (Internationalizable internationalizable : internationalizableObjects) {
            internationalizable.updateTranslation(this.language, translator);
        }
    }


    public Language getLanguage() {
        return language;
    }


    public void addInternationalizableContainer(InternationalizableContainer container) {
        removeUselessReferences();
        container.addInternationalizableComponents(this);
    }


    public void addInternationalizableComponent(InternationalizableComponent internationalizable) {
        removeUselessReferences();
        try {
            internationalizable.updateTranslation(getLanguage(), translator);
            internationalizableComponents.add(internationalizable);
        }
        catch (Exception e) {
            LOG.debug("No translation for component " + internationalizable, e);
        }
    }


    /*
     * 2 step process to clean old (unused) references to i18n objects:
     * 1) clean Internationalizable (which may be AWT or not)
     * 2) clean I18nComponents (which have a weak ref to AWT)
     */
    private synchronized void removeUselessReferences() {
        final boolean isLoggable = LOG.isDebugEnabled();
        if (isLoggable) {
            LOG.debug("objects = " + internationalizableObjects.size()
                      + "    components = " + internationalizableComponents.size());
        }

        // 1) clean Internationalizable in 2 steps
        // 1.1) if I18n is a weak ref and is not referenced anymore, then remove it
        // 1.2) if I18n is AWT and in an internal frame and frame is not visible, put it in probabation
        //   because object may have been registered in notifier but not yet used in AWT hierarchy;
        //   probation means that I18n is put in a WeakInternationalizable wrapper to be checked in step 1.1
        int objectsRemoved = 0;
        Iterator<Internationalizable> itobj = internationalizableObjects.iterator();
        List<Internationalizable> weakInternationalizableObjects = new ArrayList<Internationalizable>();
        while (itobj.hasNext()) {
            Internationalizable internationalizable = itobj.next();
            if (WeakInternationalizable.class.isInstance(internationalizable)) {
                if (!((WeakInternationalizable)internationalizable).isReferenced()) {
                    itobj.remove();
                    objectsRemoved++;
                }
            }
            else if (Component.class.isInstance(internationalizable)) {
                final Container frame = SwingUtilities.getAncestorOfClass(JInternalFrame.class,
                                                                          (Component)internationalizable);
                if (null != frame && !frame.isVisible()) {
                    weakInternationalizableObjects.add(new WeakInternationalizable(internationalizable));
                    itobj.remove();
                    LOG.debug("object put in probation");
                }
            }
        }
        internationalizableObjects.addAll(weakInternationalizableObjects);

        // 2) clean I18nComponents: remove if the weak ref is not referenced anymore
        int componentsRemoved = 0;
        Iterator<InternationalizableComponent> it = internationalizableComponents.iterator();
        while (it.hasNext()) {
            InternationalizableComponent component = it.next();
            if (null == component.getComponent()) {
                it.remove();
                componentsRemoved++;
            }
        }

        if (isLoggable && 0 < objectsRemoved + componentsRemoved) {
            LOG.debug(objectsRemoved + " objects removed    " + componentsRemoved + " components removed");
        }
    }


    public void addInternationalizable(Internationalizable internationalizable) {
        removeUselessReferences();
        internationalizableObjects.add(internationalizable);
    }


    public void addInternationalizableComponent(JLabel label, String key) {
        addInternationalizableComponent(new InternationalizableJLabel(key, label));
    }


    public void addInternationalizableComponent(JPanel panel, String key) {
        addInternationalizableComponent(new InternationalizableJPanel(key, panel));
    }


    public void addInternationalizableComponent(JTabbedPane tabbedPane, String componentKey, String[] keys) {
        addInternationalizableComponent(new InternationalizableJTabbedPane(componentKey, keys, tabbedPane));
    }


    public void addInternationalizableComponent(JInternalFrame internalFrame, String key) {
        addInternationalizableComponent(new InternationalizableJInternalFrame(key, internalFrame));
    }


    public void addInternationalizableComponent(Dialog dialog, String key) {
        addInternationalizableComponent(new InternationalizableDialog(key, dialog));
    }


    public void addInternationalizableComponent(AbstractButton abstractButton, String textKey,
                                                String tooltipKey) {
        addInternationalizableComponent(new InternationalizableAbstractButton(textKey,
                                                                              tooltipKey,
                                                                              abstractButton));
    }


    public void removeInternationalizable(Internationalizable internationalizable) {
        internationalizableObjects.remove(internationalizable);
    }


    /**
     * For TESTS only
     */
    List<InternationalizableComponent> getInternationalizableComponents() {
        return internationalizableComponents;
    }
}
