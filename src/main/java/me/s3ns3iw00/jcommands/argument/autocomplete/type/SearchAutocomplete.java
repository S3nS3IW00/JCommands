package me.s3ns3iw00.jcommands.argument.autocomplete.type;

import me.s3ns3iw00.jcommands.argument.autocomplete.Autocomplete;
import me.s3ns3iw00.jcommands.argument.util.ArgumentState;
import me.s3ns3iw00.jcommands.argument.util.Choice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A type of {@link Autocomplete} that takes a datasource and returns a list of {@link Choice} based on user input, like a search bar
 * The minimum number of characters for searching can be limited, the result can be sorted, its number can be limited
 * <p>
 * NOTE: This is currently supports only {@link String} and {@link Number} type of datasource!
 * The datasource's type has been determined by the {@link SearchType}, and any other type of value in the datasource is ignored!
 */
public class SearchAutocomplete extends Autocomplete {

    private final SearchType searchType;
    private final List<Object> dataSource;
    private final SlashCommandOptionType dataType;

    /**
     * Settings properties
     */
    private boolean ignoreCase = false;
    private int limit = 5, minCharToSearch = 1;
    private SortType sortType;

    /**
     * Default constructor
     * <p>
     * The {@link SlashCommandOptionType} has determined by the {@link SearchType}
     *
     * @param searchType the search type
     * @param dataSource the datasource
     */
    public SearchAutocomplete(SearchType searchType, List<Object> dataSource) {
        this.searchType = searchType;
        this.dataSource = dataSource;
        this.dataType = searchType.getType();
    }

    /**
     * Ignores the case of characters in string values
     *
     * @return this
     */
    public SearchAutocomplete ignoreCase() {
        if (dataType != SlashCommandOptionType.STRING) {
            throw new IllegalArgumentException("This settings only can be applied to String search type");
        }

        this.ignoreCase = true;
        return this;
    }

    /**
     * Limits the number of result
     *
     * @param limit the limit
     * @return this
     */
    public SearchAutocomplete limit(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must be greater or equal to 1");
        }

        this.limit = limit;
        return this;
    }

    /**
     * Sorts the result by {@link SortType}
     *
     * @param sortType the type of sort
     * @return this
     */
    public SearchAutocomplete sort(SortType sortType) {
        this.sortType = sortType;
        return this;
    }

    /**
     * Sets the minimum number of character that the expression's length have to be
     *
     * @param minCharToSearch the minimum number of character
     * @return this
     */
    public SearchAutocomplete minCharToSearch(int minCharToSearch) {
        this.minCharToSearch = minCharToSearch;
        return this;
    }

    @Override
    public List<Choice> getResult(ArgumentState state) {
        if (state.getValue().toString().length() < minCharToSearch) return null;

        Stream<Choice> choices = dataSource.stream()
                .filter(data -> {
                    /* If the datasource's type is String and the data in the datasource and the argument's value is also String
                       Filters the datasource by the type of search and the input
                     */
                    if (dataType == SlashCommandOptionType.STRING && data instanceof String && state.getValue() instanceof String) {
                        String strData = (String) data;
                        String strValue = (String) state.getValue();

                        if (ignoreCase) {
                            strData = strData.toLowerCase();
                            strValue = strValue.toLowerCase();
                        }

                        if (searchType == SearchType.STARTS_WITH) {
                            return strData.startsWith(strValue);
                        } else if (searchType == SearchType.ENDS_WITH) {
                            return strData.endsWith(strValue);
                        } else if (searchType == SearchType.CONTAINS) {
                            return strData.contains(strValue);
                        }
                    /* If the datasource's type is Long and the data in the datasource is Number and the argument's value is also Long
                       Filters the datasource by the type of search and the input
                     */
                    } else if (dataType == SlashCommandOptionType.LONG && data instanceof Number && state.getValue() instanceof Long) {
                        Number lData = (Number) data;
                        long lValue = (long) state.getValue();

                        if (searchType == SearchType.GREATER_THAN) {
                            return lData.longValue() > lValue;
                        } else if (searchType == SearchType.LESS_THAN) {
                            return lData.longValue() < lValue;
                        }
                    }
                    return false;
                })
                .map(data -> new Choice(data.toString(), dataType == SlashCommandOptionType.STRING ? data : ((Number) data).longValue()))
                .limit(this.limit);

        /* If sort type is set sorts the result by the type of sort */
        if (sortType != null) {
            choices = choices.sorted(((o1, o2) -> {
                String strData = o1.getKey();
                String strValue = o2.getKey();

                if (sortType == SortType.ASCENDING) {
                    return String.CASE_INSENSITIVE_ORDER.compare(strData, strValue);
                } else if (sortType == SortType.DESCENDING) {
                    return String.CASE_INSENSITIVE_ORDER.compare(strValue, strData);
                }

                return -1;
            }));
        }

        return choices.collect(Collectors.toList());
    }

    /**
     * An enum that stores the possible search types with the corresponding {@link SlashCommandOptionType}
     */
    public enum SearchType {
        STARTS_WITH(SlashCommandOptionType.STRING),
        ENDS_WITH(SlashCommandOptionType.STRING),
        CONTAINS(SlashCommandOptionType.STRING),

        GREATER_THAN(SlashCommandOptionType.LONG),
        LESS_THAN(SlashCommandOptionType.LONG);


        private final SlashCommandOptionType type;

        SearchType(SlashCommandOptionType type) {
            this.type = type;
        }

        public SlashCommandOptionType getType() {
            return type;
        }
    }

    /**
     * An enum that stores the possible sort types
     */
    public enum SortType {
        ASCENDING, DESCENDING
    }
}
