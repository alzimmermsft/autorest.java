// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.autorest.util;

import org.atteo.evo.inflector.English;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class for naming code.
 */
public class CodeNamer {
    private static final String[] BASIC_LATIN_CHARACTERS;

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
    }

    private static final Set<String> RESERVED_WORDS = new HashSet<>(
        Arrays.asList("abstract", "assert", "boolean", "Boolean", "break", "byte", "Byte", "case", "catch", "char",
            "Character", "class", "Class", "const", "continue", "default", "do", "double", "Double", "else", "enum",
            "extends", "false", "final", "finally", "float", "Float", "for", "goto", "if", "implements", "import",
            "int", "Integer", "long", "Long", "interface", "instanceof", "native", "new", "null", "package", "private",
            "protected", "public", "return", "short", "Short", "static", "strictfp", "super", "switch", "synchronized",
            "this", "throw", "throws", "transient", "true", "try", "void", "Void", "volatile", "while", "Date",
            "Datetime", "OffsetDateTime", "Duration", "Period", "Stream", "String", "Object", "header", "_"));

    private static NamerFactory factory = new DefaultNamerFactory();

    private static final Pattern MERGE_UNDERSCORES = Pattern.compile("_{2,}");
    private static final Pattern CHARACTERS_TO_REPLACE_WITH_UNDERSCORE = Pattern.compile("[\\\\/.+ -]+");
    private static final Pattern NEW_LINE = Pattern.compile("\r?\n");

    /**
     * Set the factory to use for creating CodeNamer instances.
     *
     * @param templateFactory the factory to use for creating CodeNamer instances.
     */
    public static void setFactory(NamerFactory templateFactory) {
        factory = templateFactory;
    }

    /**
     * Gets a ModelNamer instance.
     *
     * @return A ModelNamer instance.
     */
    public static ModelNamer getModelNamer() {
        return factory.getModelNamer();
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

    /**
     * Escapes an XML comment.
     *
     * @param comment the comment to escape.
     * @return the escaped comment.
     */
    public static String escapeXmlComment(String comment) {
        if (comment == null) {
            return null;
        }

        return linearReplace(comment, c -> {
            if (c == '&') {
                return "&amp;";
            } else if (c == '<') {
                return "&lt;";
            } else if (c == '>') {
                return "&gt;";
            } else {
                return null;
            }
        });
    }

    /**
     * Escapes a Java comment.
     *
     * @param comment the comment to escape.
     * @return the escaped comment.
     */
    public static String escapeComment(String comment) {
        if (comment == null) {
            return null;
        }

        return CodeNamer.linearReplace(comment, "*/", "*&#47;");
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
        return getValidName(name, c -> c == '_' || c == '-');
    }

    /**
     * Gets a valid name from a name.
     *
     * @param name the name to get a valid name from.
     * @param allowedCharacters the special characters that are allowed in the name.
     * @return the valid name.
     */
    public static String getValidName(String name, Predicate<Character> allowedCharacters) {
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
     * Gets the name of the enum member.
     *
     * @param name the name of the enum member.
     * @return the name of the enum member.
     */
    public static String getEnumMemberName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }

        // trim leading and trailing '_'
        if ((name.startsWith("_") || name.endsWith("_")) && !name.chars().allMatch(c -> c == '_')) {
            StringBuilder sb = new StringBuilder(name);
            while (sb.length() > 0 && sb.charAt(0) == '_') {
                sb.deleteCharAt(0);
            }
            while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '_') {
                sb.setLength(sb.length() - 1);
            }
            name = sb.toString();
        }

        String result = removeInvalidCharacters(CHARACTERS_TO_REPLACE_WITH_UNDERSCORE.matcher(name).replaceAll("_"));
        result = MERGE_UNDERSCORES.matcher(result).replaceAll("_");  // merge multiple underlines
        Function<Character, Boolean> isUpper = c -> c >= 'A' && c <= 'Z';
        Function<Character, Boolean> isLower = c -> c >= 'a' && c <= 'z';
        for (int i = 1; i < result.length() - 1; i++) {
            if (isUpper.apply(result.charAt(i))) {
                if (result.charAt(i - 1) != '_' && isLower.apply(result.charAt(i - 1))) {
                    result = result.substring(0, i) + "_" + result.substring(i);
                }
            }
        }

        if (result.startsWith("_") || result.endsWith("_")) {
            if (!result.chars().allMatch(c -> c == (int) '_')) {
                // some char is not '_', trim it
                int startIndex = 0;
                while (startIndex < result.length() && result.charAt(startIndex) == '_') {
                    startIndex++;
                }

                int endIndex = result.length();
                while (endIndex > startIndex && result.charAt(endIndex - 1) == '_') {
                    endIndex--;
                }

                result = result.substring(startIndex, endIndex);
            } else {
                // all char is '_', then transform some '_' to
                if (result.startsWith("_") && name.charAt(0) < 128 && BASIC_LATIN_CHARACTERS[name.charAt(0)] != null) {
                    result = BASIC_LATIN_CHARACTERS[name.charAt(0)] + result.substring(1);
                    if (result.endsWith("_") && name.charAt(name.length() - 1) < 128
                        && BASIC_LATIN_CHARACTERS[name.charAt(name.length() - 1)] != null) {
                        result = result.substring(0, result.length() - 1)
                            + BASIC_LATIN_CHARACTERS[name.charAt(name.length() - 1)];
                    }
                }
            }
        }

        return result.toUpperCase();
    }

    /**
     * Wraps the text to a given width for each line.
     *
     * @param text the text to wrap.
     * @param width the width to wrap the text to.
     * @return the wrapped text.
     */
    public static List<String> wordWrap(String text, int width) {
        Objects.requireNonNull(text);
        List<String> ret = new ArrayList<>();
        String[] lines = NEW_LINE.split(text, -1);
        for (String line : lines) {
            String processedLine = line.trim();

            // yield empty lines as they are (probably) intentional
            if (processedLine.length() == 0) {
                ret.add(processedLine);
            }

            // feast on the line until it's gone
            while (processedLine.length() > 0) {
                // determine potential wrapping points
                List<Integer> whitespacePositions = new ArrayList<>();
                for (int i = 0; i != processedLine.length(); i++) {
                    if (Character.isWhitespace(processedLine.charAt(i))) {
                        whitespacePositions.add(i);
                    }
                }
                whitespacePositions.add(processedLine.length());
                int preWidthWrapAt = -1;
                int postWidthWrapAt = -1;
                for (int i = 0; i != whitespacePositions.size() - 1; i++) {
                    if (whitespacePositions.get(i + 1) > width) {
                        preWidthWrapAt = whitespacePositions.get(i);
                        postWidthWrapAt = whitespacePositions.get(i + 1);
                        break;
                    }
                }
                int wrapAt = processedLine.length();
                if (preWidthWrapAt > 0) {
                    wrapAt = preWidthWrapAt;
                } else if (postWidthWrapAt > 0) {
                    wrapAt = postWidthWrapAt;
                }
                // wrap
                ret.add(processedLine.substring(0, wrapAt));
                processedLine = processedLine.substring(wrapAt).trim();
            }
        }
        return ret;
    }

    protected static String getEscapedReservedName(String name, String appendValue) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(appendValue);

        if (RESERVED_WORDS.contains(name)) {
            name += appendValue;
        }

        return name;
    }

    private static final Set<String> RESERVED_CLIENT_METHOD_PARAMETER_NAME = new HashSet<>(
        Arrays.asList("service",      // the ServiceInterface local variable
            "client"        // the ManagementClient local variable
        ));

    public static String getEscapedReservedClientMethodParameterName(String name) {
        if (RESERVED_CLIENT_METHOD_PARAMETER_NAME.contains(name)) {
            name += "Param";
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

    /**
     * Replaces all occurrences of a target string with a replacement string.
     * <p>
     * If no occurrences of the target string are found, the original string is returned.
     *
     * @param str the string to replace occurrences in.
     * @param target the target string to replace.
     * @param replacement the replacement string.
     * @return the string with all occurrences of the target string replaced with the replacement string.
     */
    public static String linearReplace(String str, String target, String replacement) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder sb = null;
        int prevStart = 0;
        int foundIndex;

        while ((foundIndex = str.indexOf(target, prevStart)) != -1) {
            if (sb == null) {
                int replacementDifference = replacement.length() - target.length();
                if (replacementDifference <= 0) {
                    sb = new StringBuilder(str.length());
                } else {
                    sb = new StringBuilder(str.length() + (replacementDifference * 8));
                }
            }

            sb.append(str, prevStart, foundIndex);
            sb.append(replacement);

            prevStart = foundIndex + target.length();
        }

        if (sb == null) {
            return str;
        }

        if (prevStart < str.length()) {
            sb.append(str, prevStart, str.length());
        }

        return sb.toString();
    }
}
