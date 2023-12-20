// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.preprocessor.namer;

import org.atteo.evo.inflector.English;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class for naming code.
 */
public class CodeNamer {
    private static final String[] BASIC_LATIN_CHARACTERS;

    private static final Set<String> RESERVED_WORDS = new HashSet<>(
        Arrays.asList("abstract", "assert", "boolean", "Boolean", "break", "byte", "Byte", "case", "catch", "char",
            "Character", "class", "Class", "const", "continue", "default", "do", "double", "Double", "else", "enum",
            "extends", "false", "final", "finally", "float", "Float", "for", "goto", "if", "implements", "import",
            "int", "Integer", "long", "Long", "interface", "instanceof", "native", "new", "null", "package", "private",
            "protected", "public", "return", "short", "Short", "static", "strictfp", "super", "switch", "synchronized",
            "this", "throw", "throws", "transient", "true", "try", "void", "Void", "volatile", "while", "Date",
            "Datetime", "OffsetDateTime", "Duration", "Period", "Stream", "String", "Object", "header", "_"));

    private static final Set<String> RESERVED_WORDS_CLASSES = new HashSet<>(RESERVED_WORDS);

    static {
        BASIC_LATIN_CHARACTERS = new String[128];
        BASIC_LATIN_CHARACTERS[32] = "Space";
        BASIC_LATIN_CHARACTERS[33] = "ExclamationMark";
        BASIC_LATIN_CHARACTERS[34] = "QuotationMark";
        BASIC_LATIN_CHARACTERS[35] = "NumberSign";
        BASIC_LATIN_CHARACTERS[36] = "DollarSign";
        BASIC_LATIN_CHARACTERS[37] = "PercentSign";
        BASIC_LATIN_CHARACTERS[38] = "Ampersand";
        BASIC_LATIN_CHARACTERS[39] = "Apostrophe";
        BASIC_LATIN_CHARACTERS[40] = "LeftParenthesis";
        BASIC_LATIN_CHARACTERS[41] = "RightParenthesis";
        BASIC_LATIN_CHARACTERS[42] = "Asterisk";
        BASIC_LATIN_CHARACTERS[43] = "PlusSign";
        BASIC_LATIN_CHARACTERS[44] = "Comma";
        BASIC_LATIN_CHARACTERS[45] = "HyphenMinus";
        BASIC_LATIN_CHARACTERS[46] = "FullStop";
        BASIC_LATIN_CHARACTERS[47] = "Slash";
        BASIC_LATIN_CHARACTERS[48] = "Zero";
        BASIC_LATIN_CHARACTERS[49] = "One";
        BASIC_LATIN_CHARACTERS[50] = "Two";
        BASIC_LATIN_CHARACTERS[51] = "Three";
        BASIC_LATIN_CHARACTERS[52] = "Four";
        BASIC_LATIN_CHARACTERS[53] = "Five";
        BASIC_LATIN_CHARACTERS[54] = "Six";
        BASIC_LATIN_CHARACTERS[55] = "Seven";
        BASIC_LATIN_CHARACTERS[56] = "Eight";
        BASIC_LATIN_CHARACTERS[57] = "Nine";
        BASIC_LATIN_CHARACTERS[58] = "Colon";
        BASIC_LATIN_CHARACTERS[59] = "Semicolon";
        BASIC_LATIN_CHARACTERS[60] = "LessThanSign";
        BASIC_LATIN_CHARACTERS[61] = "EqualSign";
        BASIC_LATIN_CHARACTERS[62] = "GreaterThanSign";
        BASIC_LATIN_CHARACTERS[63] = "QuestionMark";
        BASIC_LATIN_CHARACTERS[64] = "AtSign";
        BASIC_LATIN_CHARACTERS[91] = "LeftSquareBracket";
        BASIC_LATIN_CHARACTERS[92] = "Backslash";
        BASIC_LATIN_CHARACTERS[93] = "RightSquareBracket";
        BASIC_LATIN_CHARACTERS[94] = "CircumflexAccent";
        BASIC_LATIN_CHARACTERS[96] = "GraveAccent";
        BASIC_LATIN_CHARACTERS[123] = "LeftCurlyBracket";
        BASIC_LATIN_CHARACTERS[124] = "VerticalBar";
        BASIC_LATIN_CHARACTERS[125] = "RightCurlyBracket";
        BASIC_LATIN_CHARACTERS[126] = "Tilde";

        RESERVED_WORDS_CLASSES.addAll(Arrays.asList(
            // following are commonly used classes/annotations in service client, from azure-core
            "Host", "ServiceInterface", "ServiceMethod", "ServiceClient", "ReturnType", "Get", "Put", "Post", "Patch",
            "Delete", "Headers", "ExpectedResponses", "UnexpectedResponseExceptionType",
            "UnexpectedResponseExceptionTypes", "HostParam", "PathParam", "QueryParam", "HeaderParam", "FormParam",
            "BodyParam", "Fluent", "Immutable", "JsonFlatten", "Override"));
    }

    private CodeNamer() {
    }

