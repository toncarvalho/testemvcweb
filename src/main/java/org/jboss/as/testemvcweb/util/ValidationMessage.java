package org.jboss.as.testemvcweb.util;

import java.io.Serializable;

/**
 * Classe que contém valores constantes mensagens emitidas pelo sistema.
 */
public class ValidationMessage implements Serializable {

    public static final String UNIQUE = "Já existe";

    public static final String NOT_NULL = "Campo obrigatório";

    public static final String SIZE = "Deve conter no mínimo {min} e no máximo {max} caracteres";
    public static final String SIZE_MAX = "Deve conter no máximo {max} caracteres";
    public static final String SIZE_FIXED = "Deve conter {max} caracteres";

    public static final String EMAIL = "Email inválido";
    public static final String URL = "URL inválida";

    public static final String NON_NEGATIVE = "Não deve ser negativo";
    public static final String POSITIVE = "Deve ser maior que zero";

    public static final String MAX_LIMIT = "O valor deste campo não pode ser maior que {value}";
}
