package me.s3ns3iw00.jcommands.argument.util;

import me.s3ns3iw00.jcommands.argument.autocomplete.Autocomplete;

import java.util.List;

/**
 * Identifies whether an argument is autocompletable
 */
public interface Autocompletable {

    List<Autocomplete> getAutocompletes();

}
