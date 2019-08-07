package com.daikit.graphql.constants;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;

import com.daikit.graphql.utils.Message;

import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

/**
 * Custom scalar special for java
 *
 * @author tcaselli
 */
public class GQLJavaScalars {

	/**
	 * GraphQL type for {@link LocalDateTime}
	 */
	public static GraphQLScalarType GraphQLLocalDateTime = GraphQLScalarType.newScalar().name("LocalDateTime")
			.description("Date type").coercing(new Coercing<Object, Object>() {
				@Override
				public Object serialize(final Object input) {
					if (input == null) {
						return null;
					} else if (input instanceof String) {
						return parseStringToLocalDateTime((String) input);
					} else if (input instanceof LocalDateTime) {
						return input;
					} else if (input instanceof Long) {
						return parseLongToLocalDateTime((Long) input);
					} else if (input instanceof Integer) {
						return parseLongToLocalDateTime((Integer) input);
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be serialized from LocalDateTime [{}]", input));
					}
				}

				@Override
				public Object parseValue(final Object input) {
					return serialize(input);
				}

				@Override
				public Object parseLiteral(final Object input) {
					if (input instanceof StringValue) {
						return parseStringToLocalDateTime(((StringValue) input).getValue());
					} else if (input instanceof IntValue) {
						final BigInteger value = ((IntValue) input).getValue();
						return parseLongToLocalDateTime(value.longValue());
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be parsed to LocalDateTime [{}]", input));
					}
				}

				private LocalDateTime parseLongToLocalDateTime(final long input) {
					return LocalDateTime.ofInstant(Instant.ofEpochSecond(input), TimeZone.getDefault().toZoneId());
				}

				private LocalDateTime parseStringToLocalDateTime(final String input) {
					try {
						return LocalDateTime.parse(input);
					} catch (final DateTimeParseException e) {
						throw new IllegalArgumentException(
								Message.format("Input cannot be parsed to LocalDateTime [{}]", input), e);
					}
				}
			}).build();

	/**
	 * GraphQL type for {@link LocalDate}
	 */
	public static GraphQLScalarType GraphQLLocalDate = GraphQLScalarType.newScalar().name("LocalDate")
			.description("Date type").coercing(new Coercing<Object, Object>() {
				@Override
				public Object serialize(final Object input) {
					if (input instanceof String) {
						return parseStringToLocalDate((String) input);
					} else if (input instanceof LocalDate) {
						return input;
					} else if (input instanceof Long) {
						return parseLongToLocalDate((Long) input);
					} else if (input instanceof Integer) {
						return parseLongToLocalDate((Integer) input);
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be serialized from LocalDate [{}]", input));
					}
				}

				@Override
				public Object parseValue(final Object input) {
					return serialize(input);
				}

				@Override
				public Object parseLiteral(final Object input) {
					if (input instanceof StringValue) {
						return parseStringToLocalDate(((StringValue) input).getValue());
					} else if (input instanceof IntValue) {
						final BigInteger value = ((IntValue) input).getValue();
						return parseLongToLocalDate(value.longValue());
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be parsed to LocalDate [{}]", input));
					}
				}

				private LocalDate parseLongToLocalDate(final long input) {
					return LocalDateTime.ofInstant(Instant.ofEpochSecond(input), TimeZone.getDefault().toZoneId())
							.toLocalDate();
				}

				private LocalDate parseStringToLocalDate(final String input) {
					try {
						return LocalDate.parse(input);
					} catch (final DateTimeParseException e) {
						throw new IllegalArgumentException(
								Message.format("Input cannot be parsed to LocalDate [{}]", input), e);
					}
				}
			}).build();

	/**
	 * GraphQL type for {@link Date}
	 */
	public static GraphQLScalarType GraphQLDate = GraphQLScalarType.newScalar().name("Date").description("Date type")
			.coercing(new Coercing<Object, Object>() {

				@Override
				public Object serialize(final Object input) {
					if (input instanceof String) {
						return parseStringToDate((String) input);
					} else if (input instanceof Date) {
						return input;
					} else if (input instanceof Long) {
						return new Date(((Long) input).longValue());
					} else if (input instanceof Integer) {
						return new Date(((Integer) input).longValue());
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be serialized from Date [{}]", input));
					}
				}

				@Override
				public Object parseValue(final Object input) {
					return serialize(input);
				}

				@Override
				public Object parseLiteral(final Object input) {
					if (input instanceof StringValue) {
						return parseStringToDate(((StringValue) input).getValue());
					} else if (input instanceof IntValue) {
						final BigInteger value = ((IntValue) input).getValue();
						return new Date(value.longValue());
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be parsed to Date [{}]", input));
					}
				}

				private Date parseStringToDate(final String input) {
					try {
						return input == null ? null : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(input);
					} catch (final ParseException e) {
						throw new IllegalArgumentException(Message.format("Input cannot be parsed to Date [{}]", input),
								e);
					}
				}
			}).build();

	/**
	 * GraphQL type for Upload multipart file
	 */
	public static GraphQLScalarType GraphQLFile = GraphQLScalarType.newScalar().name("File").description("File")
			.coercing(new Coercing<Object, Object>() {

				@Override
				public Object serialize(final Object input) {
					Object serialized;
					if (input == null) {
						serialized = null;
					} else if (input instanceof File) {
						serialized = ((File) input).getName();
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be serialized from File [{}]", input));
					}
					return serialized;
				}

				@Override
				public Object parseValue(final Object input) {
					Object parsed;
					if (input == null) {
						parsed = null;
					} else if (input instanceof File) {
						parsed = input;
					} else {
						throw new IllegalArgumentException(
								Message.format("Input cannot be parsed to File [{}]", input));
					}
					return parsed;
				}

				@Override
				public Object parseLiteral(final Object input) {
					throw new IllegalArgumentException(Message.format("Input cannot be parsed to File [{}]", input));
				}
			}).build();
}
