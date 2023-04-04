package me.s3ns3iw00.jcommands.argument.type;

import me.s3ns3iw00.jcommands.argument.InputArgument;
import me.s3ns3iw00.jcommands.argument.validator.ArgumentValidation;
import me.s3ns3iw00.jcommands.event.listener.ArgumentMismatchEventListener;
import org.javacord.api.entity.Attachment;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Optional;
import java.util.Set;

/**
 * An argument that only accepts {@link Attachment} input
 *
 * @author S3nS3IW00
 */
public class AttachmentArgument<O> extends InputArgument<Attachment, O> {

    public AttachmentArgument(String name, String description, Class<O> resultType) {
        super(name, description, SlashCommandOptionType.ATTACHMENT, resultType);
    }

    /**
     * Creates an extension validation
     *
     * @param validExtensions the valid extensions
     * @return the {@link ArgumentValidation} to be able to specify a custom response with {@link ArgumentValidation#thenRespond(ArgumentMismatchEventListener)}
     */
    public ArgumentValidation<Attachment> whenInvalidExtension(Set<String> validExtensions) {
        return getArgumentValidator().when(attachment -> {
            Optional<String> extension = findExtension(attachment.getFileName());

            return extension.isEmpty() || !validExtensions.contains(extension.get());
        });
    }

    /**
     * Creates a size validation that limits maximum allowed size for the attachment in bytes
     *
     * @param maxSize the max size in bytes
     * @return the {@link ArgumentValidation} to be able to specify a custom response with {@link ArgumentValidation#thenRespond(ArgumentMismatchEventListener)}
     */
    public ArgumentValidation<Attachment> whenAboveMaxSize(int maxSize) {
        return getArgumentValidator().when(attachment -> attachment.getSize() >= maxSize);
    }

    /**
     * Creates a size validation that limits maximum allowed size for the attachment in megabytes
     * <br>
     * The size will be converted to bytes (multiplying by 1000 two times) and explicit converted to integer,
     * therefore the maximum value could be 2147.483647
     *
     * @param maxSizeInMB the max size in megabytes
     * @return the {@link ArgumentValidation} to be able to specify a custom response with {@link ArgumentValidation#thenRespond(ArgumentMismatchEventListener)}
     */
    public ArgumentValidation<Attachment> whenAboveMaxSizeInMB(double maxSizeInMB) {
        return whenAboveMaxSize((int) (maxSizeInMB * 1000 * 1000));
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

    /**
     * Sets the valid extensions for the attachment
     *
     * @param validExtensions the valid extensions
     * @deprecated use {@link AttachmentArgument#whenInvalidExtension(Set)}
     */
    @Deprecated
    public void setValidExtensions(String... validExtensions) {
    }

    /**
     * Sets the maximum allowed size for the attachment in bytes
     *
     * @param maxSize the max size in bytes
     * @deprecated use {@link AttachmentArgument#whenAboveMaxSize(int)}
     */
    @Deprecated
    public void setMaxSize(Integer maxSize) {
    }

    /**
     * Sets the maximum allowed size for the attachment in megabytes
     * <br>
     * The size will be converted to bytes (multiplying by 1000 two times) and explicit converted to integer,
     * therefore the maximum value could be 2147.483647
     *
     * @param maxSizeInMB the max size in megabytes
     * @deprecated use {@link AttachmentArgument#whenAboveMaxSizeInMB(double)}
     */
    @Deprecated
    public void setMaxSizeInMB(Double maxSizeInMB) {
    }
}
