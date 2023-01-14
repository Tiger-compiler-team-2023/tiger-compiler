package eu.tn.chaoscompiler.errors;

import org.codehaus.plexus.util.StringUtils;

public enum Errors {
    DEFAULT_ERROR("Erreur inconnue"),
    SYNTAX_ERROR("%s"),
    SEMANTIC_ERROR("%s"),
    UNRECOGNISED_ERROR("%s"),

    UNDECLARED_TYPE("Le type %s n'est pas déclaré"),
    ALREADY_DECLARED_TYPE("Le type %s est déjà déclaré"),
    UNAUTHORIZED_BREAK("Interdit d'utiliser break à l'extérieur des boucles For ou While"),
    UNDECLARED_VARIABLE("La variable %s n'est pas déclarée"),
    UNDECLARED_FUNCTION("La fonction %s n'est pas définie"),
    ALREADY_DECLARED("La variable %s est déjà déclarée"),

    PARAMETER_TYPE_NO_DECLARED("Le type %s du paramètre %s n'est pas défini"),
    ID_ALREADY_USED("L'identifiant %s est déjà localement utilisé"),
    INCOMPATIBLE_FUNCTION_TYPE("Le type de retour %s de la fonction %s est incompatible avec le type %s de la valeur retournée"),
    NO_VALUE_ERROR("%s n'a pas pu être initialisée (n'a pas de valeur)"),
    BAD_AFFECTATION_TYPE("Une valeur de type %s ne peut pas être affectée à une variable de type %s"),
    LOOP_TYPE("%s"),
    LOOP_COUNTER_AFFECT("l'indice %s de la boucle For ne doit pas être assigné à l'intérieur de la boucle"),

    INT_EXPECTED("Une valeur entière est attendue (type donné : %s)"),

    INCOMPATIBLES_THEN_ELSE("Les types %s et %s renvoyés par les expressions then et else ne sont pas compatibles"),
    THEN_WITH_TYPE("L'expression then est de type %s, or en l'absence de else, le type de l'expression if doit être void"),
    NO_VOID_WHILE("La boucle While ne peut pas retourner de valeur, obtenu %s"),
    INVALID_LVALUE("Le membre de gauche ne correspond pas à une variable, impossible d'y affecter une valeur"),

    BAD_AFFECT_TYPE("Une valeur de type %s ne peut pas être affectée à une variable de type %s"),
    INCOMPARABLE_TYPES("Les types %s et %s ne sont pas comparables"),
    BAD_NEGATION_TYPE("Impossible d'appliquer négation sur une valeur de type %s, attendu une valeur entière"),
    BAD_OPERATION_TYPE("Impossible d'appliquer une %s sur les valeurs de type %s et %s"),
    BAD_ARRAY_INIT_VALUE("La valeur d'initialisation est attendue de type %s, obtenu %s"),
    ARRAY_TYPE("%s"),
    NO_FUNCTION_TYPE("La variable %s n'est pas une fonction"),

    NO_RECORD_TYPE("La variable %s n'est pas un enregistrement"),
    BAD_ARGUMENT_NUMBER("Le nombre d'arguments fournis (%s) ne correspond pas au nombre d'arguments attendus (%s)"),
    BAD_ARGUMENT_TYPE("Le type %s du paramètre %s ne correspond pas au type %s attendu"),
    INEXISTING_FIELD("Le champ %s n'existe pas dans l'enregistrement %s"),
    DUPLICATE_FIELD("Impossible d'affecter plusieurs fois le champ %s de l'enregistrement %s"),
    MISSING_FIELD("Le champ %s n'a pas été affecté dans l'enregistrement %s"),
    ;

    public final int nbArg;
    public final String message;

    private Errors(String label) {
        this.message = label;
        this.nbArg = StringUtils.countMatches(message, "%s");
    }
}
