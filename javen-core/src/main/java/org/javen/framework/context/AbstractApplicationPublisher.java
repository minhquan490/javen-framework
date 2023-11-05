package org.javen.framework.context;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import org.javen.framework.context.beans.AbstractBeanFactory;
import org.javen.framework.context.event.ApplicationEvent;
import org.javen.framework.context.event.ApplicationEventMultiCaster;
import org.javen.framework.context.event.EventListener;
import org.javen.framework.context.support.DefaultApplicationMultiCaster;
import org.javen.framework.core.Component;
import org.javen.framework.core.concurrent.scheduling.ThreadPoolManager;
import org.javen.framework.core.type.filter.TypeFilter;
import org.javen.framework.core.type.filter.runtime.AnnotatedFilter;
import org.javen.framework.core.type.filter.runtime.AssignableFilter;
import org.javen.framework.core.type.scanner.PackageScanner;
import org.javen.framework.core.type.scanner.ScanResult;
import org.javen.framework.core.type.scanner.Scanner;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public abstract class AbstractApplicationPublisher extends AbstractApplicationContext {

    private final ApplicationEventMultiCaster caster;
    private final Collection<String> basePackages;
    private ThreadPoolManager threadPoolManager;

    protected AbstractApplicationPublisher(@NotNull ActiveProfileProvider activeProfileProvider, Collection<String> basePackages) {
        super(activeProfileProvider);
        this.caster = createApplicationMultiCaster();
        this.basePackages = basePackages;
    }

    @Override
    public void fireEvent(@NotNull ApplicationEvent event) {
        if (threadPoolManager == null) {
            try {
                threadPoolManager = (ThreadPoolManager) getBean(AbstractApplicationContext.THREAD_POOL_MANAGER_BEAN_NAME);
            } catch (Exception e) {
                try {
                    threadPoolManager = (ThreadPoolManager) getBean(AbstractApplicationContext.Companion.getThreadPoolManagerClassName());
                } catch (Exception e1) {
                    // Cannot find thread pool manger in context, skip it
                }
            }
        }
        if (threadPoolManager != null) {
            threadPoolManager.execute(() -> caster.invokeHandlers(event));
        } else {
            caster.invokeHandlers(event);
        }
    }

    public ApplicationEventMultiCaster getCaster() {
        return caster;
    }

    private ApplicationEventMultiCaster createApplicationMultiCaster() {
        EventHandlerScanner scanner = new EventHandlerScanner(ClassLoader.getSystemClassLoader());
        return new DefaultApplicationMultiCaster(scanner.scan(basePackages), (AbstractBeanFactory) getBeanFactory());
    }

    private static class EventHandlerScanner implements Scanner<Collection<KClass<?>>> {
        private final PackageScanner delegate;

        public EventHandlerScanner(@NotNull ClassLoader classLoader) {
            this.delegate = new PackageScanner(classLoader);
            addFilter(new AnnotatedFilter(JvmClassMappingKt.getKotlinClass(Component.class)));
            addFilter(new AssignableFilter(JvmClassMappingKt.getKotlinClass(EventListener.class)));
        }

        @NotNull
        @Override
        public ScanResult<Collection<KClass<?>>> scan(@NotNull Collection<String> paths) {
            return delegate.scan(paths);
        }

        @NotNull
        @Override
        public ClassLoader getClassLoader() {
            return delegate.getClassLoader();
        }

        @Override
        public <U> void addFilter(@NotNull TypeFilter<U> filter) {
            delegate.addFilter(filter);
        }
    }
}