    /**
     * Converts a name to camel case.
     *
     * @param name the name to convert.
     * @return the camel case version of the name.
     */
    public static String toCamelCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }

        if (name.charAt(0) == '_') {
            // Remove leading underscores.
            return toCamelCase(name.substring(1));
        }

        List<String> parts = camelCaseSplit(name);
        if (parts.isEmpty()) {
            return "";
        }

        parts.set(0, formatCase(parts.get(0), true));
        for (int i = 1; i < parts.size(); i++) {
            parts.set(i, formatCase(parts.get(i), false));
        }

        return String.join("", parts);
    }

    /**
     * Converts a name to pascal case.
     *
     * @param name the name to convert.
     * @return the pascal case version of the name.
     */
    public static String toPascalCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }

        if (name.charAt(0) == '_') {
            // Preserve leading underscores and treat them like
            // uppercase characters by calling 'CamelCase()' on the rest.
            return '_' + toCamelCase(name.substring(1));
        }

        return camelCaseSplit(name).stream()
            .filter(s -> s != null && !s.isEmpty())
            .map(s -> formatCase(s, false))
            .collect(Collectors.joining());
    }

    private static List<String> camelCaseSplit(String str) {
        List<String> result = new ArrayList<>();

        int last = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_' || c == '-' || c == ' ') {
                if (i > last) {
                    result.add(str.substring(last, i));
                }
                last = i + 1;
            }
        }

        if (last < str.length()) {
            result.add(str.substring(last));
        }

        return result;
    }

    private static String formatCase(String name, boolean toLower) {
        if (name != null && !name.isEmpty()) {
            if ((name.length() < 2) || ((name.length() == 2) && Character.isUpperCase(name.charAt(0))
                && Character.isUpperCase(name.charAt(1)))) {
                name = toLower ? name.toLowerCase() : name.toUpperCase();
            } else {
                name = (toLower ? Character.toLowerCase(name.charAt(0)) : Character.toUpperCase(name.charAt(0)))
                    + name.substring(1);
            }
        }
        return name;
    }

    /**
     * Removes invalid characters from a name.
     *
     * @param name the name to remove invalid characters from.
     * @return the name with invalid characters removed.
     */
    public static String removeInvalidCharacters(String name) {
        Predicate<Character> allowedCharacters = c -> c == '_' || c == '-';
        String correctName = removeInvalidCharacters(name, allowedCharacters);

        // here we have only letters and digits or an empty String
        if (correctName == null || correctName.isEmpty()
            || (correctName.charAt(0) < 128 && BASIC_LATIN_CHARACTERS[correctName.charAt(0)] != null)) {
            correctName = removeInvalidCharacters(linearReplace(name, c -> c < 128 ? BASIC_LATIN_CHARACTERS[c] : null),
                allowedCharacters);
        }

        // if it is still empty String, throw
        if (correctName == null || correctName.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                "Property name %s cannot be used as an Identifier, as it contains only invalid characters.", name));
        }

        return correctName;
    }

    /**
     * Gets the name of the client.
     *
     * @param name the name of the client.
     * @return the name of the client.
     */
    public static String getClientName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        return getEscapedReservedNameAndClasses(toPascalCase(removeInvalidCharacters(name)), "Client");
    }

    /**
     * Gets the name of the type.
     *
     * @param name the name of the type.
     * @return the name of the type.
     */
    public static String getTypeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        return getEscapedReservedNameAndClasses(toPascalCase(removeInvalidCharacters(name)), "Model");
    }

    /**
     * Gets the name of the parameter.
     *
     * @param name the name of the parameter.
     * @return the name of the parameter.
     */
    public static String getParameterName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        return getEscapedReservedName(toCamelCase(removeInvalidCharacters(name)), "Parameter");
    }

    /**
     * Gets the name of the property.
     *
     * @param name the name of the property.
     * @return the name of the property.
     */
    public static String getPropertyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        return getEscapedReservedName(toCamelCase(removeInvalidCharacters(name)), "Property");
    }

    /**
     * Gets the name of the method group.
     *
     * @param name the name of the method group.
     * @return the name of the method group.
     */
    public static String getMethodGroupName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        name = toPascalCase(name);
        return getEscapedReservedName(name, "Operation");
    }

    /**
     * Gets the plural of the name.
     *
     * @param name the name to get the plural of.
     * @return the plural of the name.
     */
    public static String getPlural(String name) {
        if (name != null && !name.isEmpty() && !name.endsWith("s") && !name.endsWith("S")) {
            name = English.plural(name);
        }
        return name;
    }

    /**
     * Gets the name of the method.
     *
     * @param name the name of the method.
     * @return the name of the method.
     */
    public static String getMethodName(String name) {
        name = toCamelCase(name);
        return getEscapedReservedName(name, "Method");
    }

    protected static String getEscapedReservedName(String name, String appendValue) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(appendValue);

        if (RESERVED_WORDS.contains(name)) {
            name += appendValue;
        }

        return name;
    }

    protected static String getEscapedReservedNameAndClasses(String name, String appendValue) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(appendValue);

        if (RESERVED_WORDS_CLASSES.contains(name)) {
            name += appendValue;
        }

        return name;
    }

    private static String removeInvalidCharacters(String name, Predicate<Character> allowedCharacters) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        return linearReplace(name, c -> Character.isLetterOrDigit(c) || allowedCharacters.test(c) ? null : "_");
    }

    private static String linearReplace(String str, Function<Character, String> replacer) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder sb = null;
        int prevStart = 0;

        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            String replacement = replacer.apply(str.charAt(i));
            if (replacement == null) {
                continue;
            }

            if (sb == null) {
                sb = new StringBuilder(strLen * 2);
            }

            if (prevStart != i) {
                sb.append(str, prevStart, i);
            }

            sb.append(replacement);

            prevStart = i + 1;
        }

        if (sb == null) {
            return str;
        }

        if (prevStart < strLen) {
            sb.append(str, prevStart, strLen);
        }

        return sb.toString();
    }
}
