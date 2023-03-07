package me.s3ns3iw00.jcommands.argument.type;

import org.javacord.api.entity.Attachment;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An argument that only accepts attachments
 *
 * @author S3nS3IW00
 */
public class AttachmentArgument extends ValueArgument {

    private final Set<String> extensions = new HashSet<>();
    private Integer maxSize = Integer.MAX_VALUE;

    public AttachmentArgument(String name, String description) {
        super(name, description, SlashCommandOptionType.ATTACHMENT, Attachment.class);
    }

    /**
     * Sets the valid extensions for the attachment
     *
     * @param validExtensions the valid extensions
     */
    public void setValidExtensions(String... validExtensions) {
        extensions.addAll(Arrays.asList(validExtensions));
    }

    /**
     * Sets the maximum allowed size for the attachment in bytes
     *
     * @param maxSize the max size in bytes
     */
    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Sets the maximum allowed size for the attachment in megabytes
     * <br>
     * The size will be converted to bytes (multiplying by 1000 two times) and explicit converted to integer,
     * therefore the maximum value could be 2147.483647
     *
     * @param maxSizeInMB the max size in megabytes
     */
    public void setMaxSizeInMB(Double maxSizeInMB) {
        this.maxSize = (int) (maxSizeInMB * 1000 * 1000);
    }

    /**
     * Validates the extension and size of the attachment
     *
     * @param value the value
     * @return is the attachment valid or not
     */
    @Override
    public boolean isValid(Object value) {
        if (super.isValid(value)) {
            Attachment attachment = (Attachment) value;
            Optional<String> fileExtension = findExtension(attachment.getFileName());
            return (extensions.isEmpty() || (fileExtension.isPresent() && extensions.contains(fileExtension.get()))) &&
                    attachment.getSize() <= maxSize;
        }
        return false;
    }

    /**
     * Find the extension of an attachment
     *
     * @param fileName the name of the attachment
     * @return {@link Optional#empty()} when no extension found else {@link Optional#of(Object)} with the extension
     */
    private Optional<String> findExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(fileName.substring(lastIndex + 1));
    }
}
