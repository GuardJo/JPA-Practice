package com.example.jpa.source.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(identifier);
    }

    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(identifier);
    }

    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(identifier);
    }

    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(identifier);
    }

    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(identifier);
    }

    private Identifier convertToSnakeCase(final Identifier identifier) {
        if (identifier == null) {
            return null;
        }

        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = identifier.getText()
                .replaceAll(regex, replacement)
                .toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
