package eu.tn.chaoscompiler.errors;

import org.codehaus.plexus.util.StringUtils;

public enum Errors {
    /*
    * Couleurs pour l'affichage
    * ANSI_RESET = "\u001B[0m";
    * ANSI_RED_BOLD = "\033[1;31m";
    * ANSI_BLUE = "\033[0;34m";
    * ANSI_purple = "\033[0;35m";
    * */

    DEFAULT_ERROR("Erreur inconnue"),
    SYNTAX_ERROR("%s"),
    SEMANTIC_ERROR("%s"),
    UNRECOGNISED_ERROR("%s"),

    UNDECLARED_TYPE("Le type \033[0;34m %s \u001B[0m n'est pas déclaré"),
    ALREADY_DECLARED_TYPE("Le type \033[0;34m %s \u001B[0m est déjà déclaré"),
    UNAUTHORIZED_BREAK("Interdit d'utiliser break à l'extérieur des boucles For ou While"),
    UNDECLARED_VARIABLE("La variable \033[0;35m %s \u001B[0m n'est pas déclarée"),
    UNDECLARED_FUNCTION("La fonction \033[0;35m %s \u001B[0m n'est pas définie"),
    ALREADY_DECLARED("La variable \033[0;35m %s \u001B[0m est déjà déclarée"),

    PARAMETER_TYPE_NO_DECLARED("Le type \033[0;34m %s \u001B[0m du paramètre %s n'est pas défini"),
    ID_ALREADY_USED("L'identifiant \033[0;35m %s \u001B[0m est déjà localement utilisé"),
    INCOMPATIBLE_FUNCTION_TYPE("Le type de retour \033[0;34m %s \u001B[0m de la fonction \033[0;35m %s \u001B[0m est incompatible avec le type \033[0;34m %s \u001B[0m de la valeur retournée"),
    NO_VALUE_ERROR("\033[0;35m %s \u001B[0m n'a pas pu être initialisée (n'a pas de valeur)"),
    BAD_AFFECTATION_TYPE("Une valeur de type \033[0;34m %s \u001B[0m ne peut pas être affectée à une variable de type \033[0;34m %s \u001B[0m"),
    CANT_AFFECT_TO_FOR_INDEX("Impossible d'affecter une valeur à l'indice d'une boucle for"),
    LOOP_TYPE("%s"),//la coloration de l'affichage a été ajouté dans ControlesSemantiques.java dans la méthode visit(For forExpr)
    LOOP_COUNTER_AFFECT("l'indice %s de la boucle For ne doit pas être assigné à l'intérieur de la boucle"),

    INT_EXPECTED("Une valeur de type\033[0;34m int \u001B[0m est attendue (type donné : \033[0;34m %s \u001B[0m)"),

    INCOMPATIBLES_THEN_ELSE("Les types \033[0;34m %s \u001B[0m et \033[0;34m %s \u001B[0m renvoyés par les expressions then et else ne sont pas compatibles"),
    THEN_WITH_TYPE("L'expression then est de type \033[0;34m %s\u001B[0m, or en l'absence de else, le type de l'expression if doit être \033[0;34m void \u001B[0m"),
    NO_VOID_WHILE("La boucle While ne peut pas retourner de valeur, obtenu \033[0;34m %s \u001B[0m"),
    INVALID_LVALUE("Le membre de gauche ne correspond pas à une variable, impossible d'y affecter une valeur"),

    BAD_AFFECT_TYPE("Une valeur de type \033[0;34m %s \u001B[0m ne peut pas être affectée à une variable de type \033[0;34m %s \u001B[0m"),
    INCOMPARABLE_TYPES("Les types \033[0;34m %s \u001B[0m et \033[0;34m %s \u001B[0m ne sont pas comparables"),
    BAD_NEGATION_TYPE("Impossible d'appliquer une négation sur une valeur de type \033[0;34m %s\u001B[0m, attendu une valeur  de type \033[0;34m int \u001B[0m"),
    BAD_OPERATION_TYPE("Impossible d'appliquer %s sur les valeurs de type \033[0;34m %s \u001B[0m et \033[0;34m %s \u001B[0m"),
    BAD_ARRAY_INIT_VALUE("La valeur d'initialisation est attendue de type \033[0;34m %s\u001B[0m, obtenu \033[0;34m %s \u001B[0m"),
    ARRAY_TYPE("%s"), //la coloration de l'affichage a été ajouté dans ControlesSemantiques.java dans la méthode visit(ArrayAccess node)
    NO_FUNCTION_TYPE("La variable \033[0;35m %s \u001B[0m n'est pas une fonction"),

    NO_RECORD_TYPE("La variable de type \033[0;34m %s \u001B[0m n'est pas un enregistrement"),
    BAD_ARGUMENT_NUMBER("Le nombre d'arguments fournis (\033[0;35m%s\u001B[0m) ne correspond pas au nombre d'arguments attendus (\033[0;35m%s\u001B[0m)"),
    BAD_ARGUMENT_TYPE("Le type \033[0;34m %s \u001B[0m du paramètre \033[0;35m %s \u001B[0m ne correspond pas au type \033[0;34m %s \u001B[0m attendu"),
    INEXISTING_FIELD("Le champ \033[0;35m %s \u001B[0m n'existe pas dans l'enregistrement \033[0;35m %s \u001B[0m de type \033[0;34m %s \u001B[0m"),
    DUPLICATE_FIELD("Impossible d'affecter plusieurs fois le champ \033[0;35m %s \u001B[0mde l'enregistrement \033[0;35m %s \u001B[0m de type \033[0;34m %s \u001B[0m"),
    MISSING_FIELD("Le champ \033[0;35m %s \u001B[0m n'a pas été affecté dans l'enregistrement \033[0;35m %s \u001B[0m de type \033[0;34m %s \u001B[0m"),
    ZERO_DIV("Division par une constante nulle"),
    ;

    public final int nbArg;
    public final String message;
    /*-----------Colors------------*/
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BOLD = "\033[1;31m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static final String ANSI_purple = "\033[0;35m";

    private Errors(String label) {
        this.message = label;
        this.nbArg = StringUtils.countMatches(message, "%s");
    }
}
