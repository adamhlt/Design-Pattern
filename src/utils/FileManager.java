package utils;

import java.io.File;

/**
 * Class using to manage file
 *
 * @version 1.0
 */
public class FileManager {
    /**
     * Get extension of file
     *
     * @param file a specific File
     * @return A string representing the extension
     */
    public static String getFileExtension( File file ) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf( "." );
        return ( lastIndexOf != -1 ) ? name.substring( lastIndexOf ) : "";
    }
}
