package com.naz.PlexDownloader.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class I18nUtil {

    private static Logger logger = LoggerFactory.getLogger(I18nUtil.class);

    /**
     * Return a localized message based on the locale and resource.
     *
     * @param resourceName - The resource name.
     * @param messageKey - The Resource Message Key.
     * @param args - Arguments to be formatted within the message.
     * @param locale - The User locale.
     * @return A formatted localed message.
     */
    public static String getLocalizedMessage(String resourceName, String messageKey, Object[] args, Locale locale) {
        ResourceBundle resourceBundle = loadResourceBundle(resourceName, locale);

        return formatMessage(resourceBundle, messageKey, args);
    }


    /**
     * Returns a localized message.
     *
     * @param resourceBundle  - The resource bundle.
     * @param messageKey - The message key in the bundle.
     * @param args - Arguments to be formatted within the message.
     * @return A Formatted localized message.
     */
    private static String formatMessage(ResourceBundle resourceBundle, String messageKey, Object[] args) {

        Locale locale = Locale.getDefault();

        if (null != resourceBundle) {
            locale = resourceBundle.getLocale();
        }

        String message = getMessageFromBundle(resourceBundle, messageKey);
        String formattedMessage = null;

        if (!NullUtil.isNullOrEmpty(message)) {
            if (args == null && (message.equals(messageKey))) {
                formattedMessage = message;
            } else {
                MessageFormat messageFormat = new MessageFormat(message, locale);
                formattedMessage = messageFormat.format(args);
            }
        }

        return formattedMessage;
    }

    /**
     * Retrieves the given message from the resource bundle based on the message key.
     *
     * @param bundle - resource bundle.
     * @param messageKey - The message key.
     * @return The message.
     */
    private static String getMessageFromBundle(ResourceBundle bundle, String messageKey) {

        String message = null;
        if (null != bundle && !NullUtil.isNullOrEmpty(messageKey)) {
            try {
                message = bundle.getString(messageKey);
            } catch (MissingResourceException ex) {
                logger.warn("Missing message key in bundle: " + bundle.getBaseBundleName());
                message = messageKey;
            }
        }

        return message;
    }


    /**
     * Loads the resource bundle based on locale.
     *
     * @param resourceName - The message/Resource name
     * @param locale - The locale
     * @return - The resource bundle.
     */
    public static ResourceBundle loadResourceBundle(String resourceName, Locale locale) {

        ResourceBundle bundle;

        try {
            bundle = PropertyResourceBundle.getBundle(resourceName, locale);
        } catch (MissingResourceException ex) {
            bundle = null;
        }

        return bundle;
    }

}
