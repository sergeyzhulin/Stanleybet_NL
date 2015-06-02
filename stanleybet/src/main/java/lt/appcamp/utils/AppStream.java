/**
 * 
 */

package lt.appcamp.utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Vykintas Valkaitis
 */
public class AppStream {
    /** tag for logs */
    protected static final String TAG = AppStream.class.getSimpleName();

    /** stream buffer size */
    protected static final int BUFFER_SIZE = 1024;

    /**
     * Safe copy stream from input to output
     * 
     * @param input - input stream
     * @param output - output stream
     */
    public static void copy(InputStream input, OutputStream output) {
        try {
            byte[] bytes = new byte[BUFFER_SIZE];
            while (true) {
                int count = input.read(bytes, 0, BUFFER_SIZE);
                if (count == -1)
                    break;
                output.write(bytes, 0, count);
            }
        } catch (Exception e) {
            AppLog.e( e);
        }
    }
}
