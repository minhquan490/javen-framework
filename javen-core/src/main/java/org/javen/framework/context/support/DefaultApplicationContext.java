package org.javen.framework.context.support;

import org.javen.framework.context.AbstractMessageSourceApplicationContext;
import org.javen.framework.core.annotation.Component;
import org.javen.framework.core.annotation.Ignore;
import org.javen.framework.core.message.MessageSourceRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

@Component
@Ignore // Default bean does not allow scan it.
public class DefaultApplicationContext extends AbstractMessageSourceApplicationContext {

    private MessageSourceRepository messageSourceRepository;

    public DefaultApplicationContext(@NotNull Collection<String> basePackages) {
        super(basePackages);
    }

    @Override
    public void start() {
        ShutdownHook hook = new ShutdownHook(this::close);
        Runtime.getRuntime().addShutdownHook(hook);
        super.start();
    }

    @Nullable
    @Override
    protected String doGetMessageTemplate(int code, @NotNull Locale locale) {
        if (messageSourceRepository == null) {
            messageSourceRepository = (MessageSourceRepository) getBean(MessageSourceRepository.class.getName());
        }
        return messageSourceRepository.getMessage(code, locale);
    }

    @NotNull
    @Override
    protected String formatMessage(@NotNull String template, @NotNull Object[] params) {
        return MessageFormat.format(template, params);
    }

    private static class ShutdownHook extends Thread {
        ShutdownHook(Runnable task) {
            super(task);
        }
    }
}
