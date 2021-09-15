@file:Suppress("unused", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")
package util

import java.util.*
/**═════════════════════════════════════════════════════════════════════════════════════════════════
                            Android (Java & Kotlin) Naming Convention:
════════════════════════════════════════════════════════════════════════════════════════════════════
    Naming convention for object names in Android project (Java & Kotlin)
    example: "xxx case", "xxxcase" word where xxx is type.

    camelCase            : function, property and local variable names
    dot.case             : package path, gradle module group names
    kebab-case           : gradle plugin names
    PascalCase           : class & interface names
    snake_case           : android resources xml file names
    SCREAMING_SNAKE_CASE : constant names
    _underscorePrefix    : baking property names

    word "noninitial" is a synonym of "not first" here.
══════════════════════════════════════════════════════════════════════════════════════════════════*/


/**
 * ©Paweł Zygmunciak 2021
 * ## [NamingStyleConversion] class ##
 *
 * @see <p align="right"><a href="https://regex101.com/">
 * Regex 101</a></p>
 * @see <p align="right"><a href="https://codeburst.io/java-and-regex-101-5b1e5ed315dd">
 * Java and Regex 101</a></p>
 * <p align="right"><i>22nd of June, 2017</i></p><br>
 */
open class NamingStyleConversion {
    private companion object Regex {
        // Regex special characters: . + * ? ^ $ ( ) [ ] { } | \

        // emptiness...
        const val EMPTY = ""

        //separators:
        const val DOT = "\\."
        const val HYPHEN = "-"
        const val UNDERSCORE = "_"

        //characters:
        const val LETTER = "[a-zA-Z]"
        const val LOWERCASE = "[a-z]"
        const val LOWERCASE_OR_DIGIT = "[a-z0-9]"
        const val CAPITAL = "[A-Z]"
        const val ALPHANUMERIC = "[a-zA-Z0-9]"

        
        //Regular Expression Patterns:

        // ^
        const val START_OF_STRING = "^"

        // (?<=[a-z])(?=[A-Z])
        const val START_OF_NONINITIAL_CAPITALIZED_WORD = "(?<=$LOWERCASE)(?=$CAPITAL)"

        // [A-Z]+[^A-Z]*$
        const val CAPITALIZED_SUFFIX = "$CAPITAL+$LOWERCASE_OR_DIGIT*\$" //pascal, camel

        // [a-zA-Z0-9]*$
        const val SUFFIX_AFTER_SEPARATOR = "$ALPHANUMERIC*\$" // dot, kebab, snake

        // [\.]+[a-zA-Z0-9]*$
        const val DOT_WITH_SUFFIX = "[$DOT]+$SUFFIX_AFTER_SEPARATOR" // dot

        // [-]+[a-zA-Z0-9]*$
        const val HYPHEN_WITH_SUFFIX = "[$HYPHEN]+$SUFFIX_AFTER_SEPARATOR" // kebab

        // [_]+[a-zA-Z0-9]*$
        const val UNDERSCORE_WITH_SUFFIX = "[$UNDERSCORE]+$SUFFIX_AFTER_SEPARATOR" // snake

        // [\.][a-zA-Z]
        const val DOT_WITH_LETTER = "[$DOT]$LETTER" // dot

        // [-][a-zA-Z]
        const val HYPHEN_WITH_LETTER = "[$HYPHEN]$LETTER" // kebab

        // [_][a-zA-Z]
        const val UNDERSCORE_WITH_LETTER = "[$UNDERSCORE]$LETTER" // snake


        // [a-z0-9]+
        const val CAMMELCASE_PREFIX = "$LOWERCASE_OR_DIGIT+"

        // ^[a-zA-Z0-9][a-z0-9]*
        const val PASCALCASE_PREFIX = "^$ALPHANUMERIC$LOWERCASE_OR_DIGIT*"

        // ^[a-zA-Z0-9]
        const val WORD_BEFORE_SEPARATOR = "^$ALPHANUMERIC"

        // [\.]*
        const val FIRST_DOT = "[$DOT]*"

        // [-]*
        const val FIRST_HYPHEN = "[$HYPHEN]*"

        // [_]*
        const val FIRST_UNDERSCORE = "[$UNDERSCORE]*"

        // ^[a-zA-Z0-9]+[\.]*
        const val PREFIX_WITH_DOT = "$WORD_BEFORE_SEPARATOR+$FIRST_DOT"

        // ^[a-zA-Z0-9]+[-]*
        const val PREFIX_WITH_HYPHEN = "$WORD_BEFORE_SEPARATOR+$FIRST_HYPHEN"

        // ^[a-zA-Z0-9]+[_]*
        const val PREFIX_WITH_UNDERSCORE = "$WORD_BEFORE_SEPARATOR+$FIRST_UNDERSCORE"

        // ^[_](?=[^_])
        const val LEADING_UNDERSCORE = "^[$UNDERSCORE](?=[^$UNDERSCORE])"

        // replace, remove and find methods:
        private fun String.regexReplace(oldValue: String, newValue: String): String =
            this.toRegex().replace(oldValue, newValue)

        private fun String.regexReplace(
            input: CharSequence,
            transform: (MatchResult) -> CharSequence
        ): String = this.toRegex().replace(input, transform)

        private fun String.regexRemove(string: String): String =
            this.toRegex().replace(string, EMPTY)

        private fun String.regexFind(string: String): String =
            this.toRegex().find(string).toString()

        private fun String.remove(input: String): String = this.replace(input, EMPTY)

        // "separator case" MEANS HERE "dotcase", "kebabcase" or "snakecase".

        // (camel, pascal) -> (dot, kebab, snake)
        // inserts separator before capital letter and decapitalize all
        private fun String.camelToSeparatorCase(separator: String): String =
            START_OF_NONINITIAL_CAPITALIZED_WORD
                .regexReplace(this, separator)
                .toLowerCase(Locale.ROOT)


        // (dot, kebab, snake) -> (camel, pascal)
        // removes separator before letter and capitalize it
        private fun String.separatorToCamelcase(separator: String): String = "$separator$LETTER"
            .regexReplace(this) {
                it.value.remove(separator).toUpperCase(Locale.ROOT)
            }

        private fun String.separatorCasePrefix(): String = WORD_BEFORE_SEPARATOR.regexFind(this)
        private fun String.separatorCaseSuffix(): String = SUFFIX_AFTER_SEPARATOR.regexFind(this)
    }

    // NAMING STYLE TRANSITIONS

    // e.g: twoWords -> two.words, two-words, TwoWords, two_words, TWO_WORDS
    fun String.camelToDotcase(): String = this.camelToSeparatorCase(DOT)
    fun String.camelToKebabcase(): String = this.camelToSeparatorCase(HYPHEN)
    fun String.camelToPascalcase(): String = this.capitalize(Locale.ROOT)
    fun String.camelToSnakecase(): String = this.camelToSeparatorCase(UNDERSCORE)
    fun String.camelToSnakecaseScreaming(): String = this.camelToSnakecase().toUpperCase(Locale.ROOT)

    // e.g: two.words -> twoWords, two-words, TwoWords, two_words, TWO_WORDS
    fun String.dotToCamelcase(): String = this.separatorToCamelcase(DOT)
    fun String.dotToKebabcase(): String = this.replace(DOT, HYPHEN)
    fun String.dotToPascalcase(): String = this.dotToCamelcase().capitalize(Locale.ROOT)
    fun String.dotToSnakecase(): String = this.replace(DOT, UNDERSCORE)
    fun String.dotToSnakecaseScreaming(): String = this.dotToSnakecase().toUpperCase(Locale.ROOT)

    // e.g: two-words -> twoWords, two.words, TwoWords, two_words, TWO_WORDS
    fun String.kebabToCamelcase(): String = this.separatorToCamelcase(HYPHEN)
    fun String.kebabToDotcase(): String = this.replace(HYPHEN, DOT)
    fun String.kebabToPascalcase(): String = this.kebabToCamelcase().capitalize(Locale.ROOT)
    fun String.kebabToSnakecase(): String = this.replace(HYPHEN, UNDERSCORE)
    fun String.kebabToSnakecaseScreaming(): String = this.kebabToSnakecase().toUpperCase(Locale.ROOT)

    // e.g: TwoWords -> twoWords, two.words, two-words, two_words, TWO_WORDS
    fun String.pascalToCamelcase(): String = this.decapitalize(Locale.ROOT)
    fun String.pascalToDotcase(): String = this.camelToSeparatorCase(DOT)
    fun String.pascalToKebabcase(): String = this.camelToSeparatorCase(HYPHEN)
    fun String.pascalToSnakecase(): String = this.camelToSeparatorCase(UNDERSCORE)
    fun String.pascalToSnakecaseScreaming(): String = this.camelToSnakecase().toUpperCase(Locale.ROOT)

    // e.g: two_words -> twoWords, two.words, two-words, TwoWords, TWO_WORDS
    fun String.snakeToCamelcase(): String = this.separatorToCamelcase(UNDERSCORE)
    fun String.snakeToDotcase(): String = this.replace(UNDERSCORE, DOT)
    fun String.snakeToKebabcase(): String = this.replace(UNDERSCORE, HYPHEN)
    fun String.snakeToPascalcase(): String = this.snakeToCamelcase().capitalize(Locale.ROOT)
    fun String.snakeToSnakecaseScreaming(): String = this.toUpperCase(Locale.ROOT)

    // e.g: TWO_WORDS -> twoWords, two.words, two-words, TwoWords, two_words,
    fun String.snakeScreamingToCamelcase(): String =
        this.toLowerCase(Locale.ROOT).separatorToCamelcase(UNDERSCORE)

    fun String.screamingSnakeToDotcase(): String = this.snakeToDotcase().toLowerCase(Locale.ROOT)
    fun String.screamingSnakeToKebabcase(): String = this.snakeToKebabcase().toLowerCase(Locale.ROOT)
    fun String.screamingSnakeToPascalcase(): String = this.snakeToCamelcase().capitalize(Locale.ROOT)
    fun String.screamingSnakeToSnakecase(): String = this.toLowerCase(Locale.ROOT)

    // PREFIX REMOVAL
    fun String.removeCamelcasePrefix(): String = CAMMELCASE_PREFIX.regexRemove(this)
    fun String.removeDotcasePrefix(): String = PREFIX_WITH_DOT.regexRemove(this)
    fun String.removeKebabcasePrefix(): String = PREFIX_WITH_HYPHEN.regexRemove(this)
    fun String.removePascalcasePrefix(): String = PASCALCASE_PREFIX.regexRemove(this)
    fun String.removeSnakecasePrefix(): String = PREFIX_WITH_UNDERSCORE.regexRemove(this)
    fun String.removeSnakecaseScreamingPrefix(): String = this.removeSnakecasePrefix()

    // SUFFIX REMOVAL
    fun String.removeCamelcaseSuffix(): String = CAPITALIZED_SUFFIX.regexRemove(this)
    fun String.removeDotcaseSuffix(): String = DOT_WITH_SUFFIX.regexRemove(this)
    fun String.removeKebabcaseSuffix(): String = HYPHEN_WITH_SUFFIX.regexRemove(this)
    fun String.removePascalcaseSuffix(): String = this.removeCamelcaseSuffix()
    fun String.removeSnakecaseSuffix(): String = UNDERSCORE_WITH_SUFFIX.regexRemove(this)
    fun String.removeSnakecaseScreamingSuffix(): String = this.removeSnakecaseSuffix()

    // LEADING UNDERSCORE INSERT & REMOVAL
    fun String.insertLeadingUnderscore(): String = START_OF_STRING.regexReplace(this, UNDERSCORE)
    fun String.removeLeadingUnderscore(): String = LEADING_UNDERSCORE.regexRemove(this)

    // FIND PREFIX
    fun String.camelcasePrefix(): String = CAMMELCASE_PREFIX.regexFind(this)
    fun String.dotcasePrefix(): String = this.separatorCasePrefix()
    fun String.kebabcasePrefix(): String = this.separatorCasePrefix()
    fun String.pascalcasePrefix(): String = PASCALCASE_PREFIX.regexFind(this)
    fun String.snakecasePrefix(): String = this.separatorCasePrefix()
    fun String.SnakecaseScreamingPrefix(): String = this.separatorCasePrefix()

    // FIND SUFFIX
    fun String.camelcaseSuffix(): String = CAPITALIZED_SUFFIX.regexFind(this)
    fun String.dotcaseSuffix(): String = this.separatorCaseSuffix()
    fun String.kebabcaseSuffix(): String = this.separatorCaseSuffix()
    fun String.pascalcaseSuffix(): String = this.camelcaseSuffix()
    fun String.snakecaseSuffix(): String = this.separatorCaseSuffix()
    fun String.SnakecaseScreamingSuffix(): String = this.snakecaseSuffix()

}
/*
typealias RegexTransformation = (MatchResult) -> CharSequence
typealias RegexPattern = String
*/
